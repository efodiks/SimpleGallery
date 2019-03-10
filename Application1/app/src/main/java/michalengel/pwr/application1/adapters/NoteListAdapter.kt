package michalengel.pwr.application1.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.notelist_item.view.*
import michalengel.pwr.application1.R
import michalengel.pwr.application1.domain.Note

class NoteListAdapter(private val context: Context, private val notes: MutableList<Note>) :
    RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = inflater.inflate(R.layout.notelist_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, postion: Int) {
        val currentNote = notes[postion]
            currentNote.subnotes
                .filter {it.isNotBlank()}
                .map {
                    TextView(context).apply {
                        text = it
                        setTextSize(TypedValue.COMPLEX_UNIT_SP, resources.getDimension(R.dimen.note_item_textSize))
                    }
                }
                .forEach(holder.textViewList::addView)
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewList: LinearLayout = itemView.item_textViewList
    }
}