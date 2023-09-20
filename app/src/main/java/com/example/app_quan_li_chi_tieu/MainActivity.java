package com.example.app_quan_li_chi_tieu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.app_quan_li_chi_tieu.fragment.MyViewpager2Adapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager2 = findViewById(R.id.viewPager2);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        viewPager2.setAdapter(new MyViewpager2Adapter(this));
//       // xu ly su kien khi vuốt fragment
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }
        });
        //        // xu ly su kien khi click vao bottom navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.nav_home)
                    viewPager2.setCurrentItem(0);
                else if(item.getItemId()==R.id.nav_timkiem)
                    viewPager2.setCurrentItem(1);
                else if(item.getItemId()==R.id.nav_thongke)
                    viewPager2.setCurrentItem(2);
                else if(item.getItemId()==R.id.nav_info_user)
                    viewPager2.setCurrentItem(3);
                return true;
            }
        });
    }
}