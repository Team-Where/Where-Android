package com.sooum.where_android.view.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentAgreementBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgreementFragment : AuthBaseFragment() {
    private lateinit var binding : FragmentAgreementBinding
    private lateinit var allCheckboxes: List<CheckBox>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAgreementBinding.inflate(inflater, container, false)

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
            navigateTo(EmailVerificationFragment())
        }

        binding.imageBack.setOnClickListener {
           popBackStack()
        }

        setupCheckboxListeners()
        updateNextButtonBackground()
        return binding.root
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
                binding.checkboxAgreeAll.isChecked = allChecked
                updateCheckboxDrawable(binding.checkboxAgreeAll, allChecked)
                updateNextButtonBackground()
            }
        }
    }

    private fun isRequiredChecked(): Boolean {
        return binding.checkboxRequiredAge.isChecked && binding.checkboxRequiredService.isChecked
    }

    private fun updateCheckboxDrawable(checkbox: CheckBox, isChecked: Boolean) {
        val drawableRes = if (isChecked) R.drawable.icon_agreement_color else R.drawable.icon_agreement_noncolor
        checkbox.setCompoundDrawablesWithIntrinsicBounds(drawableRes, 0, 0, 0)
    }

    private fun updateNextButtonBackground() {
        val allChecked = binding.checkboxAgreeAll.isChecked
        binding.nextBtn.isEnabled = allChecked
    }
}