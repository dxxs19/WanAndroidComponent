package com.x.module_main.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.x.lib_common.BaseFragment
import com.x.module_main.databinding.FragmentMineBinding
import com.x.module_main.viewmodel.MineViewModel

/**
 * @desc
 * @author wei
 * @date  2022/3/1
 **/
class MineFragment : BaseFragment() {

    override fun getViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        b: Boolean
    ): ViewDataBinding {
        val binding : FragmentMineBinding = FragmentMineBinding.inflate(inflater, container, b)
        binding.viewModel = ViewModelProvider(this)[MineViewModel::class.java]
        binding.owner = this
        return binding
    }
}