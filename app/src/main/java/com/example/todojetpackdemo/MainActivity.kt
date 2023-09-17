package com.example.todojetpackdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.todojetpackdemo.ui.theme.TodoJetpackDemoTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {

    private val db by lazy{
        Room.databaseBuilder(
            applicationContext,
            TodoItemDatabase::class.java,
            "todo.db"
        ).build()
    }

    private val viewModel by viewModels<MainActivityViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainActivityViewModel(db.dao) as T
                }
            }
        }
    )
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            TodoJetpackDemoTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row() {
                        OutlinedTextField(value = viewModel.todoItem, onValueChange = { viewModel.todoItem = it })
                        IconButton(
                            onClick = {
                                viewModel.addItemResetInput()
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add, contentDescription = "add",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color.Cyan)
                            )
                        }
                    }
                    val todoItems by viewModel.todoItems.collectAsState()
                    LazyColumn {
                        items(todoItems){
                            TodoItemView(what = it.item ) {
                                viewModel.delete(it)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TodoItemView(what: String, onSwipe: () -> Unit) {
    var offsetX by remember { mutableStateOf(0f) }

    Text(
        text = what,
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .fillMaxWidth()
            .draggable(orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    offsetX += delta
                    offsetX = if (offsetX < 0) {
                        0f
                    } else {
                        offsetX
                    }
                    if (offsetX > 250) {
                        onSwipe()
                    }
                }
            )
            .padding(vertical = 16.dp)

    )
    Divider()
}
