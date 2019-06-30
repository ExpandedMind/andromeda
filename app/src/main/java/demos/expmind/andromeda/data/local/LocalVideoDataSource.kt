package demos.expmind.andromeda.data.local

import demos.expmind.andromeda.common.AppExecutors
import demos.expmind.andromeda.data.Video
import demos.expmind.andromeda.data.VideoDataMapper
import demos.expmind.andromeda.data.VideoDataSource
import demos.expmind.andromeda.data.YoutubeChannels
import demos.expmind.andromeda.data.remote.RemoteVideoDataSource

class LocalVideoDataSource(val appExecutors: AppExecutors,
                           val videosDao: VideosDao,
                           val dataMapper: VideoDataMapper) : VideoDataSource {

    override fun getAll(fromCategory: YoutubeChannels, callback: VideoDataSource.GetAllCallback) {
        appExecutors.diskIO.execute {
            val queryResult = videosDao.filterByChannel(fromCategory)
            appExecutors.mainThread.execute {
                callback.onSuccess(dataMapper.fromDbToDomain(queryResult))
            }
        }
    }

    override fun store(newVideo: Video) {
        appExecutors.diskIO.execute {
            videosDao.insert(VideoEntity(newVideo))
        }
    }

    override fun store(newVideos: List<Video>) {
        appExecutors.diskIO.execute {
            videosDao.insertAll(newVideos.map { VideoEntity(it) })
        }
    }

    companion object {

        private var INSTANCE: LocalVideoDataSource? = null

        @JvmStatic
        fun getInstance(executors: AppExecutors, dao: VideosDao, dataMapper: VideoDataMapper): LocalVideoDataSource {
            if (INSTANCE == null) {
                synchronized(RemoteVideoDataSource::javaClass) {
                    INSTANCE = LocalVideoDataSource(executors, dao, dataMapper)
                }
            }
            return INSTANCE!!
        }
    }
}