package michalengel.pwr.application2.view_model

import android.net.Uri
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import michalengel.pwr.application2.data.ImagesUriDataSource

class ImagesUrisViewModel : ViewModel() {
    val TAG = "ImagesUrisViewModel"
    val imagesUrisDataSourceFactory = ImagesUriDataSource.ImageURIsDataSourceFactory()
    val imagesUriList: LiveData<PagedList<Uri>> =
        LivePagedListBuilder<Int, Uri>(imagesUrisDataSourceFactory, 5).build()

    fun changeRootDocument(rootDocument: DocumentFile) {
        imagesUrisDataSourceFactory.latestRootDocumentFile = rootDocument
        imagesUrisDataSourceFactory.sourceLiveData.value?.invalidate()
        Log.d(TAG, "datasource status ${imagesUrisDataSourceFactory.sourceLiveData?.value?.isInvalid}")
    }
}