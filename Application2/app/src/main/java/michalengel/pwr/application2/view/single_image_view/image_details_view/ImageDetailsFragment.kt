package michalengel.pwr.application2.view.single_image_view.image_details_view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_image_details.view.*

import michalengel.pwr.application2.R
import michalengel.pwr.application2.model.ImageDetailsProvider
import org.koin.android.ext.android.inject

class ImageDetailsFragment : Fragment() {

    private val args: ImageDetailsFragmentArgs by navArgs()
    private val detailsProvider by inject<ImageDetailsProvider>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_image_details, container, false)
        val imageUri = args.imageUri
        //setup tabs with viewpager
        view.details_tab_layout.setupWithViewPager(view.details_view_pager)
        //initiate adapter
        view.details_view_pager.adapter = ImageDetailsViewPagerAdapter(
            detailsProvider.getFileDetails(imageUri),
            detailsProvider.getExifDetails(imageUri),
            childFragmentManager
        )
        return view
    }
}
