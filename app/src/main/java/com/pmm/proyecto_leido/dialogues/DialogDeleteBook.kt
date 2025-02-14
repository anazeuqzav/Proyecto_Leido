package com.pmm.proyecto_leido.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.pmm.proyecto_leido.R

class DialogDeleteBook(
    val pos: Int, // La posición del libro
    val name: String, // El nombre del libro
    val onDeleteBookDialog: (Int) -> Unit // Función que maneja la eliminación del libro
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Do you want to delete the book '$name'?")
            .setPositiveButton("Yes") { _, _ ->
                // Llamar a la función que maneja la eliminación
                onDeleteBookDialog(pos)
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss() // Cierra el diálogo
            }

        // Crear el diálogo
        val dialog = builder.create()

        // Aplicar el fondo redondeado al diálogo
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_rounded_background)

        return dialog
    }
}