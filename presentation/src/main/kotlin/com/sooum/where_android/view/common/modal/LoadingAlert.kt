package com.sooum.where_android.view.common.modal

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.DialogFragment

class LoadingAlert : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.let {
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.requestWindowFeature(STYLE_NO_TITLE)
            setCancelable(false)
            it.setCanceledOnTouchOutside(false)
        }

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                CircularProgressIndicator()
            }
        }
    }
}