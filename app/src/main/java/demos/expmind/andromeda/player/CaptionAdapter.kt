package demos.expmind.andromeda.player

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import demos.expmind.andromeda.R
import demos.expmind.andromeda.data.Caption


class CaptionAdapter : RecyclerView.Adapter<CaptionAdapter.CaptionViewHolder>() {

    private val subtitles: MutableList<Caption> = mutableListOf()

    override fun onBindViewHolder(holder: CaptionViewHolder, position: Int) {
        holder.bind(subtitles[position])
    }

    override fun getItemCount(): Int = subtitles.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaptionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_caption, parent, false)
        return CaptionViewHolder(view)
    }

    fun loadItems(captions: List<Caption>) {
        subtitles.clear()
        subtitles.addAll(captions)
        notifyDataSetChanged()
    }

    inner class CaptionViewHolder(val rootView: View) : RecyclerView.ViewHolder(rootView) {

        val captionText: TextView = rootView.findViewById(R.id.captionTextView)

        fun bind(item: Caption) {
            captionText.text = item.text
        }

    }
}

