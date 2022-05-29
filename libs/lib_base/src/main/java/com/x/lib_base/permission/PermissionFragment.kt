package com.x.lib_base.permission

import android.content.pm.PackageManager
import android.os.Build
import androidx.fragment.app.Fragment

typealias PermissionCallback = (Boolean, List<String>) -> Unit
/**
 *  不可见的 Fragment
 */
class PermissionFragment : Fragment() {

    private val REQUEST_CODE = 0x11

    private var callback: PermissionCallback? = null

    fun request(cb: PermissionCallback, vararg permissions: String) {
        callback = cb
        // 调用Fragment中提供的方法立即申请运行时权限，并将permissions参数列表传递进去
        // 这样就可以实现由外部调用方法自主指定要申请哪些权限的功能了。
        requestPermissions(permissions, REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE) {
            val denies = ArrayList<String>()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                for ((index, result) in grantResults.withIndex()) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        denies.add(permissions[index])
                    }
                }
            }
            // 判断是否所有申请的权限都被授权
            val allGranted = denies.isEmpty()
            callback?.let { it(allGranted, denies) }
        }
    }
}

