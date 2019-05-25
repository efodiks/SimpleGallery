package michalengel.pwr.simplegallery.view.gallery_view

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import michalengel.pwr.simplegallery.R


class ImagesRecyclerAdapter(private val onClickListener: ((Int, View) -> Unit)) : PagedListAdapter<Uri, ImagesRecyclerViewHolder>(
    PathDiffUtil) {

    lateinit var context: Context
    val TAG = "UriPagedListAdapter"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesRecyclerViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(
            R.layout.image_recycler_view_item,
            parent,
            false
        )

        return ImagesRecyclerViewHolder(view) {
            position, v -> onClickListener.invoke(position, v)
            Log.d(TAG, "invoking callback")
        }
    }
    override fun onBindViewHolder(holder: ImagesRecyclerViewHolder, position: Int) {
        Log.d(this::class.java.simpleName, "position: $position")
        holder.bindTo(getItem(position), context)
    }

    object PathDiffUtil : DiffUtil.ItemCallback<Uri>() {
        override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }
    }
}