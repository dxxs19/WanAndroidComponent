package com.x.wanandroidcomponent

import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.x.lib_base.constant.RoutePath
import com.x.lib_common.BaseActivity
import com.x.wanandroidcomponent.databinding.ActivitySplashBinding
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun createBinding() {
        setTheme(R.style.Theme_Common)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
    }

    override fun initSetup() {
        super.initSetup()
        goToMainActivity()
    }

    private fun goToMainActivity() {
        val disposable = Observable.just(1)
            .delay(800L, TimeUnit.MILLISECONDS)
            .subscribe{
                ARouter.getInstance().build(RoutePath.mainActivityPath).navigation()
                finish()
            }
    }

}