package com.android.week8_exercise1_simplelist

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.sign
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Khai bao bien
        val number: EditText = findViewById(R.id.inputNumber)

        val radGrp: RadioGroup = findViewById(R.id.radio_group)
        val radEven: RadioButton = findViewById(R.id.even_numbers)
        val radOdd: RadioButton = findViewById(R.id.odd_numbers)
        val radSquare: RadioButton = findViewById(R.id.square_numbers)

        val showButton: Button = findViewById(R.id.show_result_button)
        val listView: ListView = findViewById(R.id.result_listView)
        val errorText: TextView = findViewById(R.id.error_text)

        // Chon radio button

        var items: ArrayList<Int> = arrayListOf()
        val adapter: ArrayAdapter<Int> = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        listView.adapter = adapter


        // Nut hien thi ket qua
        showButton.setOnClickListener {
            val radioId = radGrp.checkedRadioButtonId

            errorText.text = ""

            if (number.text.isEmpty()) {
                errorText.setText("Bạn chưa nhập gì")
            } else {
                val numberInputString = number.text.toString()
                if (!numberInputString.all { it.isDigit() }) {
                    errorText.text = "Hãy nhập đúng định dạng số!"
                } else {
                    val numberInput: Int = numberInputString.toInt()

                    if (numberInput <= 0) {
                        errorText.text = "Hãy nhập số nguyên dương!"
                    } else {
                        when (radioId) {
                            radEven.id -> {
                                items.clear()
                                for (num in 0..numberInput step 2) {
                                    items.add(num)
                                }
                            }
                            radOdd.id -> {
                                items.clear()
                                for (num in 1..numberInput step 2) {
                                    items.add(num)
                                }
                            }
                            radSquare.id -> {
                                items.clear()
                                for (num in 1..sqrt(numberInput.toDouble()).toInt()) {
                                    items.add(num * num)
                                }
                            }
                            else -> {
                                errorText.text = "Hãy lựa chọn loại số!"
                            }
                        }
                    }
                }

            }

            adapter.notifyDataSetChanged()

        }
    }
}