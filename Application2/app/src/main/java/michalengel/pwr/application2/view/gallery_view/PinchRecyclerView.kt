package michalengel.pwr.application2.view.gallery_view

import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PinchRecyclerView(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {
    private var scaleFactor = 2f
    private var currentColumns = 2
    var isGridLayout = true
    private val TAG = "PinchRecyclerView"

    private val scaleListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            scaleFactor *= (1 / (detector?.scaleFactor ?: 1f))
            scaleFactor = Math.max(2f, Math.min(scaleFactor, 6f))
            Log.d(TAG, "Current scale: $scaleFactor")
            handleScale()
            return true
        }
    }
    private val scaleDetector = ScaleGestureDetector(context, scaleListener)

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        super.onTouchEvent(e)
        scaleDetector.onTouchEvent(e)
        return true
    }

    fun swapLayoutManager() {
        isGridLayout = !isGridLayout
        layoutManager =
            if (isGridLayout) GridLayoutManager(context, currentColumns)
            else LinearLayoutManager(context, VERTICAL, false)
    }

    private fun handleScale() {
        if (layoutManager is GridLayoutManager) {
            for (i in 2..5) {
                changeColumns(i.toFloat(), (i + 1).toFloat())
            }
        }
    }

    private fun changeColumns(downLimit: Float, upLimit: Float) {
        if (scaleFactor in downLimit..upLimit && currentColumns != downLimit.toInt()) {
            layoutManager = GridLayoutManager(context, downLimit.toInt())
            currentColumns = downLimit.toInt()
            Log.d(TAG, "downlimit = $downLimit, uplimit = $upLimit, numberofcol = $currentColumns")
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        val savedState = SavedState(super.onSaveInstanceState())
        savedState.currentColumns = currentColumns
        savedState.scaleFactor = scaleFactor
        savedState.isGridLayout = isGridLayout
        savedState.layoutManager = layoutManager?.onSaveInstanceState()
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        super.onRestoreInstanceState(state.superState)
        this.scaleFactor = state.scaleFactor
        this.currentColumns = state.currentColumns
        this.isGridLayout = state.isGridLayout
        layoutManager =
            if (isGridLayout) GridLayoutManager(context, currentColumns)
            else LinearLayoutManager(context, VERTICAL, false)
        layoutManager?.onRestoreInstanceState(state.layoutManager)
    }

    internal class SavedState : BaseSavedState {
        var scaleFactor = 0f
        var currentColumns = 2
        var isGridLayout = true
        var layoutManager: Parcelable? = null

        constructor(source: Parcel) : super(source) {
            this.scaleFactor = source.readFloat()
            this.currentColumns = source.readInt()
            this.isGridLayout = source.readValue(null) as Boolean
            this.layoutManager = source.readParcelable(null)
        }

        constructor(superState: Parcelable) : super(superState)

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeFloat(this.scaleFactor)
            out.writeInt(this.currentColumns)
            out.writeValue(this.isGridLayout)
            out.writeParcelable(this.layoutManager, flags)
        }

        val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
            override fun createFromParcel(source: Parcel): SavedState {
                return SavedState(source)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }

        }

        companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel): SavedState {
                    return SavedState(source)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}