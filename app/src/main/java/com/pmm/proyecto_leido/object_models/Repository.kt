package com.pmm.proyecto_leido.object_models

import com.pmm.proyecto_leido.models.Book
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


object Repository {
    private val db = FirebaseFirestore.getInstance()
    private const val COLLECTION_NAME = "Books" // nombre de la colección el firebase firestore

    // Obtener lista de entidades en tiempo real
    fun getEntities(callback: (List<Book>) -> Unit) {
        db.collection(COLLECTION_NAME).addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.e("Repository", "Error obteniendo datos en tiempo real", e)
                callback(emptyList())
                return@addSnapshotListener
            }

            val books = snapshots?.documents?.mapNotNull { doc ->
                val book = doc.toObject(Book::class.java)
                book?.let {
                    it.id = doc.id  // Asignar manualmente el ID
                    Log.d("Repository", "Libro obtenido: $it") // Verificar que ahora tiene ID
                }
                book
            } ?: emptyList()

            callback(books)
        }
    }

    // Agregar una nueva entidad
    suspend fun addEntity(entidad: Book): Boolean {
        return try {
            val documentRef = db.collection(COLLECTION_NAME).add(entidad).await()
            val id = documentRef.id
            documentRef.update("id", id).await() // Guardar el ID en Firebase
            true
        } catch (e: Exception) {
            Log.e("Repository", "Error añadiendo", e)
            false
        }
    }

    // Actualizar una entidad
    suspend fun updateEntity(entidad: Book): Boolean {
        Log.d("Repository", "Intentando actualizar: $entidad") // <-- Imprime el libro antes de actualizar
        if (entidad.id.isNullOrEmpty()) {
            Log.e("Repository", "Error actualizando: ID inválido")
            return false
        }

        return try {
            db.collection(COLLECTION_NAME).document(entidad.id).set(entidad).await()
            Log.d("Repository", "Libro actualizado correctamente")
            true
        } catch (e: Exception) {
            Log.e("Repository", "Error actualizando", e)
            false
        }
    }

    // Eliminar una entidad
    suspend fun deleteEntity(id: String?): Boolean {
        if (id.isNullOrEmpty()) {
            Log.e("Repository", "Error eliminando: ID inválido")
            return false
        }
        return try {
            db.collection(COLLECTION_NAME).document(id).delete().await()
            true
        } catch (e: Exception) {
            Log.e("Repository", "Error eliminando", e)
            false
        }
    }
}

