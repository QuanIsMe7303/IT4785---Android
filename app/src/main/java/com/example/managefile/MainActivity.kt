package com.example.managefile

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.File
import java.io.FileInputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        val listView: ListView = findViewById(R.id.listView)
        val rootDir = Environment.getExternalStorageDirectory()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivity(intent)
            }

        if (rootDir.exists() && rootDir.isDirectory) {
            showFilesAndDirectories(listView, rootDir)
        } else {
            Toast.makeText(this, "Cannot access storage!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showFilesAndDirectories(listView: ListView, directory: File) {
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
    }
}

