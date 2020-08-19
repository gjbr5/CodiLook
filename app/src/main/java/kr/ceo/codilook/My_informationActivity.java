package kr.ceo.codilook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import kr.ceo.codilook.viewmodel.LoginViewModel;

public class My_informationActivity extends AppCompatActivity {

    LoginViewModel loginViewModel;

    Button drawer_btn_Logout;

    TextView drawer_tv_LoginModify;
    TextView drawer_tv_CodiRecommend;
    TextView drawer_tv_AppInfo;

    ImageView drawer_img_Menu;
    ImageView drawer_img_CloseMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);

        //------------------메뉴바 코드 시작---------------------------------
        loginViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(LoginViewModel.class);

        // 전체화면인 DrawerLayout 객체 참조
        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        // Drawer 화면(뷰) 객체 참조
        final View drawerView = findViewById(R.id.drawer);


        // 드로어 화면을 열고 닫을 버튼 객체 참조
        drawer_img_Menu = findViewById(R.id.ly_drawer_img_Menu);
        drawer_img_CloseMenu = findViewById(R.id.ly_drawer_img_CloseMenu);


        // 드로어 여는 이미지 버튼 리스너
        drawer_img_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        // 드로어 닫는 이미지 버튼 리스너
        drawer_img_CloseMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(drawerView);
            }
        });

        drawer_btn_Logout = findViewById(R.id.ly_drawer_btn_Logout);

        drawer_btn_Logout.setOnClickListener(view -> {
            loginViewModel.logout();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        });

        drawer_tv_LoginModify = findViewById(R.id.ly_drawer_tv_LoginModify);
        drawer_tv_CodiRecommend = findViewById(R.id.ly_drawer_tv_CodiRecommend);
        drawer_tv_AppInfo = findViewById(R.id.ly_drawer_tv_AppInfo);

        drawer_tv_LoginModify.setOnClickListener(view -> {
            //회원 정보 수정
            Intent intent = new Intent(getApplicationContext(), My_informationActivity.class);
            startActivity(intent);
            finish();
        });
        drawer_tv_CodiRecommend.setOnClickListener(view -> {
            //코디 추천
            Intent intent = new Intent(getApplicationContext(), CodiActivity.class);
            startActivity(intent);
            finish();
        });
        drawer_tv_AppInfo.setOnClickListener(view -> {
            //앱 정보
            Intent intent = new Intent(getApplicationContext(), App_informationActivity.class);
            startActivity(intent);
            finish();
        });
        //------------------메뉴바 코드 끝---------------------------------
    }
}