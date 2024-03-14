package com.example.quizapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.quizapp.model.DbQuery;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity  {

    private TextView drawerProfileName;
    private AppBarConfiguration mAppBarConfiguration;
    private FrameLayout main_frame;
    private NavigationView navigationView1;

    private BottomNavigationView bottomNavigationView;
    //    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottomNavigationView=findViewById(R.id.bottom_nav_bar);
        main_frame=findViewById(R.id.main_frame);

        setSupportActionBar(binding.appBarMain.toolbar);

        navigationView1=findViewById(R.id.nav_view);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();

        addEvents();

        drawerProfileName = navigationView.getHeaderView(0).findViewById(R.id.nav_drawer_name);

        String name = DbQuery.myProfile.getName();
        drawerProfileName.setText(name);

        setFragment(new CatergoryFragment());

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(main_frame.getId(),fragment);
        transaction.commit();
    }

    private void addEvents() {

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.navigation_home)
                {
                    setFragment(new CatergoryFragment());
                    return true;
                }
                if(item.getItemId()==R.id.navigation_leaderboard)
                {
                    setFragment(new LeaderBoardFragment());
                    return true;
                }

                if(item.getItemId()==R.id.navigation_account)
                {
                    setFragment(new AccountFragment());
                    return true;
                }
                return false;
            }
        });

        navigationView1.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home2) {
                    //Handle the camera action
                    setFragment(new CatergoryFragment());


                } if (id == R.id.nav_account2) {
                    setFragment(new AccountFragment());


                } if (id == R.id.nav_leaderboard2) {
                    setFragment(new LeaderBoardFragment());

                }if (id == R.id.nav_shared) {

                }if(id == R.id.nav_send){}
                DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}