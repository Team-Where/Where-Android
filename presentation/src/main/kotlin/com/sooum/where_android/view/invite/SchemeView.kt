package com.sooum.where_android.view.invite

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.sooum.domain.model.SimpleMeet
import com.sooum.where_android.showSimpleToast
import com.sooum.where_android.view.common.modal.LoadingView
import com.sooum.where_android.viewmodel.invite.SchemeResultViewModel

@Composable
fun SchemeView(
    schemeResultViewModel: SchemeResultViewModel = hiltViewModel(),
    navigateHome: () -> Unit = {},
    navigateMeet: (id: Int) -> Unit = {}
) {
    var simpleMeet: SimpleMeet? by remember {
        mutableStateOf(null)
    }
    val context = LocalContext.current
    LaunchedEffect(true) {
        schemeResultViewModel.parseSchemeData(
            onSuccess = {
                simpleMeet = it
            },
            onFail = {
                navigateHome()
            }
        )
    }
    BackHandler {
        navigateHome()
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (simpleMeet == null) {
            LoadingView(
                modifier = Modifier.align(Alignment.Center),
                msg = "초대장 정보를 불러오는 중 입니다."
            )
        } else {
            InviteBySchemeView(
                schemeResultViewModel.name,
                simpleMeet!!,
                onBack = navigateHome,
                onClick = {
                    schemeResultViewModel.acceptLinkCode(
                        onSuccess = {
                            navigateMeet(simpleMeet!!.id)
                        },
                        onFail = {
                            context.showSimpleToast(it)
                        }
                    )
                }
            )
        }
    }
}