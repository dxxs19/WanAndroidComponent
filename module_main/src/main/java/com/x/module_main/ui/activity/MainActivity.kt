package com.x.module_main.ui.activity

import android.os.Debug
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.x.lib_base.bus.Bus
import com.x.lib_base.constant.RoutePath
import com.x.lib_base.utils.util.LogUtil
import com.x.lib_base.utils.util.TimerUtil
import com.x.lib_common.BaseActivity
import com.x.module_main.R
import com.x.module_main.databinding.ActivityMainBinding
import com.x.module_main.viewmodel.MainViewModel

@Route(path = RoutePath.mainActivityPath)
class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    override fun createBinding() {
        setTheme(R.style.MainTheme_WanAndroidComponent)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.owner = this
        binding.lifecycleOwner = this
    }

    override fun initView() {
        super.initView()

        Bus.get().with(this).accept(1111).subscribe {
            LogUtil.e(TAG, "收到消息")
        }

        val navView: BottomNavigationView = binding.bottomNav
        val navHost: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_frag) as NavHostFragment
        val navController = navHost.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_project, R.id.navigation_mine
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        Debug.stopMethodTracing()
        val costTime = TimerUtil.costTime()
        Log.e(TAG, "app 启动耗时 $costTime ms")
    }

}