package michalengel.pwr.application2.view.images_recycler_view

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.thumbnail_item.view.*

class ThumbnailViewHolder(itemView: View, callback: ((Int?) -> Unit) ) : RecyclerView.ViewHolder(itemView) {
    private var imageView: ImageView = itemView.image
    private val TAG = "ThumbnailViewHolder"

    init {
        itemView.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) callback.invoke(layoutPosition)
            Log.d(TAG, "item $layoutPosition clicked, callback = $callback")
        }
    }

    fun bindTo(uri: Uri?, context: Context) {
        Log.d(TAG, "binding $uri to $imageView")
        uri?.let {
            Glide.with(context)
                .load(it)
                .thumbnail(0.1f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
        }
    }
}