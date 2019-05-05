package demos.expmind.andromeda.common

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Useful to perform file system I/O operations in a dedicated thread
 */
class DiskIOExecutor : Executor {

    private val diskIO = Executors.newSingleThreadExecutor()

    override fun execute(command: Runnable) {
        diskIO.execute(command)
    }
}