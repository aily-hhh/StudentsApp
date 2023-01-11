package com.hhh.studentsapp.ui.groupDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputEditText
import com.hhh.studentsapp.R
import com.hhh.studentsapp.databinding.FragmentGroupDetailsBinding
import com.hhh.studentsapp.model.Group
import com.hhh.studentsapp.ui.listOfGroups.ListOfGroupsViewModel
import com.hhh.studentsapp.ui.listOfStudents.ListOfStudentsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GroupDetailsFragment : Fragment() {

    private var _binding: FragmentGroupDetailsBinding? = null
    private val mBinding get() = _binding!!

    lateinit var nameGroupDetails: TextInputEditText
    lateinit var countMembersDetails: TextInputEditText
    lateinit var facultyDetails: TextInputEditText
    lateinit var updateGroupBtn: Button
    lateinit var deleteGroupBtn: Button

    private val viewModelGroups by viewModels<ListOfGroupsViewModel>()
    private val viewModelStudents by viewModels<ListOfStudentsViewModel>()
    private val bundleArgs: GroupDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupDetailsBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameGroupDetails = mBinding.nameGroupDetails
        countMembersDetails = mBinding.countMembersDetails
        facultyDetails = mBinding.facultyDetails
        updateGroupBtn = mBinding.updateGroupBtn
        deleteGroupBtn = mBinding.deleteGroupBtn

        val group = bundleArgs.group
        nameGroupDetails.setText(group.nameGroup)
        countMembersDetails.setText(group.countMembers.toString())
        facultyDetails.setText(group.faculty)

        viewModelStudents.getCountGroupMembers(group.nameGroup.trim()).observe(viewLifecycleOwner, Observer {
            countMembersDetails.setText(it.toString())
        })

        updateGroupBtn.setOnClickListener() {
            viewModelGroups.update(group = Group(
                id = group.id,
                nameGroup = nameGroupDetails.text.toString().trim(),
                countMembers = countMembersDetails.text.toString().toInt(),
                faculty = facultyDetails.text.toString().trim()
            ))
            Toast.makeText(context, R.string.update, Toast.LENGTH_SHORT).show()
            view.findNavController().navigate(R.id.action_groupDetailsFragment_to_listOfGroupsFragment)
        }

        deleteGroupBtn.setOnClickListener() {
            val count = group.countMembers
            if (count <= 0) {
                viewModelGroups.delete(group = Group(
                    id = group.id,
                    nameGroup = group.nameGroup,
                    countMembers = group.countMembers,
                    faculty = group.faculty
                ))
                Toast.makeText(context, R.string.delete, Toast.LENGTH_SHORT).show()
                view.findNavController().navigate(R.id.action_groupDetailsFragment_to_listOfGroupsFragment)
            } else {
                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

}