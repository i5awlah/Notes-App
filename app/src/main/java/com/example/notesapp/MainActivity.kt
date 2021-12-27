package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val databaseHelper by lazy { DatabaseHelper(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnSubmit.setOnClickListener {
                val noteContent = etName.text.toString()
                etName.text.clear()
                if (noteContent.isNotEmpty()) {
                    if (databaseHelper.saveData(noteContent).toInt() != -1)
                        Toast.makeText(this@MainActivity, "Added successfully", Toast.LENGTH_LONG).show()

                }
            }
        }
    }
}