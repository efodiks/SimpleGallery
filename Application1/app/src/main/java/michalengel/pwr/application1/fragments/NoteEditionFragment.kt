package michalengel.pwr.application1.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputType
import android.util.Log
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
import java.lang.ClassCastException
import kotlin.collections.ArrayList

class NoteEditionFragment : Fragment() {
    private val noteEditTexts = ArrayList<EditText>()
    private lateinit var listener: NoteEditionListener

    interface NoteEditionListener {
        fun onNoteDismissed (note: Note)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_note_edition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        noteEditTexts.add(main_note_edittext)
        add_subnote_button.setOnClickListener { addAnotherEditText() }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as? NoteEditionListener ?: throw ClassCastException(context.toString())
    }

    override fun onPause() {
        super.onPause()
        val noteTexts: MutableList<String> = noteEditTexts.map { it.text.toString() }.toMutableList()
        Log.d(TAG, "listiner = $listener")
        Log.d(TAG, "sending notes: $noteTexts")
        listener.onNoteDismissed(Note(noteTexts))
    }

    private fun addAnotherEditText () {
        val newNote = EditText(activity)
        noteEditTexts.add(newNote)
        newNote.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        newNote.hint = resources.getString(R.string.note_hint)
        newNote.inputType = InputType.TYPE_TEXT_FLAG_AUTO_CORRECT
        newNote.textAlignment = EditText.TEXT_ALIGNMENT_CENTER
        parent_layout.addView(newNote, parent_layout.childCount - 1)
        newNote.isFocusableInTouchMode = true
        newNote.requestFocus()
    }

    companion object {
        const val TAG = "Note_creation_fragment"
        fun newInstance(): NoteEditionFragment {return NoteEditionFragment()}
    }
}
