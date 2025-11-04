package com.example.notasapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notasapp.ui.theme.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskScreen(navController: NavController, vm: TaskViewModel, taskId: Int) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var currentTask by remember { mutableStateOf<Task?>(null) }

    LaunchedEffect(taskId) {
        if (taskId != 0) {
            val task = vm.getTaskById(taskId)
            task?.let {
                currentTask = it
                title = it.title
                content = it.content
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (taskId == 0) stringResource(R.string.new_task)
                        else stringResource(R.string.edit_task),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF4CAF50))
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (taskId != 0) {
                    Button(
                        onClick = {
                            currentTask?.let {
                                vm.deleteTask(it)
                                navController.popBackStack()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        Text(stringResource(R.string.delete), color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }

                Button(
                    onClick = {
                        if (taskId == 0) vm.addTask(title, content)
                        else vm.updateTask(taskId, title, content)
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Text(stringResource(R.string.save), color = Color.White)
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(R.string.title)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text(stringResource(R.string.content)) },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 5
            )
        }
    }
}
