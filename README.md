# SystemBarsCompat
[![](https://jitpack.io/v/kouxinhong/systembars-appcompat.svg)](https://jitpack.io/#kouxinhong/systembars-appcompat)
[![](https://img.shields.io/badge/License-Apache--2.0-blue.svg)](https://github.com/kouzi/systembars-appcompat/main/LICENSE)

Android 系统状态栏和导航栏兼容库，简化全屏显示的实现和适配。

## 功能特性

- 一键设置沉浸式状态栏和导航栏
- 支持状态栏和导航栏颜色自定义
- 自动适配底部导航栏和手势导航
- 提供显示/隐藏系统栏的便捷方法
- 支持暗黑模式适配
- 支持输入法显示/隐藏控制
- 支持检测手势导航和按钮导航模式

## 最低要求

* 支持 AndroidX
* `minSdkVersion >= 21` (Android 5.0 Lollipop)
* `compileSdkVersion >= 30`
* `jvmTarget = '1.8'`

## 安装方式
1. 在项目根目录的 build.gradle 文件中添加 JitPack 仓库
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

2. 在应用模块的 build.gradle 文件中添加依赖
```gradle
dependencies {
    implementation 'com.github.kouzi:systembars-appcompat:0.3.1-alpha'
}
```

## 使用方法

### 基础用法

#### 1. 设置无ActionBar的Activity
```kotlin
SystemBars.instance.setSystemBars(
    activity = this,
    view = binding.root,
    navigationBarColor = R.color.transparent,
    bottomView = binding.bottomNavigation,
    isAppearanceLightStatusBars = true
)
```

#### 2. 设置有ActionBar的Activity
```kotlin
SystemBars.instance.setNavigationBar(
    activity = this,
    rootView = binding.toolbar,
    navigationBarColor = R.color.transparent,
    bottomView = binding.bottomNavigation,
    isAppearanceLightStatusBars = true
)
```

### 高级功能

#### 1. 显示/隐藏系统栏
```kotlin
// 显示系统栏
SystemBars.instance.showSystemBars(binding.root)

// 隐藏系统栏
SystemBars.instance.hideSystemBars(binding.root)
```

#### 2. 控制输入法
```kotlin
// 显示输入法
SystemBars.instance.showIme(editText)

// 隐藏输入法
SystemBars.instance.hideIme(editText)
```

#### 3. 检测导航模式
```kotlin
SystemBars.instance.setOnGesturesNavigationListener(this, binding.root) {
    // isGestures 为 true 表示当前是手势导航模式
    if (it) {
        // 处理手势导航模式
    } else {
        // 处理按钮导航模式
    }
}
```

## 示例应用

项目包含一个示例应用，展示了库的各种使用场景：
- 基本的沉浸式模式实现
- 带ActionBar的页面适配
- 底部导航栏的适配
- 暗黑模式适配
- 系统栏显示/隐藏控制

## 示例截图

![边缘适配](https://github.com/kouzi/systembars-appcompat/blob/main/image/edge.png)

![示例1](https://github.com/kouzi/systembars-appcompat/blob/main/image/1.png)

![示例2](https://github.com/kouzi/systembars-appcompat/blob/main/image/2.png)

![示例3](https://github.com/kouzi/systembars-appcompat/blob/main/image/3.png)

![示例4](https://github.com/kouzi/systembars-appcompat/blob/main/image/4.png)

![示例5](https://github.com/kouzi/systembars-appcompat/blob/main/image/5.png)

## 注意事项

1. 对于 Android 10 (API 29) 及以上版本，库会自动处理手势导航和按钮导航的适配
2. 为了获得最佳效果，建议在 Activity 的 `onCreate` 方法中调用相关方法
3. 对于使用了 Fragment 的页面，请在 Fragment 的 `onViewCreated` 方法中调用
4. 如果需要支持暗黑模式，请确保在 values-night 目录下提供相应的主题配置

## License

```
Copyright (C) 2021 kxh

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
