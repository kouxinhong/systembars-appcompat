package com.kouzi.systembars

import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
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
        val instance by lazy(mode = LazyThreadSafetyMode.NONE){ SystemBars() }
        //LazyThreadSafetyMode.SYNCHRONIZED可以省略
//        val instanceSync by lazy { LazyThreadSafetyMode.SYNCHRONIZED }
//        val instancePublic by lazy { LazyThreadSafetyMode.PUBLICATION }
    }

    @UiThread
    fun setSystemBars(activity: AppCompatActivity,rootView: View,@ColorRes statusBarColor: Int,@IdRes bottomIdRes: Int = 0){
        val bottomView = rootView.findViewById<View>(bottomIdRes)
        setEdgeToEdge(activity,rootView,statusBarColor,bottomView)
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
    fun setSystemBars(activity: AppCompatActivity, rootView: View,@ColorRes statusBarColor: Int,bottomView: View? = null) {
        setEdgeToEdge(activity,rootView,statusBarColor,bottomView)
    }
    @UiThread
    fun showStatusBars(view: View, isAppearanceLightStatusBars: Boolean){
        val controller = ViewCompat.getWindowInsetsController(view)
        controller?.show(WindowInsetsCompat.Type.statusBars())
        controller?.isAppearanceLightStatusBars = isAppearanceLightStatusBars
    }
    @UiThread
    fun hideStatusBars(view: View,isAppearanceLightStatusBars: Boolean) {
        val controller = ViewCompat.getWindowInsetsController(view)
        controller?.hide(WindowInsetsCompat.Type.statusBars())
        controller?.isAppearanceLightStatusBars = isAppearanceLightStatusBars
    }
    @UiThread
    fun showNavigationBars(view: View, isAppearanceLightStatusBars: Boolean){
        val controller = ViewCompat.getWindowInsetsController(view)
        controller?.show(WindowInsetsCompat.Type.navigationBars())
        controller?.isAppearanceLightNavigationBars = isAppearanceLightStatusBars
    }

    @UiThread
    fun hideNavigationBars(view: View,isAppearanceLightStatusBars: Boolean,behavior:Int) {
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
    private fun setEdgeToEdge(activity: AppCompatActivity, rootView: View, @ColorRes statusBarColor: Int, bottomView: View? = null) {
        val window = activity.window
        window.isStatusBarContrastEnforced = false
        window.isNavigationBarContrastEnforced = false
        WindowCompat.setDecorFitsSystemWindows(activity.window, false)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            val mlp =  v.layoutParams as ViewGroup.MarginLayoutParams
            mlp.leftMargin = insets.left
            mlp.rightMargin = insets.right
            if(null == activity.supportActionBar){
                mlp.topMargin = insets.top
            }
            if (Build.VERSION.SDK_INT >= 21) {
                activity.window.statusBarColor =  ContextCompat.getColor(activity,statusBarColor)
            }
            bottomView?.apply {
                setPadding(paddingLeft, paddingTop, paddingRight, insets.bottom)
            }
            WindowInsetsCompat.CONSUMED
        }
    }
}