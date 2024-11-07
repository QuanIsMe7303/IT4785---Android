package com.android.week8_exercise2_search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var studentList: List<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val searchBar: EditText = findViewById(R.id.search_bar)
        val listView: ListView = findViewById(R.id.listView)

        studentList = listOf(
            Student("Đỗ Nhật Minh", "20225209"),
            Student("Nhữ Ngọc Minh", "20225046"),
            Student("Trần Hoàng Nhật Minh", "20225366"),
            Student("Vũ Tiến An Nguyên", "20225148"),
            Student("Nguyễn Tuấn Phong", "20225219"),
            Student("Trần Duy Phúc", "20225378"),
            Student("Đào Anh Quân", "20215631"),
            Student("Nguyễn Kiến Quốc", "20225153"),
            Student("Phan Hà Quyên", "20225224"),
            Student("Nguyễn Huyền San", "20225075"),
            Student("Nguyễn Ngọc Sơn", "20235416"),
            Student("Phan Anh Tài", "20225391")
        )

        studentAdapter = StudentAdapter(this, studentList)
        listView.adapter = studentAdapter

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val query = p0.toString()
                if (query.length > 2) {
                    val filteredList = studentList.filter {
                        it.name.contains(query, ignoreCase = true) || it.id.contains(query, ignoreCase = true)
                    }
                    studentAdapter.updateList(filteredList)
                } else {
                    studentAdapter.updateList(studentList)
                }
            }

            override fun afterTextChanged(p0: Editable?) {}

        })

    }
}