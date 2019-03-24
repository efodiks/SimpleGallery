package michalengel.pwr.application2.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.thumbnail_item.view.*

class ThumbnailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var imageView: ImageView = itemView.thumbnail

    fun bindTo(drawable: Drawable?, context: Context) {
        Log.d(TAG, "binding $drawable to $imageView")
        imageView.setImageDrawable(drawable)
    }
    companion object {
        val TAG = "ThumbnailViewHolder"
    }
}