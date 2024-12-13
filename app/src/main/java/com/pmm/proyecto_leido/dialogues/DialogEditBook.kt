package com.pmm.proyecto_leido.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.pmm.proyecto_leido.R
import com.pmm.proyecto_leido.databinding.DialogNewBookBinding
import com.pmm.proyecto_leido.models.ArgumentsBook
import com.pmm.proyecto_leido.models.Book

class DialogEditBook(
    val bookToUpdate: Book,
    val updateBookDialog: (Book) -> Unit
) : DialogFragment() {

    // Argumentos para pasar los datos al diálogo
    private val ARGUMENT_TITLE = ArgumentsBook.ARGUMENT_TITLE
    private val ARGUMENT_AUTHOR = ArgumentsBook.ARGUMENT_AUTHOR
    private val ARGUMENT_YEAR = ArgumentsBook.ARGUMENT_YEAR
    private val ARGUMENT_GENRE = ArgumentsBook.ARGUMENT_GENRE
    private val ARGUMENT_COVER = ArgumentsBook.ARGUMENT_COVER

    init {
        // Preparamos los datos que se pasarán al Bundle
        val args = Bundle().apply {
            putString(ARGUMENT_TITLE, bookToUpdate.title)
            putString(ARGUMENT_AUTHOR, bookToUpdate.author)
            putInt(ARGUMENT_YEAR, bookToUpdate.year)
            putString(ARGUMENT_GENRE, bookToUpdate.genrer)
            putString(ARGUMENT_COVER, bookToUpdate.cover) // Usamos String para la URL
        }
        this.arguments = args
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val viewDialogEditBook = inflater.inflate(R.layout.dialog_new_book, null)

        // Setear los valores dentro del diálogo
        setValuesIntoDialog(viewDialogEditBook, this.arguments)

        builder.setView(viewDialogEditBook)
            .setTitle("Editar libro")
            .setPositiveButton("Actualizar") { _, _ ->
                // Recuperar los datos del diálogo
                val updatedBook = recoverDataFromLayout(viewDialogEditBook)
                if (updatedBook != null) {
                    updateBookDialog(updatedBook)  // Llamamos al método para actualizar el libro
                } else {
                    Toast.makeText(activity, "Algún campo está vacío", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
            }
        val dialog = builder.create()

        // Establecer el fondo redondeado para el diálogo
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_rounded_background)

        return dialog
    }

    private fun setValuesIntoDialog(viewDialogEditBook: View, arguments: Bundle?) {
        val binding = DialogNewBookBinding.bind(viewDialogEditBook)

        // Establecer los valores de los datos pasados desde el Bundle
        arguments?.let {
            binding.editTitle.setText(it.getString(ARGUMENT_TITLE))
            binding.editAuthor.setText(it.getString(ARGUMENT_AUTHOR))
            binding.editYear.setText(it.getInt(ARGUMENT_YEAR).toString())
            binding.editGenre.setText(it.getString(ARGUMENT_GENRE))
            binding.editCoverUrl.setText(it.getString(ARGUMENT_COVER)) // Establecer la URL de la portada

            // Cargar la portada si existe una URL
            val coverUrl = it.getString(ARGUMENT_COVER, "")
            if (coverUrl.isNotEmpty()) {
                loadImageFromUrl(coverUrl, binding.bookCover)  // Cargar la imagen desde la URL
            }
        }
    }

    private fun loadImageFromUrl(url: String, imageView: ImageView) {
        Glide.with(this)
            .load(url)  // URL de la portada
            .centerCrop()
            .placeholder(R.drawable.ic_placeholder)
            .into(imageView) // Cargar la imagen en el ImageView
    }

    private fun recoverDataFromLayout(view: View): Book? {
        val binding = DialogNewBookBinding.bind(view)

        val titleEditText = view.findViewById<EditText>(R.id.editTitle)
        val authorEditText = view.findViewById<EditText>(R.id.editAuthor)
        val yearEditText = view.findViewById<EditText>(R.id.editYear)
        val genreEditText = view.findViewById<EditText>(R.id.editGenre)
        val coverUrlEditText = view.findViewById<EditText>(R.id.editCoverUrl)

        val title = binding.editTitle.text.toString().trim()
        val author = binding.editAuthor.text.toString().trim()
        val year = binding.editYear.text.toString().trim()
        val genre = binding.editGenre.text.toString().trim()
        val coverUrl = binding.editCoverUrl.text.toString().trim()

        // Validar que los campos obligatorios no estén vacíos
        if (title.isEmpty()) {
            titleEditText.error = "Este campo es obligatorio"
            return null
        }

        if (author.isEmpty()) {
            authorEditText.error = "Este campo es obligatorio"
            return null
        }

        if (year.isEmpty()) {
            yearEditText.error = "Este campo es obligatorio"
            return null
        }

        if (genre.isEmpty()) {
            genreEditText.error = "Este campo es obligatorio"
            return null
        }

        return Book(
            title = title,
            author = author,
            year = year.toInt(),
            genrer = genre,
            cover = if (coverUrl.isNotEmpty()) coverUrl else "default_cover_url"  // Si no se proporciona URL, usa una predeterminada
        )
    }
}
