package com.example.insights

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.compose.material3.Scaffold
import androidx.compose.ui.viewinterop.AndroidView
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.privacysandbox.tools.core.model.Type
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.compose.ui.platform.LocalContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleWebViewScreen(navController: NavHostController, articleUrl: String) {
    val isFullScreen = remember { mutableStateOf(false) }
    val activity = LocalContext.current as ComponentActivity

    Scaffold(
        topBar = {
            if (!isFullScreen.value) {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            // Implement share action here
                        }) {
                            Icon(Icons.Default.Share, contentDescription = "Share")
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        settings.apply {
                            javaScriptEnabled = true
                            domStorageEnabled = true
                            allowFileAccess = true
                            loadWithOverviewMode = true
                            useWideViewPort = true
                            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                            mediaPlaybackRequiresUserGesture = false
                        }

                        // Allow third-party cookies if required
                        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)

                        webChromeClient = object : WebChromeClient() {
                            private var customView: View? = null
                            private var customViewCallback: CustomViewCallback? = null

                            override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                                isFullScreen.value = true
                                customView = view
                                customViewCallback = callback

                                // Lock the screen orientation to landscape
                                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

                                // Set full-screen mode with WindowInsetsController for API 30 and above
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                    val windowInsetsController = activity.window.insetsController
                                    windowInsetsController?.hide(WindowInsets.Type.systemBars())
                                    windowInsetsController?.systemBarsBehavior =
                                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                                } else {
                                    // For older versions, fall back to deprecated system UI flags
                                    activity.window.decorView.systemUiVisibility = (
                                            View.SYSTEM_UI_FLAG_FULLSCREEN
                                                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                            )
                                }

                                // Show the custom full-screen view
                                (activity.findViewById<ViewGroup>(android.R.id.content)).addView(
                                    view, FrameLayout.LayoutParams(
                                        FrameLayout.LayoutParams.MATCH_PARENT,
                                        FrameLayout.LayoutParams.MATCH_PARENT
                                    )
                                )
                            }

                            override fun onHideCustomView() {
                                isFullScreen.value = false

                                // Remove the custom view
                                customView?.let {
                                    (activity.findViewById<ViewGroup>(android.R.id.content)).removeView(it)
                                }

                                // Restore orientation and clear full-screen flags
                                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                    activity.window.insetsController?.show(WindowInsets.Type.systemBars())
                                } else {
                                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                                }

                                customViewCallback?.onCustomViewHidden()
                                customView = null
                            }
                        }

                        webViewClient = object : WebViewClient() {
                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                // Inject CSS to hide the top 65px
                                view?.evaluateJavascript(
                                    """
                                    (function() {
                                        var style = document.createElement('style');
                                        style.innerHTML = 'body { position: relative; top: -120px; }';
                                        document.head.appendChild(style);
                                    })();
                                    """.trimIndent(), null
                                )
                            }
                        }

                        // Load the article URL
                        loadUrl(articleUrl)
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}