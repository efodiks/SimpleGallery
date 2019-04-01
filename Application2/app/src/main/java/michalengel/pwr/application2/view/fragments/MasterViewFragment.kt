package michalengel.pwr.application2.view.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_master_view.view.*
import michalengel.pwr.application2.R
import michalengel.pwr.application2.view.ThumbnailAdapter
import michalengel.pwr.application2.view_model.ImagesViewModel
import org.koin.android.architecture.ext.getViewModel
import org.koin.android.architecture.ext.sharedViewModel
import org.koin.android.architecture.ext.viewModel

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
    private val viewModel by sharedViewModel<ImagesViewModel> ()
    private var listener: OnFragmentInteractionListener? = null
    private val TAG = "MasterViewFragment"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_master_view, container, false)
        Log.d(TAG, "Inflating view")
        Log.d(TAG, "viewModel is $viewModel")
        view.imagesRecyclerView.layoutManager = GridLayoutManager(listener as Context, 4)

        val adapter = ThumbnailAdapter().apply {
            onClickListener = {
                Log.d(TAG, "received onClick image: $it")
                viewModel.select(it)
                listener?.onImagePressed()
            }
        }
        view.imagesRecyclerView.adapter = adapter
        viewModel.images.observe(this, Observer {
            adapter.submitList(it)
        })
        return view
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
        fun onImagePressed()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment MasterViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = MasterViewFragment()
    }
}
