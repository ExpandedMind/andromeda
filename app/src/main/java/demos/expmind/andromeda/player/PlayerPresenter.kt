package demos.expmind.andromeda.player

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.google.android.youtube.player.YouTubePlayer

/**
 * Initializes and controls related operations to a {@link YouTubePlayer}.
 * Coordinates player with events coming from view layer.
 */
class PlayerPresenter(val view: PlayerContract.View)
    : ViewModel(), PlayerContract.Presenter, LifecycleObserver {

    var player: YouTubePlayer? = null
        set(value) {
            field = value
            field?.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT)
            field?.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL)
            //For additional control on player, we can assign
            // PlaylistEventListener, PlaybackEventListener, PlayerStateChangeListener
        }
    private var selectedVideoId = "OkO2lWmInr4"

    companion object {
        val TAG = PlayerPresenter::class.java.simpleName
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun start() {
        Log.d(TAG, "initializing Youtube service ...")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun lifeCycleOwnerDestroyed() {
        Log.d(TAG, "host life cycle component destroyed")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun lifeCycleOwnerResumed() {
        Log.d(TAG, "reanuda reproduccion")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun lifeCycleOwnerPaused() {
        Log.d(TAG, "pausa video")
    }

    fun loadCurrentVideo() {
        player?.loadVideo(selectedVideoId)
    }

    override fun goToCaption(captionIndex: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun repeatMode(activated: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun syncSubtitlesMode(activated: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}