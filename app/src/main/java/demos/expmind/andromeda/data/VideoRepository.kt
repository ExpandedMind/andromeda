package demos.expmind.andromeda.data

import demos.expmind.andromeda.data.remote.AbstractRemoteVideoDataSource
import java.util.LinkedHashMap
import kotlin.collections.ArrayList

/**
 * Concrete implementation to load videos from the data sources into a cache.
 */
class VideoRepository(val remoteSource: AbstractRemoteVideoDataSource) : VideoDataSource {

    /**
     * In memory storage for already downloaded videos
     */
    var cacheVideos: LinkedHashMap<String, Video> = LinkedHashMap()

    override fun getAll(fromCategory: YoutubeChannels, callback: VideoDataSource.GetAllCallback) {
        val videosFromCategory = cacheVideos.filterValues { it.category.equals(fromCategory) }
        // In case there are videos on cache, then return them immediately
        if (videosFromCategory.isNotEmpty()) {
            callback.onSuccess(ArrayList(videosFromCategory.values))
            return;
        }
        // If not, then download from remote source
        remoteSource.getAll(fromCategory, object : VideoDataSource.GetAllCallback {
            override fun onSuccess(r: List<Video>) {
                // update cache
                r.forEach {
                    cacheVideos.put(it.ytID, it)
                }
                callback.onSuccess(r)
            }

            override fun onError(errorMessage: String) {
                callback.onError(errorMessage)
            }
        })
    }

    fun search(query: String, callback: Searchable.Callback) {
        remoteSource.search(query, callback)
    }

}