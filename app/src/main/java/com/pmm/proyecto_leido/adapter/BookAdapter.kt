package com.pmm.proyecto_leido.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pmm.proyecto_leido.R
import com.pmm.proyecto_leido.databinding.ItemBookBinding
import com.pmm.proyecto_leido.models.Book

class BookAdapter(private val listBooks: MutableList<Book>) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutItemBook = R.layout.item_book // El layout para un ítem
        return BookViewHolder(layoutInflater.inflate(layoutItemBook, parent, false))
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.renderize(listBooks[position]) // Renderizamos los datos
    }

    override fun getItemCount(): Int = listBooks.size


    // Clase ViewHolder anidada
    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemBookBinding.bind(view) // Usa ViewBinding para mapear elementos

        // Renderiza un libro
        fun renderize(book: Book) {
            binding.bookTitle.text = book.title
            binding.bookAuthor.text = book.author
            binding.bookDetails.text = "${book.year} | ${book.genrer}"

            // Cargar imagen usando Glide
            Glide.with(itemView.context)
                .load(book.cover) // Si es un URL, usa la URL aquí
                .centerCrop() // Escalar la imagen al centro
                .placeholder(R.drawable.ic_placeholder) // Imagen por defecto mientras carga
                .into(binding.bookCover) // Elemento ImageView donde se muestra
        }
    }
}
