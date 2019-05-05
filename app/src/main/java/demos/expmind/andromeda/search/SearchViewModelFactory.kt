package demos.expmind.andromeda.search

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import demos.expmind.andromeda.data.VideoRepository
import java.lang.IllegalArgumentException

class SearchViewModelFactory(private val repository: VideoRepository) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}