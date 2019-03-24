package michalengel.pwr.application2.view.viewmodels

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import michalengel.pwr.application2.data.ImagesDataSourceFactory

class ImagesViewModel(private val contentResolver: ContentResolver) : ViewModel() {
    val thumbnails: LiveData<PagedList<Bitmap>> = loadThumbnails()


    fun loadThumbnails(): LiveData<PagedList<Bitmap>> {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(true)
            .build()
        return LivePagedListBuilder<Int, Bitmap>(
            ImagesDataSourceFactory(contentResolver), config
        ).build()
    }
    companion object {
        const val TAG = "ImagesViewModel"
    }

}
