package michalengel.pwr.application2.view.gallery_view

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.transition.TransitionManager
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.WindowManager
import androidx.core.view.children
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PinchRecyclerView(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {
    private var gridScaleFactor = 2f
    private var linearScaleFactor = 1f
    private var currentColumns = 2
    var isGridLayout = true
    private val TAG = "PinchRecyclerView"

    private val scaleListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            if (isGridLayout) {
                gridScaleFactor *= (1 / (detector?.scaleFactor ?: 1f))
                gridScaleFactor = Math.max(2f, Math.min(gridScaleFactor, 6f))
                Log.d(TAG, "Current gridScale: $gridScaleFactor")
                handleGridScale()
            } else {
                linearScaleFactor *= detector?.scaleFactor ?: 1f
                linearScaleFactor = Math.max(0.25f, Math.min(linearScaleFactor, 1f))
                Log.d(TAG, "Current linearScale: $linearScaleFactor")
                handleLinearScale()
            }
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
        TransitionManager.beginDelayedTransition(this)
        layoutManager =
            if (isGridLayout) GridLayoutManager(context, currentColumns)
            else LinearLayoutManager(context, VERTICAL, false)
    }

    private fun handleGridScale() {
        if (layoutManager is GridLayoutManager) {
            for (i in 2..5) {
                changeColumns(i.toFloat(), (i + 1).toFloat())
            }
        }
    }

    private fun changeColumns(downLimit: Float, upLimit: Float) {
        if (gridScaleFactor in downLimit..upLimit && currentColumns != downLimit.toInt()) {
            TransitionManager.beginDelayedTransition(this)
            (layoutManager as GridLayoutManager).spanCount = downLimit.toInt()
            currentColumns = downLimit.toInt()
            Log.d(TAG, "downlimit = $downLimit, uplimit = $upLimit, numberofcol = $currentColumns")
        }
    }

    private fun handleLinearScale() {
/*        val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)
        layoutParams.width = Math.round(displayMetrics.widthPixels * linearScaleFactor)
        val adapt = adapter
        adapter = adapt*/
//        (adapter!! as GalleryScaleAdapter).changeScale(linearScaleFactor)
//        this.children.forEach { Log.d(TAG, "PRE: Child$it width:${it.width} height:${it.height}") }
//        this.children.forEach {
//            it.layoutParams.width = Math.round((it.measuredWidth * linearScaleFactor))
//            it.layoutParams.height = Math.round((it.measuredHeight * linearScaleFactor))
//            it.requestLayout()
//            it.invalidate()
//        }
//        this.children.forEach {
//            it.scaleX = linearScaleFactor
//            it.scaleY = linearScaleFactor
//            it.requestLayout()
//        }
        //TransitionManager.beginDelayedTransition(this)
//        this.children.forEach { Log.d(TAG, "POST: Child$it width:${it.width} height:${it.height}") }
//        val manager = layoutManager as LinearLayoutManager
//
//        //adapter?.notifyItemRangeChanged(manager.findFirstVisibleItemPosition(), manager.findLastVisibleItemPosition())
//        Log.d(
//            TAG,
//            "firstPos = ${manager.findFirstVisibleItemPosition()}, lasPos = ${manager.findLastVisibleItemPosition()}"
//        )
    }

    override fun onSaveInstanceState(): Parcelable? {
        val savedState = SavedState(super.onSaveInstanceState())
        savedState.currentColumns = currentColumns
        savedState.gridScaleFactor = gridScaleFactor
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
        this.gridScaleFactor = state.gridScaleFactor
        this.linearScaleFactor = state.linearScaleFactor
        this.currentColumns = state.currentColumns
        this.isGridLayout = state.isGridLayout
        layoutManager =
            if (isGridLayout) GridLayoutManager(context, currentColumns)
            else LinearLayoutManager(context, VERTICAL, false)
        layoutManager?.onRestoreInstanceState(state.layoutManager)
    }

    internal class SavedState : BaseSavedState {
        var gridScaleFactor = 1f
        var linearScaleFactor = 1f
        var currentColumns = 2
        var isGridLayout = true
        var layoutManager: Parcelable? = null

        constructor(source: Parcel) : super(source) {
            this.gridScaleFactor = source.readFloat()
            this.linearScaleFactor = source.readFloat()
            this.currentColumns = source.readInt()
            this.isGridLayout = source.readValue(null) as Boolean
            this.layoutManager = source.readParcelable(null)
        }

        constructor(superState: Parcelable) : super(superState)

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeFloat(this.gridScaleFactor)
            out.writeFloat(this.linearScaleFactor)
            out.writeInt(this.currentColumns)
            out.writeValue(this.isGridLayout)
            out.writeParcelable(this.layoutManager, flags)
        }

//        val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
//            override fun createFromParcel(source: Parcel): SavedState {
//                return SavedState(source)
//            }
//
//            override fun newArray(size: Int): Array<SavedState?> {
//                return arrayOfNulls(size)
//            }
//
//        }

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