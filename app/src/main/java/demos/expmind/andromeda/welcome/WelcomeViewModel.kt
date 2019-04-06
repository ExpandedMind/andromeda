package demos.expmind.andromeda.welcome

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import demos.expmind.andromeda.BuildConfig
import demos.expmind.andromeda.common.AppExecutors
import demos.expmind.andromeda.data.Video
import demos.expmind.andromeda.data.VideoCategory
import demos.expmind.andromeda.data.VideoDataSource
import demos.expmind.andromeda.data.VideoRepository
import demos.expmind.andromeda.data.remote.RemoteVideoDataSource
import demos.expmind.andromeda.network.YoutubeService
import demos.expmind.network.ServiceGenerator
import demos.expmind.network.models.ApiKey

/**
 * ViewModel layer for welcome feature.
 * It holds all observable necessary to feed welcome screen (top video lists, categories)
 */
//TODO Injectar parametros de constructor a viewmodel
class WelcomeViewModel(val category: VideoCategory) : ViewModel() {

    val TAG: String = WelcomeViewModel::class.java.simpleName
    var todayVideos: MutableLiveData<List<Video>> = MutableLiveData()
    val videoRepository: VideoRepository

    init {
        //TODO checar como injectar este arbol de dependencias
        ServiceGenerator.setBaseUrl(BuildConfig.YOUTUBE_URL,
                ApiKey("key", BuildConfig.YOUTUBE_DEVELOPER_KEY))
        val youtubeService: YoutubeService = ServiceGenerator.createService(YoutubeService::class.java)
        val remoteDataSource: RemoteVideoDataSource =
                RemoteVideoDataSource.getInstance(AppExecutors(), youtubeService)
        videoRepository = VideoRepository.getInstance(remoteDataSource)
    }

    fun getTodayVideos(): LiveData<List<Video>> {
        if (todayVideos.value == null) {
            todayVideos = MutableLiveData<List<Video>>()
            videoRepository.getAll(category, object : VideoDataSource.GetAllCallback{
                override fun onSuccess(r: List<Video>) {
                    todayVideos.setValue(r)
                }

                override fun onError() {
                    Log.e(TAG, "Error downloading videos")
                }

            })
        }
        return todayVideos
    }

    override fun onCleared() {
        super.onCleared()
        //TODO clean up any repository pending calls
    }
}