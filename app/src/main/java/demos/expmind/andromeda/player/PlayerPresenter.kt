package demos.expmind.andromeda.player

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import com.google.android.youtube.player.YouTubePlayer
import demos.expmind.andromeda.data.Video

/**
 * Initializes and controls related operations to a {@link YouTubePlayer}.
 * Coordinates player with events coming from view layer.
 */
class PlayerPresenter(val view: PlayerContract.View) : PlayerContract.Presenter, LifecycleObserver {

    var player: YouTubePlayer? = null
        set(value) {
            field = value
            field?.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT)
            field?.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL)
            //For additional control on player, we can assign
            // PlaylistEventListener, PlaybackEventListener, PlayerStateChangeListener
        }

    init {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun start() {
        Log.d(TAG, "lifecycle owner starting")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun lifeCycleOwnerDestroyed() {
        Log.d(TAG, "host life cycle component destroyed")
        player?.release()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun lifeCycleOwnerResumed() {
        player?.play()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun lifeCycleOwnerPaused() {
        player?.pause()
        Log.d(TAG, "pause video")
    }

    fun loadVideo(video: Video) {
        player?.loadVideo(video.ytID)
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

    companion object {
        val TAG = PlayerPresenter::class.java.simpleName
    }

}
