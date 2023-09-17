package com.example.todojetpackdemo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val item: String,
    val addAt: Long
)
