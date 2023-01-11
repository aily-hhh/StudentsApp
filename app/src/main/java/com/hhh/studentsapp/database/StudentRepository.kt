package com.hhh.studentsapp.database

import androidx.lifecycle.LiveData
import com.hhh.studentsapp.model.Group
import com.hhh.studentsapp.model.Student
import javax.inject.Inject

class StudentRepository @Inject constructor(private val studentDao: StudentDao) {

    val readAll: LiveData<List<Student>> = studentDao.getAllStudents()

    fun searchStudent(search: String): LiveData<List<Student>> = studentDao.searchStudents(search)

    fun getCountGroupMembers(group: String): LiveData<Int> = studentDao.getCountGroupMembers(group)

    suspend fun createStudent(student: Student) {
        studentDao.insertIntoStudent(student)
    }

    suspend fun updateStudent(student: Student) {
        studentDao.updateIntoStudent(student)
    }

    suspend fun deleteStudent(student: Student) {
        studentDao.deleteIntoStudent(student)
    }
}