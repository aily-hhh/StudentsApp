package com.hhh.studentsapp.database

import android.icu.text.StringSearch
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hhh.studentsapp.model.Group
import com.hhh.studentsapp.model.Student

@Dao
interface StudentDao {

    @Query("SELECT * FROM student")
    fun getAllStudents(): LiveData<List<Student>>

    @Insert
    suspend fun insertIntoStudent(student: Student)

    @Update
    suspend fun updateIntoStudent(student: Student)

    @Delete
    suspend fun deleteIntoStudent(student: Student)

    @Query("SELECT * FROM student WHERE lastName LIKE '%' || :search || '%' " +
            "OR groupForStudent LIKE '%' || :search || '%'")
    fun searchStudents(search: String): LiveData<List<Student>>

    @Query("SELECT COUNT(idStudent) FROM student WHERE groupForStudent LIKE :group")
    fun getCountGroupMembers(group: String): LiveData<Int>
}