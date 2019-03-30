package michalengel.pwr.application2.data

import android.content.ContentResolver
import android.provider.MediaStore
import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import michalengel.pwr.application2.model.Image


class ImagesDataSource(private val contentResolver: ContentResolver) :
    PositionalDataSource<Image>() {
    companion object {
        private const val TAG = "ImageDataSource"
    }


    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Image>) {
        Log.d(TAG, "startPosition = ${params.startPosition}")
        Log.d(TAG, "loadSize = ${params.loadSize}")
        callback.onResult(getImages(params.loadSize, params.startPosition))
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Image>) {
        Log.d(
            TAG,
            "loadInitial, loadsize: ${params.requestedLoadSize}, startPosition: ${params.requestedStartPosition}"
        )
        callback.onResult(
            getImages(params.requestedLoadSize, params.requestedStartPosition),
            0
        )
    }

    private fun getImages(limit: Int, offset: Int): MutableList<Image> {
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

        val images = mutableListOf<Image>()

        cursor.moveToFirst()
        Log.d(TAG, "cursor size: ${cursor.count}")
        while (!cursor.isAfterLast) {
            val path = cursor.getString(cursor.getColumnIndexOrThrow(PROJECTION[0]))
            val dateTaken = cursor.getString(cursor.getColumnIndexOrThrow(PROJECTION[1]))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(PROJECTION[2]))
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(PROJECTION[3]))
            images.add(Image(id, path, dateTaken, description))
            cursor.moveToNext()
        }
        cursor.close()
        return images
    }

    class ImagesDataSourceFactory(private val contentResolver: ContentResolver) :
        DataSource.Factory<Int, Image>() {
        override fun create(): DataSource<Int, Image> {
            return ImagesDataSource(contentResolver)
        }
    }
}

