package michalengel.pwr.application2.data

import android.content.ContentResolver
import android.content.Context
import android.graphics.drawable.Drawable
import android.provider.BaseColumns
import android.provider.MediaStore
import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource


class ImagesDataSource(private val contentResolver: ContentResolver, val context: Context) :
    PositionalDataSource<Image>() {
    companion object {
        private const val TAG = "ImageDataSource"
    }


    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Image>) {
        Log.d(TAG, "startPosition = ${params.startPosition}")
        Log.d(TAG, "loadSize = ${params.loadSize}")
        callback.onResult(getThumbnails(params.loadSize, params.startPosition))
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Image>) {
        Log.d(
            TAG,
            "loadInitial, loadsize: ${params.requestedLoadSize}, startPosition: ${params.requestedStartPosition}"
        )
        callback.onResult(
            getThumbnails(params.requestedLoadSize, params.requestedStartPosition),
            0
        )
    }

    private fun getThumbnails(limit: Int, offset: Int): MutableList<Image> {
        Log.d(TAG, "limit = $limit, offset = $offset")

        val PROJECTION: Array<String> = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media.DESCRIPTION)

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
            images.add(Image(path, dateTaken, description))
            cursor.moveToNext()
        }
        cursor.close()
        return images
    }
    class ImagesDataSourceFactory(private val contentResolver: ContentResolver, private val context: Context) :
        DataSource.Factory<Int, Image>() {
        override fun create(): DataSource<Int, Image> {
            return ImagesDataSource(contentResolver, context)
        }
    }
}

