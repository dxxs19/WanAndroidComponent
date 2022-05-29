package com.x.lib_base.permission

import androidx.fragment.app.FragmentActivity

/**
 *  单例类，对外接口
 */
object PermissionX {

    private const val TAG = "PermissionFragment"

    fun request(activity: FragmentActivity, vararg permissions: String,
                callback: PermissionCallback
    ) {
        val fragmentManager = activity.supportFragmentManager
        val existedFragment = fragmentManager.findFragmentByTag(TAG)
        val fragment = if (existedFragment != null) {
            existedFragment as PermissionFragment
        } else {
            val invisibleFragment = PermissionFragment()
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment
        }
        fragment.request(callback, *permissions)
    }

}