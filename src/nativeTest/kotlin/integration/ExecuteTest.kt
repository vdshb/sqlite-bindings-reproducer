/*
 * Copyright (c) 2025 Vadim Shabanov.
 */

package integration

import bindings.Sqlite3Api
import bindings.Sqlite3ConnectHelper
import kotlinx.cinterop.toKString
import kotlinx.coroutines.test.runTest
import platform.posix.getenv
import kotlin.test.Test

class ExecuteTest {

    val api = Sqlite3Api()
    val sqlite3ConnectHelper = Sqlite3ConnectHelper(api)

    @Test
    fun should_establish_connection() = runTest {
        val tmpDir = getenv("TMPDIR")?.toKString() ?: getenv("TEMP")?.toKString() ?: getenv("TMP")?.toKString() ?: "/tmp"
        val testDbFile = "$tmpDir/test.db"
        val sqliteFlags = sqlite3ConnectHelper.generateConnectionFlagSet(
            isReadonly = false,
            createFileOnAbsent = true,
            asUri = false
        )
        val databasePointer = sqlite3ConnectHelper.openConnection(testDbFile, sqliteFlags, null)
//        sqlite3ConnectHelper.setUpConnectionForeignKeyConstraintAvailability(databasePointer, true)
//        sqlite3ConnectHelper.setUpConnectionLookaside(databasePointer, 128, 32)
    }

}