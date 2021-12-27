package com.example.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context,"details.db", null, 1) {
    private val sqLiteDatabase: SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        if(db != null){
            db.execSQL("create table notes (Content text)")
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun saveData(content: String) : Long {
        val contentValues = ContentValues()
        contentValues.put("Content", content)
        return sqLiteDatabase.insert("notes", null, contentValues)
    }
}