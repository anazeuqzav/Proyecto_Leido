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

/**
 * Adaptador que conecta una fuente de datos con una vista, en este caso, el adaptador
 * BookAdapter conecta una lista de libros (listBooks) con un RecyclerView. Este adaptador maneja
 * cómo se crean, enlazan y muestran los ítems de la lista en el RecyclerView
 */
class BookAdapter(
    private val listBooks: MutableList<Book>,
    private val deleteOnClick: (Int) -> Unit,
    private val updateOnClick: (Int) -> Unit
    ) : RecyclerView.Adapter<BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutItemBook = R.layout.item_book // El layout para un ítem
        return BookViewHolder(
            layoutInflater.inflate(layoutItemBook, parent, false),
            deleteOnClick,
            updateOnClick
            )
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.renderize(listBooks[position]) // Renderizamos los datos
    }

    override fun getItemCount(): Int = listBooks.size

    // Método para agregar un libro
    fun addBook(newBook: Book) {
        listBooks.add(newBook) // Añadir el libro a la lista
        notifyItemInserted(listBooks.size - 1) // Notificar que se ha insertado un nuevo ítem
    }

    fun updateCoverImage(book: Book, imageView: ImageView) {
        Glide.with(imageView.context)
            .load(book.cover) // Cargar la imagen desde la URL de la portada
            .centerCrop()
            .placeholder(R.drawable.ic_placeholder)
            .into(imageView)
    }

}
