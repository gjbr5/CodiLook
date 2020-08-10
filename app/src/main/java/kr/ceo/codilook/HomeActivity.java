package kr.ceo.codilook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button btn_recommend;//추천받기 버튼

    ImageView img_menubar;//메뉴바 이미지

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn_recommend = findViewById(R.id.home_btn_recommend);
        btn_recommend.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CodiActivity.class);
            startActivity(intent);
        });
    }
}