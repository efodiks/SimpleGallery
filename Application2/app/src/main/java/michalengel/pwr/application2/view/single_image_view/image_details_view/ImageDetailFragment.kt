package michalengel.pwr.application2.view.single_image_view.image_details_view


import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_image_detail.view.*

import michalengel.pwr.application2.R
import michalengel.pwr.application2.model.ImageDetailsProvider
import org.koin.android.ext.android.inject

class ImageDetailFragment : Fragment() {

    private val args: ImageDetailFragmentArgs by navArgs()
    private val detailsProvider by inject<ImageDetailsProvider>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
/*        arguments?.let {
            imageUri = it.getParcelable<Uri>(ARG_IMAGE_URI)!!
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_image_detail, container, false)
        val imageUri = args.imageUri
        //setup tabs with viewpager
        view.details_tab_layout.setupWithViewPager(view.details_view_pager)
        //initiate adapter
        view.details_view_pager.adapter = DetailsViewPagerAdapter(
            detailsProvider.getFileDetails(imageUri),
            detailsProvider.getExifDetails(imageUri),
            childFragmentManager
        )
        return view
    }


    companion object {
        private const val ARG_IMAGE_URI = "image_uri"

/*        @JvmStatic
        fun newInstance(imageUri: Uri) =
            ImageDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_IMAGE_URI, imageUri)
                }
            }*/
    }
}
