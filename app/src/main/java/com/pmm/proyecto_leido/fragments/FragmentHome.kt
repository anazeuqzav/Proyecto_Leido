package com.pmm.proyecto_leido.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.pmm.proyecto_leido.R


/**
 * Fragmento que representa la pantalla de inicio de la aplicación.
 * En este fragmento se muestra una imagen con Glide, se reproduce un sonido y se carga un video desde YouTube.
 */
class FragmentHome : Fragment() {

    // MediaPlayer para reproducir sonido
    private lateinit var mediaPlayer: MediaPlayer

    /**
     * Infla el layout del fragmento cuando se crea.
     * @return Vista inflada con el layout asociado.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    /**
     * Se ejecuta después de que la vista ha sido creada.
     * Aquí se configuran la carga de imagen, la reproducción de sonido y la carga del video.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cargar imagen con Glide desde una URL
        val imageView = view.findViewById<ImageView>(R.id.imagenGilde)
        val imageUrl = "https://st3.depositphotos.com/5934840/36019/v/450/depositphotos_360194732-stock-illustration-text-book-open-isolated-icon.jpg"
        Glide.with(requireContext()).load(imageUrl).into(imageView)

        // Reproducir sonido al abrir el fragmento
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.sonido)
        mediaPlayer.start()

        // Configurar WebView para mostrar un video de YouTube
        val videoView = view.findViewById<WebView>(R.id.videoView)
        val videoUrl = "https://www.youtube.com/embed/vvKUuFk_uWI?si=dQv5FuH-DXNlhWG-"

        // Habilitar JavaScript en WebView para la correcta reproducción del video
        videoView.settings.javaScriptEnabled = true
        videoView.webViewClient = WebViewClient()
        videoView.loadUrl(videoUrl)
    }

    /**
     * Se ejecuta cuando la vista del fragmento se destruye.
     * Libera el recurso de MediaPlayer para evitar fugas de memoria.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }
}
