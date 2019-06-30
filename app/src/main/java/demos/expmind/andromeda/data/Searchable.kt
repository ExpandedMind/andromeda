package demos.expmind.andromeda.data

//TODO make it generic
interface Searchable {

    interface Callback {
        fun onSuccess(r: List<Video>)
        fun onResultsNotFound()
        fun onError()
    }

    fun search(query: String, callback: Callback)

}