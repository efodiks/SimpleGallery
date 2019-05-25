package michalengel.pwr.simplegallery.data

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource


class ImagesDataSource(private val contentResolver: ContentResolver) :
    PositionalDataSource<Uri>() {
    //TODO
    companion object {
        private const val TAG = "ImageDataSource"
    }


    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Uri>) {
        Log.d(TAG, "startPosition = ${params.startPosition}")
        Log.d(TAG, "loadSize = ${params.loadSize}")
        callback.onResult(getImages(params.loadSize, params.startPosition))
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Uri>) {
        Log.d(
            TAG,
            "loadInitial, loadsize: ${params.requestedLoadSize}, startPosition: ${params.requestedStartPosition}"
        )
        callback.onResult(
            getImages(params.requestedLoadSize, params.requestedStartPosition),
            0
        )
    }

    private fun getImages(limit: Int, offset: Int): MutableList<Uri> {
        Log.d(TAG, "limit = $limit, offset = $offset")

        val PROJECTION: Array<String> = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media.DESCRIPTION,
            MediaStore.Images.Media.BUCKET_ID
        )

        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            PROJECTION,
            null,
            null,
            "${MediaStore.Images.Media.DATE_TAKEN} DESC LIMIT $limit OFFSET $offset"
        ) ?: throw NullPointerException("empty cursor in $TAG")

        val images = mutableListOf<Uri>()

        cursor.moveToFirst()
        Log.d(TAG, "cursor size: ${cursor.count}")
        while (!cursor.isAfterLast) {
            //TODO
            val path = cursor.getString(cursor.getColumnIndexOrThrow(PROJECTION[0]))
            val dateTaken = cursor.getString(cursor.getColumnIndexOrThrow(PROJECTION[1]))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(PROJECTION[2]))
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(PROJECTION[3]))
            //images.add(Image(id, path, dateTaken, description))
            cursor.moveToNext()
        }
        cursor.close()
        return images
    }

    class ImagesDataSourceFactory(private val contentResolver: ContentResolver) :
        DataSource.Factory<Int, Uri>() {
        override fun create(): DataSource<Int, Uri> {
            return ImagesDataSource(contentResolver)
        }
    }
}

