package com.ridecell.maps.ui.customdialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ridecell.maps.R
import kotlinx.android.synthetic.main.layout_custom_dialog.*

class CustomDialog : DialogFragment() {

    var latString : String = ""
    var longString : String = ""

    companion object {

        fun getInstance(data : Bundle) : CustomDialog {
            val customDialog = CustomDialog()
            customDialog.arguments = data
            return  customDialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            latString = it.getString("LAT", "").toString()
            longString = it.getString("LONG", "").toString()
        }

        return layoutInflater.inflate(R.layout.layout_custom_dialog, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvLatLong.text = "$latString | $longString"
    }
}