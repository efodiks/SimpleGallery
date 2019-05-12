package michalengel.pwr.application2.view.fragments


import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ShareActionProvider
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.fragment_detail_view.*
import michalengel.pwr.application2.R
import michalengel.pwr.application2.model.Image
import michalengel.pwr.application2.view_model.ImagesUrisViewModel
import michalengel.pwr.application2.view_model.ImagesViewModel
import org.koin.android.architecture.ext.sharedViewModel
import java.io.File
import java.io.IOException


class DetailViewFragment : Fragment() {
    private val viewModel by sharedViewModel<ImagesUrisViewModel>()
    val TAG = "DetailViewFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.selected.observe(this, Observer {
            setImage(it)
        })
    }

    private fun setImage(uri: Uri?) {
        Glide.with(context!!)
            .load(uri)
            .into(detail_image)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "destroying view")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail, menu)
        val shareActionProvider =
            MenuItemCompat.getActionProvider(menu.findItem(R.id.action_item_share)) as androidx.appcompat.widget.ShareActionProvider
        viewModel.selected.observe(this, Observer {
            shareActionProvider.setShareIntent(
                Intent(Intent.ACTION_SEND).apply {
                    type = "image/*"
                    putExtra(Intent.EXTRA_STREAM, it)
                }
            )
        })
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
        fun newInstance(): DetailViewFragment {
            return DetailViewFragment()
        }
    }
}