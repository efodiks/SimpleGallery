package michalengel.pwr.application2.view.single_image_view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val imagesUris: List<Uri>) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return imagesUris.size
    }

    override fun getItem(position: Int): Fragment {
        Log.d("ViewPagerAdapter", "getting item at position $position")
        val fragment = ViewPagerItem.newInstance()
        fragment.arguments = Bundle()
            .apply { putParcelable("imageUri", imagesUris[position]) }
        return fragment
    }
}