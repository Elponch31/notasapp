package com.example.notasapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = NoteDatabase.getDatabase(this)
        val repository = NoteRepository(database.noteDao())

        setContent {
            val navController = rememberNavController()
            val vm: NoteViewModel = viewModel(factory = NoteViewModelFactory(repository))

            NavHost(navController = navController, startDestination = "notes") {
                composable("notes") {
                    NotesScreen(navController, vm)
                }
                composable("new_note/{noteId}") { backStackEntry ->
                    val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull() ?: 0
                    NewNoteScreen(navController, vm, noteId)
                }
            }
        }
    }
}
