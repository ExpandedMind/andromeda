package demos.expmind.andromeda.player

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.google.android.youtube.player.YouTubePlayer
import demos.expmind.andromeda.data.Caption
import demos.expmind.andromeda.data.Video

/**
 * Initializes and controls related operations to a {@link YouTubePlayer}.
 * Coordinates player with events coming from view layer.
 */
class PlayerViewModel(val view: PlayerContract.View)
    : ViewModel(), PlayerContract.Presenter, LifecycleObserver {

    var player: YouTubePlayer? = null
        set(value) {
            field = value
            field?.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT)
            field?.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL)
            //For additional control on player, we can assign
            // PlaylistEventListener, PlaybackEventListener, PlayerStateChangeListener
        }
    private var currentVideo = Video("OkO2lWmInr4",
            listOf(Caption("Have you lost all your senses?"),
                    Caption("Why did you have to go tempting fate?"),
                    Caption("Blinded by your ambitions"),
                    Caption("Why can't you see the chaos you create?"),
                    Caption("Oh - have we gone too far, too fast?"),
                    Caption("My dying eyes - scream with pain"),
                    Caption("All my hopes and my dreams drown in flames"),
                    Caption("And all the Lies - shine so bright"),
                    Caption("All the souls of the world Light up the Night"),
                    Caption("You're a slave to desire"),
                    Caption("For lust of profit you consume my soul"),
                    Caption("Do you think you can make it stop?"),
                    Caption("Oh no - it's too late to turn, you've lost control"),
                    Caption("Oh - have we gone too far too fast?"),
                    Caption("My dying eyes - scream with pain"),
                    Caption("All my hopes and my dreams drown in flames"),
                    Caption("And all the Lies - shine so bright"),
                    Caption("All the souls of the world Light up the Night"),
                    Caption("Pushing the boundaries - reinvent a new reality"),
                    Caption("Changing Evolution - ignoring the past"),
                    Caption("As we realize our destructive destiny..."),
                    Caption("My dying eyes - scream with pain"),
                    Caption("All my hopes and my dreams drown in flames"),
                    Caption("And all the Lies - shine so bright"),
                    Caption("All the souls of the world Light up the Night")))


    companion object {
        val TAG = PlayerViewModel::class.java.simpleName
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

    fun loadCurrentVideo() {
        player?.loadVideo(currentVideo.ytID)
        view.setupSubtitles(currentVideo.subtitles)
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