package michalengel.pwr.simplegallery.view.single_image_view


import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.image_view_pager_item.*
import kotlinx.android.synthetic.main.image_view_pager_item.view.*
import michalengel.pwr.simplegallery.R


class ImageViewPagerItem : Fragment() {
    val TAG = "ImageViewPagerItem"
    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        imageUri = arguments!!.getParcelable<Uri>("imageUri")
        val view = inflater.inflate(R.layout.image_view_pager_item, container, false)

        Glide.with(context!!)
            .load(imageUri)
            .into(view.detail_image)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(imageUri != null) detail_image.transitionName = imageUri.toString()
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

    companion object {
        fun newInstance(): ImageViewPagerItem {
            return ImageViewPagerItem()
        }
    }
}