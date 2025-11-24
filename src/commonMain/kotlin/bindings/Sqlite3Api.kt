/*
 * Copyright (c) 2025 Vadim Shabanov.
 */

package bindings

expect class DatabasePointer

data class Ref<T>(var value: T)

expect class Sqlite3Api {

    suspend fun SQLITE_OPEN_READONLY(): Int
    suspend fun SQLITE_OPEN_READWRITE(): Int
    suspend fun SQLITE_OPEN_CREATE(): Int
    suspend fun SQLITE_OPEN_URI(): Int
    suspend fun SQLITE_DBCONFIG_ENABLE_FKEY(): Int
    suspend fun SQLITE_DBCONFIG_LOOKASIDE(): Int

    suspend fun sqlite3_open_v2(filename: String, ppDb: Ref<DatabasePointer?>, flags: Int, zVfs: String?): Int

    suspend fun sqlite3_db_config(db: DatabasePointer, op: Int, vararg variadicArguments: Any?): Int

}
