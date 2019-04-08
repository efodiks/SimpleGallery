package michalengel.pwr.application2.view_model

import android.content.ContentResolver
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import michalengel.pwr.application2.model.Image
import michalengel.pwr.application2.data.ImagesDataSource

class ImagesViewModel(val contentResolver: ContentResolver) : ViewModel() {
    val images: LiveData<PagedList<Image>> = loadImages()
    val selected = MutableLiveData<Image?>()
    private val dataSourceFactory: ImagesDataSource.ImagesDataSourceFactory =
        ImagesDataSource.ImagesDataSourceFactory(contentResolver)

    fun select(image: Image?) {
        selected.value = image
    }

    fun loadImages(): LiveData<PagedList<Image>> {
        val config = PagedList.Config.Builder()
            .setPageSize(5)
            .setEnablePlaceholders(false)
            .build()
        return LivePagedListBuilder<Int, Image>(
            ImagesDataSource.ImagesDataSourceFactory(contentResolver), config
        ).build()
    }

    companion object {
        const val TAG = "ImagesViewModel"
    }

}
