# SystemBarsCompat
[![](https://jitpack.io/v/kouxinhong/systembars-appcompat.svg)](https://jitpack.io/#kouxinhong/systembars-appcompat)
[![](https://img.shields.io/badge/License-Apache--2.0-blue.svg)](https://github.com/kouxinhong/systembars-appcompat/main/LICENSE)
[![Build Status](https://www.travis-ci.org/systembars-appcompat/main.svg?branch=master)](https://www.travis-ci.org/systembars-appcompat/main)

Android statusbars and navigationBars library utils,make the full screen display better.

## Requirements

* `support androidx`
* `minSdkVersion > 20`
* `jvmTarget = '1.8'`

## Download
1. Add the JitPack repository to your build file
```gradle
allprojects {
    repositories {
	...
	maven { url 'https://jitpack.io' }
    }
}
```

2. Add the dependency

```gradle
dependencies {
    implementation 'com.github.kouxinhong:systembars-appcompat:0.3.1-alpha'
}
```
3. Add Activity
```kotlin
//style ***.NoActionBar
   SystemBars.instance.setSystemBars(
            activity,
            rootView,
            navigationBarsColor,
            bottomNavigationView,
            true
            )
//style ***.*ActionBar

  SystemBars.instance.setNavigationBar(
            activity,
            rootView,
            navigationBarColor,
            bottomNavigationView,
            true
        )
```

##  Screenshot

![图1.png](https://github.com/kouxinhong/SystemBarsCompat/blob/main/image/edge.png)

![图2.png](https://github.com/kouxinhong/SystemBarsCompat/blob/main/image/1.png)

![图3.png](https://github.com/kouxinhong/SystemBarsCompat/blob/main/image/2.png)

![图4.png](https://github.com/kouxinhong/SystemBarsCompat/blob/main/image/3.png)

![图5.png](https://github.com/kouxinhong/SystemBarsCompat/blob/main/image/4.png)

![图6.png](https://github.com/kouxinhong/SystemBarsCompat/blob/main/image/5.png)

## License

```
Copyright (C) 2020. Dylan Cai

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
