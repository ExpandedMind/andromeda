package demos.expmind.andromeda.data

import demos.expmind.andromeda.data.remote.RemoteVideoDataSource
import demos.expmind.andromeda.data.remote.VideoCategory
import java.util.LinkedHashMap
import kotlin.collections.ArrayList
import kotlin.collections.List
import kotlin.collections.filterValues
import kotlin.collections.forEach
import kotlin.collections.isNotEmpty

/**
 * Concrete implementation to load videos from the data sources into a cache.
 */
class VideoRepository(val remoteSource: VideoDataSource) : VideoDataSource {

    /**
     * In memory storage for already downloaded videos
     */
    var cacheVideos: LinkedHashMap<String, Video> = LinkedHashMap()

    override fun get(ytID: String, callback: VideoDataSource.GetCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(fromCategory: VideoCategory, callback: VideoDataSource.GetAllCallback) {
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

            override fun onError() {
                callback.onError()
            }

        })
    }

    override fun search(query: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        var INSTANCE: VideoRepository? = null

        @JvmStatic
        fun getInstance(remoteSource: VideoDataSource) =
                INSTANCE ?: synchronized(VideoRepository::class.java) {
                    INSTANCE ?: VideoRepository(remoteSource).also { INSTANCE = it }
                }

        /**
         * Used to force class to create a new instance next time getInstance is called
         */
        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }

}