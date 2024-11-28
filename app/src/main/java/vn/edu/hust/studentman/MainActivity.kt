package vn.edu.hust.studentman

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
  private lateinit var students: MutableList<StudentModel>
  private lateinit var launcher1: ActivityResultLauncher<Intent>
  private lateinit var launcher2: ActivityResultLauncher<Intent>
  private lateinit var studentAdapter : StudentAdapter
  var pos = -1
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

      students = mutableListOf(
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

    studentAdapter = StudentAdapter(students)

    val listview =findViewById<ListView>(R.id.list_view_students)
    listview.adapter = studentAdapter

    launcher1 = registerForActivityResult(
      ActivityResultContracts.StartActivityForResult()
    ) { it: ActivityResult ->
      if (it.resultCode == RESULT_OK) {
        val hoten = it.data?.getStringExtra("name")
        val mssv = it.data?.getStringExtra("code")
        students.add(StudentModel(hoten.toString(),mssv.toString()))
        studentAdapter.notifyDataSetChanged()
        Toast.makeText(this,"Add a student",Toast.LENGTH_LONG).show()
      } else {
        Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
      }
    }

    launcher2 = registerForActivityResult(
      ActivityResultContracts.StartActivityForResult()
    ) { it: ActivityResult ->
      if (it.resultCode == RESULT_OK) {
        val hoten = it.data?.getStringExtra("name")
        val mssv = it.data?.getStringExtra("code")
        students[pos] = StudentModel(hoten.toString(),mssv.toString())
        studentAdapter.notifyDataSetChanged()
        Toast.makeText(this,"Edit a student",Toast.LENGTH_SHORT).show()
      } else {
        Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
      }
    }

    registerForContextMenu(listview)

    supportActionBar?.title = "List student"
  }

  override fun onCreateContextMenu(
    menu: ContextMenu?,
    v: View?,
    menuInfo: ContextMenu.ContextMenuInfo?
  ) {
    menuInflater.inflate(R.menu.student_delete_adit,menu)
    super.onCreateContextMenu(menu, v, menuInfo)
  }

  override fun onContextItemSelected(item: MenuItem): Boolean {
    pos = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
    when(item.itemId){
      R.id.edit_student ->{
        val intent = Intent(this, AddStudent::class.java)
        intent.putExtra("hoten",students[pos].studentName)
        intent.putExtra("maso",students[pos].studentId)
        intent.putExtra("action","Edit Student")
        launcher2.launch(intent)
      }
      R.id.delete_student ->{
        val student = students[pos]
        AlertDialog.Builder(this)
          .setTitle("Are you sure to delete ?")
          .setMessage(students[pos].studentName)
          .setPositiveButton("YES"){
              dialogInterface: DialogInterface, i: Int ->
            students.removeAt(pos)
            studentAdapter.notifyDataSetChanged()
            Snackbar.make(findViewById(R.id.list_view_students),"Student removed", Snackbar.LENGTH_LONG)
              .setAction("UNDO"){
                students.add(pos,student)
                studentAdapter.notifyDataSetChanged()
              }
              .show()

          }
          .setNegativeButton("CANCEL",null)
          .show()
      }
    }
    return super.onContextItemSelected(item)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.student_menu_bar,menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when(item.itemId){
      R.id.add_student -> {
        val intent = Intent(this, AddStudent::class.java)
        intent.putExtra("action","Add New Student")
        launcher1.launch(intent)
      }
    }
    return super.onOptionsItemSelected(item)
  }
}