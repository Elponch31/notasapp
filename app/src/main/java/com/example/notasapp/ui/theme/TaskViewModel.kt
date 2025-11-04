package com.example.notasapp.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.notasapp.Task
import com.example.notasapp.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> get() = _tasks

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = repository.getAll() // ✅ usa método genérico del BaseRepository
        }
    }

    fun addTask(title: String, content: String) {
        viewModelScope.launch {
            repository.insert(Task(title = title, content = content))
            loadTasks()
        }
    }

    fun updateTask(id: Int, title: String, content: String) {
        viewModelScope.launch {
            repository.update(Task(id = id, title = title, content = content))
            loadTasks()
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
            loadTasks()
        }
    }

    suspend fun getTaskById(id: Int): Task? {
        return repository.getById(id) // ✅ también usa el método genérico
    }
}


class TaskViewModelFactory(private val repository: TaskRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
