package demos.expmind.andromeda.common

import java.util.concurrent.Executor

/**
 * Useful class to access different executors to perform certain tasks in corresponding threads.
 */
open class AppExecutors @JvmOverloads constructor(val diskIO: Executor = DiskIOExecutor(),
                   val networkIO: Executor = NetworkIOExecutor(),
                   val mainThread: Executor = MainUIExecutor())