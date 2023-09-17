package com.example.todojetpackdemo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel
@Inject
constructor(private val dao: TodoItemDao) : ViewModel() {

    var todoItem by mutableStateOf("")

    private val _todoItems = dao.getTodoItems()

    val todoItems = _todoItems.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),
        emptyList())

    fun addItemResetInput() {
        if (todoItem.isBlank()|| todoItem.isEmpty()){
            return
        }
        viewModelScope.launch {
            dao.insertTodoItem(TodoItem(item = todoItem, addAt = System.currentTimeMillis()/1000))
        }
        todoItem =""
    }

    fun delete(todoItem: TodoItem){
        viewModelScope.launch{
            dao.deleteTodoItem(todoItem)
        }
    }

}