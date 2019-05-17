package michalengel.pwr.application2.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import michalengel.pwr.application2.view.fragments.ViewPagerItem
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.viewpager_item.view.*
import michalengel.pwr.application2.R


class ViewPagerAdapter(fragmentManager: FragmentManager, private val imagesUris: List<Uri>) :
    FragmentStatePagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return imagesUris.size
    }

    override fun getItem(position: Int): Fragment {
        val fragment =  ViewPagerItem.newInstance()
        fragment.arguments = Bundle()
            .apply { putParcelable("imageUri" ,imagesUris[position]) }
        return fragment
    }

//    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`
//
//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        val inflater = LayoutInflater.from(context)
//        val view = inflater.inflate(R.layout.viewpager_item, container, false) as ViewGroup
//        Glide.with(context)
//            .load(imagesUris[position])
//            .into(view.detail_image)
//        container.addView(view)
//        return view
//    }
//
//    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        container.removeView(`object` as View)
//    }

//    override fun getCount(): Int = imagesUris.size
}