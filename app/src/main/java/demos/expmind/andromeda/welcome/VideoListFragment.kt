package demos.expmind.andromeda.welcome

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import demos.expmind.andromeda.AndromedaApp
import demos.expmind.andromeda.R
import demos.expmind.andromeda.data.Video
import demos.expmind.andromeda.data.VideoCategory
import demos.expmind.andromeda.network.YoutubeService
import demos.expmind.andromeda.player.PlayerActivity
import demos.expmind.andromeda.video.VideosAdapter
import javax.inject.Inject


/**
 * UI Component that shows a list of videos.
 */
class VideoListFragment : Fragment(), VideosAdapter.VideoAdapterListener {

    private val adapter: VideosAdapter = VideosAdapter(this)
    @Inject
    lateinit var service: YoutubeService

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
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.videoRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
    }

    //TODO replace listener with Eventbus
    override fun onItemSelected(video: Video) {
        val toVideoIntent = Intent(context, PlayerActivity::class.java)
        toVideoIntent.putExtra(PlayerActivity.SELECTED_VIDEO, video)
        startActivity(toVideoIntent)
    }

    private fun observeViewModel() {
        //TODO replace with the most generic video cateogry ("today videos")
        val categoryName = arguments?.getString(ARG_CATEGORY_NAME) ?: VideoCategory.FILM.name
        //TODO inject this view model
        val welcomeViewModel: WelcomeViewModel = ViewModelProviders
                .of(this, WelcomeViewModelFactory(VideoCategory.valueOf(categoryName), service))
                .get(categoryName, WelcomeViewModel::class.java)
        welcomeViewModel.getTodayVideos().observe(this, object : Observer<List<Video>> {
            override fun onChanged(t: List<Video>?) {
                t?.let {
                    adapter.setVideos(t)
                }
            }

        })
    }
}