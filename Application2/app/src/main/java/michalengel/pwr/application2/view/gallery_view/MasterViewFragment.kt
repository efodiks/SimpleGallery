package michalengel.pwr.application2.view.gallery_view

import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_master_view.*
import kotlinx.android.synthetic.main.fragment_master_view.view.*
import kotlinx.android.synthetic.main.thumbnail_item.view.*
import michalengel.pwr.application2.R
import michalengel.pwr.application2.view_model.ImagesUrisViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.lang.IllegalStateException


class MasterViewFragment : Fragment() {
    private val viewModel by sharedViewModel<ImagesUrisViewModel>()
    private val TAG = "MasterViewFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_master_view, container, false)

        view.imagesRecyclerView.layoutManager = GridLayoutManager(context, 2)

        val adapter = GalleryScaleAdapter { position, v ->
            viewModel.select(position)
            val extras = FragmentNavigatorExtras(
                v to v.transitionName
            )
            view.findNavController().navigate(R.id.detailViewFragment, null, null, extras)
        }
        view.imagesRecyclerView.adapter = adapter
        viewModel.imagesUriList.observe(this, Observer {
            TransitionManager.beginDelayedTransition(view.imagesRecyclerView)
            adapter.submitList(it)
        })
        prepareTransitions()
        postponeEnterTransition()
        view.imagesRecyclerView.viewTreeObserver.addOnPreDrawListener (
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    view.imagesRecyclerView.viewTreeObserver.removeOnPreDrawListener(this)
                    startPostponedEnterTransition()
                    return false
                }
            }
        )
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun prepareTransitions() {
        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(names: MutableList<String>?, sharedElements: MutableMap<String, View>?) {
                val v =
                    imagesRecyclerView.findViewHolderForAdapterPosition(viewModel.selected.value ?: throw IllegalStateException())
                if(v == null) {
                    sharedElements?.clear()
                    return
                }
                if (names.isNullOrEmpty() || sharedElements == null) {
                    Log.d(
                        TAG,
                        "OnMapSharedElements error: names = $names, sharedElements = $sharedElements, viewHolder = $v"
                    )
                    return
                }
                sharedElements[names[0]] = v.itemView.image
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_master, menu)
        Log.d(TAG, "inflating options menu")
        //TODO work out changing icon
        menu.findItem(R.id.action_grid_layout).icon =
            if (imagesRecyclerView.isGridLayout) resources.getDrawable(R.drawable.ic_view_list_white_24dp, null)
            else resources.getDrawable(R.drawable.ic_grid_on_white_24dp, null)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_grid_layout)
            return onChangeLayoutMenuItemClicked(item)
        return super.onOptionsItemSelected(item)
    }


    private fun onChangeLayoutMenuItemClicked(item: MenuItem): Boolean {
        //imagesRecyclerView.swapLayoutManager()
        //TODO Icon and swapping columns
        item.icon =
            if (imagesRecyclerView.isGridLayout) resources.getDrawable(R.drawable.ic_view_list_white_24dp, null)
            else resources.getDrawable(R.drawable.ic_grid_on_white_24dp, null)
        return true
    }
}
