# SystemBarsCompat

android statusbars and navigationBars utils

# use

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
	        implementation 'com.github.kouxinhong:SystemBarsCompat:-SNAPSHOT'
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

# screenshot

![图1.png](https://github.com/kouxinhong/SystemBarsCompat/blob/main/image/edge.png)

![图2.png](https://github.com/kouxinhong/SystemBarsCompat/blob/main/image/1.png)

![图3.png](https://github.com/kouxinhong/SystemBarsCompat/blob/main/image/2.png)

![图4.png](https://github.com/kouxinhong/SystemBarsCompat/blob/main/image/3.png)

![图5.png](https://github.com/kouxinhong/SystemBarsCompat/blob/main/image/4.png)

![图6.png](https://github.com/kouxinhong/SystemBarsCompat/blob/main/image/5.png)
