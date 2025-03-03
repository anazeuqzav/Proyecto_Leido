package com.pmm.proyecto_leido.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.pmm.proyecto_leido.R
import com.pmm.proyecto_leido.models.Book

/**
 * DialogNewBook es un cuadro de diálogo personalizado que permite al usuario añadir un nuevo libro.
 * Contiene campos de entrada para el título, autor, año, género y URL de la portada.
 * Al confirmar la adición, se ejecuta la función proporcionada {@code onNewBookDialog}.
 */
class DialogNewBook(
    private val onNewBookDialog: (Book) -> Unit
) : DialogFragment() {

    /**
     * Crea y configura el cuadro de diálogo para añadir un nuevo libro.
     *
     * @param savedInstanceState Estado guardado previamente (si existe).
     * @return El diálogo configurado.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val viewDialogNewBook = inflater.inflate(R.layout.dialog_new_book, null)

        builder.setView(viewDialogNewBook)
            .setTitle("Añadir nuevo libro")
            .setPositiveButton("Añadir") { _, _ -> }
            .setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }

        val dialog = builder.create()

        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                val newBook = recoverDataFromLayout(viewDialogNewBook)

                if (newBook != null) {
                    onNewBookDialog(newBook) // Llamamos a la función para añadir el libro
                    dialog.dismiss() // Cerrar el diálogo si la validación pasa
                } else {
                    // Mostrar error si algún campo está vacío
                    Toast.makeText(activity, "Algún campo está vacío", Toast.LENGTH_LONG).show()
                }
            }
        }
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_rounded_background)

        return dialog
    }

    /**
     * Recupera y valida los datos ingresados por el usuario en el cuadro de diálogo.
     *
     * @param view Vista del cuadro de diálogo.
     * @return Un objeto Book si los datos son válidos, o null si falta información.
     */
    private fun recoverDataFromLayout(view: View): Book? {
        val titleEditText = view.findViewById<EditText>(R.id.editTitle)
        val authorEditText = view.findViewById<EditText>(R.id.editAuthor)
        val yearEditText = view.findViewById<EditText>(R.id.editYear)
        val genreEditText = view.findViewById<EditText>(R.id.editGenre)
        val coverUrlEditText = view.findViewById<EditText>(R.id.editCoverUrl)

        // Recuperar datos
        val title = titleEditText.text.toString().trim()
        val author = authorEditText.text.toString().trim()
        val year = yearEditText.text.toString().trim()
        val genre = genreEditText.text.toString().trim()
        val coverUrl = coverUrlEditText.text.toString().trim()

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

        // Crear el libro con la URL de portada
        return Book(
            title = title,
            author = author,
            year = year.toInt(),
            genrer = genre,
            cover = if (coverUrl.isNotEmpty()) coverUrl else "E:\\Proyecto_Leido\\app\\src\\main\\res\\drawable\\ic_placeholder.png" // URL por defecto si está vacía
        )
    }
}
