package com.example.todojetpackdemo

import androidx.room.Database
import androidx.room.RoomDatabase
import javax.inject.Singleton

@Database(
    entities =  [TodoItem::class],
    version = 1
)
abstract class TodoItemDatabase: RoomDatabase() {

    abstract val dao: TodoItemDao
}