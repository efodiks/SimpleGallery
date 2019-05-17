package michalengel.pwr.application2.view.fragments


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.viewpager_item.*
import michalengel.pwr.application2.R
import michalengel.pwr.application2.view_model.ImagesUrisViewModel
import org.koin.android.architecture.ext.sharedViewModel


class ViewPagerItem(val imageUri: Uri) : Fragment() {
    val TAG = "ViewPagerItem"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.viewpager_item, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "destroying view")
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_item_set_as_wallpaper -> {
                Log.v(TAG, "setting wallpaper")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
//    fun onSetAsWallpaper() {
//        val wallpaperManager = WallpaperManager.getInstance(context)
//        try {
//            wallpaperManager.setBitmap(BitmapFactory.decodeFile(currentPath))
//            Toast.makeText(context, "Setting wallpaper!", Toast.LENGTH_LONG).show()
//        } catch (e: IOException) {
//            Log.e(TAG, "IOException, uri = $currentPath, exc = $e")
//        }
//    }

    companion object {
        fun newInstance(imageUri: Uri): ViewPagerItem {
            return ViewPagerItem(imageUri)
        }
    }
}