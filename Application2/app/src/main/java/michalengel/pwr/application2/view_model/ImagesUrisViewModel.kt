package michalengel.pwr.application2.view_model

import android.net.Uri
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import michalengel.pwr.application2.data.ImagesUriDataSource

class ImagesUrisViewModel : ViewModel() {
    val TAG = "ImagesUrisViewModel"
    private val imagesUrisDataSourceFactory = ImagesUriDataSource.ImageURIsDataSourceFactory()
    val selected: MutableLiveData<Int?> = MutableLiveData()
    val imagesUriList: LiveData<PagedList<Uri>> =
        LivePagedListBuilder<Int, Uri>(imagesUrisDataSourceFactory,
            PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(5)
                .build())
            .build()

    fun changeRootDocument(rootDocument: DocumentFile) {
        imagesUrisDataSourceFactory.latestRootDocumentFile = rootDocument
        imagesUrisDataSourceFactory.sourceLiveData.value?.invalidate()
    }
    fun select(int: Int?) = selected.postValue(int)
}