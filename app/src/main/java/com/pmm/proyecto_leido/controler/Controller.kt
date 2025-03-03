package com.pmm.proyecto_leido.controler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmm.proyecto_leido.models.Book
import com.pmm.proyecto_leido.object_models.Repository
import kotlinx.coroutines.launch

/**
 * ViewModel que gestiona la lógica de la aplicación y la comunicación con el repositorio de datos.
 * Maneja la obtención, adición, actualización y eliminación de libros en Firebase en tiempo real.
 */
class Controller : ViewModel() {

    /** Lista observable de libros. */
    private val _entidades = MutableLiveData<List<Book>>()

    /** Exposición de la lista de libros como LiveData para su observación en la UI. */
    val entidades: LiveData<List<Book>> get() = _entidades

    init {
        obtenerEntidades()
    }

    /**
     * Obtiene los datos de los libros desde Firebase en tiempo real
     * y los almacena en el LiveData.
     */
    private fun obtenerEntidades() {
        Repository.getEntities { lista ->
            _entidades.postValue(lista)
        }
    }

    /**
     * Agrega un nuevo libro a Firebase.
     *
     * @param entidad Objeto de tipo [Book] que se añadirá a la base de datos.
     */
    fun agregarEntidad(entidad: Book) {
        viewModelScope.launch {
            Repository.addEntity(entidad)
        }
    }

    /**
     * Actualiza un libro existente en Firebase.
     *
     * @param entidad Objeto de tipo [Book] con los datos actualizados.
     */
    fun actualizarEntidad(entidad: Book) {
        viewModelScope.launch {
            Repository.updateEntity(entidad)
        }
    }

    /**
     * Elimina un libro de Firebase utilizando su ID.
     *
     * @param id Identificador único del libro que se va a eliminar.
     */
    fun eliminarEntidad(id: String) {
        viewModelScope.launch {
            Repository.deleteEntity(id)
        }
    }
}

