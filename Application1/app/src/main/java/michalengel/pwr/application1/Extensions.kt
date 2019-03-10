package michalengel.pwr.application1

import android.content.Context
import android.widget.TextView

internal fun TextView.withText (text: String): TextView {
    this.text = text
    return this
}