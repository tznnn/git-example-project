package com.example.exampleproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawer;
    @BindView(R.id.navView)
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentHome()).commit();
            setTitle("Anasayfa");
            navigationView.setCheckedItem(R.id.navHome);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        String TAG = "createFormFragment";
        switch (item.getItemId()) {
            case R.id.navHome:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentHome()).commit();
                toolbar.setTitle("Anasayfa");
                break;
            case R.id.navForm:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentForm()).commit();
                Fragment nextFrag = new FragmentForm();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, nextFrag, TAG);
                fragmentTransaction.addToBackStack("formFragment");
                fragmentTransaction.commit();

                toolbar.setTitle("Form Ekranı");
                break;
            case R.id.navFormDetail:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentFormDetail()).commit();
                toolbar.setTitle("Form Listesi");
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}