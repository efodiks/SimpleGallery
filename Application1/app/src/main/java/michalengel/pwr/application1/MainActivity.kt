package michalengel.pwr.application1

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*
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
    fun floatingButtonHandler (v: View) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, NoteEditionFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    override fun onNoteDismissed(note: Note) {
        notes.add(note)
        Log.d(TAG, note.toString())
    }

    override fun provideWithNotes(): MutableList<Note> {
        Log.d(TAG, notes.toString())
        return notes
    }

    companion object {
        const val TAG = "Main_activity"
    }
}
