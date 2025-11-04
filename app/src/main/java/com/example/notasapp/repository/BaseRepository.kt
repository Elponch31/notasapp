package com.example.notasapp

/**
 * Clase abstracta base para los repositorios del sistema.
 * Define la estructura de los métodos CRUD que deberán implementar las clases hijas.
 */
abstract class BaseRepository<T> {

    abstract suspend fun getAll(): List<T>

    abstract suspend fun getById(id: Int): T?

    abstract suspend fun insert(item: T)

    abstract suspend fun update(item: T)

    abstract suspend fun delete(item: T)
}
