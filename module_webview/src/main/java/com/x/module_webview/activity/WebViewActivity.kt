package com.x.module_webview.activity

import android.os.Build
import android.view.KeyEvent
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.x.lib_base.bus.Bus
import com.x.lib_base.constant.RoutePath
import com.x.lib_base.utils.util.LogUtil
import com.x.lib_common.BaseActivity
import com.x.module_webview.R
import com.x.module_webview.customview.MyWebChromeClient
import com.x.module_webview.customview.MyWebViewClient
import com.x.module_webview.databinding.ActivityWebviewBinding

@Route(path = RoutePath.webViewActivityPath)
class WebViewActivity : BaseActivity() {

    @Autowired
    lateinit var url: String

    @Autowired(name = "content")
    lateinit var title: String

    lateinit var binding: ActivityWebviewBinding

    override fun createBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_webview)
        binding.lifecycleOwner = this
    }

    override fun initView() {
        super.initView()
        //注入参数和服务(这里用到@Autowired所以要设置)
        //不使用自动注入,可不写，如CollectActivity没接收参数就没有设置
        ARouter.getInstance().inject(this)
        setTitle(title)
        initWebView()

        Bus.get().send(1111)
    }

    private fun initWebView() {
        val webSettings: WebSettings = binding.webView.settings
        with(webSettings) {
            userAgentString =
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36"
            domStorageEnabled = true
            cacheMode = WebSettings.LOAD_DEFAULT
            builtInZoomControls = true
            useWideViewPort = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
            // 解决部分网页显示不全问题
            javaScriptEnabled = true
        }

        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    request?.url.toString()
                } else {
                    ""
                }
                LogUtil.e(TAG, "shouldOverrideUrlLoading: $url")
                if ((url.startsWith("http:") || url.startsWith("https:"))) {
                    binding.webView.loadUrl(url)
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        binding.webView.loadUrl(url)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (binding.webView.visibility == View.VISIBLE && keyCode == KeyEvent.KEYCODE_BACK) {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack()
            } else {
                finish()
            }
            true
        } else {
            finish()
            true
        }
    }

    override fun onDestroy() {
        binding.webView.isFocusable = false
        binding.webView.removeAllViews()
        binding.webView.destroy()
        super.onDestroy()
    }

}