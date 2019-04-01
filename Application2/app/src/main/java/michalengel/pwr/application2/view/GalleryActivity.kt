package michalengel.pwr.application2.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_gallery.*
import michalengel.pwr.application2.R
import michalengel.pwr.application2.view.fragments.DetailViewFragment
import michalengel.pwr.application2.view.fragments.MasterViewFragment
import michalengel.pwr.application2.view_model.ImagesViewModel
import org.koin.android.architecture.ext.getViewModel


class GalleryActivity : AppCompatActivity(), MasterViewFragment.OnFragmentInteractionListener {
    private val TAG = "GalleryActivity"
    private lateinit var viewModel: ImagesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel<ImagesViewModel>()
        setContentView(michalengel.pwr.application2.R.layout.activity_gallery)
        setSupportActionBar(toolbar)

        if (isReadStoragePermissionGranted()) {
            instantiateMasterViewFragment()
        }
    }
    private fun instantiateMasterViewFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, MasterViewFragment.newInstance())
            .commit()
    }

    override fun onImagePressed() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, DetailViewFragment.newInstance())
            .addToBackStack("detailView")
            .commit()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            2 -> {
                Log.d(TAG, "External storage2")
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0])
                }
            }
            3 -> {
                Log.d(TAG, "External storage1")
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0])
                }
            }
        }
    }

    fun isReadStoragePermissionGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted")
                return true
            } else {
                Log.v(TAG, "Permission is revoked")
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 3)
                return false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted")
            return true
        }
    }
}
