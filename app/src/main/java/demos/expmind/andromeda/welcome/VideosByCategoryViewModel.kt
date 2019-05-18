package demos.expmind.andromeda.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log
import demos.expmind.andromeda.common.AppExecutors
import demos.expmind.andromeda.data.*
import demos.expmind.andromeda.data.remote.RemoteVideoDataSource
import demos.expmind.andromeda.network.YoutubeService

/**
 * ViewModel layer for welcome feature.
 * It holds all observable necessary to feed welcome screen (top video lists, categories)
 */
//TODO Injectar parametros de constructor a viewmodel
class VideosByCategoryViewModel(val category: YoutubeChannels,
                                youtubeService: YoutubeService) : ViewModel() {

    val TAG: String = VideosByCategoryViewModel::class.java.simpleName
    private val _downloadStatus: MutableLiveData<DownloadStatus> = MutableLiveData()
    private val _categoryVideos: MutableLiveData<Result<List<Video>>> = MutableLiveData()
    val videoRepository: VideoRepository

    init {
        val remoteDataSource: RemoteVideoDataSource =
                RemoteVideoDataSource.getInstance(AppExecutors(), youtubeService)
        videoRepository = VideoRepository(remoteDataSource)
    }

    fun loadVideos() {
        _downloadStatus.setValue(DownloadStatus.LOADING)
        videoRepository.getAll(category, object : VideoDataSource.GetAllCallback {
            override fun onSuccess(r: List<Video>) {
                _categoryVideos.setValue(Result(r))
                _downloadStatus.setValue(DownloadStatus.DOWNLOADED)
            }

            override fun onError(errorMessage: String) {
                Log.e(TAG, "Error downloading videos")
                _categoryVideos.setValue(Result(listOf(), false, errorMessage))
                _downloadStatus.setValue(DownloadStatus.DOWNLOADED)
            }
        })
    }

    fun getLoadingStatus(): LiveData<DownloadStatus> = _downloadStatus

    fun getLoadedVideos(): LiveData<Result<List<Video>>> = _categoryVideos

}

