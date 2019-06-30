package demos.expmind.andromeda.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import demos.expmind.andromeda.data.VideoRepository
import demos.expmind.andromeda.data.YoutubeChannels
import demos.expmind.andromeda.network.YoutubeService
import javax.inject.Inject

class WelcomeViewModelFactory @Inject constructor(val videoRepository: VideoRepository) :
        ViewModelProvider.NewInstanceFactory() {

    lateinit var category: YoutubeChannels

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VideosByCategoryViewModel(category, videoRepository) as T
    }
}