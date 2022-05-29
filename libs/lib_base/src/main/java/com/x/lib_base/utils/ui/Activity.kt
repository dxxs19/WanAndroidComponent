package com.x.lib_base.utils.ui

import android.app.Activity
import android.content.Context
import android.content.Intent

// inline 意思是内联函数。表示直接执行方法体内代码，而不会有调用普通方法的压栈出栈耗费资源的行为。
// 提升了性能，而且增加的代码量是在编译期执行的，对程序可读性不会造成影响。
// reified 意思是具体化的。reified关键字是用于Kotlin内联函数的,修饰内联函数的泛型,泛型被修饰后,
// 在方法体里,能从泛型拿到泛型的Class对象,这与java是不同的,java需要泛型且需要泛型的Class类型时,
// 是要把Class传过来的,但是kotlin不用了
inline fun <reified T: Activity> Context.startActivity() {
    val intent = Intent(this, T::class.java)
    if (this !is Activity) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(intent)
}

/**
 *  带参数跳转
 *  val pairs1: Pair<String, String> = Pair("first", "second")
 *  val pairs2: Pair<String, String> = Pair("beauty", "beautyleg")
 *  startActivity<TestActivity>(pairs1, pairs2)
 */
inline fun <reified T: Activity> Context.startActivity(vararg params: Pair<String, String>) {
    val intent = Intent(this, T::class.java)
    params.forEach {
        intent.putExtra(it.first, it.second)
    }
    startActivity(intent)
}


/**
 *  Activity控制类，可以添加，移除并且全部关闭
 */
object ActivityCollector {

    private val activities = ArrayList<Activity>()

    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    fun finishAll() {
        for (activity in activities) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        activities.clear()
        android.os.Process.killProcess(android.os.Process.myPid())
    }

}

