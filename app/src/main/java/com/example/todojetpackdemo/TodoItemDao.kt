package com.example.todojetpackdemo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface TodoItemDao {

    @Insert
    suspend fun insertTodoItem(todoItem: TodoItem)


   @Delete
   suspend  fun deleteTodoItem(todoItem: TodoItem)

   @Query("Select * from  TodoItem  Order By addAt ASC")
   fun getTodoItems(): Flow<List<TodoItem>>
}