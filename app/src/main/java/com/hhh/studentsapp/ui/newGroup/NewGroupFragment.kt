package com.hhh.studentsapp.ui.newGroup

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.hhh.studentsapp.R
import com.hhh.studentsapp.databinding.FragmentNewGroupBinding
import com.hhh.studentsapp.model.Group
import com.hhh.studentsapp.ui.listOfGroups.ListOfGroupsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewGroupFragment : Fragment() {

    private var _binding: FragmentNewGroupBinding? = null
    private val mBinding get() = _binding!!

    private var faculties = mutableListOf<String>()
    private val viewModel by viewModels<ListOfGroupsViewModel>()

    lateinit var nameNewGroup: TextInputEditText
    lateinit var facultyNew: MaterialAutoCompleteTextView
    lateinit var saveGroupBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewGroupBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameNewGroup = mBinding.nameNewGroup
        facultyNew = mBinding.facultyNew
        saveGroupBtn = mBinding.saveGroupBtn

        faculties = resources.getStringArray(R.array.Faculties).toMutableList()
        val adapterFaculties = activity?.let {
            ArrayAdapter(it, android.R.layout.simple_list_item_1, faculties)
        }
        facultyNew.setAdapter(adapterFaculties)
        facultyNew.threshold = 1

        saveGroupBtn.setOnClickListener() {
            val nameGr = nameNewGroup.text.toString().trim()
            val fac = facultyNew.text.toString().trim()

            if (checkValid(nameGr = nameGr, fac = fac)) {
                viewModel.create(
                    group = Group(
                        nameGroup = nameGr,
                        countMembers = 0,
                        faculty = fac
                    )
                )
                Toast.makeText(context, R.string.save, Toast.LENGTH_SHORT).show()
                view.findNavController()
                    .navigate(R.id.action_newGroupFragment_to_listOfGroupsFragment)
            } else {
                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkValid(nameGr: String, fac: String): Boolean {
        var flag = true
        if (!faculties.contains(fac)) {
            facultyNew.setHintTextColor(Color.RED)
            facultyNew.setBackgroundResource(R.color.errorBack)
            flag = false
        } else {
            facultyNew.setBackgroundColor(Color.WHITE)
            facultyNew.setHintTextColor(Color.BLACK)
        }
        if (nameGr == "") {
            nameNewGroup.setBackgroundResource(R.color.errorBack)
            nameNewGroup.setHintTextColor(Color.RED)
            flag = false
        } else {
            nameNewGroup.setBackgroundResource(R.color.white)
            nameNewGroup.setHintTextColor(Color.BLACK)
        }
        return flag
    }
}
