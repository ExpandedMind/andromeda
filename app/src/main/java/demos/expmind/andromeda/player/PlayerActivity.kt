package demos.expmind.andromeda.player

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import dagger.android.AndroidInjection
import demos.expmind.andromeda.BuildConfig
import demos.expmind.andromeda.R
import demos.expmind.andromeda.data.Caption
import demos.expmind.andromeda.data.Video
import kotlinx.android.synthetic.main.activity_player.*
import javax.inject.Inject

/**
 * View layer in charge to load video and subtitles to the UI
 */
class PlayerActivity : YouTubeBaseActivity(), PlayerContract.View, YouTubePlayer.OnInitializedListener,
        LifecycleOwner {

    @Inject
    lateinit var presenter: PlayerPresenter

    var currentlySelectedId: String? = ""
    //FIXME THIS UI state should be wrapped by VIEWModel
    lateinit var selectedVideo: Video
    lateinit var playerView: YouTubePlayerView
    lateinit private var lifeCycleRegistry: LifecycleRegistry
    lateinit var adapter: CaptionAdapter

    companion object {
        val RECOVERY_DIALOG_REQUEST = 1
        val KEY_CURRENTLY_SELECTED_ID = "restore:selected_id"
        val SELECTED_VIDEO = "EXTRA:SELECTED:VIDEO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        lifeCycleRegistry = LifecycleRegistry(this)
        selectedVideo = intent.getParcelableExtra(SELECTED_VIDEO)
        currentlySelectedId = selectedVideo.ytID
        playerView = findViewById(R.id.youtube_view)
        playerView.initialize(BuildConfig.YOUTUBE_DEVELOPER_KEY, this)
        lifecycle.addObserver(presenter)
        adapter = CaptionAdapter()
        captionsRecycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        captionsRecycler.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        captionsRecycler.emptyView = findViewById(R.id.empty_captions_view)
        captionsRecycler.adapter = adapter
        lifeCycleRegistry.markState(Lifecycle.State.CREATED)
    }

    override fun onSaveInstanceState(state: Bundle?) {
        super.onSaveInstanceState(state)
        state?.putString(KEY_CURRENTLY_SELECTED_ID, currentlySelectedId)
    }

    override fun onRestoreInstanceState(state: Bundle?) {
        super.onRestoreInstanceState(state)
        currentlySelectedId = state?.getString(KEY_CURRENTLY_SELECTED_ID)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            presenter.start()
        }
    }

    override fun onStart() {
        super.onStart()
        lifeCycleRegistry.markState(Lifecycle.State.STARTED)
    }

    override fun onDestroy() {
        lifeCycleRegistry.markState(Lifecycle.State.DESTROYED)
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        lifeCycleRegistry.markState(Lifecycle.State.RESUMED)
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider,
                                         youtubePlayer: YouTubePlayer, wasRestored: Boolean) {
        presenter.player = youtubePlayer
        if (!wasRestored) {
            presenter.loadVideo(selectedVideo)
        }
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider, errorReason: YouTubeInitializationResult) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show()
        } else {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show()
        }
    }

    override fun showLoadingIndicator(isLoading: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun focusCaption(captionIndex: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setupSubtitles(captionList: List<Caption>) {
        adapter.loadItems(captionList)
    }


    override fun getLifecycle(): Lifecycle = lifeCycleRegistry

}
