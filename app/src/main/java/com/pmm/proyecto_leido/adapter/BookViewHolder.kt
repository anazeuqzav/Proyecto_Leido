package com.pmm.proyecto_leido.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pmm.proyecto_leido.MainActivity
import com.pmm.proyecto_leido.R
import com.pmm.proyecto_leido.databinding.ItemBookBinding
import com.pmm.proyecto_leido.models.Book

/**
 * El ViewHolder es responsable de contener y gestionar los elementos visuales de cada ítem del RecyclerView.
 */
class BookViewHolder(view: View,
                     private val deleteOnClick: (Int) -> Unit,
                     private val updateOnClick: (Int) -> Unit
) : RecyclerView.ViewHolder(view) {
    private val binding = ItemBookBinding.bind(view) // Usa ViewBinding para mapear elementos

    // Renderiza un libro
    fun renderize(book: Book) {
        binding.bookTitle.text = book.title
        binding.bookAuthor.text = book.author
        binding.bookDetails.text = "${book.year} | ${book.genrer}"

        // Cargar imagen usando Glide
        Glide.with(itemView.context)
            .load(book.cover)
            .centerCrop() // Escalar la imagen al centro
            .placeholder(R.drawable.ic_placeholder) // Imagen por defecto mientras carga
            .into(binding.bookCover) // Elemento ImageView donde se muestra

        // Configurar los listeners
        setOnClickListener(position)
    }

    // Configura los listeners para los botones de editar y eliminar
    private fun setOnClickListener(position: Int) {
        // Configurar el listener para el botón de eliminar
        binding.deleteButton.setOnClickListener {
            // Llamar al método para mostrar el diálogo de eliminación
            (itemView.context as MainActivity).showDeleteDialog(adapterPosition) //da error el método showDeleteDialog
        }
        binding.updateButton.setOnClickListener {
            updateOnClick(position) // Llama a la función lambda de actualizar
        }

    }
}