package com.sooum.where_android.view.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * 뷰바인딩 초기화 기능을 담은 기본형 Fragment
 * - binding 객체를 초기화 하고, [initView]를 통해 binding 연결후 안전하게 동작을 보장한다
 * - [onDestroyView]에서 binding 객체를 해제하여 메모리 누수를 방지한다.
 *
 * 예제 코드
 * ```
 * class SampleFragment : BaseViewBindingFragment<FragmentSampleBinding>(
 *     FragmentSampleBinding::inflate
 * ) {
 *     override fun initView() {
 *         TODO("Not yet implemented")
 *     }
 * }
 * ```
 */
abstract class BaseViewBindingFragment<VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
): Fragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * View init
     */
    abstract fun initView()
}