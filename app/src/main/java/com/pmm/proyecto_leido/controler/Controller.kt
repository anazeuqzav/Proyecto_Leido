package com.pmm.proyecto_leido.controler

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pmm.proyecto_leido.MainActivity
import com.pmm.proyecto_leido.R
import com.pmm.proyecto_leido.adapter.BookAdapter
import com.pmm.proyecto_leido.dialogues.DialogEditBook
import com.pmm.proyecto_leido.models.Book
import com.pmm.proyecto_leido.object_models.Repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class Controller : ViewModel() {
    private val _entidades = MutableLiveData<List<Book>>()
    val entidades: LiveData<List<Book>> get() = _entidades
    init {
        obtenerEntidades()
    }
    // Obtener datos desde Firebase en tiempo real
    private fun obtenerEntidades() {
        Repository.getEntities { lista ->
            _entidades.postValue(lista)
        }
    }
    // Agregar una entidad
    fun agregarEntidad(entidad: Book) {
        viewModelScope.launch {
            Repository.addEntity(entidad)
        }
    }
    // Actualizar entidad
    fun actualizarEntidad(entidad: Book) {
        viewModelScope.launch {
            Repository.updateEntity(entidad)
        }
    }
    // Eliminar entidad
    fun eliminarEntidad(id: String) {
        viewModelScope.launch {
            Repository.deleteEntity(id)
        }
    }
}
