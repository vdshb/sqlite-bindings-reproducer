/*
 * Copyright (c) 2025 Vadim Shabanov.
 */

package bindings

actual data class DatabasePointer(val nativePointer: dynamic)

actual class Sqlite3Api {

    actual suspend fun SQLITE_OPEN_READONLY(): Int = TODO("js implementation is irrelevant")
    actual suspend fun SQLITE_OPEN_READWRITE(): Int = TODO("js implementation is irrelevant")
    actual suspend fun SQLITE_OPEN_CREATE(): Int = TODO("js implementation is irrelevant")
    actual suspend fun SQLITE_OPEN_URI(): Int = TODO("js implementation is irrelevant")
    actual suspend fun SQLITE_DBCONFIG_ENABLE_FKEY(): Int = TODO("js implementation is irrelevant")
    actual suspend fun SQLITE_DBCONFIG_LOOKASIDE(): Int = TODO("js implementation is irrelevant")

    actual suspend fun sqlite3_open_v2(filename: String, ppDb: Ref<DatabasePointer?>, flags: Int, zVfs: String?): Int = TODO("js implementation is irrelevant")

//    actual suspend fun sqlite3_db_config(db: DatabasePointer, op: Int, vararg variadicArguments: Any?): Int = callFunction("sqlite3_db_config", arrayOf(db.nativePointer, op, *variadicArguments))
//    actual suspend fun sqlite3_db_config(db: DatabasePointer, op: Int, vararg variadicArguments: Any?): Int = callFunction("sqlite3_db_config", arrayOf(db.nativePointer, op) + variadicArguments))
    actual suspend fun sqlite3_db_config(db: DatabasePointer, op: Int, arg1: Any?): Int = TODO("js implementation is irrelevant")
    actual suspend fun sqlite3_db_config(db: DatabasePointer, op: Int, arg1: Any?, arg2: Any?): Int = TODO("js implementation is irrelevant")
    actual suspend fun sqlite3_db_config(db: DatabasePointer, op: Int, arg1: Any?, arg2: Any?, arg3: Any?): Int = TODO("js implementation is irrelevant")

}
