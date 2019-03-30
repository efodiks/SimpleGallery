package michalengel.pwr.application2.view

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.thumbnail_item.view.*
import michalengel.pwr.application2.model.Image

class ThumbnailViewHolder(itemView: View, callback: ((Image?) -> Unit)? ) : RecyclerView.ViewHolder(itemView) {
    private var imageView: ImageView = itemView.thumbnail
    private var heldImage: Image? = null
    private val TAG = "ThumbnailViewHolder"

    init {
        itemView.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) callback?.invoke(heldImage)
        }
    }

    fun bindTo(image: Image?, context: Context) {
        Log.d(TAG, "binding $image to $imageView")
        heldImage = image
        Glide.with(context)
            .load(image?.path)
            .thumbnail(0.1f)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }
}