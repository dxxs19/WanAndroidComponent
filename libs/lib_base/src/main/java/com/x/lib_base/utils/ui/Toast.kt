package com.x.lib_base.utils.ui

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

private object Toaster {
    var keeper: Toast? = null
    var text: String? = null

    fun show(context: Context, text: String?, duration: Int = Toast.LENGTH_SHORT) {
        if (text.isNullOrEmpty()) {
            return
        }

        if (keeper == null || text != Toaster.text) {
            keeper = Toast.makeText(context, text, duration)
            Toaster.text = text
        }
        keeper?.show()
    }

    fun show(context: Context, @StringRes id: Int, duration: Int = Toast.LENGTH_SHORT) {
        show(context, context.getString(id), duration)
    }
}

fun View.toast(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    Toaster.show(context, text, duration)
}

fun Context.toast(vararg texts: String?, duration: Int = Toast.LENGTH_SHORT) {
    if (texts.isEmpty()) return

    var content = String()
    texts.forEach {
        content = content.plus(it)
    }
    Toaster.show(this, content, duration)
}

fun Context.toast(@StringRes vararg ids: Int, duration: Int = Toast.LENGTH_SHORT) {
    if (ids.isEmpty()) return

    var content = String()
    ids.forEach {
        content = content.plus(getString(it))
    }
    Toaster.show(this, content, duration)
}

fun Fragment.toast(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    context?.let { Toaster.show(it, text, duration) }
}

fun Fragment.toast(@StringRes id: Int, duration: Int = Toast.LENGTH_SHORT) {
    context?.let { Toaster.show(it, id, duration) }
}
