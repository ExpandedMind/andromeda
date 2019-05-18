package demos.expmind.andromeda.data.local

import demos.expmind.andromeda.common.AppExecutors
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
            callback.onSuccess(dataMapper.fromDbToDomain(queryResult))
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