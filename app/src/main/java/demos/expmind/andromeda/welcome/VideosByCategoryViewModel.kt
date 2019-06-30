package demos.expmind.andromeda.welcome

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import demos.expmind.andromeda.data.DownloadStatus
import demos.expmind.andromeda.data.Result
import demos.expmind.andromeda.data.Video
import demos.expmind.andromeda.data.VideoDataSource
import demos.expmind.andromeda.data.VideoRepository
import demos.expmind.andromeda.data.YoutubeChannels
import demos.expmind.andromeda.network.YoutubeService

/**
 * ViewModel layer for welcome feature.
 * It holds all observable necessary to feed welcome screen (top video lists, categories)
 */
class VideosByCategoryViewModel(val category: YoutubeChannels,
                                val videoRepository: VideoRepository) : ViewModel() {

    val TAG: String = VideosByCategoryViewModel::class.java.simpleName
    private val _downloadStatus: MutableLiveData<DownloadStatus> = MutableLiveData()
    private val _categoryVideos: MutableLiveData<Result<List<Video>>> = MutableLiveData()

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


