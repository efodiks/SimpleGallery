package michalengel.pwr.application1.activities

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_widget_abundance.*
import michalengel.pwr.application1.R

class WidgetAbundanceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widget_abundance)
        useless_button1.setOnClickListener { Toast.makeText(this, "First button clicked", Toast.LENGTH_SHORT).show() }
        useless_button2.setOnLongClickListener (this::longClickListener)
    }
    fun longClickListener (view: View): Boolean {
        Toast.makeText(this, "Second button clicked", Toast.LENGTH_LONG).show()
        return true
    }
    fun checkBoxListener (view: View) {
        if (view is CheckBox) {
            var message = ""
            val checked = view.isChecked
            when(view.id) {
                R.id.checkbox_1 -> message = "checkbox1"
                R.id.checkbox_2 -> message = "checkbox2"
            }
            Toast.makeText(this, message + (if (checked) " checked" else " unchecked"), Toast.LENGTH_SHORT).show()
        }
    }
}