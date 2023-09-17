package com.example.todojetpackdemo

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext appContext: Context): TodoItemDatabase {
        return    Room.databaseBuilder(
            appContext,
            TodoItemDatabase::class.java,
            "todo.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(todoItemDatabase: TodoItemDatabase) : TodoItemDao{
        return todoItemDatabase.dao
    }
}