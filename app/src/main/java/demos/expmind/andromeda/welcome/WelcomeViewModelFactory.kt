package demos.expmind.andromeda.welcome

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import demos.expmind.andromeda.data.VideoCategory

class WelcomeViewModelFactory(val category: VideoCategory) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WelcomeViewModel(category) as T
    }
}