package michalengel.pwr.application2.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.content_gallery.*
import michalengel.pwr.application2.R
import michalengel.pwr.application2.model.Image
import michalengel.pwr.application2.view.fragments.DetailViewFragment
import michalengel.pwr.application2.view_model.ImagesViewModel
import org.koin.android.architecture.ext.viewModel


class GalleryActivity : AppCompatActivity() {
    private val viewModel by viewModel<ImagesViewModel>()
    private val TAG = "GalleryActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(michalengel.pwr.application2.R.layout.activity_gallery)
        setSupportActionBar(toolbar)
        if (isReadStoragePermissionGranted()) {
            imagesRecyclerView.layoutManager = GridLayoutManager(this, 4)
            val adapter = ThumbnailAdapter().apply {
                onClickListener = {
                    Log.d(TAG, "received onClick image: $it")
                    viewModel.select(it)
                    moveToDetailView()
                }
            }
            imagesRecyclerView.adapter = adapter
            viewModel.images.observe(this, Observer {
                adapter.submitList(it)
            })
        }
    }

    fun moveToDetailView() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, DetailViewFragment.newInstance())
            .commit()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(michalengel.pwr.application2.R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            michalengel.pwr.application2.R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
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
