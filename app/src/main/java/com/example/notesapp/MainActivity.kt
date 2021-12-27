package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

        binding.apply {
            btnSubmit.setOnClickListener {
                val noteContent = etName.text.toString()
                etName.text.clear()
                if (noteContent.isNotEmpty()) {
                    if (databaseHelper.saveData(noteContent).toInt() != -1)
                        Toast.makeText(this@MainActivity, "Added successfully", Toast.LENGTH_LONG).show()

                }
                adapter.update(databaseHelper.readData())
            }
        }
    }

    private fun setupRV() {
        rvNotes = binding.rvNotes
        adapter = NoteAdapter()
        rvNotes.adapter = adapter
        rvNotes.layoutManager = LinearLayoutManager(this)
    }
}