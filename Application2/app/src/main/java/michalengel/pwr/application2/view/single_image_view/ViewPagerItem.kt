package michalengel.pwr.application2.view.single_image_view


import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.viewpager_item.*
import kotlinx.android.synthetic.main.viewpager_item.view.*
import michalengel.pwr.application2.R


class ViewPagerItem : Fragment() {
    val TAG = "ViewPagerItem"
    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        imageUri = arguments!!.getParcelable<Uri>("imageUri")
        val view = inflater.inflate(R.layout.viewpager_item, container, false)

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
        fun newInstance(): ViewPagerItem {
            return ViewPagerItem()
        }
    }
}