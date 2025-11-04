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
import com.example.notasapp.ui.theme.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewNoteScreen(navController: NavController, vm: NoteViewModel, noteId: Int) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var currentNote by remember { mutableStateOf<Note?>(null) }

    LaunchedEffect(noteId) {
        if (noteId != 0) {
            val note = vm.getNoteById(noteId)
            note?.let {
                currentNote = it
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
                        if (noteId == 0) stringResource(R.string.new_note)
                        else stringResource(R.string.edit_note),
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
                if (noteId != 0) {
                    Button(
                        onClick = {
                            currentNote?.let {
                                vm.deleteNote(it)
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
                        if (noteId == 0) vm.addNote(title, content)
                        else vm.updateNote(noteId, title, content)
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
