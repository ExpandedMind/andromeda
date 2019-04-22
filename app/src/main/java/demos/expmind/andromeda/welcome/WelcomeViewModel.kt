package demos.expmind.andromeda.welcome

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import demos.expmind.andromeda.common.AppExecutors
import demos.expmind.andromeda.data.Video
import demos.expmind.andromeda.data.YoutubeChannels
import demos.expmind.andromeda.data.VideoDataSource
import demos.expmind.andromeda.data.VideoRepository
import demos.expmind.andromeda.data.remote.RemoteVideoDataSource
import demos.expmind.andromeda.network.YoutubeService

/**
 * ViewModel layer for welcome feature.
 * It holds all observable necessary to feed welcome screen (top video lists, categories)
 */
//TODO Injectar parametros de constructor a viewmodel
class WelcomeViewModel(val category: YoutubeChannels,
                       youtubeService: YoutubeService) : ViewModel() {

    val TAG: String = WelcomeViewModel::class.java.simpleName
    var todayVideos: MutableLiveData<List<Video>> = MutableLiveData()
    val videoRepository: VideoRepository

    init {
        val remoteDataSource: RemoteVideoDataSource =
                RemoteVideoDataSource.getInstance(AppExecutors(), youtubeService)
        videoRepository = VideoRepository.getInstance(remoteDataSource)
    }

    fun getTodayVideos(): LiveData<List<Video>> {
        if (todayVideos.value == null) {
            todayVideos = MutableLiveData()
            videoRepository.getAll(category, object : VideoDataSource.GetAllCallback {
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