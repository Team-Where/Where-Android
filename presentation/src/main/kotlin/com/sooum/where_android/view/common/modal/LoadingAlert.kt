package com.sooum.where_android.view.common.modal

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard

class LoadingAlert : DialogFragment() {

    companion object {
        private const val LOADING_MSG_KEY = "loading_msg_key"
        fun getInstance(): LoadingAlert {
            return LoadingAlert()
        }

        fun getInstance(msg: String): LoadingAlert {
            return LoadingAlert().apply {
                arguments = bundleOf(
                    LOADING_MSG_KEY to msg
                )
            }
        }

    }

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
        val msg = arguments?.getString(LOADING_MSG_KEY)

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                LoadingView(
                    modifier = Modifier,
                    msg = msg
                )
            }
        }
    }
}

@Composable
fun LoadingView(
    modifier: Modifier,
    msg: String?
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = Primary600
        )
        if (!msg.isNullOrEmpty()) {
            Text(
                text = msg,
                fontFamily = pretendard
            )
        }
    }
}

class LoadingAlertProvider(
    private val context: Context,
    private val fragmentManager: FragmentManager,
) {

    constructor(fragment: Fragment) : this(
        fragment.requireContext(),
        fragment.parentFragmentManager
    )

    constructor(activity: AppCompatActivity) : this(
        activity,
        activity.supportFragmentManager
    )

    private var loadingAlert: LoadingAlert? = null

    /**
     * 로딩을 시작합니다.
     */
    fun startLoading() {
        loadingAlert?.dismiss()
        loadingAlert = LoadingAlert.getInstance()
        loadingAlert!!.show(fragmentManager, "loading")
    }

    /**
     * 로딩을 시작합니다.
     * @param[msg] 보여질 msg
     */
    fun startLoading(
        msg: String
    ) {
        loadingAlert?.dismiss()
        loadingAlert = LoadingAlert.getInstance(msg)
        loadingAlert!!.show(fragmentManager, "loading")
    }

    /**
     * 로딩을 끝냅니다.
     */
    fun endLoading(
        withAction: () -> Unit = {}
    ) {
        loadingAlert?.dismiss()
        loadingAlert = null
        withAction()
    }

    /**
     * 로딩을 끝내고 Toast 메시지를 출력합니다.
     */
    fun endLoadingWithMessage(
        msg: String
    ) {
        endLoading {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

}