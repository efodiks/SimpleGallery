package michalengel.pwr.application2.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_detail_view.*
import kotlinx.android.synthetic.main.fragment_detail_view.view.*
import michalengel.pwr.application2.R
import michalengel.pwr.application2.view.ViewPagerAdapter
import michalengel.pwr.application2.view_model.ImagesUrisViewModel
import org.koin.android.architecture.ext.sharedViewModel

class DetailViewFragment : Fragment(){
    val viewModel: ImagesUrisViewModel by sharedViewModel<ImagesUrisViewModel>()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_detail_view, container, false)
        view.photos_viewpager.adapter = ViewPagerAdapter(context!!, viewModel.imagesUriList.value!!)
        view.photos_viewpager.currentItem = viewModel.selected.value!!
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail, menu)
        val shareActionProvider =
            MenuItemCompat.getActionProvider(menu.findItem(R.id.action_item_share)) as androidx.appcompat.widget.ShareActionProvider
        shareActionProvider.setShareIntent(
            Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                //putExtra(Intent.EXTRA_STREAM
            }
        )
    }

    companion object {
        fun newInstance(): DetailViewFragment = DetailViewFragment()
    }
}