package demos.expmind.andromeda.data

interface Searchable {

    interface Callback {
        fun onSuccess(r: List<Video>)
        fun onResultsNotFound()
        fun onError()
    }

    fun search(query: String, callback: Callback)

}