package michalengel.pwr.application1.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import michalengel.pwr.application1.R
import michalengel.pwr.application1.activities.WidgetAbundanceActivity
import michalengel.pwr.application1.adapters.NoteListAdapter
import michalengel.pwr.application1.domain.Note
import java.lang.ClassCastException

class MainViewFragment : Fragment() {
    lateinit var provider: NotesProvider
    lateinit var notesDataSet: MutableList<Note>

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        provider = context as? NotesProvider ?: throw ClassCastException(context?.toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "calling onViewCreated")
        //set onClicks
        fab_add_note.setOnClickListener(this::startNoteEditionFragment)
        fab_widget_madness.setOnClickListener(this::startWidgetAbundanceActivity)

        notesDataSet = provider.provideWithNotes()
        Log.d(TAG, "creating dataSet from: $notesDataSet")
        Log.d(TAG, "activity = $activity")

        val a = activity as? Activity ?: throw IllegalStateException(getString(R.string.ActivityNullExceptionMessage))
        view.recycler_view.adapter = NoteListAdapter(a, notesDataSet)
        view.recycler_view.layoutManager = GridLayoutManager(a, 2)

    }

    private fun startNoteEditionFragment(v: View) {
        (activity?.supportFragmentManager
            ?: throw IllegalStateException(getString(R.string.ActivityNullExceptionMessage)))
            .beginTransaction()
            .replace(R.id.fragment_container, NoteEditionFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    private fun startWidgetAbundanceActivity (v: View) {
        val a = activity as? Activity ?: throw IllegalStateException(getString(R.string.ActivityNullExceptionMessage))
        val intent = Intent(a, WidgetAbundanceActivity::class.java)
        startActivity(intent)
    }


    interface NotesProvider {
        fun provideWithNotes(): MutableList<Note>
    }

    companion object {
        const val TAG = "Main_fragment"
        fun newInstance(): MainViewFragment {
            return MainViewFragment()
        }
    }
}