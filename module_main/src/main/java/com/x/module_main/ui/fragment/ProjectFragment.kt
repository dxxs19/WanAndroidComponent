package com.x.module_main.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.x.lib_common.BaseFragment
import com.x.module_main.databinding.FragmentProjectBinding
import com.x.module_main.viewmodel.ProjectViewModel

/**
 * @desc
 * @author wei
 * @date  2022/3/1
 **/
class ProjectFragment : BaseFragment() {

    override fun getViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        b: Boolean
    ): ViewDataBinding {
        val binding : FragmentProjectBinding = FragmentProjectBinding.inflate(inflater, container, b)
        binding.viewModel = ViewModelProvider(this)[ProjectViewModel::class.java]
        binding.owner = this
        binding.lifecycleOwner = this
        return binding
    }

}