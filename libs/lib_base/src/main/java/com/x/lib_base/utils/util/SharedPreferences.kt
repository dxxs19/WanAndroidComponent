package com.x.lib_base.utils.util

import android.content.SharedPreferences

/**
 * @desc
 * @author wei
 * @date  2022/3/7
 **/

fun SharedPreferences.open(block: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.block()
    editor.apply()
}
