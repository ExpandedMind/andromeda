package demos.expmind.andromeda.welcome

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
import demos.expmind.andromeda.player.PlayerActivity
import demos.expmind.andromeda.video.VideosAdapter

/**
 * UI Component that shows a list of videos.
 */
class VideoListFragment : Fragment(), VideosAdapter.VideoAdapterListener {

    private val adapter: VideosAdapter = VideosAdapter(this)

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(sectionNumber: Int): VideoListFragment {
            val fragment = VideoListFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
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
                        "03:38"),
                Video("VgVQKCcfwnU",
                        "The NEW Periodic Table Song (Updated)",
                        "https://i.ytimg.com/vi/VgVQKCcfwnU/mqdefault.jpg",
                        "02:54"),
                Video("YuOBzWF0Aws",
                        "If Google Was A Guy",
                        "https://i.ytimg.com/vi/YuOBzWF0Aws/mqdefault.jpg",
                        "02:12"),
                Video("2l2HQdPt92U",
                        "Miley Cyrus, Alicia Keys, Adam Levine and Blake Shelton: \\\"Dream On\\\" - The Voice 2016",
                        "https://i.ytimg.com/vi/2l2HQdPt92U/mqdefault.jpg",
                        "03:38"),
                Video("wTAJSuhgZxA",
                        "Ellen Inspired Adele's New Song",
                        "https://i.ytimg.com/vi/wTAJSuhgZxA/mqdefault.jpg",
                        "02:50"),
                Video("Wh8m4PYlSGY",
                        "J.Lo and Ellen Play Never Have I Ever",
                        "https://i.ytimg.com/vi/Wh8m4PYlSGY/mqdefault.jpg",
                        "02:44"),
                Video("GjkFr48jk68",
                        "Official Trailer: Hindi Medium | Irrfan Khan | Saba Qamar & Deepak Dobriyal | In Cinemas 12th May",
                        "https://i.ytimg.com/vi/GjkFr48jk68/mqdefault.jpg",
                        "02:43"))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_video_list, container, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.videoRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        adapter.setVideos(MOCK_VIDEOS)
        return rootView
    }

    //TODO replace listener with Eventbus
    override fun onItemSelected(videoId: String) {
        startActivity(Intent(context, PlayerActivity::class.java))
    }
}