package com.hhh.studentsapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hhh.studentsapp.model.Group
import com.hhh.studentsapp.model.Student

@Database(entities = [Student::class, Group::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun getStudentDao(): StudentDao
    abstract fun getGroupDao(): GroupDao
}