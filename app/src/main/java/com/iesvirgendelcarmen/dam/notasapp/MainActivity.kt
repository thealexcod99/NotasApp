package com.iesvirgendelcarmen.dam.notasapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter: ArrayAdapter<Note>
    lateinit var editTextTitle: EditText
    lateinit var editTextNote: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextTitle = findViewById<EditText>(R.id.editTextTitle)
        editTextNote = findViewById<EditText>(R.id.editTextNewNote)
        val buttonNewNote = findViewById<Button>(R.id.buttonNewNote)
        val listViewNotes = findViewById<ListView>(R.id.listViewNotes)

        buttonNewNote.setOnClickListener {
            val title = editTextTitle.text.toString()
            val text = editTextNote.text.toString()
            val note = Note(title, text, Date())
            NoteRepository.add(note)
            adapter.notifyDataSetChanged()
            cleanViews()
        }


        adapter =
            ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1, NoteRepository.notes)
        listViewNotes.adapter = adapter

        listViewNotes.setOnItemClickListener() { parent, view, position, id ->
            editTextTitle.setText(NoteRepository.get(position).title)
            editTextNote.setText(NoteRepository.get(position).text)
        }

        listViewNotes.setOnItemLongClickListener { _, _, position, _ ->
            NoteRepository.remove(position)
            adapter.notifyDataSetChanged()
            true
        }
    }

    fun cleanViews() {
        editTextTitle.setText("")
        editTextNote.setText("")
    }

    override fun onStart() {
        super.onStart()
        NoteRepository.read(this)
        adapter.notifyDataSetChanged()
    }

    override fun onStop() {
        super.onStop()
        NoteRepository.save(this)
        adapter.notifyDataSetChanged()
    }
}
