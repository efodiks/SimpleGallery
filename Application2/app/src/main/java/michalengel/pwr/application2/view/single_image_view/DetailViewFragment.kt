package michalengel.pwr.application2.view.single_image_view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_detail_view.view.*
import michalengel.pwr.application2.R
import michalengel.pwr.application2.model.ImageDetailsProvider
import michalengel.pwr.application2.view.single_image_view.image_details_view.ImageDetailFragment
import michalengel.pwr.application2.view_model.ImagesUrisViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailViewFragment : Fragment() {
    val viewModel by sharedViewModel<ImagesUrisViewModel>()
    val TAG = "DetailViewFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView called")
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_detail_view, container, false)
        view.photos_viewpager.adapter = ViewPagerAdapter(
            childFragmentManager,
            viewModel.imagesUriList.value!!
        )
        view.photos_viewpager.addOnPageChangeListener(
            object : ViewPager.OnPageChangeListener {
                override fun onPageSelected(position: Int) {
                    viewModel.selected.postValue(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }
            })
        view.photos_viewpager.currentItem = viewModel.selected.value!!
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail, menu)
        val shareActionProvider =
            MenuItemCompat.getActionProvider(menu.findItem(R.id.action_item_share)) as androidx.appcompat.widget.ShareActionProvider
        viewModel.selected.observe(this, Observer {
            if (viewModel.imagesUriList.value != null && viewModel.selected.value != null) {
                Log.d("DetailViewFragment", "selected: $it")
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
        if (item.itemId == R.id.action_image_details) {
            val uri = viewModel.imagesUriList.value!![viewModel.selected.value!!]!!
            instantiateImageDetails(uri)
        }
        return super.onOptionsItemSelected(item)
    }

    fun instantiateImageDetails(uri: Uri) {
            fragmentManager!!.beginTransaction()
            .replace(R.id.fragment_container, ImageDetailFragment.newInstance(uri), "image_detail")
            .addToBackStack("image_detail")
            .commit()
    }

    companion object {
        fun newInstance(): DetailViewFragment = DetailViewFragment()
    }
}