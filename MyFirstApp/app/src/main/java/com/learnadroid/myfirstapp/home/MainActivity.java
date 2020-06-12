package com.learnadroid.myfirstapp.home;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.learnadroid.myfirstapp.R;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    ImageView searchIcon;
    ImageView orderIcon;
    ImageView profileIcon;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();

    }

    private void initView() {
        searchIcon = findViewById(R.id.imageButtonFindeCI);
        orderIcon = findViewById(R.id.imageButtonOrderCI);
        profileIcon = findViewById(R.id.imageButtonAccountCI);

        viewPager = (ViewPager) findViewById(R.id.vp_main);
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), 3));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setActiveIcon(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tl_main);
        tabLayout.setSmoothScrollingEnabled(true);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setActiveIcon(int pos){
        searchIcon.setImageResource(R.drawable.ic_findroom);
        orderIcon.setImageResource(R.drawable.ic_order_foreground);
        profileIcon.setImageResource(R.drawable.ic_profile_foreground);

        switch (pos){
            case 0:
                searchIcon.setImageResource(R.drawable.ic_findroom4_foreground);
                break;
            case 1:
                orderIcon.setImageResource(R.drawable.ic_order);
                break;
            case 2:
                profileIcon.setImageResource(R.drawable.ic_profile);
                break;
            default:
                break;

        }

    }

}