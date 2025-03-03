package com.pmm.proyecto_leido.object_models

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.pmm.proyecto_leido.models.Book
import kotlinx.coroutines.tasks.await

/**
 * Objeto Repository para gestionar la colección "Books" en Firebase Firestore.
 * Proporciona métodos para obtener, agregar, actualizar y eliminar libros.
 */
object Repository {
    private val db = FirebaseFirestore.getInstance() // Instancia de Firestore
    private const val COLLECTION_NAME = "Books" // Nombre de la colección en Firestore

    /**
     * Obtiene la lista de libros en tiempo real y la devuelve a través del callback.
     * Se actualiza automáticamente con cada cambio en la base de datos.
     *
     * @param callback Función que recibe la lista de libros obtenida.
     */
    fun getEntities(callback: (List<Book>) -> Unit) {
        db.collection(COLLECTION_NAME).addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.e("Repository", "Error obteniendo datos en tiempo real", e)
                callback(emptyList())
                return@addSnapshotListener
            }

            val books = snapshots?.documents?.mapNotNull { doc ->
                val book = doc.toObject(Book::class.java) // Convertir documento a objeto Book
                book?.let {
                    it.id = doc.id  // Asignar manualmente el ID del documento al objeto
                    Log.d("Repository", "Libro obtenido: $it") // Verificar que ahora tiene ID
                }
                book
            } ?: emptyList()

            callback(books) // Pasar la lista de libros al callback
        }
    }

    /**
     * Agrega un nuevo libro a la colección en Firestore.
     *
     * @param entidad Objeto Book a agregar.
     * @return `true` si la operación es exitosa, `false` si ocurre un error.
     */
    suspend fun addEntity(entidad: Book): Boolean {
        return try {
            val documentRef = db.collection(COLLECTION_NAME).add(entidad).await()
            val id = documentRef.id
            documentRef.update("id", id).await() // Guardar el ID dentro del documento

            true // Operación exitosa
        } catch (e: Exception) {
            Log.e("Repository", "Error añadiendo libro", e)
            false // Operación fallida
        }
    }

    /**
     * Actualiza un libro en Firestore usando su ID.
     *
     * @param entidad Objeto Book con la información actualizada.
     * @return `true` si la operación es exitosa, `false` si hay error o el ID es inválido.
     */
    suspend fun updateEntity(entidad: Book): Boolean {
        Log.d("Repository", "Intentando actualizar: $entidad") // Imprimir el libro antes de actualizar
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

    /**
     * Elimina un libro de Firestore a partir de su ID.
     *
     * @param id Identificador único del libro.
     * @return `true` si la eliminación es exitosa, `false` si hay error o el ID es inválido.
     */
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
