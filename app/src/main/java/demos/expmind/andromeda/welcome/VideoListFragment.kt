package demos.expmind.andromeda.welcome

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import demos.expmind.andromeda.AndromedaApp
import demos.expmind.andromeda.R
import demos.expmind.andromeda.data.DownloadStatus
import demos.expmind.andromeda.data.Result
import demos.expmind.andromeda.data.Video
import demos.expmind.andromeda.data.YoutubeChannels
import demos.expmind.andromeda.network.YoutubeService
import demos.expmind.andromeda.player.PlayerActivity
import demos.expmind.andromeda.video.VideosAdapter
import javax.inject.Inject


/**
 * UI Component that shows a list of videos.
 */
class VideoListFragment : androidx.fragment.app.Fragment(), VideosAdapter.VideoAdapterListener {

    private val adapter: VideosAdapter = VideosAdapter(this)
    @Inject
    lateinit var service: YoutubeService

    lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    lateinit var loadingBar: ProgressBar
    lateinit var noInternetView: View
    lateinit var noInternetText: TextView
    lateinit var swipeRefresh: androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    lateinit var viewModel: VideosByCategoryViewModel

    companion object {
        /**
         * The fragment argument representing the video category id.
         */
        private val ARG_CATEGORY_NAME = "category_name"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(categoryName: String): VideoListFragment {
            val fragment = VideoListFragment()
            val args = Bundle()
            args.putString(ARG_CATEGORY_NAME, categoryName)
            fragment.arguments = args
            return fragment
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AndromedaApp).getApplicationComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_video_list, container, false)
        bindUIElements(rootView)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val categoryName = arguments?.getString(ARG_CATEGORY_NAME) ?: YoutubeChannels.TED.name
        //TODO inject ViewModel Factory
        viewModel = ViewModelProviders
                .of(this, WelcomeViewModelFactory(YoutubeChannels.valueOf(categoryName), service))
                .get(categoryName, VideosByCategoryViewModel::class.java)
        observeViewModel()
        viewModel.loadVideos()
    }

    //TODO replace listener with Eventbus
    override fun onItemSelected(video: Video) {
        val toVideoIntent = Intent(context, PlayerActivity::class.java)
        toVideoIntent.putExtra(PlayerActivity.SELECTED_VIDEO, video)
        startActivity(toVideoIntent)
    }

    private fun observeViewModel() {

        viewModel.getLoadedVideos().observe(this, object : Observer<Result<List<Video>>> {
            override fun onChanged(r: Result<List<Video>>?) {
                r?.let {
                    if (it.successful) {
                        adapter.setVideos(it.value)
                    } else {
                        showError(it.errorMessage)
                    }

                }
            }
        })

        viewModel.getLoadingStatus().observe(this, Observer<DownloadStatus> { status ->
            when (status) {
                DownloadStatus.LOADING -> {
                    showLoadingIndicator(true)
                }
                DownloadStatus.DOWNLOADED -> {
                    showLoadingIndicator(false)
                }
            }
        })
    }

    private fun showLoadingIndicator(show: Boolean) {
        if (show) {
            loadingBar.visibility = View.VISIBLE
            recyclerView.visibility = View.INVISIBLE
        } else {
            loadingBar.visibility = View.GONE
            swipeRefresh.setRefreshing(false)
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun showError(message: String) {
        noInternetText.text = message
        noInternetView.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE
    }

    private fun bindUIElements(viewRoot: View) {
        loadingBar = viewRoot.findViewById(R.id.indeterminateBar)
        recyclerView = viewRoot.findViewById(R.id.videoRecyclerView)
        noInternetView = viewRoot.findViewById(R.id.no_internet_view)
        noInternetText = noInternetView.findViewById(R.id.no_internet_text)
        swipeRefresh = viewRoot.findViewById(R.id.swipe_refresh_layout)
        swipeRefresh.setColorSchemeColors(resources.getColor(R.color.colorAccent))
        swipeRefresh.setOnRefreshListener {
            noInternetView.visibility = View.INVISIBLE
            viewModel.loadVideos()
        }
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

}