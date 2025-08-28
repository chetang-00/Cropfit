package com.example.cropfit;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class NavActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (AppConstants.currentUser==null) {
            AppConstants.currentUser= auth.getCurrentUser();
        }
        setContentView(R.layout.activity_nav);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer =  findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        navigationView.setNavigationItemSelectedListener(this);
        //HomeFragment homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).commit();
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch(id)
        {
            case R.id.nav_profile:
                Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),ProfileActivity2.class));
                //fragmentClass = ThirdFragment.class;

            case R.id.nav_home:
                // fragmentClass = SecondFragment.class;
                Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).commit();
                break;
            case R.id.nav_shop:
                Toast.makeText(getApplicationContext(),"Shopping Time",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),ShopActivity.class));
                //fragmentClass = ThirdFragment.class;
                break;
           case R.id.nav_orders:
                Toast.makeText(getApplicationContext(),"Your Order's",Toast.LENGTH_SHORT).show();
               startActivity(new Intent(getApplicationContext(),OrderActivity.class));
             break;
            case R.id.nav_appointment:
                Toast.makeText(getApplicationContext(),"Book Your Appointment",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),AppointmentActivity.class));
                break;
//            case R.id.nav_support:
//                Toast.makeText(getApplicationContext(),"Customer Support",Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getApplicationContext(),SupportActivity.class));
//                break;
           case R.id.nav_logout:

               FirebaseAuth auth = FirebaseAuth.getInstance();
               auth.signOut();
               AppConstants.currentUser=null;
               Toast.makeText(getApplicationContext(),"Thank You",Toast.LENGTH_SHORT).show();
               startActivity(new Intent(getApplicationContext(),LoginActivity.class));

               break;
            default:
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).commit();
                break;

        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);


        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}