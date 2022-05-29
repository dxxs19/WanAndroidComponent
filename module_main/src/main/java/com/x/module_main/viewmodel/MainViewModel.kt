package com.x.module_main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @desc
 * @author wei
 * @date  2022/3/3
 **/
class MainViewModel : ViewModel() {
    var currentSecond : MutableLiveData<Int>? = null

    fun getCurrentSec() : MutableLiveData<Int>? {
        if (currentSecond == null) {
            currentSecond = MutableLiveData(0)
        }
        return currentSecond
    }

}