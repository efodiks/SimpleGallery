package michalengel.pwr.application1

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*
import michalengel.pwr.application1.activities.WidgetAbundanceActivity
import michalengel.pwr.application1.domain.Note
import michalengel.pwr.application1.fragments.MainViewFragment
import michalengel.pwr.application1.fragments.NoteEditionFragment

class MainActivity : AppCompatActivity(), NoteEditionFragment.NoteEditionListener, MainViewFragment.NotesProvider {
    private val notes: MutableList<Note> = mutableListOf()

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

    override fun onNoteDismissed(note: Note) {
        Log.d(TAG, "receiving note: $note")
        note.subnotes = note.subnotes.filter { it.isNotBlank() }
        if (note.subnotes.isNotEmpty()) {
            notes.add(note)
            Log.d(TAG, "Adding note: $note")
        }
    }

    override fun provideWithNotes(): MutableList<Note> {
        Log.d(TAG, "sending notes: $notes")
        return notes
    }

    companion object {
        const val TAG = "Main_activity"
    }
}
