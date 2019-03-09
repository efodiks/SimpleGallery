package michalengel.pwr.application1

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*
import michalengel.pwr.application1.views.MainViewFragment
import michalengel.pwr.application1.views.NoteEditionFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        addMainViewFragment()
    }
    private fun addMainViewFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, MainViewFragment.newInstance())
            .commit()
    }
    fun floatingButtonHandler (v: View) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, NoteEditionFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }
}
