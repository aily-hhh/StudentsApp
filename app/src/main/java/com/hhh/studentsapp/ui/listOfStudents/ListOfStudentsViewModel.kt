package com.hhh.studentsapp.ui.listOfStudents

import android.icu.text.StringSearch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hhh.studentsapp.database.StudentRepository
import com.hhh.studentsapp.model.Group
import com.hhh.studentsapp.model.Student
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOfStudentsViewModel @Inject constructor(private val studentRepository: StudentRepository) : ViewModel() {

    var studentsLiveData: LiveData<List<Student>> = studentRepository.readAll

    private fun searchStudent(search: String): LiveData<List<Student>> = studentRepository.searchStudent(search)

    fun newStudentLiveData(search: String) {
        if (search.equals(null) || search == "") {
            studentsLiveData = studentRepository.readAll
        } else {
            studentsLiveData = searchStudent(search)
        }
    }

    fun getCountGroupMembers(group: String): LiveData<Int> = studentRepository.getCountGroupMembers(group)

    fun create(student: Student) =
        viewModelScope.launch {
            studentRepository.createStudent(student)
        }

    fun update(student: Student) =
        viewModelScope.launch {
            studentRepository.updateStudent(student)
        }

    fun delete(student: Student) =
        viewModelScope.launch {
            studentRepository.deleteStudent(student)
        }
}