package com.example.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context,"details.db", null, 2) {
    private val sqLiteDatabase: SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        if(db != null){
            db.execSQL("create table notes (Pk integer primary key autoincrement, Content text)")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("drop table if exists notes")
        onCreate(db)
    }

    fun addNote(content: String) : Int {
        val contentValues = ContentValues()
        contentValues.put("Content", content)
        return sqLiteDatabase.insert("notes", null, contentValues).toInt()
    }

    fun readData() : ArrayList<Note> {
        var notes = arrayListOf<Note>()
        val cursor: Cursor = sqLiteDatabase.rawQuery("SELECT * FROM notes", null)

        if (cursor.count == 0) {
            println("No data found!")
        } else {
            while (cursor.moveToNext()) {
                val pk = cursor.getInt(0)
                val content = cursor.getString(1)
                notes.add(Note(pk,content))
            }
        }
        return notes
    }

    fun updateNote(pk: Int, newContent: String) : Int {
        val contentValues = ContentValues()
        contentValues.put("Content", newContent)
        return sqLiteDatabase.update("notes", contentValues, "Pk = $pk", null).toInt()
    }

    fun deleteNote(pk: Int) : Int {
        return sqLiteDatabase.delete("notes", "Pk = $pk", null).toInt()
    }
}