package com.kouzi.systembars

import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
import androidx.core.view.WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

/**
 * Android 系统栏工具类，用于简化状态栏和导航栏的沉浸式适配，支持全面屏设备
 *
 * @author kxh
 * @date 2021/7/7
 * @version 1.0.0
 */
class SystemBars private constructor() {
    companion object {
        /**
         * 实例化工具类，非线程安全模式
         * 适合单线程环境或已确保线程安全的场景使用
         */
        @JvmStatic
        val instance by lazy(mode = LazyThreadSafetyMode.NONE) { SystemBars() }

        /**
         * 线程安全的实例
         * 适合多线程环境下使用
         */
        @JvmStatic
        val instanceSync by lazy { SystemBars() }
    }

    /**
     * 设置沉浸式系统栏（状态栏和导航栏）
     *
     * @param activity 活动页面
     * @param rootView 根视图
     * @param navigationBarColor 导航栏颜色资源ID
     * @param bottomView 底部视图（如BottomNavigationView）
     * @param isLightStatusBars 是否使用浅色状态栏图标（适用于深色背景）
     * @param isLightNavigationBars 是否使用浅色导航栏图标（适用于深色背景）
     * @param autoAdjustForNavigationMode 是否根据导航模式自动调整UI
     */
    @UiThread
    fun setupImmersiveMode(
        activity: AppCompatActivity,
        rootView: View,
        @ColorRes navigationBarColor: Int = 0,
        bottomView: View? = null,
        isLightStatusBars: Boolean = false,
        isLightNavigationBars: Boolean? = null,
        autoAdjustForNavigationMode: Boolean = true
    ) {
        val window = activity.window
        val bottomViewOriginalPadding = bottomView?.paddingBottom ?: 0
        val rootViewOriginalPadding = rootView.paddingTop

        // 禁用系统栏自动调整内容布局
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // 配置全面屏设备支持
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // 设置刘海屏适配
            window.attributes.layoutInDisplayCutoutMode = 
                android.view.WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        // 设置窗口内边距监听器，处理不同系统版本的适配
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, windowInsets ->
            // 获取系统栏的内边距
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            
            // 调整根视图的外边距以适应左右系统栏
            val mlp = v.layoutParams as ViewGroup.MarginLayoutParams
            mlp.leftMargin = insets.left
            mlp.rightMargin = insets.right
            
            // 调整根视图的内边距以适应顶部状态栏
            v.setPadding(
                v.paddingLeft,
                rootViewOriginalPadding + insets.top,
                v.paddingRight,
                v.paddingBottom
            )
            
            // 调整底部视图的内边距以适应底部导航栏
            bottomView?.setPadding(
                bottomView.paddingLeft,
                bottomView.paddingTop,
                bottomView.paddingRight,
                bottomViewOriginalPadding + insets.bottom
            )

            // 获取WindowInsetsController用于控制系统栏行为
            val controller = ViewCompat.getWindowInsetsController(rootView)
            controller?.let {
                // 设置状态栏图标样式
                it.isAppearanceLightStatusBars = isLightStatusBars
                
                // 设置导航栏图标样式，如果未指定则与状态栏保持一致
                val lightNavBar = isLightNavigationBars ?: isLightStatusBars
                it.isAppearanceLightNavigationBars = lightNavBar
            }

            // 根据Android版本设置导航栏颜色和其他特性
            when {
                Build.VERSION.SDK_INT >= 29 -> {
                    // Android 10及以上版本的适配
                    window.isStatusBarContrastEnforced = false
                    window.isNavigationBarContrastEnforced = false
                    
                    // 根据导航模式动态设置导航栏颜色
                    if (autoAdjustForNavigationMode && insets.bottom > 90) {
                        // 手势导航模式
                        if (navigationBarColor != 0) {
                            window.navigationBarColor = ContextCompat.getColor(activity, navigationBarColor)
                        }
                    } else {
                        // 按钮导航模式或无需自动调整
                        if (navigationBarColor != 0) {
                            window.navigationBarColor = ContextCompat.getColor(activity, navigationBarColor)
                        }
                    }
                }
                Build.VERSION.SDK_INT >= 26 -> {
                    // Android 8.0至Android 9版本的适配
                    if (navigationBarColor != 0) {
                        window.navigationBarColor = ContextCompat.getColor(activity, navigationBarColor)
                    }
                }
                Build.VERSION.SDK_INT >= 21 -> {
                    // Android 5.0至Android 7版本的适配
                    if (navigationBarColor != 0) {
                        window.navigationBarColor = ContextCompat.getColor(activity, navigationBarColor)
                    }
                }
            }
            
            // 标记内边距已处理，避免重复调用
            WindowInsetsCompat.CONSUMED
        }
    }

    /**
     * 显示系统栏（状态栏和导航栏）
     *
     * @param view 任意视图，用于获取WindowInsetsController
     */
    @UiThread
    fun showSystemBars(view: View) {
        val controller = ViewCompat.getWindowInsetsController(view)
        controller?.show(WindowInsetsCompat.Type.systemBars())
    }

    /**
     * 隐藏系统栏（状态栏和导航栏）
     *
     * @param view 任意视图，用于获取WindowInsetsController
     * @param useTransientBehavior 是否使用短暂显示行为（滑动后自动隐藏）
     */
    @UiThread
    fun hideSystemBars(view: View, useTransientBehavior: Boolean = true) {
        val controller = ViewCompat.getWindowInsetsController(view)
        controller?.hide(WindowInsetsCompat.Type.systemBars())
        // 设置行为模式
        controller?.systemBarsBehavior = if (useTransientBehavior) {
            BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            BEHAVIOR_DEFAULT
        }
    }

    /**
     * 显示状态栏
     *
     * @param view 任意视图，用于获取WindowInsetsController
     * @param isLightStatusBars 是否使用浅色状态栏图标
     */
    @UiThread
    fun showStatusBar(view: View, isLightStatusBars: Boolean = false) {
        val controller = ViewCompat.getWindowInsetsController(view)
        controller?.show(WindowInsetsCompat.Type.statusBars())
        controller?.isAppearanceLightStatusBars = isLightStatusBars
    }

    /**
     * 隐藏状态栏
     *
     * @param view 任意视图，用于获取WindowInsetsController
     * @param isLightStatusBars 是否使用浅色状态栏图标
     */
    @UiThread
    fun hideStatusBar(view: View, isLightStatusBars: Boolean = false) {
        val controller = ViewCompat.getWindowInsetsController(view)
        controller?.hide(WindowInsetsCompat.Type.statusBars())
        controller?.isAppearanceLightStatusBars = isLightStatusBars
    }

    /**
     * 显示导航栏
     *
     * @param view 任意视图，用于获取WindowInsetsController
     * @param isLightNavigationBars 是否使用浅色导航栏图标
     */
    @UiThread
    fun showNavigationBar(view: View, isLightNavigationBars: Boolean = false) {
        val controller = ViewCompat.getWindowInsetsController(view)
        controller?.show(WindowInsetsCompat.Type.navigationBars())
        controller?.isAppearanceLightNavigationBars = isLightNavigationBars
    }

    /**
     * 隐藏导航栏
     *
     * @param view 任意视图，用于获取WindowInsetsController
     * @param isLightNavigationBars 是否使用浅色导航栏图标
     * @param useTransientBehavior 是否使用短暂显示行为（滑动后自动隐藏）
     */
    @UiThread
    fun hideNavigationBar(view: View, isLightNavigationBars: Boolean = false, useTransientBehavior: Boolean = true) {
        val controller = ViewCompat.getWindowInsetsController(view)
        controller?.hide(WindowInsetsCompat.Type.navigationBars())
        controller?.isAppearanceLightNavigationBars = isLightNavigationBars
        // 设置行为模式
        controller?.systemBarsBehavior = if (useTransientBehavior) {
            BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            BEHAVIOR_DEFAULT
        }
    }

    /**
     * 显示输入法
     *
     * @param view 通常是EditText或其父视图
     */
    @UiThread
    fun showIme(view: View) {
        val controller = ViewCompat.getWindowInsetsController(view)
        controller?.show(WindowInsetsCompat.Type.ime())
    }

    /**
     * 隐藏输入法
     *
     * @param view 任意视图，用于获取WindowInsetsController
     */
    @UiThread
    fun hideIme(view: View) {
        val controller = ViewCompat.getWindowInsetsController(view)
        controller?.hide(WindowInsetsCompat.Type.ime())
    }

    /**
     * 检测当前导航模式（手势导航或按钮导航）
     *
     * @param activity 活动页面
     * @param rootView 根视图
     * @param listener 导航模式变化监听器，true表示手势导航，false表示按钮导航
     */
    @UiThread
    fun setOnNavigationModeChangedListener(
        activity: AppCompatActivity,
        rootView: View,
        listener: (isGestureNavigation: Boolean) -> Unit
    ) {
        WindowCompat.setDecorFitsSystemWindows(activity.window, false)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { _, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            // 通过底部内边距的大小判断是否为手势导航模式
            val isGestureNavigation = insets.bottom > 90
            listener.invoke(isGestureNavigation)
            WindowInsetsCompat.CONSUMED
        }
    }

    /**
     * 快速设置系统栏颜色和图标样式
     *
     * @param view 任意视图，用于获取WindowInsetsController
     * @param statusBarColor 状态栏颜色资源ID
     * @param navigationBarColor 导航栏颜色资源ID
     * @param isLightStatusBars 是否使用浅色状态栏图标
     * @param isLightNavigationBars 是否使用浅色导航栏图标
     */
    @UiThread
    fun setSystemBarsColors(
        view: View,
        @ColorRes statusBarColor: Int = 0,
        @ColorRes navigationBarColor: Int = 0,
        isLightStatusBars: Boolean = false,
        isLightNavigationBars: Boolean? = null
    ) {
        val activity = view.context as? AppCompatActivity ?: return
        val window = activity.window
        val controller = ViewCompat.getWindowInsetsController(view)
        
        // 设置状态栏颜色
        if (statusBarColor != 0) {
            window.statusBarColor = ContextCompat.getColor(activity, statusBarColor)
        }
        
        // 设置导航栏颜色
        if (navigationBarColor != 0) {
            window.navigationBarColor = ContextCompat.getColor(activity, navigationBarColor)
        }
        
        // 设置状态栏图标样式
        controller?.isAppearanceLightStatusBars = isLightStatusBars
        
        // 设置导航栏图标样式，如果未指定则与状态栏保持一致
        val lightNavBar = isLightNavigationBars ?: isLightStatusBars
        controller?.isAppearanceLightNavigationBars = lightNavBar
    }
}