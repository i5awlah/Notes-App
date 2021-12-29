package com.example.notesapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var rvNotes: RecyclerView
    lateinit var adapter: NoteAdapter
    private val noteDao by lazy { NoteDatabase.getDatabase(this).noteDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRV()
        readNote()

        binding.apply {
            btnSubmit.setOnClickListener {
                val noteContent = etName.text.toString()
                etName.text.clear()
                addNote(noteContent)
            }
        }
    }

    private fun readNote() {
        CoroutineScope(IO).launch {
            val data = async { noteDao.getNote() }.await()
            if (data.isNotEmpty()) {
                withContext(Main) {
                    adapter.update(data)
                }
            }
        }
    }

    private fun setupRV() {
        rvNotes = binding.rvNotes
        adapter = NoteAdapter(this)
        rvNotes.adapter = adapter
        rvNotes.layoutManager = LinearLayoutManager(this)
    }

    private fun addNote(noteContent: String) {
        if (noteContent.isNotEmpty()) {
            CoroutineScope(IO).launch {
                noteDao.addNote(Note(0, noteContent))
            }
            Toast.makeText(this@MainActivity, "Added successfully", Toast.LENGTH_LONG).show()


        }

    }

    private fun updateNote(pk: Int, newContent: String) {
        if (newContent.isNotEmpty()) {
            CoroutineScope(IO).launch {
                noteDao.updateNote(Note(pk, newContent))
            }
            Toast.makeText(this@MainActivity, "updated successfully", Toast.LENGTH_LONG).show()

        }
    }

    private fun deleteNote(pk: Int) {
        CoroutineScope(IO).launch {
            noteDao.deleteNote(Note(pk,""))
        }
        Toast.makeText(this@MainActivity, "Deleted successfully", Toast.LENGTH_LONG).show()
    }

    fun showAlert(pk: Int, content: String, type: String){
        val dialogBuilder = AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        updatedNote.setHint(content)
        if (type == "update") {
            dialogBuilder.setMessage("Update note")
                .setPositiveButton("Save") { _, _ ->
                    updateNote(pk, updatedNote.text.toString())
                }
        } else {
            dialogBuilder.setMessage("Are you sure to delete note?")
                .setPositiveButton("Yes") { _, _ ->
                    deleteNote(pk)
                }
        }

        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener {
                dialog, _ -> dialog.cancel()
            })

        val alert = dialogBuilder.create()
        if (type == "update") {
            alert.setTitle("Update")
            alert.setView(updatedNote)
        } else {
            alert.setTitle("delete")
        }
        alert.show()
    }
}