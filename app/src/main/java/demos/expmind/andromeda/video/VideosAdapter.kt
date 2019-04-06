package demos.expmind.andromeda.video

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import demos.expmind.andromeda.R
import demos.expmind.andromeda.data.Video

/**
 * Created by RAJ1GA on 27/10/2018.
 */
internal class VideosAdapter(private val mListener: VideoAdapterListener) :
        RecyclerView.Adapter<VideosAdapter.VideoItemViewHolder>() {

    private val videoList: MutableList<Video> = mutableListOf()

    /**
     * Main contract to communicate with outside UI components
     */
    interface VideoAdapterListener {
        fun onItemSelected(video: Video)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoItemViewHolder {
        val context = parent.context
        val holderLayout = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false)
        return VideoItemViewHolder(holderLayout)
    }

    override fun onBindViewHolder(holder: VideoItemViewHolder, position: Int) {
        val currentVideo = videoList[position]
        holder.bind(currentVideo, mListener)
    }

    override fun getItemCount(): Int = videoList.size

    fun setVideos(newVideos: List<Video>) {
        videoList.clear()
        videoList.addAll(newVideos)
        notifyDataSetChanged()
    }

    internal inner class VideoItemViewHolder(private val mRootView: View) : RecyclerView.ViewHolder(mRootView) {

        private val mTitleTextView: TextView = mRootView.findViewById(R.id.text_video_title)
        private val mLengthTextView: TextView = mRootView.findViewById(R.id.text_video_length)
        private val mThumbImageView: ImageView = mRootView.findViewById<ImageView>(R.id.image_video_thumbnail)
        private val imageOptions = DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.thumb_loading)
                .showImageOnFail(R.drawable.thumb_error)
                .build()

        fun bind(video: Video, adapterListener: VideoAdapterListener) {
            mTitleTextView.text = video.title
            ImageLoader.getInstance().displayImage(video.thumbnail, mThumbImageView, imageOptions)
            mLengthTextView.text = video.duration
            mRootView.setOnClickListener { v -> adapterListener.onItemSelected(video) }
        }
    }

}
