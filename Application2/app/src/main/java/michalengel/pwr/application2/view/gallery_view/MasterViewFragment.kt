package michalengel.pwr.application2.view.gallery_view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_master_view.*
import kotlinx.android.synthetic.main.fragment_master_view.view.*
import michalengel.pwr.application2.R
import michalengel.pwr.application2.view_model.ImagesUrisViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MasterViewFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MasterViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MasterViewFragment : Fragment() {
    private val viewModel by sharedViewModel<ImagesUrisViewModel>()
    private var listener: OnFragmentInteractionListener? = null
    private val TAG = "MasterViewFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_master_view, container, false)

        view.imagesRecyclerView.layoutManager = GridLayoutManager(context, 2)


        val adapter = ThumbnailAdapter {
            Log.d(TAG, "received onClick image: $it")
            viewModel.select(it)
            listener!!.onDetailFragmentImagePressed()
        }
        view.imagesRecyclerView.adapter = adapter
        viewModel.imagesUriList.observe(this, Observer {
            adapter.submitList(it)
        })
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        Log.d(TAG, "inflating options menu")
        val view = inflater.inflate(R.menu.menu_master, menu)
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
        imagesRecyclerView.swapLayoutManager()
        item.icon =
            if (imagesRecyclerView.isGridLayout) resources.getDrawable(R.drawable.ic_view_list_white_24dp, null)
            else resources.getDrawable(R.drawable.ic_grid_on_white_24dp, null)
        return true
    }

    // TODO: Rename method, update argument and hook method into UI event

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        fun onDetailFragmentImagePressed()
    }

    companion object {
        private const val ARG_COL_COUNT = "COL_COUNT"
        private const val ARG_IS_GRID_LAYOUT = "IS_GRID_LAYOUT"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment MasterViewFragment.
         */
        @JvmStatic
        fun newInstance() = MasterViewFragment()
    }
}
