package com.pmm.proyecto_leido.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.pmm.proyecto_leido.R

/**
 * DialogFragment que muestra una alerta de confirmación para eliminar un libro.
 *
 * @param pos La posición del libro en la lista.
 * @param name El nombre del libro que se desea eliminar.
 * @param onDeleteBookDialog Función de callback que se ejecuta cuando el usuario confirma la eliminación.
 */
class DialogDeleteBook(
    val pos: Int, // La posición del libro
    val name: String, // El nombre del libro
    val onDeleteBookDialog: (Int) -> Unit // Función que maneja la eliminación del libro
) : DialogFragment() {

    /**
     * Se ejecuta cuando se crea el diálogo.
     * Muestra un mensaje de confirmación con opciones "Sí" y "No".
     *
     * @return El diálogo de confirmación construido.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        builder.setMessage("Do you want to delete the book '$name'?")
            .setPositiveButton("Yes") { _, _ ->
                // Llamar a la función que maneja la eliminación
                onDeleteBookDialog(pos)
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss() // Cierra el diálogo sin hacer cambios
            }

        // Crear el diálogo
        val dialog = builder.create()

        // Aplicar un fondo redondeado personalizado al diálogo
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_rounded_background)

        return dialog
    }
}
