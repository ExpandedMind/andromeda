package demos.expmind.andromeda

import demos.expmind.andromeda.common.AppExecutors
import demos.expmind.andromeda.common.DirectExecutor

/**
 * Executor collection useful for testing.
 * All executors run over the test thread.
 */
class TestExecutors : AppExecutors(DirectExecutor(), DirectExecutor(), DirectExecutor())