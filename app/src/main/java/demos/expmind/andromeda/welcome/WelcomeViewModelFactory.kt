package demos.expmind.andromeda.welcome

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import demos.expmind.andromeda.data.YoutubeChannels
import demos.expmind.andromeda.network.YoutubeService

class WelcomeViewModelFactory(val category: YoutubeChannels, val service: YoutubeService) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VideosByCategoryViewModel(category, service) as T
    }
}