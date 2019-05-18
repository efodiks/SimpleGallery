package michalengel.pwr.application2.view.single_image_view.image_details_view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_details_view_pager.*
import kotlinx.android.synthetic.main.fragment_details_view_pager.view.*
import michalengel.pwr.application2.R

class DetailsViewPagerFragment : Fragment() {
    private lateinit var text: HashMap<String, String>
    val TAG = this.javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            text = it.getSerializable(ARG_TEXT) as HashMap<String, String>
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_details_view_pager, container, false)
        view.details_text_view.text = processMap(text)
        Log.d(TAG, "setting text to ${processMap(text)}")
        return view
    }

    companion object {
       private const val ARG_TEXT = "text"
        @JvmStatic
        fun newInstance(text: HashMap<String, String>): DetailsViewPagerFragment {
            return DetailsViewPagerFragment()
                .apply {
                    arguments = Bundle()
                        .apply { putSerializable(ARG_TEXT, text) }
                }
        }
    }

    private fun processMap(map: HashMap<String, String>): String {
        return map.entries.fold(StringBuilder(), {acc, entry -> acc.append("${entry.key}: ${entry.value}\n")} ).toString()
    }
}