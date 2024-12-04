package vn.edu.hust.studentman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StudentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_student, container, false)

        val nameview = view.findViewById<EditText>(R.id.namestudent)
        val codeview = view.findViewById<EditText>(R.id.codestudent)

        var pos = arguments?.getInt("param0", -1)
        var nameedit = arguments?.getString("param1")
        var codeedit = arguments?.getString("param2")

        if(nameedit!=null) nameview.setText(nameedit)
        if(codeedit!=null) codeview.setText(codeedit)

        view.findViewById<Button>(R.id.buttonoke).setOnClickListener{
            val name = nameview.text.toString()
            val mssv = codeview.text.toString()

            // Xử lý lưu thông tin và quay lại ListFragment
            val args = Bundle()
            if (pos != null) {
                args.putInt("param0", pos)
            }else{
                args.putInt("param0", -1)
            }
            args.putString("param1", name)
            args.putString("param2", mssv)
            findNavController().navigate(R.id.action_studentFragment_to_listFragment,args)
        }

        view.findViewById<Button>(R.id.buttoncancel).setOnClickListener{
            findNavController().navigate(R.id.action_studentFragment_to_listFragment)
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StudentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StudentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}