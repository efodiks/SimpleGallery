package michalengel.pwr.simplegallery.model

import android.content.ContentResolver
import android.media.ExifInterface
import android.net.Uri
import java.io.File

class ImageDetailsProvider(private val contentResolver: ContentResolver) {
    fun getFileDetails(uri: Uri): HashMap<String, String> {
        val result = HashMap<String, String>()
        val file = File(uri.toString())
        result["Path"] = file.path
        result["Name"] = file.name
        result["Size"] = file.length().toString()
        result["Is hidden"] = file.isHidden.toString()
        return result
    }
    fun getExifDetails(uri: Uri): HashMap<String, String> {
        val exif = ExifInterface(contentResolver.openInputStream(uri))
        val result = HashMap<String, String>()
        result["Image Length"] = exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH) ?: ""
        result["Image Width"] = exif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH) ?: ""

        result["Date and time taken"] = exif.getAttribute(ExifInterface.TAG_DATETIME) ?: ""
        result["Artist"] = exif.getAttribute(ExifInterface.TAG_ARTIST) ?: ""
        result["Copyright"] = exif.getAttribute(ExifInterface.TAG_COPYRIGHT) ?: ""

        result["Camera model"] = exif.getAttribute(ExifInterface.TAG_MODEL) ?: ""
        result["Orientation"] = exif.getAttribute(ExifInterface.TAG_ORIENTATION) ?: ""
        result["White balance"] = exif.getAttribute(ExifInterface.TAG_WHITE_BALANCE) ?: ""

        result["Brightness"] = exif.getAttribute(ExifInterface.TAG_BRIGHTNESS_VALUE) ?: ""
        result["Contrast"] = exif.getAttribute(ExifInterface.TAG_CONTRAST) ?: ""

        result["Flash"] = exif.getAttribute(ExifInterface.TAG_FLASH) ?: ""

        result["Gps longitude"] = exif.getAttribute(ExifInterface.TAG_GPS_DEST_LONGITUDE) ?: ""
        result["Gps latitude"] = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE) ?: ""
        result["Gps timestamp"] = exif.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP) ?: ""

        return result
    }
}