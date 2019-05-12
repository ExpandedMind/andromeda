package demos.expmind.andromeda.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import demos.expmind.andromeda.data.Video
import demos.expmind.andromeda.data.VideoDataSource
import demos.expmind.andromeda.data.VideoRepository
import retrofit2.Call

class SearchViewModel(private val repository: VideoRepository) : ViewModel() {

    private val _foundVideos: MutableLiveData<List<Video>> = MutableLiveData()
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val _showError: MutableLiveData<Boolean> = MutableLiveData()
    private val _resultsNotFound: MutableLiveData<Boolean> = MutableLiveData()
    private var searchCall: Call<List<Video>>? = null

    fun searchVideos(queryString: String) {
        _isLoading.setValue(true)
        repository.search(queryString, object : VideoDataSource.SearchCallback {

            override fun onSuccess(r: List<Video>) {
                _foundVideos.setValue(r)
                _isLoading.setValue(false)
            }

            override fun onResultsNotFound() {
                _isLoading.setValue(false)
                _resultsNotFound.setValue(true)
            }

            override fun onError() {
                _isLoading.setValue(false)
                _showError.setValue(true)
            }
        })
    }


    fun getSearchResults(): LiveData<List<Video>> = _foundVideos

    fun isSearchInProgress(): LiveData<Boolean> = _isLoading

    fun showError(): LiveData<Boolean> = _showError

    override fun onCleared() {
        super.onCleared()
        searchCall?.cancel()
    }
}