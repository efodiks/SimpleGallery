package michalengel.pwr.application2.view.viewmodels

import android.content.ContentResolver
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import michalengel.pwr.application2.data.Image
import michalengel.pwr.application2.data.ImagesDataSource

class ImagesViewModel(private val contentResolver: ContentResolver, private val context: Context) : ViewModel() {
    val images: LiveData<PagedList<Image>> = loadImages()


    fun loadImages(): LiveData<PagedList<Image>> {
        val config = PagedList.Config.Builder()
            .setPageSize(5)
            .setEnablePlaceholders(false)
            .build()
        return LivePagedListBuilder<Int, Image>(
            ImagesDataSource.ImagesDataSourceFactory(contentResolver, context), config
        ).build()
    }
    companion object {
        const val TAG = "ImagesViewModel"
    }

}
