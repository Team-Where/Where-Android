package com.sooum.where_android.view.main.myMeetDetail.modal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toDrawable
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.sooum.domain.model.ImageAddType
import com.sooum.where_android.showSimpleToast
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.common.modal.ImagePickerDialog
import com.sooum.where_android.view.common.modal.LoadingAlert
import com.sooum.where_android.view.common.modal.LoadingAlertProvider
import com.sooum.where_android.view.widget.CoverImageView
import com.sooum.where_android.viewmodel.MyMeetDetailViewModel

class MeetCoverDialog : DialogFragment() {

    interface CoverActionHandler {
        fun changeCover()
    }

    private var coverActionHandler : CoverActionHandler? = null

    fun setHandler(handler: CoverActionHandler) {
        coverActionHandler = handler
    }

    companion object {
        private const val IMAGE_LINK_KEY = "image_link_key"
        const val TAG = "MeetCoverDialog"

        fun getInstance(
            imageLink: String? = null,
            handler: CoverActionHandler
        ): MeetCoverDialog {
            return MeetCoverDialog().apply {
                setHandler(handler)
                arguments = bundleOf(IMAGE_LINK_KEY to imageLink)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawable(android.graphics.Color.TRANSPARENT.toDrawable())

        val imageLink = arguments?.getString(IMAGE_LINK_KEY)

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                Column(
                    modifier = Modifier.background(Color.Transparent),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CoverImageView(
                        src = imageLink,
                        size = 200.dp,
                        radius = 32.dp,
                        modifier = Modifier.border(
                            border = BorderStroke(1.dp, Color.White),
                            shape = RoundedCornerShape(32.dp)
                        )
                    )
                    Spacer(Modifier.height(20.dp))
                    Button(
                        onClick = {
                            coverActionHandler?.changeCover()
                            dismiss()
                        },
                        modifier = Modifier
                            .widthIn(min = 118.dp)
                            .height(43.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0x80000000)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "커버 사진 변경",
                            fontFamily = pretendard,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}