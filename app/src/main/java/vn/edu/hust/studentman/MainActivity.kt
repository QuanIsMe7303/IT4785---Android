package vn.edu.hust.studentman

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

  val listFragment = ListFragment()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    supportActionBar?.title = "List student"
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.student_menu_bar,menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when(item.itemId){
      R.id.add_student -> {
        val args = Bundle()
        args.putInt("param0",-1)
        args.putString("param1","name")
        args.putString("param2","code")
        findNavController(R.id.container).navigate(R.id.action_listFragment_to_studentFragment,args)
        true
      }
    }
    return super.onOptionsItemSelected(item)
  }

}