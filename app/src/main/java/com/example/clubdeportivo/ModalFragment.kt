package com.example.clubdeportivo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

private const val KEY_TITLE = "title"
private const val KEY_TEXT = "text"
private const val BTN_REJECT = "btn_reject"
private const val BTN_SUCCESS = "btn_success"

class ModalFragment : DialogFragment() {

    interface ModalListener {
        fun onModalResult(success: Boolean)
    }

    private var listener: ModalListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ModalListener) {
            listener = context
        } else {
            throw ClassCastException("$context must implement ModalListener")
        }
    }

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_modal, container, false)

        val title = view.findViewById<TextView>(R.id.textTitle)
        title.text = arguments?.getString(KEY_TITLE)

        val text = view.findViewById<TextView>(R.id.textText)
        text.text = arguments?.getString(KEY_TEXT)

        val btnReject = view.findViewById<Button>(R.id.btnReject)
        val btnRejectText = arguments?.getString(BTN_REJECT)

        if (btnRejectText.isNullOrEmpty()) {
            btnReject.visibility = View.GONE
        } else {
            btnReject.text = btnRejectText
            btnReject.setOnClickListener {
                listener?.onModalResult(false)
                dismiss()
            }
        }

        val btnSuccess = view.findViewById<Button>(R.id.btnSuccess)
        btnSuccess.text = arguments?.getString(BTN_SUCCESS)
        btnSuccess.setOnClickListener {
            listener?.onModalResult(true)
            dismiss()
        }

        return view
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        @JvmStatic
        fun newInstance(
            title: String,
            text: String,
            btnSuccess: String,
            btnReject: String? = null
        ) = ModalFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_TITLE , title)
                putString(KEY_TEXT , text)
                putString(BTN_SUCCESS , btnSuccess)
                btnReject?.let { putString(BTN_REJECT, it) }
            }
        }
    }
}