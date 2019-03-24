package michalengel.pwr.application2.view.viewmodels

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.DrawFilter
import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import michalengel.pwr.application2.data.ImagesDataSourceFactory

class ImagesViewModel(private val contentResolver: ContentResolver, private val context: Context) : ViewModel() {
    val thumbnails: LiveData<PagedList<Drawable>> = loadThumbnails()


    fun loadThumbnails(): LiveData<PagedList<Drawable>> {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(true)
            .build()
        return LivePagedListBuilder<Int, Drawable>(
            ImagesDataSourceFactory(contentResolver, context), config
        ).build()
    }
    companion object {
        const val TAG = "ImagesViewModel"
    }

}
