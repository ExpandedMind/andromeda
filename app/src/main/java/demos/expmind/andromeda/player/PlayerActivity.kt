package demos.expmind.andromeda.player

import android.content.Intent
import android.os.Bundle
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayerView
import demos.expmind.andromeda.R

/**
 * View layer in charge to load video and subtitles to the UI
 */
class PlayerActivity : YouTubeBaseActivity(), PlayerContract.View {

    lateinit var presenter: PlayerPresenter
    var currentlySelectedId: String? = ""
    lateinit var playerView: YouTubePlayerView

    companion object {
        val RECOVERY_DIALOG_REQUEST = 1
        val KEY_CURRENTLY_SELECTED_ID = "restore:selected_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        playerView = findViewById(R.id.youtube_view)
        presenter = PlayerPresenter(this, playerView)
        presenter.start()
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

    override fun showLoadingIndicator(isLoading: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun focusCaption(captionIndex: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showYoutubeErrorDialog(errorResult: YouTubeInitializationResult) {
        errorResult.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show()
    }

    override fun showUnrecoverableYoutubePlayerError(errorMsg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
