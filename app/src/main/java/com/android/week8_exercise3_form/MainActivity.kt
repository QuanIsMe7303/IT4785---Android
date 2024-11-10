package com.android.week8_exercise3_form

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var addressHelper: AddressHelper
    private lateinit var calendarView: CalendarView
    private lateinit var toggleCalendarButton: Button
    private lateinit var provinceSpinner: Spinner
    private lateinit var districtSpinner: Spinner
    private lateinit var wardSpinner: Spinner
    private lateinit var selectedDateText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addressHelper = AddressHelper(resources)

        // Initialize views
        val studentIdInput = findViewById<EditText>(R.id.studentIdInput)
        val nameInput = findViewById<EditText>(R.id.nameInput)
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val phoneInput = findViewById<EditText>(R.id.phoneInput)
        val maleRadio = findViewById<RadioButton>(R.id.maleRadio)
        val femaleRadio = findViewById<RadioButton>(R.id.femaleRadio)

        calendarView = findViewById(R.id.calendarView)
        toggleCalendarButton = findViewById(R.id.toggleCalendarButton)
        selectedDateText = findViewById(R.id.selectedDateText)

        provinceSpinner = findViewById(R.id.provinceSpinner)
        districtSpinner = findViewById(R.id.districtSpinner)
        wardSpinner = findViewById(R.id.wardSpinner)

        val sportsCheckbox = findViewById<CheckBox>(R.id.sportsCheckbox)
        val moviesCheckbox = findViewById<CheckBox>(R.id.moviesCheckbox)
        val musicCheckbox = findViewById<CheckBox>(R.id.musicCheckbox)
        val termsCheckbox = findViewById<CheckBox>(R.id.termsCheckbox)
        val submitButton = findViewById<Button>(R.id.submitButton)

        // Setup calendar
        calendarView.visibility = View.GONE
        toggleCalendarButton.setOnClickListener {
            if (calendarView.visibility == View.VISIBLE) {
                calendarView.visibility = View.GONE
                toggleCalendarButton.text = "Hiện lịch"
            } else {
                calendarView.visibility = View.VISIBLE
                toggleCalendarButton.text = "Ẩn lịch"
            }
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDateText.text = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
            calendarView.visibility = View.GONE
            toggleCalendarButton.text = "Hiện lịch"
        }

        // Setup address spinners
        setupAddressSpinners()

        // Submit button click handler
        submitButton.setOnClickListener {
            if (validateForm(
                    studentIdInput.text.toString(),
                    nameInput.text.toString(),
                    emailInput.text.toString(),
                    phoneInput.text.toString(),
                    selectedDateText.text.toString(),
                    maleRadio.isChecked || femaleRadio.isChecked,
                    provinceSpinner.selectedItem != null,
                    districtSpinner.selectedItem != null,
                    wardSpinner.selectedItem != null,
                    sportsCheckbox.isChecked || moviesCheckbox.isChecked || musicCheckbox.isChecked,
                    termsCheckbox.isChecked
                )) {
                // Form is valid, proceed with submission
                Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupAddressSpinners() {
        // Setup province spinner
        val provinces = addressHelper.getProvinces()
        val provinceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provinces)
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        provinceSpinner.adapter = provinceAdapter

        provinceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedProvince = provinces[position]
                updateDistrictSpinner(selectedProvince)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Reset district and ward spinners
                districtSpinner.adapter = null
                wardSpinner.adapter = null
            }
        }
    }

    private fun updateDistrictSpinner(province: String) {
        val districts = addressHelper.getDistricts(province)
        val districtAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, districts)
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        districtSpinner.adapter = districtAdapter

        districtSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedDistrict = districts[position]
                updateWardSpinner(province, selectedDistrict)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                wardSpinner.adapter = null
            }
        }
    }

    private fun updateWardSpinner(province: String, district: String) {
        val wards = addressHelper.getWards(province, district)
        val wardAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, wards)
        wardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        wardSpinner.adapter = wardAdapter
    }

    private fun validateForm(
        studentId: String,
        name: String,
        email: String,
        phone: String,
        birthDate: String,
        hasGender: Boolean,
        hasProvince: Boolean,
        hasDistrict: Boolean,
        hasWard: Boolean,
        hasHobbies: Boolean,
        agreedToTerms: Boolean
    ): Boolean {
        var isValid = true

        if (studentId.isEmpty()) {
            showError("Vui lòng nhập MSSV")
            isValid = false
        }

        if (name.isEmpty()) {
            showError("Vui lòng nhập họ tên")
            isValid = false
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError("Vui lòng nhập email hợp lệ")
            isValid = false
        }

        if (phone.isEmpty() || !android.util.Patterns.PHONE.matcher(phone).matches()) {
            showError("Vui lòng nhập số điện thoại hợp lệ")
            isValid = false
        }

        if (birthDate == "Chọn ngày sinh") {
            showError("Vui lòng chọn ngày sinh")
            isValid = false
        }

        if (!hasGender) {
            showError("Vui lòng chọn giới tính")
            isValid = false
        }

        if (!hasProvince || !hasDistrict || !hasWard) {
            showError("Vui lòng chọn đầy đủ địa chỉ")
            isValid = false
        }

        if (!hasHobbies) {
            showError("Vui lòng chọn ít nhất một sở thích")
            isValid = false
        }

        if (!agreedToTerms) {
            showError("Vui lòng đồng ý với điều khoản")
            isValid = false
        }

        return isValid
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}