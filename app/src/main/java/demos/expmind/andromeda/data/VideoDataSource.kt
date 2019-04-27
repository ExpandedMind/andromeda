package demos.expmind.andromeda.data


/**
 * Main contract to follow for any component that interacts with videos on the data layer
 */
interface VideoDataSource {

    interface GetCallback {
        fun onSuccess(v: Video)
        fun onError()
    }

    interface GetAllCallback {
        fun onSuccess(r: List<Video>)
        fun onError(errorMessage: String = "")
    }

    interface SearchCallback {
        fun onSuccess(r: List<Video>)
        fun onResultsNotFound()
        fun onError()
    }

    fun get(ytID: String, callback: GetCallback)

    fun getAll(fromCategory: YoutubeChannels, callback: GetAllCallback)

    fun search(query: String, callback: SearchCallback)

}