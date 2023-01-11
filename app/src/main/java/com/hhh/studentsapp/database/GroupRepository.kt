package com.hhh.studentsapp.database

import androidx.lifecycle.LiveData
import com.hhh.studentsapp.model.Group
import javax.inject.Inject


class GroupRepository @Inject constructor(private val groupDao: GroupDao) {

    val reedAll: LiveData<List<Group>> = groupDao.getAllGroups()
    val getAllGroupNames: LiveData<List<String>> = groupDao.getAllGroupNames()

    suspend fun createGroup(group: Group) {
        groupDao.insertIntoGroup(group)
    }

    suspend fun updateGroup(group: Group) {
        groupDao.updateIntoGroup(group)
    }

    suspend fun deleteGroup(group: Group) {
        groupDao.deleteFromGroup(group)
    }
}