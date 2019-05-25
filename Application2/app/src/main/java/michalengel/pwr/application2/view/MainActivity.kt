package michalengel.pwr.application2.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.documentfile.provider.DocumentFile
import kotlinx.android.synthetic.main.activity_main.*
import michalengel.pwr.application2.R
import michalengel.pwr.application2.view_model.ImagesUrisViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val PICK_FOLDER_CODE = 1
    private val viewModelUri by viewModel<ImagesUrisViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            if (it.itemId == R.id.action_choose_folder)
                Intent()
                    .apply {
                        action = Intent.ACTION_OPEN_DOCUMENT_TREE
                    }.run {
                        startActivityForResult(this, PICK_FOLDER_CODE)
                    }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK || requestCode != PICK_FOLDER_CODE) {
            Log.e(TAG, "open folder fail, resultCode = $resultCode, requestCode = $requestCode")
            return
        }
        if (data?.data == null) {
            Log.e(TAG, "no data onActivityResult")
            return
        }
        val uri = data.data
        val pickedDirectory = DocumentFile.fromTreeUri(applicationContext, uri!!)!!
        Log.d(TAG, "picked dir = $pickedDirectory")
        viewModelUri.changeRootDocument(pickedDirectory)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
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
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission is granted")
            return true
        } else {
            Log.v(TAG, "Permission is revoked")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 3)
            return false
        }
    }
}
