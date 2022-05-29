package com.x.lib_common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.x.lib_base.utils.util.LogUtil

/**
 * @desc
 * @author wei
 * @date  2022/3/1
 **/
abstract class BaseFragment : Fragment(), UIHost {

    val TAG by lazy { javaClass.simpleName }

    override val hostView: View
        get() = activity?.window?.decorView!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewDataBinding: ViewDataBinding = getViewDataBinding(inflater, container, false)
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
//                LogUtil.e(TAG, "Lifecycle.Event : $event")
            }
        })
        return viewDataBinding.root
    }

    abstract fun getViewDataBinding(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): ViewDataBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeFlow()
    }

}