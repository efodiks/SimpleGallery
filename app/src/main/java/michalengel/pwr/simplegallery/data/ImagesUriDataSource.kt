package michalengel.pwr.simplegallery.data

import android.net.Uri
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource

class ImagesUriDataSource(val rootDocumentFile: DocumentFile?) : PositionalDataSource<Uri>() {
    lateinit var imagesUris: List<Uri>
    val TAG = "ImagesUriDataSource"

    private fun loadInternal(startPosition: Int, loadSize: Int): List<Uri> {
        imagesUris =
            rootDocumentFile?.listFiles()
                ?.filter { it.isFile && it.type?.startsWith("image") ?: false }
                ?.map { it.uri } ?: emptyList()
        if(startPosition >= imagesUris.size) return emptyList()
        if(startPosition + loadSize > imagesUris.size) return imagesUris
        return imagesUris.subList(startPosition, startPosition + loadSize)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Uri>) {
        callback.onResult(imagesUris.subList(params.startPosition, params.startPosition + params.loadSize))
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Uri>) {
        val startPosition = params.requestedStartPosition
        val loadSize = params.requestedLoadSize
        callback.onResult(loadInternal(startPosition, loadSize), 0, imagesUris.size)
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