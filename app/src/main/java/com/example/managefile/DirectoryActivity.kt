package com.example.managefile

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class DirectoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directory)

        val listView: ListView = findViewById(R.id.listView)
        val path = intent.getStringExtra("path")
        val directory = File(path ?: "")

        if (directory.exists() && directory.isDirectory) {
            val files = directory.listFiles()
            val fileNames = files?.map { it.name } ?: emptyList()
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, fileNames)

            listView.adapter = adapter

            listView.setOnItemClickListener { _, _, position, _ ->
                val selectedFile = files?.get(position)

                if (selectedFile != null) {
                    if (selectedFile.isDirectory) {
                        val intent = Intent(this, DirectoryActivity::class.java)
                        intent.putExtra("path", selectedFile.absolutePath)
                        startActivity(intent)
                    } else if (selectedFile.isFile) {
                        when (selectedFile.extension) {
                            "txt" -> {
                                val intent = Intent(this, FileContentActivity::class.java)
                                intent.putExtra("path", selectedFile.absolutePath)
                                startActivity(intent)
                            }
                            "docx" -> {
                                val intent = Intent(this, FileContentActivity::class.java)
                                intent.putExtra("path", selectedFile.absolutePath)
                                startActivity(intent)
                            }
                            else -> {
                                Toast.makeText(this, "Unsupported file type!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        } else {
            Toast.makeText(this, "Cannot open directory!", Toast.LENGTH_SHORT).show()
        }
    }
}

