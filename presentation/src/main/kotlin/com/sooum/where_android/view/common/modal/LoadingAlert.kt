package com.sooum.where_android.view.common.modal

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.DialogFragment
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.FragmentManager

class LoadingAlert : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.let {
            it.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
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

class LoadingAlertProvider(
    private val fragmentManager: FragmentManager,
) {
    private var loadingAlert: LoadingAlert? = null

    /**
     * 로딩을 시작합니다.
     */
    fun startLoading() {
        if (loadingAlert == null) {
            loadingAlert = LoadingAlert()
        }
        loadingAlert!!.show(fragmentManager, "loading")
    }

    /**
     * 로딩을 끝냅니다.
     */
    fun endLoading(
        withAction : () -> Unit ={}
    ) {
        loadingAlert?.dismiss()
        loadingAlert = null
        withAction()
    }

}