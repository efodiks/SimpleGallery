package michalengel.pwr.application2.data

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.BaseColumns
import android.provider.MediaStore
import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource

class ImagesDataSource(private val contentResolver: ContentResolver) : PositionalDataSource<Bitmap>() {
    companion object {
        private const val TAG = "ImageDataSource"
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Bitmap>) {
        Log.d(TAG, "startPosition = ${params.startPosition}")
        Log.d(TAG, "loadSize = ${params.loadSize}")
        callback.onResult(getThumbnails(params.loadSize, params.startPosition))
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Bitmap>) {
        Log.d(
            TAG,
            "loadInitial, loadsize: ${params.requestedLoadSize}, startPosition: ${params.requestedStartPosition}"
        )
        callback.onResult(
            getThumbnails(params.requestedLoadSize, params.requestedStartPosition),
            0,
            params.requestedStartPosition + params.requestedLoadSize * 3
        )
    }

    private fun getThumbnails(limit: Int, offset: Int): MutableList<Bitmap> {
        Log.d(TAG, "limit = $limit, offset = $offset")
        val PROJECTION: Array<String> = arrayOf(MediaStore.Images.Thumbnails.DATA)
        val cursor = contentResolver.query(
            MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
            PROJECTION,
            "kind = ${MediaStore.Images.Thumbnails.MINI_KIND}",
            null,
            "${BaseColumns._ID} ASC LIMIT $limit OFFSET $offset"
        ) ?: throw NullPointerException("empty cursor in $TAG")

        val thumbnails = mutableListOf<Bitmap>()

        cursor.moveToFirst()
        Log.d(TAG, "cursor size: ${cursor.count}")
        while (!cursor.isAfterLast) {
            thumbnails.add(BitmapFactory.decodeFile(cursor.getString(cursor.getColumnIndexOrThrow(PROJECTION[0]))))
            //thumbnails.add()
            cursor.moveToNext()
        }
        cursor.close()
        return thumbnails
    }
}

class ImagesDataSourceFactory(private val contentResolver: ContentResolver) : DataSource.Factory<Int, Bitmap>() {
    override fun create(): DataSource<Int, Bitmap> {
        return ImagesDataSource(contentResolver)
    }
}