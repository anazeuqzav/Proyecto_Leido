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
/**
 * DialogFragment que permite editar la información de un libro.
 *
 * @param bookToUpdate El libro que se va a editar.
 * @param updateBookDialog Función de callback que se ejecuta al confirmar la edición.
 */
class DialogEditBook(
    val bookToUpdate: Book,
    val updateBookDialog: (Book) -> Unit
) : DialogFragment() {

    // Claves para los argumentos del Bundle
    private val ARGUMENT_TITLE = ArgumentsBook.ARGUMENT_TITLE
    private val ARGUMENT_AUTHOR = ArgumentsBook.ARGUMENT_AUTHOR
    private val ARGUMENT_YEAR = ArgumentsBook.ARGUMENT_YEAR
    private val ARGUMENT_GENRE = ArgumentsBook.ARGUMENT_GENRE
    private val ARGUMENT_COVER = ArgumentsBook.ARGUMENT_COVER

    init {
        // Guardamos los datos del libro en un Bundle para pasarlos al diálogo
        val args = Bundle().apply {
            putString(ARGUMENT_TITLE, bookToUpdate.title)
            putString(ARGUMENT_AUTHOR, bookToUpdate.author)
            putInt(ARGUMENT_YEAR, bookToUpdate.year)
            putString(ARGUMENT_GENRE, bookToUpdate.genrer)
            putString(ARGUMENT_COVER, bookToUpdate.cover) // URL de la portada
        }
        this.arguments = args
    }

    /**
     * Se ejecuta al crear el diálogo.
     * Construye un AlertDialog con los campos de edición del libro.
     *
     * @return El diálogo de edición del libro.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val viewDialogEditBook = inflater.inflate(R.layout.dialog_new_book, null)

        // Llenar los campos con la información actual del libro
        setValuesIntoDialog(viewDialogEditBook, this.arguments)

        builder.setView(viewDialogEditBook)
            .setTitle("Editar libro")
            .setPositiveButton("Actualizar") { _, _ ->
                // Recuperar los datos del formulario y actualizar el libro
                val updatedBook = recoverDataFromLayout(viewDialogEditBook)
                if (updatedBook != null) {
                    updateBookDialog(updatedBook)  // Llamamos a la función para actualizar
                } else {
                    Toast.makeText(activity, "Algún campo está vacío", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
            }

        val dialog = builder.create()

        // Aplicar fondo redondeado al diálogo
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_rounded_background)

        return dialog
    }

    /**
     * Llena los campos del diálogo con la información actual del libro.
     *
     * @param viewDialogEditBook Vista del diálogo.
     * @param arguments Bundle con los datos del libro.
     */
    private fun setValuesIntoDialog(viewDialogEditBook: View, arguments: Bundle?) {
        val binding = DialogNewBookBinding.bind(viewDialogEditBook)

        arguments?.let {
            binding.editTitle.setText(it.getString(ARGUMENT_TITLE))
            binding.editAuthor.setText(it.getString(ARGUMENT_AUTHOR))
            binding.editYear.setText(it.getInt(ARGUMENT_YEAR).toString())
            binding.editGenre.setText(it.getString(ARGUMENT_GENRE))
            binding.editCoverUrl.setText(it.getString(ARGUMENT_COVER))

            // Cargar imagen de portada si hay una URL válida
            val coverUrl = it.getString(ARGUMENT_COVER, "")
            if (coverUrl.isNotEmpty()) {
                loadImageFromUrl(coverUrl, binding.bookCover)
            }
        }
    }

    /**
     * Carga una imagen desde una URL en un ImageView usando Glide.
     *
     * @param url URL de la imagen de portada.
     * @param imageView ImageView donde se mostrará la imagen.
     */
    private fun loadImageFromUrl(url: String, imageView: ImageView) {
        Glide.with(this)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.ic_placeholder) // Imagen por defecto si falla la carga
            .into(imageView)
    }

    /**
     * Recupera los datos ingresados en el formulario y los valida.
     *
     * @param view Vista del diálogo.
     * @return Un objeto Book con los nuevos valores, o null si hay campos vacíos.
     */
    private fun recoverDataFromLayout(view: View): Book? {
        val binding = DialogNewBookBinding.bind(view)

        val title = binding.editTitle.text.toString().trim()
        val author = binding.editAuthor.text.toString().trim()
        val year = binding.editYear.text.toString().trim()
        val genre = binding.editGenre.text.toString().trim()
        val coverUrl = binding.editCoverUrl.text.toString().trim()

        // Validaciones de campos obligatorios
        if (title.isEmpty()) {
            binding.editTitle.error = "Este campo es obligatorio"
            return null
        }
        if (author.isEmpty()) {
            binding.editAuthor.error = "Este campo es obligatorio"
            return null
        }
        if (year.isEmpty()) {
            binding.editYear.error = "Este campo es obligatorio"
            return null
        }
        if (genre.isEmpty()) {
            binding.editGenre.error = "Este campo es obligatorio"
            return null
        }

        return Book(
            id = bookToUpdate.id, // Mantener el ID original
            title = title,
            author = author,
            year = year.toInt(),
            genrer = genre,
            cover = if (coverUrl.isNotEmpty()) coverUrl else "default_cover_url"
        )
    }
}
