package demos.expmind.andromeda.player

import com.google.android.youtube.player.YouTubeInitializationResult

/**
 * Defines all operations related to custom youtube player.
 */
interface PlayerContract {

    interface View : MvpView {
        fun focusCaption(captionIndex: Int)
        fun showYoutubeErrorDialog(errorResult: YouTubeInitializationResult)
        fun showUnrecoverableYoutubePlayerError(errorMsg: String)
    }

    interface Presenter : MvpPresenter {
        fun goToCaption(captionIndex: Int)
        fun repeatMode(activated: Boolean)
        fun syncSubtitlesMode(activated: Boolean)
    }
}