package com.hhh.studentsapp.ui.newGroup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
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

    private val viewModel by viewModels<ListOfGroupsViewModel>()

    lateinit var nameNewGroup: TextInputEditText
    lateinit var facultyNew: TextInputEditText
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

        saveGroupBtn.setOnClickListener() {
            viewModel.create(group = Group(
                nameGroup = nameNewGroup.text.toString().trim(),
                countMembers = 0,
                faculty = facultyNew.text.toString().trim()
            ))
            Toast.makeText(context, R.string.save, Toast.LENGTH_SHORT).show()
            view.findNavController().navigate(R.id.action_newGroupFragment_to_listOfGroupsFragment)
        }
    }

}