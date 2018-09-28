package demos.expmind.andromeda.player

/**
 * Base definition for any view components inside Model View Presenter architecture.
 */
interface MvpView {
    fun showLoadingIndicator(isLoading: Boolean)
}