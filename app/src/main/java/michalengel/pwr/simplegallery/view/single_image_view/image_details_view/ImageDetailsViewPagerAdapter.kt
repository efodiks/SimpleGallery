package michalengel.pwr.simplegallery.view.single_image_view.image_details_view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ImageDetailsViewPagerAdapter(
    private val fileProps: HashMap<String, String>,
    private val exifProps: HashMap<String, String>,
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pageTitles = arrayOf("File info", "Exif info")

    override fun getItem(position: Int): Fragment {
        return if (position == 0) ImageDetailsViewPagerItem.newInstance(fileProps)
        else ImageDetailsViewPagerItem.newInstance(exifProps)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return pageTitles[position]
    }

    override fun getCount(): Int = 2
}