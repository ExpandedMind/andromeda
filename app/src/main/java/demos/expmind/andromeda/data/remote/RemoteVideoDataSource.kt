package demos.expmind.andromeda.data.remote

import demos.expmind.andromeda.common.AppExecutors
import demos.expmind.andromeda.data.VideoDataMapper
import demos.expmind.andromeda.data.VideoDataSource
import demos.expmind.andromeda.data.VideoListDTO
import demos.expmind.andromeda.network.YoutubeService
import retrofit2.Response

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

    override fun getAll(category: VideoCategory, callback: VideoDataSource.GetAllCallback) {
        val callListVideos = service.listVideos(category.ytIndex)
        appExecutors.networkIO.execute {
            val response: Response<VideoListDTO> = callListVideos.execute()
            val videoResponse = response.body()
            if (response.isSuccessful && response.code() == 200 && videoResponse != null) {
                val videos = dataMapper.fromDTOtoDomain(videoResponse)
                appExecutors.mainThread.execute {
                    callback.onSuccess(videos)
                }
            } else appExecutors.mainThread.execute { callback.onError() }

        }
    }

    override fun search(query: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

/**
 * Hardcoded categories that belong official Youtube API
 */
enum class VideoCategory(val category: String, val ytIndex: String) {
    FILM("Film & Animation", "1"),
    VEHICLES("Autos & Vehicles", "2"),
    MUSIC("Music", "10"),
    PETS("Pets & Animals", "15"),
    SPORTS("Sports", "17"),
    MOVIES("Short Movies", "18"),
    TRAVEL("Travel & Events", "19"),
    GAMING("Gaming", "20")
}