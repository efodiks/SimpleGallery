package michalengel.pwr.application2.data

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.provider.BaseColumns
import android.provider.MediaStore
import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject



class ImagesDataSource(private val contentResolver: ContentResolver, val context: Context) : PositionalDataSource<Drawable>() {
    companion object {
        private const val TAG = "ImageDataSource"
    }


    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Drawable>) {
        Log.d(TAG, "startPosition = ${params.startPosition}")
        Log.d(TAG, "loadSize = ${params.loadSize}")
        callback.onResult(getThumbnails(params.loadSize, params.startPosition))
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Drawable>) {
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

    private fun getThumbnails(limit: Int, offset: Int): MutableList<Drawable> {
        Log.d(TAG, "limit = $limit, offset = $offset")
        val PROJECTION: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(
            MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
            PROJECTION,
            null,
            null,
            "${BaseColumns._ID} ASC LIMIT $limit OFFSET $offset"
        ) ?: throw NullPointerException("empty cursor in $TAG")

        val thumbnails = mutableListOf<Drawable>()

        cursor.moveToFirst()
        Log.d(TAG, "cursor size: ${cursor.count}")
        while (!cursor.isAfterLast) {
            val url = cursor.getString(cursor.getColumnIndexOrThrow(PROJECTION[0]))
            val bitmap = BitmapFactory.decodeFile(cursor.getString(cursor.getColumnIndexOrThrow(PROJECTION[0])))
            val b = Glide.with(context)
                .load(url)
                .submit(100, 100)
                .get()
            thumbnails.add(b)
            bitmap.recycle()
            cursor.moveToNext()
        }
        cursor.close()
        return thumbnails
    }
}

class ImagesDataSourceFactory(private val contentResolver: ContentResolver, private val context: Context) : DataSource.Factory<Int, Drawable>() {
    override fun create(): DataSource<Int, Drawable> {
        return ImagesDataSource(contentResolver, context)
    }
}