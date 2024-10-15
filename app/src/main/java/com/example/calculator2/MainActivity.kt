package com.example.calculator2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var textResult: TextView
    lateinit var opDisplay: TextView
    var state: Int = 1
    var op: Int = 0
    var op1: Int = 0
    var op2: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textResult = findViewById<TextView>(R.id.result)
        opDisplay = findViewById<TextView>(R.id.showOp)

        findViewById<Button>(R.id.number0).setOnClickListener(this)
        findViewById<Button>(R.id.number1).setOnClickListener(this)
        findViewById<Button>(R.id.number2).setOnClickListener(this)
        findViewById<Button>(R.id.number3).setOnClickListener(this)
        findViewById<Button>(R.id.number4).setOnClickListener(this)
        findViewById<Button>(R.id.number5).setOnClickListener(this)
        findViewById<Button>(R.id.number6).setOnClickListener(this)
        findViewById<Button>(R.id.number7).setOnClickListener(this)
        findViewById<Button>(R.id.number8).setOnClickListener(this)
        findViewById<Button>(R.id.number9).setOnClickListener(this)
        findViewById<Button>(R.id.plus).setOnClickListener(this)
        findViewById<Button>(R.id.minus).setOnClickListener(this)
        findViewById<Button>(R.id.multiply).setOnClickListener(this)
        findViewById<Button>(R.id.divide).setOnClickListener(this)
        findViewById<Button>(R.id.equal).setOnClickListener(this)
        findViewById<Button>(R.id.sign).setOnClickListener(this)
        findViewById<Button>(R.id.clearAll).setOnClickListener(this)
        findViewById<Button>(R.id.clearCur).setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val id = p0?.id

        if (id == R.id.number0) {
            addDigit(0)
        } else if (id == R.id.number1) {
            addDigit(1)
        } else if (id == R.id.number2) {
            addDigit(2)
        } else if (id == R.id.number3) {
            addDigit(3)
        } else if (id == R.id.number4) {
            addDigit(4)
        } else if (id == R.id.number5) {
            addDigit(5)
        } else if (id == R.id.number6) {
            addDigit(6)
        } else if (id == R.id.number7) {
            addDigit(7)
        } else if (id == R.id.number8) {
            addDigit(8)
        } else if (id == R.id.number9) {
            addDigit(9)
        } else if (id == R.id.plus) {
            op = 1
            state = 2
            opDisplay.text = "+"
        } else if (id == R.id.minus) {
            op = 2
            state = 2
            opDisplay.text = "-"
        } else if (id == R.id.multiply) {
            op = 3
            state = 2
            opDisplay.text = "*"
        } else if (id == R.id.divide) {
            op = 4
            state = 2
            opDisplay.text = "/"
        } else if (id == R.id.equal) {
            var result = 0

            if (op == 1) { // Phep cong
                result = op1 + op2
            } else if (op == 2) { // Phep tru
                result = op1 - op2
            } else if (op == 3) { // Phep nhan
                result = op1 * op2
            } else if (op == 4 && op2 != 0) { // Phep chia
                result = op1 / op2
            }

            opDisplay.text = ""
            textResult.text = "$result"
            state = 1
            op1 = result
            op2 = 0
            op = 0
        } else if (id == R.id.sign) {
            if (state == 1) {
                op1 = -op1
                textResult.text = "$op1"
            }
            else if (state == 2) {
                op2 = -op2
                textResult.text = "$op2"
            }
        } else if (id == R.id.clearAll) {
            textResult.text = "0"
            op1 = 0
            op2 = 0
            op = 0
            state = 1
        } else if (id == R.id.clearCur) {
            if (state == 1) {
                op1 = 0
                textResult.text = "$op1"
            } else if (state == 2) {
                op2 = 0
                textResult.text = "$op2"
            }
        }



    }

    fun addDigit(c: Int) {
        if (state == 1) {
            op1 = op1 * 10 + c
            textResult.text = "$op1"
        } else {
            op2 = op2 * 10 + c
            textResult.text = "$op2"
        }
    }
}