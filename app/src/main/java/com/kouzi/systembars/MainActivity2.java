package com.kouzi.systembars;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity2 extends AppCompatActivity {
    private static final String TAG = "MainActivity2";
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = findViewById(R.id.toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        AppCompatImageView imageView  = findViewById(R.id.image_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        int navigationBarsColor = Build.VERSION.SDK_INT >= 26 ? R.color.white_300 : R.color.black;
        BottomNavigationView BottomNavigationView = findViewById(R.id.bottom_navigation);
        SystemBars.getInstance().setSystemBars(this, toolbar, android.R.color.transparent,navigationBarsColor, BottomNavigationView, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_day_night){
            Log.i(TAG, "onOptionsItemSelected: "+isDarkThem());
            if(isDarkThem()){
                SystemBars.getInstance().showNavigationBars(toolbar,true);
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }else {
                SystemBars.getInstance().showNavigationBars(toolbar,false);
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull @NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int uiMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if(Configuration.UI_MODE_NIGHT_NO == uiMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            return;
        }
        if(Configuration.UI_MODE_NIGHT_YES == uiMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private boolean isDarkThem(){
        int uiMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return uiMode == Configuration.UI_MODE_NIGHT_YES;
    }

}