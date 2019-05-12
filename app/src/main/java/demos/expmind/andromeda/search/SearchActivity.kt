package demos.expmind.andromeda.search

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.AndroidInjection
import demos.expmind.andromeda.R
import demos.expmind.andromeda.data.Video
import demos.expmind.andromeda.player.PlayerActivity
import demos.expmind.andromeda.video.VideosAdapter
import javax.inject.Inject

class SearchActivity : AppCompatActivity(), VideosAdapter.VideoAdapterListener {

    private val adapter: VideosAdapter = VideosAdapter(this)

    @Inject
    lateinit var viewModelFactory: SearchViewModelFactory

    lateinit var viewModel: SearchViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupUI()
        observeViewModel()
        searchVideos()
    }

    private fun searchVideos() {
        val searchQuery: String? = intent.getStringExtra(EXTRA_QUERY)
        searchQuery?.also {
            viewModel.searchVideos(it)
        }
    }

    private fun setupUI() {
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.videoRecyclerView)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.getSearchResults().observe(this, Observer<List<Video>> { foundVideos ->
            foundVideos?.let {
                adapter.setVideos(it)
            }
        })
    }

    override fun onItemSelected(video: Video) {
        val toVideoIntent = Intent(this, PlayerActivity::class.java)
        toVideoIntent.putExtra(PlayerActivity.SELECTED_VIDEO, video)
        startActivity(toVideoIntent)
    }


    companion object Extras {
        const val EXTRA_QUERY = "extra:search:query"
    }
}
