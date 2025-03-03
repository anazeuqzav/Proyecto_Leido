package com.pmm.proyecto_leido.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pmm.proyecto_leido.R
import com.pmm.proyecto_leido.databinding.ItemBookBinding
import com.pmm.proyecto_leido.models.Book

/**
 * ViewHolder que gestiona y muestra los elementos visuales de cada ítem en el RecyclerView.
 * Se encarga de renderizar la información del libro y manejar los eventos de edición y eliminación.
 *
 * @param view Vista asociada al ViewHolder.
 * @param deleteOnClick Función lambda que se ejecuta al hacer clic en el botón de eliminar.
 * @param updateOnClick Función lambda que se ejecuta al hacer clic en el botón de actualizar.
 */
class BookViewHolder(
    view: View,
    private val deleteOnClick: (Int) -> Unit,
    private val updateOnClick: (Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    /** ViewBinding para acceder a los elementos de la vista. */
    private val binding = ItemBookBinding.bind(view)

    /**
     * Renderiza los datos de un libro en la vista del ViewHolder.
     *
     * @param book Objeto [Book] que contiene la información del libro.
     */
    fun renderize(book: Book) {
        binding.bookTitle.text = book.title
        binding.bookAuthor.text = book.author
        binding.bookDetails.text = "${book.year} | ${book.genrer}"

        // Cargar imagen usando Glide
        Glide.with(itemView.context)
            .load(book.cover)
            .centerCrop() // Escalar la imagen al centro
            .placeholder(R.drawable.ic_placeholder) // Imagen por defecto mientras carga
            .into(binding.bookCover) // Elemento ImageView donde se muestra la portada

        // Configurar los listeners para los botones de editar y eliminar
        setOnClickListener(adapterPosition)
    }

    /**
     * Configura los listeners para los botones de eliminar y actualizar.
     *
     * @param position Posición del elemento en el RecyclerView.
     */
    private fun setOnClickListener(position: Int) {
        binding.deleteButton.setOnClickListener {
            deleteOnClick(adapterPosition) // Llama a la función lambda para eliminar el libro
        }
        binding.updateButton.setOnClickListener {
            updateOnClick(position) // Llama a la función lambda para actualizar el libro
        }
    }
}
