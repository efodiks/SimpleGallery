package michalengel.pwr.application2.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.DrawFilter
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import michalengel.pwr.application2.R
import michalengel.pwr.application2.data.Image

class ThumbnailAdapter : PagedListAdapter<Image, ThumbnailViewHolder>(PathDiffUtil) {
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbnailViewHolder {
        context = parent.context
        return ThumbnailViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.thumbnail_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ThumbnailViewHolder, position: Int) {
        Log.d(this::class.java.simpleName, "position: $position")
        holder.bindTo(getItem(position), context)
    }

    object PathDiffUtil : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }
}