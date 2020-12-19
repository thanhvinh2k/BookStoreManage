package com.poly.duan1;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements HoaDonFragment.OnFragmentInteractionListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private TextView tvLoaiTB, tvThietBi, tvHoaDon, tvTopBanChay, tvThongKe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("cửa hàng thiết bị điện tử");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout = findViewById(R.id.activity_main_drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        loadThongKeFragment();
        tvLoaiTB = findViewById(R.id.tvLoaiTB);
        tvThietBi = findViewById(R.id.tvThietBi);
        tvHoaDon = findViewById(R.id.tvHoaDon);
        tvTopBanChay = findViewById(R.id.tvTopBanChay);
        tvThongKe = findViewById(R.id.tvThongKe);

        tvThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadThongKeFragment();
            }
        });

        tvLoaiTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLoaiTBFragment();
            }
        });

        tvThietBi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadThietBiFragment();
            }
        });

        tvHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHoaDonFragment();
            }
        });

        tvTopBanChay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTopThietBi();
            }
        });
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(this, "Search button selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.about:
                Toast.makeText(this, "About button selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.help:
                Toast.makeText(this, "Help button selected", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void loadLoaiTBFragment() {
        LoaiTBFragment loaiTBFragment = new LoaiTBFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_layout, loaiTBFragment);
        fragmentTransaction.commit();
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void loadThietBiFragment() {
        ThietBiFragment thietBiFragment = new ThietBiFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_layout, thietBiFragment);
        fragmentTransaction.commit();
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void loadHoaDonFragment() {
        HoaDonFragment hoaDonFragment = new HoaDonFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_layout, hoaDonFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void loadThongKeFragment() {
        ThongKeFragment thongKeFragment = new ThongKeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_layout, thongKeFragment);
        fragmentTransaction.commit();
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void loadTopThietBi() {
        TopTBFragment topTBFragment = new TopTBFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_layout, topTBFragment);
        fragmentTransaction.commit();
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onFragmentInteraction(String maHD) {
        HoaDonChiTietFragment hoaDonChiTietFragment = new HoaDonChiTietFragment();
        Bundle bd = new Bundle();
        bd.putString("maHoaDon", maHD);
        hoaDonChiTietFragment.setArguments(bd);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_layout, hoaDonChiTietFragment);
        fragmentTransaction.commit();
        drawerLayout.closeDrawer(GravityCompat.START);

    }

    @Override
    public void getMaHoaDon(String maHoaDon) {
        HDCTByIDFragment hdctByIDFragment = new HDCTByIDFragment();
        Bundle bd = new Bundle();
        bd.putString("maHD", maHoaDon);
        hdctByIDFragment.setArguments(bd);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, hdctByIDFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        drawerLayout.closeDrawer(GravityCompat.START);

    }
}
