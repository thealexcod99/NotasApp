package com.iesvirgendelcarmen.dam.notasapp

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat


object NoteRepository {

    val notes = mutableListOf<Note>()
    val FILENAME = "notes"

    fun add(note: Note) {
        notes.add(note)
    }

    fun delete(note: Note) {
        notes.remove(note)
    }

    operator fun get(pos: Int): Note = notes[pos]

    fun save(context: Context) {
        var file = File(context.filesDir, FILENAME)

        file.writer().use {
            for (note in notes) {
                it.write(note.title + System.getProperty("line.separator"))
                it.write(note.text + System.getProperty("line.separator"))
                it.write(note.date.toString() + System.getProperty("line.separator"))
            }
        }
    }

    fun read(context: Context) {
        notes.clear()
        try {
            var file = File(context.filesDir, FILENAME)
            val lines = file.readLines()
            for (i in 0 until lines.size step 3) {
                val title = lines[i]
                val text = lines[i + 1]
                val date = lines[i + 2]
                val note =
                    Note(title, text, SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy").parse(date))
                notes.add(note)
            }
        } catch (e: Exception) {

        }
    }

    fun remove(position: Int) {
        notes.removeAt(position)
    }

}