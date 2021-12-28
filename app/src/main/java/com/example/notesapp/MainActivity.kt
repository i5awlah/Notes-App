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

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var rvNotes: RecyclerView
    lateinit var adapter: NoteAdapter
    private val databaseHelper by lazy { DatabaseHelper(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRV()
        adapter.update(databaseHelper.readData())

        binding.apply {
            btnSubmit.setOnClickListener {
                val noteContent = etName.text.toString()
                etName.text.clear()
                addNote(noteContent)
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
            if (databaseHelper.addNote(noteContent) != -1)
                Toast.makeText(this@MainActivity, "Added successfully", Toast.LENGTH_LONG).show()

        }
        adapter.update(databaseHelper.readData())
    }

    private fun updateNote(pk: Int, newContent: String) {
        if (newContent.isNotEmpty()) {
            if (databaseHelper.updateNote(pk, newContent) != -1)
                Toast.makeText(this@MainActivity, "updated successfully", Toast.LENGTH_LONG).show()

        }
        adapter.update(databaseHelper.readData())
    }

    private fun deleteNote(pk: Int) {
            if (databaseHelper.deleteNote(pk) != -1)
                Toast.makeText(this@MainActivity, "Deleted successfully", Toast.LENGTH_LONG).show()

        adapter.update(databaseHelper.readData())
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