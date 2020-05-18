package com.iitu.gowithme.ui.event_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide

import com.iitu.gowithme.R
import kotlinx.android.synthetic.main.fragment_image_dialog.view.*


class ImageDialogFragment : DialogFragment() {

    companion object {
        private const val URI = "URI"

        fun newInstance(uri: String) = ImageDialogFragment().apply {
            arguments = bundleOf(
                URI to uri
            )
        }

    }

    private var uri: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uri = it.getString(URI)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            Glide.with(context).load(uri).into(image)
        }
    }

}
