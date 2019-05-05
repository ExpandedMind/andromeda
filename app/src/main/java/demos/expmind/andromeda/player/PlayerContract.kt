package demos.expmind.andromeda.player

import demos.expmind.andromeda.data.Caption

/**
 * Defines all operations related to custom youtube player.
 */
interface PlayerContract {

    interface View : MvpView {
        fun focusCaption(captionIndex: Int)
        fun setupSubtitles(captionList: List<Caption>)
    }

    interface Presenter : MvpPresenter {
        fun goToCaption(captionIndex: Int)
        fun repeatMode(activated: Boolean)
        fun syncSubtitlesMode(activated: Boolean)
    }
}