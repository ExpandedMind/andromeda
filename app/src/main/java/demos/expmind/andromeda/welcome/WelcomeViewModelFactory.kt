package demos.expmind.andromeda.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import demos.expmind.andromeda.data.YoutubeChannels
import demos.expmind.andromeda.network.YoutubeService
import javax.inject.Inject

class WelcomeViewModelFactory @Inject constructor(val service: YoutubeService) :
        ViewModelProvider.NewInstanceFactory() {

    lateinit var category: YoutubeChannels

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VideosByCategoryViewModel(category, service) as T
    }
}