/*
 * Copyright (c) 2025 Vadim Shabanov.
 */

package bindings

import cnames.structs.sqlite3
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CPointerVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

actual data class DatabasePointer(val nativePointer: CPointer<sqlite3>)

actual class Sqlite3Api {

    actual suspend fun SQLITE_OPEN_READONLY(): Int = bindings.sqlite3.SQLITE_OPEN_READONLY
    actual suspend fun SQLITE_OPEN_READWRITE(): Int = bindings.sqlite3.SQLITE_OPEN_READWRITE
    actual suspend fun SQLITE_OPEN_CREATE(): Int = bindings.sqlite3.SQLITE_OPEN_CREATE
    actual suspend fun SQLITE_OPEN_URI(): Int = bindings.sqlite3.SQLITE_OPEN_URI
    actual suspend fun SQLITE_DBCONFIG_ENABLE_FKEY(): Int = bindings.sqlite3.SQLITE_DBCONFIG_ENABLE_FKEY
    actual suspend fun SQLITE_DBCONFIG_LOOKASIDE(): Int = bindings.sqlite3.SQLITE_DBCONFIG_LOOKASIDE

    actual suspend fun sqlite3_open_v2(filename: String, ppDb: Ref<DatabasePointer?>, flags: Int, zVfs: String?): Int = memScoped {
        val nativePointerRef = alloc<CPointerVar<sqlite3>>()
        val result = bindings.sqlite3.sqlite3_open_v2(filename, nativePointerRef.ptr, flags, zVfs)
        ppDb.value = nativePointerRef.value?.let { bindings.DatabasePointer(it) }
        result
    }

    actual suspend fun sqlite3_db_config(db: DatabasePointer, op: Int, vararg variadicArguments: Any?): Int {
        when (variadicArguments.size) {
            1 -> return bindings.sqlite3.sqlite3_db_config(db.nativePointer, op, variadicArguments[0])
            2 -> return bindings.sqlite3.sqlite3_db_config(db.nativePointer, op, variadicArguments[0], variadicArguments[1])
            3 -> return bindings.sqlite3.sqlite3_db_config(db.nativePointer, op, variadicArguments[0], variadicArguments[1], variadicArguments[2])
            4 -> return bindings.sqlite3.sqlite3_db_config(db.nativePointer, op, variadicArguments[0], variadicArguments[1], variadicArguments[2], variadicArguments[3])
            else -> throw IllegalArgumentException("Number of params ${variadicArguments.size} is not supported for sqlite3_db_config")
        }
    }

}