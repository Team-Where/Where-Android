package com.sooum.where_android.view.main.myMeetDetail.modal

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sooum.where_android.R
import com.sooum.where_android.view.widget.ModalHeader
import kotlinx.coroutines.launch

private sealed class MapItem(
    @DrawableRes val imageRes: Int,
    @StringRes val stringRes: Int,
    val marketPackage: String,
    val scheme: Uri
) {
    data object Naver : MapItem(
        imageRes = R.drawable.icon_map_naver,
        stringRes = R.string.map_share_by_naver,
        marketPackage = "com.nhn.android.nmap",
        scheme = Uri.parse("nmap://search?")
    )

    data object Kakao : MapItem(
        imageRes = R.drawable.icon_map_kakao,
        stringRes = R.string.map_share_by_kakao,
        marketPackage = "net.daum.android.map",
        scheme = Uri.parse("kakaomap://open?page=placeSearch")
    )

    fun checkOrStart(
        context: Context
    ) {
        val intent = Intent(Intent.ACTION_VIEW, scheme)
        intent.addCategory(Intent.CATEGORY_BROWSABLE)

        val list: List<ResolveInfo> =
            context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        if (list.isEmpty()) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$marketPackage")
                )
            )
        } else {
            context.startActivity(intent)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapShareModal(
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = null,
        containerColor = Color.White,
        sheetState = sheetState
    ) {
        MapShareModalContent(
            modifier = Modifier
                .padding(15.dp)
                .navigationBarsPadding(),
            onDismiss = {
                scope.launch {
                    sheetState.hide()
                    onDismiss()
                }
            }
        )
    }
}

class MapShareModalFragment : BottomSheetDialogFragment() {

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogRoundStyle
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                MapShareModalContent(
                    modifier = Modifier.padding(15.dp)
                        .navigationBarsPadding(),
                    onDismiss = {
                        this@MapShareModalFragment.dismiss()
                    }
                )
            }
        }
    }

    companion object {
        const val TAG = "MapShareModal"

        fun getInstance(): MapShareModalFragment {
            return MapShareModalFragment()
        }
    }
}

@Composable
private fun MapShareModalContent(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
    ) {
        ModalHeader(
            Modifier.fillMaxWidth(),
            header = "장소 공유",
            onDismiss = onDismiss
        )
        Column {
            listOf(
                MapItem.Naver,
                MapItem.Kakao
            ).forEach { item ->
                Button(
                    onClick = {
                        item.checkOrStart(context)
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xff282828)
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(item.imageRes),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = stringResource(item.stringRes),
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun MapShareContentPreview() {
    MapShareModalContent(
        modifier = Modifier.padding(10.dp),
        onDismiss = {}
    )
}