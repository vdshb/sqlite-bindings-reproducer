/*
 * Copyright (c) 2025 Vadim Shabanov.
 */

package bindings

class Sqlite3ConnectHelper(
    val api: Sqlite3Api,
) {

    suspend fun generateConnectionFlagSet(
        isReadonly: Boolean,
        createFileOnAbsent: Boolean,
        asUri: Boolean,
    ): Int {
        var result = if (isReadonly) api.SQLITE_OPEN_READONLY() else api.SQLITE_OPEN_READWRITE()
        if (createFileOnAbsent) {
            result = result or api.SQLITE_OPEN_CREATE()
        }
        if (asUri) {
            result = result or api.SQLITE_OPEN_URI()
        }
        return result
    }

    suspend fun openConnection(filePath: String, sqliteFlags: Int, zVfs: String?): DatabasePointer {
        val databasePointerRef = Ref<DatabasePointer?>(null)
        api.sqlite3_open_v2(filePath, databasePointerRef, sqliteFlags, zVfs)
        val databasePointer = databasePointerRef.value ?: throw RuntimeException("Can't open database (filePath=$filePath; sqliteFlags=$sqliteFlags)")
        return databasePointer
    }

    suspend fun setUpConnectionLookaside(
        databasePointer: DatabasePointer,
        lookasideBufferSlotSize: Int,
        lookasideBufferSlotCount: Int
    ): Int {
        return api.sqlite3_db_config(
            databasePointer,
            api.SQLITE_DBCONFIG_LOOKASIDE(),
            null,
            lookasideBufferSlotSize,
            lookasideBufferSlotCount
        )
    }

    suspend fun setUpConnectionForeignKeyConstraintAvailability(
        databasePointer: DatabasePointer,
        foreignKeyEnabled: Boolean,
    ): Int {
        return api.sqlite3_db_config(
            databasePointer,
            api.SQLITE_DBCONFIG_ENABLE_FKEY(),
            if (foreignKeyEnabled) 1 else 0,
        )
    }

}