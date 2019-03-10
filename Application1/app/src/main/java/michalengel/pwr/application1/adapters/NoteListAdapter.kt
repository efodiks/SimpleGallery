package michalengel.pwr.application1.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.notelist_item.view.*
import michalengel.pwr.application1.R
import michalengel.pwr.application1.domain.Note
import michalengel.pwr.application1.withText

class NoteListAdapter(val context: Context, val notes: MutableList<Note>) :
    RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = inflater.inflate(R.layout.notelist_item, parent, false)
        return NoteViewHolder(view, this)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, postion: Int) {
        val currentNote = notes[postion]
        currentNote.subnotes
            .map { TextView(context).withText(it) }
            .forEach(holder.textViewList::addView)
    }

    class NoteViewHolder(itemView: View, adapter: NoteListAdapter) : RecyclerView.ViewHolder(itemView) {
        val textViewList = itemView.item_textViewList
    }
}