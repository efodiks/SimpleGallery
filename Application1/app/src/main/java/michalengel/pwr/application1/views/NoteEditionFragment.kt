package michalengel.pwr.application1.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.EditText
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_note_edition.*
import michalengel.pwr.application1.R
import michalengel.pwr.application1.domain.Note
import kotlin.collections.ArrayList

class NoteEditionFragment : Fragment() {
    private val notesEditTexts = ArrayList<EditText>()
    private lateinit var listener: NoteEditionListener

    interface NoteEditionListener {
        fun onNoteDismissed (notes: Note)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_note_edition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        notesEditTexts.add(main_note_edittext)
        add_subnote_button.setOnClickListener { addAnotherEditText() }
    }

    override fun onDestroy() {
        super.onDestroy()
        listener.onNoteDismissed(Note(notesEditTexts.map { it.text.toString() }))
    }

    private fun addAnotherEditText () {
        val newNote = EditText(activity)
        notesEditTexts.add(newNote)
        newNote.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        newNote.hint = resources.getString(R.string.note_hint)
        newNote.inputType = InputType.TYPE_TEXT_FLAG_AUTO_CORRECT
        newNote.textAlignment = EditText.TEXT_ALIGNMENT_CENTER
        parent_layout.addView(newNote, parent_layout.childCount - 1)
    }

    companion object {
        fun newInstance(): NoteEditionFragment {return NoteEditionFragment()}
    }
}
