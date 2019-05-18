package demos.expmind.andromeda.data


/**
 * Main contract to follow for any component that interacts with videos on the data layer
 */
interface VideoDataSource {

    interface GetAllCallback {
        fun onSuccess(r: List<Video>)
        fun onError(errorMessage: String = "")
    }

    fun getAll(fromCategory: YoutubeChannels, callback: GetAllCallback)

}