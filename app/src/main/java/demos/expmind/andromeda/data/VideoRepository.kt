package demos.expmind.andromeda.data

import androidx.annotation.VisibleForTesting
import demos.expmind.andromeda.data.remote.AbstractRemoteVideoDataSource
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Concrete implementation to load videos from the data sources into a cache.
 * Strategy to provide information:
 *  1. Look into memory
 *  2. Look into persistence layer
 *  3. Look into internet
 */
class VideoRepository @Inject constructor(val remoteSource: AbstractRemoteVideoDataSource,
                      val localSource: VideoDataSource) : VideoDataSource {

    /**
     * In memory storage for already downloaded videos
     */
    @VisibleForTesting
    val cachedVideos: MutableList<Video> = mutableListOf()

    override fun getAll(fromCategory: YoutubeChannels, callback: VideoDataSource.GetAllCallback) {
        // In case there are videos on cache, then return them immediately
        if (cachedVideos.isNotEmpty()) {
            callback.onSuccess(cachedVideos)
            return
        }

        localSource.getAll(fromCategory, object : VideoDataSource.GetAllCallback {
            override fun onSuccess(r: List<Video>) {
                if (r.isNotEmpty()) {
                    updateChache(r)
                    callback.onSuccess(cachedVideos)
                } else {
                    pullVideosFromCloud(fromCategory, callback)
                }
            }

            override fun onError(errorMessage: String) {
                callback.onError(errorMessage)
            }

        })

    }

    fun search(query: String, callback: Searchable.Callback) {
        remoteSource.search(query, callback)
    }

    private fun pullVideosFromCloud(fromCategory: YoutubeChannels, callback: VideoDataSource.GetAllCallback) {
        remoteSource.getAll(fromCategory, object : VideoDataSource.GetAllCallback {
            override fun onSuccess(r: List<Video>) {
                updateChache(r)
                localSource.store(r)
                callback.onSuccess(cachedVideos)
            }

            override fun onError(errorMessage: String) {
                callback.onError(errorMessage)
            }
        })
    }

    private fun updateChache(r: List<Video>) {
        cachedVideos.clear()
        cachedVideos.addAll(r)
    }

}