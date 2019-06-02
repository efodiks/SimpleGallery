package michalengel.pwr.simplegallery.view

import android.view.View
import androidx.viewpager.widget.ViewPager


class ViewPagerAnimation : ViewPager.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.alpha = if (Math.abs(position) > 1) 0f else 1 - Math.abs(position)
    }
}