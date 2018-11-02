package demos.expmind.andromeda.common

import java.util.concurrent.Executor

/**
 * Runs on the same caller thread
 */
class DirectExecutor : Executor {
    override fun execute(command: Runnable) {
        command.run()
    }
}