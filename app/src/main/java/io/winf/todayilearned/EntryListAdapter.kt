package io.winf.todayilearned

import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TextView
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.winf.todayilearned.utils.DateFormatter


class EntryListAdapter internal constructor(context: Context) : RecyclerView.Adapter<EntryListAdapter.EntryViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var cachedEntries = emptyList<Entry>()
    private val dateFormatter = DateFormatter() // ToDo inject this

    inner class EntryViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val entryItemDateTimeView: TextView = itemView.findViewById(R.id.entryDate)
        val entryItemTextView: TextView = itemView.findViewById(R.id.entryText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return EntryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val current = cachedEntries[position]
        holder.entryItemDateTimeView.text = dateFormatter.formatDate(current.entryDateTime)
        holder.entryItemTextView.text = current.entryText
    }

    override fun getItemCount(): Int {
        return cachedEntries.size
    }

    internal fun updateCachedEntries(entries: List<Entry>) {
        this.cachedEntries = entries
        notifyDataSetChanged()
    }
}