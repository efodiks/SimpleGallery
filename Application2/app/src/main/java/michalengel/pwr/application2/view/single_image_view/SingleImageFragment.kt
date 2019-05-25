package michalengel.pwr.application2.view.single_image_view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.app.SharedElementCallback
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.transition.ChangeBounds
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_single_image.*
import kotlinx.android.synthetic.main.fragment_single_image.view.*
import kotlinx.android.synthetic.main.image_view_pager_item.view.*
import michalengel.pwr.application2.R
import michalengel.pwr.application2.view_model.ImagesUrisViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SingleImageFragment : Fragment() {
    val viewModel by sharedViewModel<ImagesUrisViewModel>()
    val TAG = "SingleImageFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(names: MutableList<String>?, sharedElements: MutableMap<String, View>?) {
                val currentFragment =
                    photos_viewpager.adapter?.instantiateItem(
                        photos_viewpager,
                        viewModel.selected.value ?: return
                    ) as Fragment
                val v = currentFragment.view
                if (names.isNullOrEmpty() || sharedElements == null || v == null) {
                    Log.d(
                        TAG,
                        "OnMapSharedElements error: names = $names, sharedElements = $sharedElements, viewHolder = $v"
                    )
                    return
                }
                sharedElements[names[0]] = v.detail_image
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView called")
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_single_image, container, false)
        view.photos_viewpager.adapter = ImagesViewPagerAdapter(
            childFragmentManager,
            viewModel.imagesUriList.value!!
        )
        view.photos_viewpager.addOnPageChangeListener(
            object : ViewPager.OnPageChangeListener {
                override fun onPageSelected(position: Int) {
                    viewModel.selected.value = position
                }

                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }
            })
        view.photos_viewpager.currentItem = viewModel.selected.value!!
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition()
        sharedElementEnterTransition = ChangeBounds().apply {
            duration = 375
        }
        sharedElementReturnTransition = ChangeBounds().apply {
            duration = 375
        }
        photos_viewpager.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                photos_viewpager.viewTreeObserver.removeOnPreDrawListener(this)
                startPostponedEnterTransition()
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail, menu)
        val shareActionProvider =
            MenuItemCompat.getActionProvider(menu.findItem(R.id.action_item_share)) as androidx.appcompat.widget.ShareActionProvider
        viewModel.selected.observe(this, Observer {
            if (viewModel.imagesUriList.value != null && viewModel.selected.value != null) {
                Log.d("SingleImageFragment", "selected: $it")
                shareActionProvider.setShareIntent(
                    Intent(Intent.ACTION_SEND).apply {
                        type = "image/*"
                        putExtra(Intent.EXTRA_STREAM, (viewModel.imagesUriList.value as PagedList<Uri>)[it as Int])
                    }
                )
            }
        }
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val uri = viewModel.imagesUriList.value!![viewModel.selected.value!!]!!
        when (item.itemId) {
            R.id.action_image_details -> instantiateImageDetails(uri)
            R.id.action_item_set_as_wallpaper -> setAsWallpaper(uri)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setAsWallpaper(uri: Uri) {
        val intent = Intent(Intent.ACTION_ATTACH_DATA)
            .apply {
                addCategory(Intent.CATEGORY_DEFAULT)
                setDataAndType(uri, "image/*")
                putExtra("mimeType", "image/*")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        startActivity(Intent.createChooser(intent, "Use the image as:"))
    }

    private fun instantiateImageDetails(uri: Uri) {
        val action = SingleImageFragmentDirections.actionDetailViewFragmentToImageDetailFragment(uri)
        findNavController().navigate(action)
    }
}