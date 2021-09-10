package com.kouzi.systembars

import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

/**
 * @author kxh
 * @date 2021/7/7
 *
 * desc: SystemBars main
 */
class SystemBars private constructor() {


    companion object {
        @JvmStatic
        val instance by lazy(mode = LazyThreadSafetyMode.NONE) { SystemBars() }

        //LazyThreadSafetyMode.SYNCHRONIZED可以省略
        @JvmStatic
        val instanceSync by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED) { SystemBars() }
        @JvmStatic
        val instancePublic by lazy (mode = LazyThreadSafetyMode.PUBLICATION ) { SystemBars() }
    }

    /**
     * set statusBar and navigationBar
     *
     * @param activity *: AppCompatActivity
     * @param rootView root layout view
     * @param navigationBarColor navigationBar color
     * @param bottomView  bottom view
     * @param isAppearanceLightStatusBars navigationBar button color
     */
    @UiThread
    fun setNavigationBar(
        activity: AppCompatActivity,
        rootView: View,
        @ColorRes navigationBarColor: Int,
        bottomView: View,
        isAppearanceLightStatusBars: Boolean = false) {
        setEdgeToEdge(
            activity,
            rootView,
            navigationBarColor,
            bottomView,
            isAppearanceLightStatusBars
        )
    }

    /**
     * GesturesNavigation or ButtonNavigation Listener
     *
     * @param activity AppCompatActivity
     * @param rootView layout root View
     * @param listener (isGestures: Boolean) -> Unit
     */
    @UiThread
    fun setOnGesturesNavigationListener(activity: AppCompatActivity,rootView: View, listener: (isGestures: Boolean) -> Unit){
        WindowCompat.setDecorFitsSystemWindows(activity.window, false)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { _, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            listener.invoke(insets.bottom > 90)
            WindowInsetsCompat.CONSUMED
        }
    }

    @UiThread
    fun setSystemBars(
        activity: AppCompatActivity,
        view: View,
        @ColorRes navigationBarColor: Int = 0,
        bottomView: View? = null,
        isAppearanceLightStatusBars: Boolean = false) {
        setEdgeToEdge(activity, view,navigationBarColor, bottomView,isAppearanceLightStatusBars)
    }

    @UiThread
    fun showSystemBars(view: View){
        val controller = ViewCompat.getWindowInsetsController(view)
        controller?.show(WindowInsetsCompat.Type.systemBars())
    }

    @UiThread
    fun hideSystemBars(view: View){
        val controller = ViewCompat.getWindowInsetsController(view)
        controller?.hide(WindowInsetsCompat.Type.systemBars())
    }


    @UiThread
    internal fun showStatusBars(view: View, isAppearanceLightStatusBars: Boolean){
        val controller = ViewCompat.getWindowInsetsController(view)
        controller?.show(WindowInsetsCompat.Type.statusBars())
        controller?.isAppearanceLightStatusBars = isAppearanceLightStatusBars
    }

    @UiThread
    internal fun hideStatusBars(view: View,isAppearanceLightStatusBars: Boolean) {
        val controller = ViewCompat.getWindowInsetsController(view)
        controller?.hide(WindowInsetsCompat.Type.statusBars())
        controller?.isAppearanceLightStatusBars = isAppearanceLightStatusBars
    }

    @UiThread
    internal fun showNavigationBars(view: View, isAppearanceLightStatusBars: Boolean){
        val controller = ViewCompat.getWindowInsetsController(view)
        controller?.show(WindowInsetsCompat.Type.navigationBars())
        controller?.isAppearanceLightNavigationBars = isAppearanceLightStatusBars
    }

    @UiThread
    internal fun hideNavigationBars(view: View,isAppearanceLightStatusBars: Boolean,behavior:Int) {
        val controller = ViewCompat.getWindowInsetsController(view)
        controller?.hide(WindowInsetsCompat.Type.navigationBars())
        controller?.isAppearanceLightNavigationBars = isAppearanceLightStatusBars
        controller?.systemBarsBehavior = behavior
        /**
        systemBarsBehavior有三个值：
        BEHAVIOR_SHOW_BARS_BY_SWIPE
        BEHAVIOR_SHOW_BARS_BY_TOUCH
        BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
*/
    }

    @UiThread
    fun showIme(view: View){
        val controller = ViewCompat.getWindowInsetsController(view)
        controller?.show(WindowInsetsCompat.Type.ime())
    }

    @UiThread
    fun hideIme(view: View){
        val controller = ViewCompat.getWindowInsetsController(view)
        controller?.hide(WindowInsetsCompat.Type.ime())
    }

    //https://developer.android.google.cn/training/gestures/edge-to-edge#java
    private fun setEdgeToEdge(
        activity: AppCompatActivity,
        rootView: View,
        @ColorRes navigationBarColor: Int,
        bottomView: View? = null,
        isAppearanceLightStatusBars: Boolean) {
        val window = activity.window

        val bottomViewPaddingBottom = bottomView?.paddingBottom ?: 0
        val rootViewPaddingBottom = rootView.paddingTop

        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            val mlp =  v.layoutParams as ViewGroup.MarginLayoutParams
            mlp.leftMargin = insets.left
            mlp.rightMargin = insets.right
            v.apply {
                setPadding(paddingLeft, rootViewPaddingBottom+insets.top, paddingRight, paddingBottom)
            }
            bottomView?.apply {
                setPadding(paddingLeft, paddingTop, paddingRight, bottomViewPaddingBottom+insets.bottom)
            }
            if(Build.VERSION.SDK_INT >= 21){
                window.navigationBarColor =  ContextCompat.getColor(activity,navigationBarColor)
            }
            if(Build.VERSION.SDK_INT >= 26){
                val controller = ViewCompat.getWindowInsetsController(rootView)
                controller?.isAppearanceLightNavigationBars = isAppearanceLightStatusBars
                window.navigationBarColor =  ContextCompat.getColor(activity,navigationBarColor)
            }
            if (Build.VERSION.SDK_INT >= 29) {
                val controller = ViewCompat.getWindowInsetsController(rootView)
                window.isStatusBarContrastEnforced = false
                window.isNavigationBarContrastEnforced = false
                controller?.isAppearanceLightNavigationBars = isAppearanceLightStatusBars
                window.navigationBarColor= if(insets.bottom > 90) {
                    ContextCompat.getColor(activity, navigationBarColor)
                } else{
                    ContextCompat.getColor(activity, android.R.color.transparent)
                }
            }
            WindowInsetsCompat.CONSUMED
        }
    }
}