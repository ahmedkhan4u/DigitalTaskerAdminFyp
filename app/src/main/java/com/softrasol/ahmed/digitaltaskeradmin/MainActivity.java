package com.softrasol.ahmed.digitaltaskeradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.softrasol.ahmed.digitaltaskeradmin.Adapters.HomeTabsAccessorAdapter;
import com.softrasol.ahmed.digitaltaskeradmin.Fragments.HomeFragment;
import com.softrasol.ahmed.digitaltaskeradmin.Fragments.ManageWorkersFragment;
import com.softrasol.ahmed.digitaltaskeradmin.Fragments.MenuFragment;
import com.softrasol.ahmed.digitaltaskeradmin.Fragments.NotificationsFragment;
import com.softrasol.ahmed.digitaltaskeradmin.Interfaces.ToastMessage;
import com.softrasol.ahmed.digitaltaskeradmin.Services.NotificationService;

public class MainActivity extends AppCompatActivity implements ToastMessage {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_home,
            R.drawable.ic_notifications,
            R.drawable.icon_complaint,
            R.drawable.ic_menu
    };

    private Toolbar toolbar;
    private TextView textView;
    private ImageButton mBtnBack;

    //...............................................................................................
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayoutInflation();
        toolbarInflation();
        //checkNotificationAndTabSelection();

    }

    private void checkNotificationAndTabSelection() {

        String mNotify = getIntent().getStringExtra("notify");

        if (mNotify != null && mNotify.equalsIgnoreCase("notify")){
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();
        }

    }
    //...............................................................................................

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.settings){
            startActivity(new Intent(getApplicationContext(), ProfileSettingsActivity.class));
            return true;
        }
        if (itemId == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            finish();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_home,menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void tabLayoutInflation() {


        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        HomeTabsAccessorAdapter adapter = new HomeTabsAccessorAdapter(getSupportFragmentManager());
        adapter.setFragment(new HomeFragment(),"");
        adapter.setFragment(new NotificationsFragment(),"");
        adapter.setFragment(new ManageWorkersFragment(), "");
        adapter.setFragment(new MenuFragment(), "");
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        setupTabIcons();


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeToolbarText(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void changeToolbarText(int position) {
        switch (position){
            case 0:
                textView.setText("Home");
                break;

            case 1:
                textView.setText("Notifications");
                break;

            case 2:
                textView.setText("Complaints");
                break;

            case 3:
                textView.setText("Menu");
                break;
        }
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void toolbarInflation() {
        toolbar = findViewById(R.id.toolbar_home);
        textView = toolbar.findViewById(R.id.toolbarText);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        textView.setText("Home");
        mBtnBack = toolbar.findViewById(R.id.btnBack);
        mBtnBack.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            finish();
        }

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
