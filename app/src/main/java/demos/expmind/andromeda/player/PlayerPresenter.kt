package demos.expmind.andromeda.player

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import demos.expmind.andromeda.Configurations

/**
 * Initializes and controls related operations to a {@link YouTubePlayer}.
 * Coordinates player with events coming from view layer.
 */
class PlayerPresenter(val view: PlayerContract.View, val youtubeProvider: YouTubePlayer.Provider)
    : PlayerContract.Presenter, YouTubePlayer.OnInitializedListener, LifecycleObserver {

    lateinit var player: YouTubePlayer
    private var selectedVideoId = "OkO2lWmInr4"

    companion object {
        val TAG = PlayerPresenter::class.java.simpleName
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun start() {
        Log.d(TAG, "initializing Youtube service ...")
        youtubeProvider.initialize(Configurations.YOUTUBE_DEVELOPER_KEY, this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun lifeCycleOwnerDestroyed() {
        Log.d(TAG, "host life cycle component destroyed")
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

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider,
                                         youtubePlayer: YouTubePlayer, wasRestored: Boolean) {
        player = youtubePlayer
        player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT)
        player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL)
        if (!wasRestored) {
            player.loadVideo(selectedVideoId)
        }
        //For additional control on player, we can assign
        // PlaylistEventListener, PlaybackEventListener, PlayerStateChangeListener
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider, errorReason: YouTubeInitializationResult) {
        if (errorReason.isUserRecoverableError()) {
            view.showYoutubeErrorDialog(errorReason)
        } else {
            view.showUnrecoverableYoutubePlayerError(errorReason.toString())
        }
    }
}