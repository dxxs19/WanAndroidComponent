package com.x.module_main.ui.fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.launcher.ARouter
import com.x.lib_base.bus.Bus
import com.x.lib_base.constant.RoutePath
import com.x.lib_base.utils.util.LogUtil
import com.x.lib_common.BaseFragment
import com.x.module_main.R
import com.x.module_main.databinding.FragmentArticlesBinding
import com.x.module_main.ui.adapter.ArticlePagingDataAdapter
import com.x.module_main.viewmodel.ArticleViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

/**
 * @desc
 * @author wei
 * @date  2022/3/1
 **/
class ArticleFragment : BaseFragment() {
    val viewModel: ArticleViewModel by lazy { ViewModelProvider(this)[ArticleViewModel::class.java] }
    private lateinit var binding: FragmentArticlesBinding
    private val pagingAdapter: ArticlePagingDataAdapter by lazy { ArticlePagingDataAdapter() }

    override fun getViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        b: Boolean
    ): ViewDataBinding {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_articles, container, false)
        binding.viewModel = this.viewModel
        binding.owner = this
        binding.lifecycleOwner = this
        return binding
    }

    override fun initView() {
        super.initView()
//        pagingAdapter = ArticlePagingDataAdapter()
        binding.recyclerView.adapter = pagingAdapter
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.postDelayed({ binding.swipeRefresh.isRefreshing = false }, 2000)
        }
        viewModel.dbArticles?.observe(this) {
            it.forEach { item ->
                LogUtil.e(TAG, item.toString())
            }
        }

        pagingAdapter.itemClickBlock = {
            LogUtil.e(TAG, it.toString())
            ARouter.getInstance().build(RoutePath.webViewActivityPath)
                .withString("url", it.link)
                .withString("content", it.title)
                .navigation()
        }
    }

    override fun initSetup() {
        super.initSetup()
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getArticles().collectLatest {
                pagingAdapter.submitData(it)
            }
        }

//        testCoroutines()
    }

    private fun testCoroutines() {
        val job = GlobalScope.launch(Dispatchers.Main) {
            Log.e(TAG, "当前线程: ${Thread.currentThread()}")
            launch(Dispatchers.IO) {
                Log.e(TAG, "launch1 当前线程: ${Thread.currentThread()}")
            }
            launch(Dispatchers.Default) {
                Log.e(TAG, "launch2 当前线程: ${Thread.currentThread()}")
            }
            coroutineScopeTest()
        }
        job.cancel()

        runBlocking {
            val start = System.currentTimeMillis()
            val result1 = async {
                delay(1000)
                5 + 5
            }.await()

            val result2 = async {
                delay(1000)
                4 + 6
            }.await()
            println("result: ${result1 + result2}")
            val end = System.currentTimeMillis()
            println("cost ${end - start} ms.")
        }

        lifecycleScope.launch(Dispatchers.Main) {
            ioCode()
            mainCode()
        }
    }

    private suspend fun coroutineScopeTest() = coroutineScope {
        launch {
            println("......")
        }
    }

    private suspend fun ioCode() {
        withContext(Dispatchers.IO) {
            Log.e(TAG, "我是io线程： ${Thread.currentThread()}")
        }
    }

    private suspend fun mainCode() {
        withContext(Dispatchers.Main) {
            Log.e(TAG, "我是Main线程： ${Thread.currentThread()}")
        }
    }



}