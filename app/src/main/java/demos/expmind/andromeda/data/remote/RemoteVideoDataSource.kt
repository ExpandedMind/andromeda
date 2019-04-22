package demos.expmind.andromeda.data.remote

import android.util.Log
import demos.expmind.andromeda.common.AppExecutors
import demos.expmind.andromeda.data.YoutubeChannels
import demos.expmind.andromeda.data.VideoDataMapper
import demos.expmind.andromeda.data.VideoDataSource
import demos.expmind.andromeda.data.VideoListDTO
import demos.expmind.andromeda.network.YoutubeService
import retrofit2.Response
import java.lang.Exception
import java.net.UnknownHostException

/**
 * Data source that hits server API to retrieve information.
 */
class RemoteVideoDataSource private constructor(val appExecutors: AppExecutors,
                                                val service: YoutubeService,
                                                val dataMapper: VideoDataMapper = VideoDataMapper())
    : VideoDataSource {

    companion object {

        private var INSTANCE: RemoteVideoDataSource? = null

        @JvmStatic
        fun getInstance(executors: AppExecutors, service: YoutubeService): RemoteVideoDataSource {
            if (INSTANCE == null) {
                synchronized(RemoteVideoDataSource::javaClass) {
                    INSTANCE = RemoteVideoDataSource(executors, service)
                }
            }
            return INSTANCE!!
        }
    }


    override fun get(ytID: String, callback: VideoDataSource.GetCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(fromCategory: YoutubeChannels, callback: VideoDataSource.GetAllCallback) {
        val callListVideos = service.listVideos(fromCategory.ytIndex)
        appExecutors.networkIO.execute {
            try {
                val response: Response<VideoListDTO> = callListVideos.execute()
                val videoResponse = response.body()
                if (response.isSuccessful && response.code() == 200 && videoResponse != null) {
                    val videos = dataMapper.fromDTOtoDomain(videoResponse, fromCategory)
                    appExecutors.mainThread.execute {
                        callback.onSuccess(videos)
                    }
                } else appExecutors.mainThread.execute { callback.onError() }
            } catch (uhe: Exception) {
                //TODO deal with no internet connenctions
                callback.onError()
                Log.e("RemoteDataSource", "No internet")
            }


        }
    }

    override fun search(query: String, callback: VideoDataSource.SearchCallback) {
        val searchCall = service.searchVideos(query)
        appExecutors.networkIO.execute {

            try {
                val response = searchCall.execute()
                if (response.isSuccessful && response.body() != null) {
                    val foundVideos = dataMapper.fromDTOtoDomain(response.body()!!, YoutubeChannels.BBC)
                    appExecutors.mainThread.execute {
                        callback.onSuccess(foundVideos)
                    }
                } else {
                    appExecutors.mainThread.execute {
                        callback.onResultsNotFound()
                    }
                }
            } catch (uhe: UnknownHostException) {
                callback.onError()
            }

        }
    }

}
