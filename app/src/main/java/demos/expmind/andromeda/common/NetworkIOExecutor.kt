package demos.expmind.andromeda.common

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Useful to perform networking operations in a pool of thread handled automatically.
 */
const val THREAD_COUNT = 3

class NetworkIOExecutor : Executor {

    val executor = Executors.newFixedThreadPool(THREAD_COUNT)

    override fun execute(command: Runnable) {
        executor.execute(command)
    }
}