package com.iesvirgendelcarmen.dam.notasapp

import java.text.SimpleDateFormat
import java.util.*

data class Note(var title: String, var text: String, var date: Date) {

    override fun toString(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val formattedDate = formatter.format(date)
        return "$title ($formattedDate)"
    }

}