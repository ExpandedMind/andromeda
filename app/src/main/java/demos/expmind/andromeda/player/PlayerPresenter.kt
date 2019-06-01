package demos.expmind.andromeda.player

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import android.util.Log
import com.google.android.youtube.player.YouTubePlayer
import demos.expmind.andromeda.data.TranscriptDTO
import demos.expmind.andromeda.data.TranscriptDataMapper
import demos.expmind.andromeda.data.Video
import demos.expmind.andromeda.network.CaptionsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Initializes and controls related operations to a {@link YouTubePlayer}.
 * Coordinates player with events coming from view layer.
 */
@SuppressWarnings("Deprecated")
class PlayerPresenter  @Inject constructor(val service: CaptionsService)
    : PlayerContract.Presenter, LifecycleObserver {

    lateinit var view: PlayerContract.View

    var player: YouTubePlayer? = null
        set(value) {
            field = value
            field?.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT)
            field?.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
            //For additional control on player, we can assign
            // PlaylistEventListener, PlaybackEventListener, PlayerStateChangeListener
        }

    var transcriptCall: Call<TranscriptDTO>? = null

    val mapper: TranscriptDataMapper = TranscriptDataMapper()


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun start() {
        Log.d(TAG, "lifecycle owner starting")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun lifeCycleOwnerDestroyed() {
        Log.d(TAG, "host life cycle component destroyed")
        player?.release()
        transcriptCall?.cancel()
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
        transcriptCall = service.getTranscript(video.ytID, lang = video.category.lang)
        transcriptCall?.enqueue(object : Callback<TranscriptDTO> {
            override fun onResponse(call: Call<TranscriptDTO>, response: Response<TranscriptDTO>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "successul")
                    response.body()?.let {
                        view.setupSubtitles(mapper.fromDTOToModel(it))
                    }
                } else {
                    when(response.code()) {
                        404 -> Log.d(TAG, "TranscriptDTO not found for this video")
                        else -> Log.e(TAG, "something went wrong with request")
                    }
                }
            }

            override fun onFailure(call: Call<TranscriptDTO>, t: Throwable) {
                Log.d(TAG, "error", t)
                //TODO Notify to view that something has failed
            }

        })
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
