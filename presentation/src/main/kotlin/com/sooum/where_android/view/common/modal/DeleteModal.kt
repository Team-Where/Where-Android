package com.sooum.where_android.view.common.modal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray100
import com.sooum.where_android.theme.Red500
import com.sooum.where_android.theme.pretendard

@Composable
fun DeleteModalContent(
    modifier: Modifier = Modifier,
    buttonText: String,
    onDelete: () -> Unit = {}
) {
    Column(
        modifier = modifier.height(150.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextButton(
            onClick = onDelete,
            colors = ButtonDefaults.buttonColors(
                containerColor = Gray100
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(horizontal = 10.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = buttonText,
                color = Red500,
                fontWeight = FontWeight.Medium,
                fontFamily = pretendard,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DeleteUserModalContentPreview() {
    DeleteModalContent(
        buttonText = "Test"
    )
}

/**
 * Xml 호환용 삭제 바텀 다이얼로그
 * ```
 * DeleteModalFragment.getInstance("버튼",[DeleteModalAction]).show(
 *     parentFragmentManager,DeleteModalFragment.TAG
 * )
 * ```
 */
class DeleteModalFragment : BottomSheetDialogFragment() {

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogRoundStyle
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val buttonText = requireArguments().getString(DELETE_TITLE_KEY)!!

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                DeleteModalContent(
                    onDelete = {
                        deleteModalAction?.onDelete()
                        dismiss()
                    },
                    modifier = Modifier.navigationBarsPadding(),
                    buttonText = buttonText
                )
            }
        }
    }

    interface DeleteModalAction {
        fun onDelete()
    }

    private var deleteModalAction: DeleteModalAction? = null

    fun setModalAction(deleteModalAction: DeleteModalAction) {
        this.deleteModalAction = deleteModalAction
    }

    companion object {
        const val TAG = "DeleteModalFragment"
        private const val DELETE_TITLE_KEY = "delete_title_key"

        fun getInstance(
            buttonText: String,
            action: DeleteModalAction
        ): DeleteModalFragment {
            return DeleteModalFragment().apply {
                arguments = bundleOf(
                    DELETE_TITLE_KEY to buttonText
                )
                setModalAction(action)
            }
        }
    }
}