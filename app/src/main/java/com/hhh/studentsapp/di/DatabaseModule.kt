package com.hhh.studentsapp.di

import android.content.Context
import androidx.room.Room
import com.hhh.studentsapp.database.AppDataBase
import com.hhh.studentsapp.database.GroupDao
import com.hhh.studentsapp.database.StudentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideStudentDao(appDataBase: AppDataBase): StudentDao {
        return appDataBase.getStudentDao()
    }

    @Provides
    fun provideGroupDao(appDataBase: AppDataBase): GroupDao {
        return  appDataBase.getGroupDao()
    }
}