# SystemBarsCompat 使用指南

## 快速开始

### 基本配置

在使用 SystemBarsCompat 前，请确保您的项目满足以下要求：
- 已迁移到 AndroidX
- 最低支持 Android 5.0 (API 21) 或更高版本
- 使用 JDK 8 或更高版本

### 初始化配置

建议在 Activity 的 `onCreate` 方法中进行 SystemBarsCompat 的初始化配置。

## 常用场景示例

### 1. 基本的沉浸式模式

```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // 设置沉浸式模式
        SystemBars.instance.setSystemBars(
            activity = this,
            view = binding.root,
            navigationBarColor = R.color.transparent,
            bottomView = binding.bottomNavigation,
            isAppearanceLightStatusBars = true
        )
    }
}
```

### 2. 带 ActionBar 的页面适配

```kotlin
class WithActionBarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWithActionBarBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithActionBarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // 设置 ActionBar
        setSupportActionBar(binding.toolbar)
        
        // 设置导航栏
        SystemBars.instance.setNavigationBar(
            activity = this,
            rootView = binding.toolbar,
            navigationBarColor = R.color.black_transparent,
            bottomView = binding.bottomNavigation,
            isAppearanceLightStatusBars = true
        )
    }
}
```

### 3. 全屏模式切换

```kotlin
// 隐藏系统栏（进入全屏模式）
binding.fullScreenButton.setOnClickListener {
    SystemBars.instance.hideSystemBars(binding.root)
}

// 显示系统栏（退出全屏模式）
binding.exitFullScreenButton.setOnClickListener {
    SystemBars.instance.showSystemBars(binding.root)
}
```

### 4. 输入法控制

```kotlin
// 显示输入法
binding.showKeyboardButton.setOnClickListener {
    SystemBars.instance.showIme(binding.editText)
}

// 隐藏输入法
binding.hideKeyboardButton.setOnClickListener {
    SystemBars.instance.hideIme(binding.editText)
}
```

### 5. 检测导航模式

```kotlin
// 检测当前是手势导航还是按钮导航
SystemBars.instance.setOnGesturesNavigationListener(this, binding.root) { isGestures ->
    if (isGestures) {
        // 手势导航模式 - 调整UI以适应手势区域
        binding.bottomNavigation.setPadding(0, 0, 0, 24.dp)
    } else {
        // 按钮导航模式 - 恢复默认padding
        binding.bottomNavigation.setPadding(0, 0, 0, 0)
    }
}

// dp转px工具方法
private val Int.dp: Int
    get() = (this * resources.displayMetrics.density).toInt()
```

## 暗黑模式适配

SystemBarsCompat 支持暗黑模式，您需要在项目中提供相应的资源文件：

1. 在 `res/values-night` 目录下创建 `themes.xml` 文件
2. 定义适合暗黑模式的颜色和样式
3. SystemBarsCompat 会自动根据系统模式应用相应的样式

示例：

```xml
<!-- res/values-night/themes.xml -->
<resources xmlns:tools="http://schemas.android.com/tools">
    <style name="Theme.YourApp" parent="Theme.MaterialComponents.DayNight.NoActionBar">
        <!-- 暗黑模式下的颜色配置 -->
        <item name="android:statusBarColor">@android:color/black</item>
        <item name="android:navigationBarColor">@android:color/black</item>
    </style>
</resources>
```

## 常见问题解决方案

### 1. 适配刘海屏设备

对于刘海屏设备，Android 官方推荐使用 WindowInsets API 进行适配，SystemBarsCompat 已经内置了相关处理。但如果您遇到特殊情况，可以通过以下方式进一步调整：

```kotlin
// 在 setSystemBars 调用后添加额外的适配逻辑
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
    window.attributes.layoutInDisplayCutoutMode = 
        WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
}
```

### 2. 适配不同分辨率设备

为了确保在不同分辨率的设备上都能获得良好的显示效果，建议使用约束布局（ConstraintLayout）和 dp 单位进行 UI 设计。

### 3. 处理 Fragment 中的适配

在 Fragment 中使用 SystemBarsCompat 时，请在 `onViewCreated` 方法中进行调用：

```kotlin
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    // 在 Fragment 中设置系统栏
    SystemBars.instance.setSystemBars(
        activity = requireActivity(),
        view = view,
        navigationBarColor = R.color.transparent,
        isAppearanceLightStatusBars = true
    )
}
```

## 性能优化建议

1. 只在必要的 Activity 或 Fragment 中调用 SystemBarsCompat 的方法
2. 避免在 `onResume` 或 `onPause` 等频繁调用的生命周期方法中重复设置系统栏
3. 对于统一风格的应用，可以考虑创建一个 BaseActivity 来统一处理系统栏适配
4. 在配置变化（如屏幕旋转）时，不需要重新设置 SystemBarsCompat，它会自动适应新的配置

## 版本更新日志

### 0.3.1-alpha
- 优化了 Android 10 及以上版本的手势导航适配
- 修复了部分设备上的状态栏颜色异常问题
- 增加了对输入法的控制功能

### 0.3.0-alpha
- 重构了代码结构，提高了可维护性
- 增加了对暗黑模式的支持
- 修复了已知的兼容性问题