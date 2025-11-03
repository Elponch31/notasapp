package com.example.notasapp

class TaskRepository(private val dao: TaskDao) {

    suspend fun getAllTasks() = dao.getAllTasks()

    suspend fun getTaskById(id: Int) = dao.getTaskById(id)

    suspend fun insert(task: Task) = dao.insert(task)

    suspend fun update(task: Task) = dao.update(task)

    suspend fun delete(task: Task) = dao.delete(task)
}
