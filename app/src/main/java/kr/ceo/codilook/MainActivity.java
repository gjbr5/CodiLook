package kr.ceo.codilook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btn_register;//회원가입 버튼
    Button btn_login;//로그인 버튼

    EditText et_id;//ID 입력창
    EditText et_pw;//PW 입력창

    ImageView img_menu;//메뉴바 이미지

    CheckBox cb_login;//로그인 유지 체크박스

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        btn_register = findViewById(R.id.BT_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view)  {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        btn_login = findViewById(R.id.BT_login);
        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {//로그인기능 구현
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}