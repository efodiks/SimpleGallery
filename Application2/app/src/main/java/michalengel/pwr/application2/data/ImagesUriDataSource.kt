package michalengel.pwr.application2.data

import android.net.Uri
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource

class ImagesUriDataSource(val rootDocumentFile: DocumentFile?) : PositionalDataSource<Uri>() {
    lateinit var imagesUris: List<Uri>
    val TAG = "ImagesUriDataSource"
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Uri>) {
        if (imagesUris.isNotEmpty())
            callback.onResult(imagesUris.subList(params.startPosition, params.startPosition + params.loadSize))
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Uri>) {
        imagesUris =
            rootDocumentFile?.listFiles()
                ?.filter { it.isFile && it.type?.startsWith("image") ?: false }
                ?.map { it.uri } ?: emptyList()
        Log.d(TAG, "$imagesUris")
        if (imagesUris.isNotEmpty())
            callback.onResult(
                imagesUris.subList(
                    params.requestedStartPosition,
                    params.requestedStartPosition + params.requestedLoadSize
                ),
                0
            )
    }
    class ImageURIsDataSourceFactory : DataSource.Factory<Int, Uri>() {
        val sourceLiveData = MutableLiveData<ImagesUriDataSource>()
        var latestRootDocumentFile: DocumentFile? = null

        override fun create(): DataSource<Int, Uri> {
            val latestSource = ImagesUriDataSource(latestRootDocumentFile)
            Log.d("ImageURIsDataSourceFac", "creating datasource")
            sourceLiveData.postValue(latestSource)
            return latestSource
        }
    }
}