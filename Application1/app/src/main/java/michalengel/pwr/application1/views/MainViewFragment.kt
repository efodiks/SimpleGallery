package michalengel.pwr.application1.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import michalengel.pwr.application1.R

class MainViewFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }
    companion object {
        fun newInstance(): MainViewFragment {return MainViewFragment()}
    }
}