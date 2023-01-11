package com.hhh.studentsapp.ui.newStudent

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.hhh.studentsapp.R
import com.hhh.studentsapp.databinding.FragmentNewStudentBinding
import com.hhh.studentsapp.databinding.FragmentStudentDetailsBinding
import com.hhh.studentsapp.model.Student
import com.hhh.studentsapp.ui.listOfGroups.ListOfGroupsViewModel
import com.hhh.studentsapp.ui.listOfStudents.ListOfStudentsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewStudentFragment : Fragment() {

    private var _binding: FragmentNewStudentBinding? = null
    private val mBinding get() = _binding!!

    lateinit var firstName: TextInputEditText
    lateinit var lastName: TextInputEditText
    lateinit var patronymic: TextInputEditText
    lateinit var birthday: TextInputEditText
    lateinit var groupForStudent: MaterialAutoCompleteTextView
    lateinit var saveStudent: Button

    private val listGroupNames: MutableList<String> = mutableListOf()

    private val viewModelStudents by viewModels<ListOfStudentsViewModel>()
    private val viewModelGroups by viewModels<ListOfGroupsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewStudentBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firstName = mBinding.firstNameDetails
        lastName = mBinding.lastNameDetails
        patronymic = mBinding.patronymicDetails
        birthday = mBinding.birthdayDetails
        groupForStudent = mBinding.groupForStudentDetails
        saveStudent = mBinding.saveStudent

        viewModelGroups.getAllGroupNames.observe(viewLifecycleOwner, Observer {
            it?.let { listGroupNames.addAll(it) }
        })
        val adapterGroupNames = activity?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_list_item_1,
                listGroupNames
            )
        }
        groupForStudent.threshold = 1
        groupForStudent.setAdapter(adapterGroupNames)

        saveStudent.setOnClickListener {
            val group = groupForStudent.text.toString().trim()
            val fname = firstName.text?.trim().toString()
            val lname = lastName.text?.trim().toString()
            val patron = patronymic.text?.trim().toString()
            val birth = birthday.text?.trim().toString()

            if (checkValid(group, fname, lname, patron, birth)) {
                viewModelStudents.create(
                    student = Student(
                        firstName = fname,
                        lastName = lname,
                        patronymic = patron,
                        birthday = birth,
                        groupForStudent = group
                    )
                )
                Toast.makeText(context, R.string.save, Toast.LENGTH_SHORT).show()
                view.findNavController()
                    .navigate(R.id.action_newStudentFragment_to_listOfStudentsFragment)
            } else {
                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkValid(group: String, fname: String, lname: String, patron: String, birth: String): Boolean {
        var flag = true
        if (!listGroupNames.contains(group)) {
            groupForStudent.setHintTextColor(Color.RED)
            groupForStudent.setBackgroundResource(R.color.errorBack)
            flag = false
        } else {
            groupForStudent.setBackgroundColor(Color.WHITE)
            groupForStudent.setHintTextColor(Color.BLACK)
        }
        if (fname == "") {
            firstName.setBackgroundResource(R.color.errorBack)
            firstName.setHintTextColor(Color.RED)
            flag = false
        } else {
            firstName.setBackgroundResource(R.color.white)
            firstName.setHintTextColor(Color.BLACK)
        }
        if (lname == "") {
            lastName.setBackgroundResource(R.color.errorBack)
            lastName.setHintTextColor(Color.RED)
            flag = false
        } else {
            lastName.setBackgroundResource(R.color.white)
            lastName.setHintTextColor(Color.BLACK)
        }
        if (patron == "") {
            patronymic.setBackgroundResource(R.color.errorBack)
            patronymic.setHintTextColor(Color.RED)
            flag = false
        } else {
            patronymic.setBackgroundResource(R.color.white)
            patronymic.setHintTextColor(Color.BLACK)
        }
        if (birth == "") {
            birthday.setBackgroundResource(R.color.errorBack)
            birthday.setHintTextColor(Color.RED)
            flag = false
        } else {
            birthday.setBackgroundResource(R.color.white)
            birthday.setHintTextColor(Color.BLACK)
        }

        return flag
    }
}