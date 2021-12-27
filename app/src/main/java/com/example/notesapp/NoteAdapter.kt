package com.example.notesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.RowNoteBinding

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    var notes = arrayListOf<String>()
    class NoteViewHolder(val binding: RowNoteBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(RowNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.binding.apply {
            tvNote.text = note
        }
    }

    override fun getItemCount() = notes.size

    fun update(notes: ArrayList<String>) {
        this.notes = notes
        notifyDataSetChanged()
    }
}