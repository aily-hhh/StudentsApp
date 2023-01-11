package com.hhh.studentsapp.ui.listOfGroups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hhh.studentsapp.R
import com.hhh.studentsapp.adapter.GroupAdapter
import com.hhh.studentsapp.databinding.FragmentListOfGroupsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListOfGroupsFragment : Fragment() {

    private var _binding: FragmentListOfGroupsBinding? = null
    private val mBinding get() = _binding!!

    private val viewModel by viewModels<ListOfGroupsViewModel>()
    lateinit var groupAdapter: GroupAdapter

    lateinit var addGroupBtn: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListOfGroupsBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        groupAdapter.setOnItemClickListener {
            val bundle = bundleOf("group" to it)
            view.findNavController().navigate(
                R.id.action_listOfGroupsFragment_to_groupDetailsFragment,
                bundle
            )
        }

        viewModel.groupsLiveData.observe(viewLifecycleOwner) {
            groupAdapter.differ.submitList(it)
        }

        addGroupBtn = mBinding.addGroupBtn
        addGroupBtn.setOnClickListener {
            view.findNavController().navigate(
                R.id.action_listOfGroupsFragment_to_newGroupFragment
            )
        }
    }

    private fun initAdapter() {
        groupAdapter = GroupAdapter()
        mBinding.groupRecyclerView.apply {
            adapter = groupAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}