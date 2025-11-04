package com.example.notasapp

/**
 * Clase base genérica para repositorios.
 * No es abstracta, por lo que puede heredarse sin obligación de implementar los métodos.
 * Sirve como punto común para lógica compartida o utilidades.
 */
open class BaseRepository<T> {

    open suspend fun getAll(): List<T> {
        // Implementación vacía o genérica
        return emptyList()
    }

    open suspend fun getById(id: Int): T? {
        return null
    }

    open suspend fun insert(item: T) {
        // Implementación vacía
    }

    open suspend fun update(item: T) {
        // Implementación vacía
    }

    open suspend fun delete(item: T) {
        // Implementación vacía
    }
}
