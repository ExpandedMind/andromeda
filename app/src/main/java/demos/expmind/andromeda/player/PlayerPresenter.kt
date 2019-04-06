package demos.expmind.andromeda.player

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import com.google.android.youtube.player.YouTubePlayer
import demos.expmind.andromeda.BuildConfig
import demos.expmind.andromeda.data.Caption
import demos.expmind.andromeda.data.Video
import demos.expmind.andromeda.data.VideoCategory
import demos.expmind.andromeda.network.LyricsResponse
import demos.expmind.andromeda.network.LyricsService
import demos.expmind.network.ServiceGenerator
import demos.expmind.network.models.ApiKey
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    val lyricsService: LyricsService

    init {
        ServiceGenerator.setBaseUrl(BuildConfig.MUSIXMATCH_URL, ApiKey("apikey",
                BuildConfig.MUSIXMATCH_DEVELOPER_KEY))
        lyricsService = ServiceGenerator.createService(LyricsService::class.java)
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

        //TODO this is going to dissapear
//        lyricsService.search("light up the night", "symphony x").enqueue(object : Callback<LyricsResponse> {
//            override fun onResponse(call: Call<LyricsResponse>, response: Response<LyricsResponse>) {
//                if (response.isSuccessful()) {
//                    //check
//                    val lyricsDTO: LyricsResponse? = response.body();
//                    val lyricsText = lyricsDTO?.message?.body?.lyrics?.lyrics_body
//                    val hugeCaption: Caption = Caption(lyricsText ?: "Not available")
//                    view.setupSubtitles(listOf(hugeCaption))
//                } else {
//                    // handle request errors
//                    val statusCode = response.code()
//                }
//            }
//
//            override fun onFailure(call: Call<LyricsResponse>?, t: Throwable?) {
//                // handle execution failures like no internet connectivity
//                Log.e(TAG, "error on processing request")
//            }
//
//        })
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
