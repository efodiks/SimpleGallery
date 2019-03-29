package michalengel.pwr.application2.view

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.thumbnail_item.view.*
import michalengel.pwr.application2.data.Image

class ThumbnailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var imageView: ImageView = itemView.thumbnail
    private val TAG = "ThumbnailViewHolder"


    fun bindTo(image: Image?, context: Context) {
        Log.d(TAG, "binding $image to $imageView")
        Glide.with(context)
            .load(image?.path)
            .thumbnail(0.1f)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }
}