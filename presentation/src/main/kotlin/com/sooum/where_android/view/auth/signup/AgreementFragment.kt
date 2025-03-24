package com.sooum.where_android.view.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentAgreementBinding
import com.sooum.where_android.databinding.FragmentSignInBinding
import com.sooum.where_android.view.auth.AuthActivity
import com.sooum.where_android.view.auth.SignInFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgreementFragment : Fragment() {
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
                Toast.makeText(requireContext(), "체크박스를 확인해주세요.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            (activity as AuthActivity).navigateToFragment(EmailVerificationFragment())
        }

        binding.imageBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        setupCheckboxListeners()

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
        val backgroundRes = if (allChecked) R.drawable.shape_rounded_button_main_color else R.drawable.shape_rounded_button_gray_scale_300
        binding.nextBtn.setBackgroundResource(backgroundRes)
    }
}