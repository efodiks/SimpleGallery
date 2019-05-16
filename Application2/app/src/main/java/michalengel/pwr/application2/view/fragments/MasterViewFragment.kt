package michalengel.pwr.application2.view.fragments

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
import michalengel.pwr.application2.view.images_recycler_view.ThumbnailAdapter
import michalengel.pwr.application2.view_model.ImagesUrisViewModel
import michalengel.pwr.application2.view_model.ImagesViewModel
import org.koin.android.architecture.ext.sharedViewModel

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
    private var isGridLayoutManager: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_master_view, container, false)
        Log.d(TAG, "Inflating view")
        Log.d(TAG, "viewModel is $viewModel")
        view.imagesRecyclerView.layoutManager =
            if (isGridLayoutManager) GridLayoutManager(listener as Context, 4)
            else LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val adapter = ThumbnailAdapter{
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
        inflater.inflate(R.menu.menu_master, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_grid_layout)
            return onChangeLayoutMenuItemClicked(item)
        return super.onOptionsItemSelected(item)
    }

    private fun onChangeLayoutMenuItemClicked(item: MenuItem): Boolean {
        //flip button isChecked
        val isChecked = item.isChecked
        Log.d(TAG, "item is $isChecked")
        item.isChecked = !isChecked
        //if item is checked, change layout to grid and icon to linear, else layout to linear and icon to grid
        imagesRecyclerView.layoutManager =
            if (isChecked) GridLayoutManager(context, 4)
            else LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        item.icon =
            if (isChecked) resources.getDrawable(R.drawable.ic_view_list_white_24dp, null)
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
