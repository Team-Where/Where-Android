package com.sooum.where_android.view.hamburger.setting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.compose.AndroidFragment
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentWebviewBinding
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment
import com.sooum.where_android.view.widget.PrimaryButton

class WebViewFragment : HamburgerBaseFragment<FragmentWebviewBinding>(
    FragmentWebviewBinding::inflate
) {
    override fun initView() {
        with(binding.webView) {
            with(settings) {
                javaScriptEnabled = true
                loadsImagesAutomatically = true
                useWideViewPort = true
                domStorageEnabled = true
            }
        }
        val url = requireArguments().getString("url")!!
        loadUrl(url)
    }

    private fun loadUrl(url: String) {
        binding.webView.loadUrl(url)
    }
}

@Composable
fun WebViewContent(
    url: String,
    onBack: () -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 74.dp)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_back),
                        contentDescription = null
                    )
                }
            }
            AndroidFragment<WebViewFragment>(
                modifier = Modifier.fillMaxSize(),
                arguments = bundleOf(
                    "url" to url
                )
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(74.dp)
                .align(Alignment.BottomCenter)
                .padding(10.dp),
        ) {
            PrimaryButton(
                onClick = onClick,
                title = "확인",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}