package vn.edu.hust.studentman

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val students = mutableListOf(
      StudentModel("Nguyễn Văn An", "SV001"),
      StudentModel("Trần Thị Bảo", "SV002"),
      StudentModel("Lê Hoàng Cường", "SV003"),
      StudentModel("Phạm Thị Dung", "SV004"),
      StudentModel("Đỗ Minh Đức", "SV005"),
      StudentModel("Vũ Thị Hoa", "SV006"),
      StudentModel("Hoàng Văn Hải", "SV007"),
      StudentModel("Bùi Thị Hạnh", "SV008"),
      StudentModel("Đinh Văn Hùng", "SV009"),
      StudentModel("Nguyễn Thị Linh", "SV010"),
      StudentModel("Phạm Văn Long", "SV011"),
      StudentModel("Trần Thị Mai", "SV012"),
      StudentModel("Lê Thị Ngọc", "SV013"),
      StudentModel("Vũ Văn Nam", "SV014"),
      StudentModel("Hoàng Thị Phương", "SV015"),
      StudentModel("Đỗ Văn Quân", "SV016"),
      StudentModel("Nguyễn Thị Thu", "SV017"),
      StudentModel("Trần Văn Tài", "SV018"),
      StudentModel("Phạm Thị Tuyết", "SV019"),
      StudentModel("Lê Văn Vũ", "SV020")
    )

    val studentAdapter = StudentAdapter(
      students,
      onEditClick = { adapter, student, position ->
        val dialogView = LayoutInflater.from(this).inflate(R.layout.layout_dialog_add, null)
        val editName = dialogView.findViewById<EditText>(R.id.editText_name)
        val editId = dialogView.findViewById<EditText>(R.id.editText_id)

        editName.setText(student.studentName)
        editId.setText(student.studentId)

        AlertDialog.Builder(this)
          .setTitle("Chỉnh sửa sinh viên")
          .setView(dialogView)
          .setPositiveButton("Lưu") { _, _ ->
            val updatedName = editName.text.toString().trim()
            val updatedId = editId.text.toString().trim()

            if (updatedName.isEmpty() || updatedId.isEmpty()) {
              Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
              return@setPositiveButton
            }

            students[position] = student.copy(studentName = updatedName, studentId = updatedId)
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show()
          }
          .setNegativeButton("Hủy", null)
          .show()
      },
      onDeleteClick = { adapter, student, position ->
        AlertDialog.Builder(this)
          .setTitle("Xóa sinh viên")
          .setMessage("Bạn có chắc chắn muốn xóa sinh viên ${student.studentName} không?")
          .setPositiveButton("Xóa") { _, _ ->
            // Lưu lại sinh viên trước khi xóa để hoàn tác
            val removedStudent = students[position]
            students.removeAt(position)
            adapter.notifyDataSetChanged()

            Snackbar.make(
              findViewById(R.id.recycler_view_students),
              "Đã xóa sinh viên!",
              Snackbar.LENGTH_LONG
            ).setAction("Hoàn tác") {
              // Hoàn tác xóa sinh viên
              students.add(position, removedStudent)
              adapter.notifyDataSetChanged()
              Snackbar.make(
                findViewById(R.id.recycler_view_students),
                "Đã hoàn tác xóa sinh viên ${removedStudent.studentName}!",
                Snackbar.LENGTH_SHORT
              ).show()
            }.show()
          }
          .setNegativeButton("Hủy", null)
          .show()
      }

    )

    findViewById<RecyclerView>(R.id.recycler_view_students).run {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    // Them sinh vien
    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      val dialogView = LayoutInflater.from(this).inflate(R.layout.layout_dialog_add, null)
      val editName = dialogView.findViewById<EditText>(R.id.editText_name)
      val editId = dialogView.findViewById<EditText>(R.id.editText_id)

        AlertDialog.Builder(this)
        .setTitle("Thêm sinh viên mới")
        .setView(dialogView)
        .setPositiveButton("Thêm", {_, _ ->
          val name = editName.text.toString()
          val id = editId.text.toString()

          students.add(StudentModel(name, id))
          Toast.makeText(this, "Thêm sinh viên mới thành công!", Toast.LENGTH_SHORT).show()
          studentAdapter.notifyDataSetChanged()
        })
        .setNegativeButton("Hủy", null)
        .show()
    }

  }
}