package demos.expmind.andromeda.common

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

/**
 * Used to perform any UI related work on the Android Main UI thread.
 */
class MainUIExecutor : Executor {

    val handler: Handler = Handler(Looper.getMainLooper())

    override fun execute(command: Runnable) {
        handler.post(command)
    }
}