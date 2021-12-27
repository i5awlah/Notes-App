package com.example.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context,"details.db", null, 1) {
    private val sqLiteDatabase: SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        if(db != null){
            db.execSQL("create table notes (Content text)")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun saveData(content: String) : Long {
        val contentValues = ContentValues()
        contentValues.put("Content", content)
        return sqLiteDatabase.insert("notes", null, contentValues)
    }
    fun readData() : ArrayList<String> {
        var notes = arrayListOf<String>()
        val cursor: Cursor = sqLiteDatabase.rawQuery("SELECT * FROM notes", null)

        if (cursor.count == 0) {
            println("No data found!")
        } else {
            while (cursor.moveToNext()) {
                val content = cursor.getString(0)
                notes.add(content)
            }
        }
        return notes
    }
}