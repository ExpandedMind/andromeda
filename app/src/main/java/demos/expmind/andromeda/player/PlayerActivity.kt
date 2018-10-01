package demos.expmind.andromeda.player

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import demos.expmind.andromeda.Configurations
import demos.expmind.andromeda.R
import demos.expmind.andromeda.data.Caption
import kotlinx.android.synthetic.main.activity_player.*

/**
 * View layer in charge to load video and subtitles to the UI
 */
class PlayerActivity : YouTubeBaseActivity(), PlayerContract.View, YouTubePlayer.OnInitializedListener,
        LifecycleOwner {

    lateinit var presenter: PlayerViewModel
    var currentlySelectedId: String? = ""
    lateinit var playerView: YouTubePlayerView
    lateinit private var lifeCycleRegistry: LifecycleRegistry
    lateinit var adapter: CaptionAdapter

    companion object {
        val RECOVERY_DIALOG_REQUEST = 1
        val KEY_CURRENTLY_SELECTED_ID = "restore:selected_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        lifeCycleRegistry = LifecycleRegistry(this)
        playerView = findViewById(R.id.youtube_view)
        playerView.initialize(Configurations.YOUTUBE_DEVELOPER_KEY, this)
        presenter = PlayerViewModel(this)
        lifecycle.addObserver(presenter)
        adapter = CaptionAdapter()
        captionsRecycler.layoutManager = LinearLayoutManager(this)
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
            presenter.loadCurrentVideo()
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
