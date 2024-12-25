package com.example.managefile

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileReader
import java.io.BufferedReader
import java.io.FileInputStream

class FileContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_content)

        val textView: TextView = findViewById(R.id.textView)
        val path = intent.getStringExtra("path")
        val file = File(path ?: "")

        if (file.exists() && file.isFile) {
            when (file.extension) {
                "txt" -> {
                    val content = FileInputStream(file).bufferedReader().use { it.readText() }
                    textView.text = content
                }
                "docx" -> {
                    try {
                        val fis = FileInputStream(file)
                        val document = org.apache.poi.xwpf.usermodel.XWPFDocument(fis)
                        val paragraphs = document.paragraphs

                        val content = paragraphs.joinToString("\n") { it.text }
                        textView.text = content
                        fis.close()
                    } catch (e: Exception) {
                        textView.text = "Error reading .docx file: ${e.message}"
                    }
                }
                else -> {
                    textView.text = "Unsupported file type!"
                }
            }
        } else {
            textView.text = "Cannot open file!"
        }
    }
}