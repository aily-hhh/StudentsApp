package com.hhh.studentsapp.ui.listOfGroups

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hhh.studentsapp.database.GroupRepository
import com.hhh.studentsapp.model.Group
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListOfGroupsViewModel @Inject constructor(private val groupRepository: GroupRepository): ViewModel() {

    var groupsLiveData: LiveData<List<Group>> = groupRepository.reedAll
    var getAllGroupNames: LiveData<List<String>> = groupRepository.getAllGroupNames

    fun create(group: Group) =
        viewModelScope.launch {
            groupRepository.createGroup(group)
        }

    fun update(group: Group) =
        viewModelScope.launch {
            groupRepository.updateGroup(group)
        }

    fun delete(group: Group) =
        viewModelScope.launch {
            groupRepository.deleteGroup(group)
        }
}