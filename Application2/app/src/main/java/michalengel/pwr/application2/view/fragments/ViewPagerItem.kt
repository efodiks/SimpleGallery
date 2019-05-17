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
import kotlinx.android.synthetic.main.viewpager_item.view.*
import michalengel.pwr.application2.R
import michalengel.pwr.application2.view_model.ImagesUrisViewModel
import org.koin.android.architecture.ext.sharedViewModel


class ViewPagerItem : Fragment() {
    val TAG = "ViewPagerItem"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val imageUri = arguments!!.getParcelable<Uri>("imageUri")
        val view = inflater.inflate(R.layout.viewpager_item, container, false)
        Glide.with(context!!)
            .load(imageUri)
            .into(view.detail_image)
        return view
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
        fun newInstance(): ViewPagerItem {
            return ViewPagerItem()
        }
    }
}