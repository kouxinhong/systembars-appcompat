package com.kouzi.systembars

import android.os.Build
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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
        val instanceSync by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED) { SystemBars() }

        val instancePublic by lazy (mode = LazyThreadSafetyMode.PUBLICATION ) { SystemBars() }
    }

//    @UiThread
//    fun setSystemBars(activity: AppCompatActivity,view: View,@ColorRes statusBarColor: Int,@IdRes bottomIdRes: Int = 0){
//        val bottomView = activity.findViewById<View>(bottomIdRes)
//        setEdgeToEdge(activity,view,statusBarColor,bottomView)
//    }

    @UiThread
    fun setSystemBars(
        activity: AppCompatActivity,
        view: View,
        @ColorRes statusBarColor: Int = 0,
        @ColorRes navigationBarColor: Int = 0,
        bottomView: View? = null,
        isAppearanceLightStatusBars: Boolean = false
    ) {
        setEdgeToEdge(activity, view, statusBarColor,navigationBarColor, bottomView,isAppearanceLightStatusBars)
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
    private fun setEdgeToEdge(
        activity: AppCompatActivity,
        rootView: View,
        @ColorRes statusBarColor: Int,
        @ColorRes navigationBarColor: Int,
        bottomView: View? = null,
        isAppearanceLightStatusBars: Boolean
    ) {
        val window = activity.window
        if(Build.VERSION.SDK_INT >= 29){
            val controller = ViewCompat.getWindowInsetsController(rootView)
            controller?.isAppearanceLightNavigationBars = isAppearanceLightStatusBars
        }else{
            val controller = ViewCompat.getWindowInsetsController(rootView)
            controller?.isAppearanceLightNavigationBars = isAppearanceLightStatusBars
            window.navigationBarColor =  ContextCompat.getColor(activity,navigationBarColor)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            window.isStatusBarContrastEnforced = false
            window.isNavigationBarContrastEnforced = false
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            val mlp =  v.layoutParams as ViewGroup.MarginLayoutParams
            mlp.leftMargin = insets.left
            mlp.rightMargin = insets.right
//            if(null == activity.supportActionBar){
//                mlp.topMargin = insets.top
//            }
            v.apply {
                setPadding(paddingLeft, insets.top, paddingRight, paddingBottom)
            }
            if (Build.VERSION.SDK_INT >= 21) {
                window.statusBarColor =  ContextCompat.getColor(activity,statusBarColor)
            }
            bottomView?.apply {
                setPadding(paddingLeft, paddingTop, paddingRight, insets.bottom)
            }
            WindowInsetsCompat.CONSUMED
        }
    }
}