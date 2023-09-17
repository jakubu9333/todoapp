package com.example.todojetpackdemo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    fun addItemResetInput() {
        todoItems.add(todoItem)
        todoItem =""
    }

    var todoItem by mutableStateOf("")

    val todoItems = mutableStateListOf<String>()

}