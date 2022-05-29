package com.x.lib_application

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.x.lib_base.utils.util.LogUtil
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

open class BaseApplication : MultiDexApplication() {

    private val TAG = javaClass.simpleName

    // 线程等待锁，每调用一次countDownLatch.countDown()，则减1，为0时调用await()函数
    private val countDownLatch by lazy { CountDownLatch(3) }
    // cpu 核数
    private val cpuCount = Runtime.getRuntime().availableProcessors()
    // 核心线程数
    private val corePoolSize = Math.max(2, Math.min(cpuCount - 1, 4))

    override fun onCreate() {
        super.onCreate()

        // cpuCount: 8 ; corePoolSize: 4
        LogUtil.e(TAG,"cpuCount: $cpuCount ; corePoolSize: $corePoolSize")
        val service = Executors.newFixedThreadPool(corePoolSize)
        if (isDebug()) {
            service.submit {
                ARouter.openLog()  // 打印日志
                countDownLatch.countDown()  // 每次countDown()，减1，直到等于0则执行await()
                LogUtil.e(TAG, "ARouter.openLog : ${countDownLatch.count}") // ARouter.openLog : 2
                countDownLatch.await()
                LogUtil.e(TAG, "ARouter.openLog : end")
            }
            service.submit {
                ARouter.openDebug()
                countDownLatch.countDown()  // 每次countDown()，减1，直到等于0则执行await()
                LogUtil.e(TAG, "ARouter.openDebug : ${countDownLatch.count}") // ARouter.openDebug : 1
                countDownLatch.await()
                LogUtil.e(TAG, "ARouter.openDebug : end")
            }
        }

        service.submit {
            ARouter.init(this)
            countDownLatch.countDown()  // 每次countDown()，减1，直到等于0则执行await()
            LogUtil.e(TAG, "ARouter.init : ${countDownLatch.count}")
            countDownLatch.await()
            LogUtil.e(TAG, "ARouter.init : end")
        }
        appContext = applicationContext

//        // 这两行必须写在init之前，否则这些配置在init过程中将无效
//        if (isDebug()) {
//            ARouter.openLog()  // 打印日志
//            ARouter.openDebug() //开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//        }
//
//        // 尽可能早，推荐在Application中初始化
//        ARouter.init(this)
//        appContext = applicationContext

    }

    private fun isDebug(): Boolean {
        return true
    }

}

/**
 * 全局applicationContext
 */
lateinit var appContext: Context