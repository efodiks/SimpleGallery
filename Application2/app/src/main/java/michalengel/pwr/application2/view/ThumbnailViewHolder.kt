package michalengel.pwr.application2.view

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.thumbnail_item.view.*

class ThumbnailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var imageView: ImageView = itemView.thumbnail

    fun bindTo(bitmap: Bitmap?) {
        Log.d(TAG, "binding $bitmap to $imageView")
        bitmap?.let { imageView.setImageBitmap(it) }
    }
    companion object {
        val TAG = "ThumbnailViewHolder"
    }
}