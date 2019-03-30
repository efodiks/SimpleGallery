package michalengel.pwr.application2.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detail_view.view.*
import michalengel.pwr.application2.R
import michalengel.pwr.application2.view_model.ImagesViewModel
import org.koin.android.architecture.ext.sharedViewModel
import org.koin.android.architecture.ext.viewModel


class DetailViewFragment : Fragment() {
    private val viewModel by sharedViewModel<ImagesViewModel>()
    val TAG = "DetailViewFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail_view, container, false)

        val selected = viewModel.selected.value
        Log.d(TAG, "selected image is $selected")

        Glide.with(context ?: throw IllegalStateException())
            .load(viewModel.selected.value?.path)
            .into(view.detail_image)
        return view
    }

    companion object {
        fun newInstance(): DetailViewFragment {return DetailViewFragment()
        }
    }
}