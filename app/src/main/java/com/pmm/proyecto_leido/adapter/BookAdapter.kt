package com.pmm.proyecto_leido.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pmm.proyecto_leido.R
import com.pmm.proyecto_leido.databinding.ItemBookBinding
import com.pmm.proyecto_leido.models.Book


import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
/**
 * Adaptador para la lista de libros en un RecyclerView.
 * Utiliza ListAdapter para mejorar la eficiencia en la actualización de los datos.
 *
 * @param deleteOnClick Función lambda que se ejecuta al hacer clic en el botón de eliminar.
 * @param updateOnClick Función lambda que se ejecuta al hacer clic en el botón de actualizar.
 */
class BookAdapter(
    private val deleteOnClick: (Int) -> Unit,
    private val updateOnClick: (Int) -> Unit
) : ListAdapter<Book, BookViewHolder>(BookDiffCallback()) { // Usamos ListAdapter para una mejor gestión de la lista

    /**
     * Crea y devuelve un nuevo ViewHolder para un elemento de la lista.
     *
     * @param parent El ViewGroup en el que se insertará la vista del ítem.
     * @param viewType Tipo de vista del ítem (no se usa en este caso).
     * @return Un nuevo [BookViewHolder] configurado con la vista del ítem.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutItemBook = R.layout.item_book // Referencia al layout de un ítem
        return BookViewHolder(
            layoutInflater.inflate(layoutItemBook, parent, false),
            deleteOnClick,
            updateOnClick
        )
    }

    /**
     * Enlaza los datos de un libro con el ViewHolder.
     *
     * @param holder El ViewHolder que mostrará los datos.
     * @param position Posición del libro en la lista.
     */
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.renderize(getItem(position)) // Utiliza getItem() de ListAdapter para obtener el elemento actual
    }
}

/**
 * Implementación de DiffUtil para optimizar la comparación de listas en BookAdapter.
 * Ayuda a identificar cambios en los elementos de la lista y mejorar la eficiencia del RecyclerView.
 */
class BookDiffCallback : DiffUtil.ItemCallback<Book>() {

    /**
     * Determina si dos objetos [Book] representan el mismo ítem en la lista.
     *
     * @param oldItem Objeto existente en la lista.
     * @param newItem Nuevo objeto que se quiere comparar.
     * @return `true` si ambos elementos tienen el mismo ID, `false` en caso contrario.
     */
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem.id == newItem.id // Compara por ID para determinar si es el mismo libro
    }

    /**
     * Determina si dos objetos [Book] tienen el mismo contenido.
     *
     * @param oldItem Objeto existente en la lista.
     * @param newItem Nuevo objeto que se quiere comparar.
     * @return `true` si los contenidos de ambos libros son idénticos, `false` en caso contrario.
     */
    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem.title == newItem.title &&
                oldItem.author == newItem.author &&
                oldItem.year == newItem.year &&
                oldItem.genrer == newItem.genrer &&
                oldItem.cover == newItem.cover
    }
}
