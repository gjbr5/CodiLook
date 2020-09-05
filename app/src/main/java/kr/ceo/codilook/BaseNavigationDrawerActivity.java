package kr.ceo.codilook;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import kr.ceo.codilook.ui.appinfo.AppInfoActivity;
import kr.ceo.codilook.ui.login.LoginActivity;
import kr.ceo.codilook.ui.main.HomeActivity;
import kr.ceo.codilook.ui.register.MyInfoActivity;

public class BaseNavigationDrawerActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navDrawer;
    private NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = item -> {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.nav_user_info:
                intent.setClass(BaseNavigationDrawerActivity.this, MyInfoActivity.class);
                break;
            case R.id.nav_recommend:
                intent.setClass(BaseNavigationDrawerActivity.this, HomeActivity.class);
                break;
            case R.id.nav_app_info:
                intent.setClass(BaseNavigationDrawerActivity.this, AppInfoActivity.class);
                break;
            case R.id.nav_logout:
                intent.putExtra("logout", true);
                intent.setClass(BaseNavigationDrawerActivity.this, LoginActivity.class);
                break;
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        return true;
    };

    @Override
    public void setContentView(int layoutResID) {
        ViewGroup contentParent = findViewById(R.id.base_content_parent);
        contentParent.removeAllViews();
        getLayoutInflater().inflate(layoutResID, contentParent);
    }

    @Override
    public void setContentView(View view) {
        ViewGroup contentParent = findViewById(R.id.base_content_parent);
        contentParent.removeAllViews();
        contentParent.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        ViewGroup contentParent = findViewById(R.id.base_content_parent);
        contentParent.removeAllViews();
        contentParent.addView(view, params);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base_navigation_drawer);

        setSupportActionBar(findViewById(R.id.base_toolbar_title));
        ActionBar actionBar = Objects.requireNonNull(getSupportActionBar());
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        drawerLayout = findViewById(R.id.base_drawer_layout);
        navDrawer = findViewById(R.id.base_nav_drawer);
        navDrawer.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        drawerLayout.openDrawer(navDrawer);
        return super.onOptionsItemSelected(item);
    }
}