package com.hhh.studentsapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hhh.studentsapp.model.Group


@Dao
interface GroupDao {

    @Query ("SELECT * FROM groupOfStudents")
    fun getAllGroups(): LiveData<List<Group>>

    @Insert
    suspend fun insertIntoGroup(group: Group)

    @Update
    suspend fun updateIntoGroup(group: Group)

    @Delete
    suspend fun deleteFromGroup(group: Group)

    @Query("SELECT nameGroup FROM groupOfStudents")
    fun getAllGroupNames(): LiveData<List<String>>
}