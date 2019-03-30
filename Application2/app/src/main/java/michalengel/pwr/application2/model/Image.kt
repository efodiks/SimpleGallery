package michalengel.pwr.application2.model

import android.os.Parcel
import android.os.Parcelable


data class Image (val id: Long, val path: String, val dateTaken: String?, val description: String?) : Parcelable {

    @JvmField val CREATOR = object: Parcelable.Creator<Image> {
        override fun createFromParcel(source: Parcel): Image {
            return Image(source)
        }

        override fun newArray(size: Int): Array<Image?> {
            return arrayOfNulls<Image>(size)
        }
    }

    constructor(inParcel: Parcel):
            this(inParcel.readLong(),
                inParcel.readString(),
                inParcel.readString(),
                inParcel.readString())

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeLong(id)
        dest?.writeString(path)
        dest?.writeString(dateTaken)
        dest?.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }
}