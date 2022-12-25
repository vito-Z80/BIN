package com.example.bin.app

import android.content.Context
import android.widget.Toast
import java.io.File


//  https://www.digitalocean.com/community/tutorials/android-external-storage-read-write-save-file

//  https://stackoverflow.com/questions/44587187/android-how-to-write-a-file-to-internal-storage


fun addBinValue(context: Context, value: String) {
    try {
        val file = File(context.filesDir, "postBins.bank")
        fun exe() {
            val data = file.readLines()
            if (data.contains(value)) return
            if (data.size > 50) {
                file.writeText(data.drop(1).plus(value).sorted().joinToString("\n"))
            } else {
                file.writeText(data.plus(value).sorted().joinToString("\n"))
            }
        }
        if (!file.exists()) {
            file.createNewFile()
        }
        exe()
    } catch (e: Exception) {
        Toast.makeText(context, "No known issues with saving history.", Toast.LENGTH_SHORT).show()
        e.printStackTrace()
    }
}

fun readBinValues(context: Context) =
    try {
        val file = File(context.filesDir, "postBins.bank")
        file.readLines()
    } catch (e: Exception) {
        e.printStackTrace()
        listOf()
    }
