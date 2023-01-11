package com.hhh.studentsapp.ui.listOfStudents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hhh.studentsapp.R
import com.hhh.studentsapp.adapter.StudentAdapter
import com.hhh.studentsapp.databinding.FragmentListOfStudentsBinding
import com.hhh.studentsapp.model.Student
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListOfStudentsFragment : Fragment() {

    private var _binding: FragmentListOfStudentsBinding? = null
    private val mBinding get() = _binding!!

    private val viewModel by viewModels<ListOfStudentsViewModel>()
    lateinit var studentAdapter: StudentAdapter

    lateinit var searchView: EditText
    lateinit var addStudentBtn: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListOfStudentsBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        studentAdapter.setOnItemClickListener {
            val bundle = bundleOf("student" to it)
            view.findNavController().navigate(
                R.id.action_listOfStudentsFragment_to_studentDetailsFragment,
                bundle
            )
        }

        viewModel.studentsLiveData.observe(viewLifecycleOwner) {
            studentAdapter.differ.submitList(it)
        }
        searchView = mBinding.searchView
        addStudentBtn = mBinding.addStudentBtn

        addStudentBtn.setOnClickListener {
            view.findNavController().navigate(
                R.id.action_listOfStudentsFragment_to_newStudentFragment
            )
        }

        searchView.addTextChangedListener() {
            viewModel.newStudentLiveData(search = searchView.text.toString().trim())
            viewModel.studentsLiveData.observe(viewLifecycleOwner) {
                studentAdapter.differ.submitList(it)
            }
        }
    }

    private fun initAdapter() {
        studentAdapter = StudentAdapter()
        mBinding.studentRecyclerView.apply {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}