package demos.expmind.andromeda.welcome

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import demos.expmind.andromeda.R
import demos.expmind.andromeda.data.Video
import demos.expmind.andromeda.data.VideoCategory
import demos.expmind.andromeda.player.PlayerActivity
import demos.expmind.andromeda.video.VideosAdapter


/**
 * UI Component that shows a list of videos.
 */
class VideoListFragment : Fragment(), VideosAdapter.VideoAdapterListener {

    private val adapter: VideosAdapter = VideosAdapter(this)

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

        /**
         * Fake data
         */

        val MOCK_VIDEOS = listOf<Video>(
                Video("Io0fBr1XBUA",
                        "The Chainsmokers - Don't Let Me Down ft. Daya",
                        "https://i.ytimg.com/vi/Io0fBr1XBUA/mqdefault.jpg",
                        "03:38", VideoCategory.FILM),
                Video("VgVQKCcfwnU",
                        "The NEW Periodic Table Song (Updated)",
                        "https://i.ytimg.com/vi/VgVQKCcfwnU/mqdefault.jpg",
                        "02:54", VideoCategory.FILM),
                Video("YuOBzWF0Aws",
                        "If Google Was A Guy",
                        "https://i.ytimg.com/vi/YuOBzWF0Aws/mqdefault.jpg",
                        "02:12", VideoCategory.FILM),
                Video("2l2HQdPt92U",
                        "Miley Cyrus, Alicia Keys, Adam Levine and Blake Shelton: \\\"Dream On\\\" - The Voice 2016",
                        "https://i.ytimg.com/vi/2l2HQdPt92U/mqdefault.jpg",
                        "03:38", VideoCategory.FILM),
                Video("wTAJSuhgZxA",
                        "Ellen Inspired Adele's New Song",
                        "https://i.ytimg.com/vi/wTAJSuhgZxA/mqdefault.jpg",
                        "02:50", VideoCategory.FILM),
                Video("Wh8m4PYlSGY",
                        "J.Lo and Ellen Play Never Have I Ever",
                        "https://i.ytimg.com/vi/Wh8m4PYlSGY/mqdefault.jpg",
                        "02:44", VideoCategory.FILM),
                Video("GjkFr48jk68",
                        "Official Trailer: Hindi Medium | Irrfan Khan | Saba Qamar & Deepak Dobriyal | In Cinemas 12th May",
                        "https://i.ytimg.com/vi/GjkFr48jk68/mqdefault.jpg",
                        "02:43", VideoCategory.FILM))
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
    override fun onItemSelected(videoId: String) {
        startActivity(Intent(context, PlayerActivity::class.java))
    }
    private fun observeViewModel() {
        //TODO replace with the most generic video cateogry ("today videos")
        val categoryName = arguments?.getString(ARG_CATEGORY_NAME) ?: VideoCategory.FILM.name
        //TODO inject this view model
        val welcomeViewModel: WelcomeViewModel = ViewModelProviders
                .of(this, WelcomeViewModelFactory(VideoCategory.valueOf(categoryName)))
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