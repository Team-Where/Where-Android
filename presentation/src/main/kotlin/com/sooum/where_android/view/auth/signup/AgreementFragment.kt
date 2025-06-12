package com.sooum.where_android.view.auth.signup

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentAgreementBinding
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.view.auth.AuthBaseFragment
import com.sooum.where_android.view.auth.navigateEmailVerification
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AgreementFragment : AuthBaseFragment<FragmentAgreementBinding>(
    FragmentAgreementBinding::inflate
) {
    private lateinit var allCheckboxes: List<CheckBox>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allCheckboxes = listOf(
            binding.checkboxRequiredAge,
            binding.checkboxRequiredService,
            binding.checkboxOptionalAds,
            binding.checkboxOptionalMarketing
        )

        binding.nextBtn.setOnClickListener {
            if (!isRequiredChecked()) {
                showToast("체크박스를 확인해주세요.")
                return@setOnClickListener
            }
            navHostController.navigateEmailVerification()
        }

        binding.imageBack.setOnClickListener {
            navHostController.popBackStack<ScreenRoute.AuthRoute.SocialLogin>(inclusive = false)
        }

        setupCheckboxListeners()
        updateNextButtonBackground()
    }

    override fun initView() {

        with(binding) {
            listOf(
                buttonViewServiceTerms to WebType.SERVICE_TERMS,
                buttonViewAgeTerms to WebType.USE_TERMS,
                buttonViewMarketingTerms to WebType.MARKETING_TERMS,
                buttonViewAdsTerms to WebType.ADS_TERMS
            ).forEach { (button, type) ->
                button.setOnClickListener {
                    openWebTab(type)
                }
            }
        }

    }

    private fun setupCheckboxListeners() {
        binding.checkboxAgreeAll.setOnCheckedChangeListener { _, isChecked ->
            allCheckboxes.forEach { checkbox ->
                checkbox.isChecked = isChecked
                updateCheckboxDrawable(checkbox, isChecked)
            }
            updateNextButtonBackground()
        }

        allCheckboxes.forEach { checkbox ->
            checkbox.setOnCheckedChangeListener { _, _ ->
                updateCheckboxDrawable(checkbox, checkbox.isChecked)
                val allChecked = allCheckboxes.all { it.isChecked }
                binding.checkboxAgreeAll.setOnCheckedChangeListener(null)
                binding.checkboxAgreeAll.isChecked = allChecked
                binding.checkboxAgreeAll.setOnCheckedChangeListener { _, isChecked ->
                    allCheckboxes.forEach { cb ->
                        cb.isChecked = isChecked
                        updateCheckboxDrawable(cb, isChecked)
                    }
                    updateNextButtonBackground()
                }
                updateCheckboxDrawable(binding.checkboxAgreeAll, allChecked)
                updateNextButtonBackground()
            }
        }
    }

    private fun isRequiredChecked(): Boolean {
        return binding.checkboxRequiredAge.isChecked && binding.checkboxRequiredService.isChecked
    }

    private fun updateCheckboxDrawable(checkbox: CheckBox, isChecked: Boolean) {
        val drawableRes =
            if (isChecked) R.drawable.icon_agreement_color else R.drawable.icon_agreement_noncolor
        checkbox.setCompoundDrawablesWithIntrinsicBounds(drawableRes, 0, 0, 0)
    }

    private fun updateNextButtonBackground() {
        val isRequiredChecked = isRequiredChecked()
        binding.nextBtn.isEnabled = isRequiredChecked
    }

    enum class WebType(
        val url: String
    ) {
        SERVICE_TERMS("https://meteor-condor-9e6.notion.site/205912bcf29c801ca18fc73edfdc6036"),
        USE_TERMS("https://meteor-condor-9e6.notion.site/205912bcf29c80e79057f5a07578cf05"),
        MARKETING_TERMS("https://meteor-condor-9e6.notion.site/205912bcf29c80e7b3f7e359e35a3ee6"),
        ADS_TERMS("https://meteor-condor-9e6.notion.site/3-205912bcf29c807fbdc4d69da0a841df")
    }

    private fun openWebTab(
        type: WebType
    ) {
        val intent = CustomTabsIntent.Builder()
            .build()
        intent.launchUrl(requireContext(), type.url.toUri())
    }
}
