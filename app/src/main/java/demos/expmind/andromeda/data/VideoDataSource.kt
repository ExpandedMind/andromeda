package demos.expmind.andromeda.data

/**
 * Main contract to follow for any component that interacts with videos on the data layer
 */
interface VideoDataSource {

    interface getCallback {
        fun onSuccess(v: Video)
        fun onError()
    }

    interface getAllCallback {
        fun onSuccess(r: List<Video>)
        fun onError()
    }

    interface searchCallback {
        fun onSuccess(r: List<Video>)
        fun onResultsNotFound()
        fun onError()
    }

    fun get(ytID: String)

    fun getAll(category: String? = null)

    fun searchVideos(query: String)

}