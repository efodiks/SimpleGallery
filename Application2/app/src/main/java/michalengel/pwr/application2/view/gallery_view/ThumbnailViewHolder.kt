package michalengel.pwr.application2.view.gallery_view

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.thumbnail_item.view.*
import michalengel.pwr.application2.R

class ThumbnailViewHolder(itemView: View, callback: ((Int?) -> Unit) ) : RecyclerView.ViewHolder(itemView) {
    private var imageView: ImageView = itemView.image
    private val TAG = "ThumbnailViewHolder"

    init {
        itemView.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) callback.invoke(adapterPosition)
            Log.d(TAG, "item $adapterPosition clicked, callback = $callback")
        }
    }

    fun bindTo(uri: Uri?, context: Context) {
        Log.d(TAG, "binding $uri to $imageView")
            Glide.with(context)
                .load(uri)
                .thumbnail(0.1f)
                .placeholder(R.drawable.ic_photo_primary_24dp)
                .dontAnimate()
                .into(imageView)
    }
}