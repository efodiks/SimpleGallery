package michalengel.pwr.application1.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.view.*
import michalengel.pwr.application1.R
import michalengel.pwr.application1.adapters.NoteListAdapter
import michalengel.pwr.application1.domain.Note
import java.lang.ClassCastException

class MainViewFragment : Fragment() {
    lateinit var adapter: NoteListAdapter
    lateinit var provider: NotesProvider
    lateinit var notesDataSet: MutableList<Note>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        provider = context as? NotesProvider ?: throw ClassCastException(context?.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesDataSet = provider.provideWithNotes()
        Log.d(TAG, "creating dataSet from: $notesDataSet")
        Log.d(TAG, "activity = $activity")
        adapter = NoteListAdapter(activity as Activity, notesDataSet)
        view.recycler_view.adapter = adapter
        view.recycler_view.layoutManager = GridLayoutManager(activity as Activity, 2)
    }

    interface NotesProvider {
        fun provideWithNotes(): MutableList<Note>
    }
    companion object {
        const val TAG = "Main_fragment"
        fun newInstance(): MainViewFragment {return MainViewFragment()}
    }
}