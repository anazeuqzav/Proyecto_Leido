package com.pmm.proyecto_leido.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.pmm.proyecto_leido.R
import android.media.MediaPlayer
import android.net.Uri
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.VideoView

class FragmentHome : Fragment() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cargar imagen con Glide
        val imageView = view.findViewById<ImageView>(R.id.imagenGilde)
        val imageUrl = "https://st3.depositphotos.com/5934840/36019/v/450/depositphotos_360194732-stock-illustration-text-book-open-isolated-icon.jpg"
        Glide.with(requireContext()).load(imageUrl).into(imageView)

        // Reproducir sonido
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.sonido)
        mediaPlayer.start()

        // Reproducir video
        val videoView = view.findViewById<WebView>(R.id.videoView)
        val videoUrl = "https://www.youtube.com/embed/vvKUuFk_uWI?si=dQv5FuH-DXNlhWG-"
        videoView.settings.javaScriptEnabled = true
        videoView.settings.pluginState = WebSettings.PluginState.ON
        videoView.webViewClient = WebViewClient()
        videoView.loadUrl(videoUrl)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Liberar MediaPlayer al cerrar el fragmento
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }
}
