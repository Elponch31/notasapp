package com.example.notasapp

class NoteRepository(private val dao: NoteDao) {

    suspend fun getAllNotes() = dao.getAllNotes()

    suspend fun getNoteById(id: Int) = dao.getNoteById(id)

    suspend fun insert(note: Note) = dao.insert(note)

    suspend fun update(note: Note) = dao.update(note)

    suspend fun delete(note: Note) = dao.delete(note)
}
