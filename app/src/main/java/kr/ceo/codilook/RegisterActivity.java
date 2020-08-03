package kr.ceo.codilook;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    Button btn_login_confirm;//ID 중복확인 버튼
    Button btn_email_confirm;//email 중복확인 버튼
    Button btnMbtiLink;//MBTI검사 링크이동 버튼
    Button btnComplete;//회원가입 완료 버튼

    EditText etEmail;//email 입력창
    EditText etPw;//PW 입력창
    EditText et_pw_confirm;//PW 일치확인 버튼

    Spinner sp_bloodType;//혈액형 스피너
    Spinner sp_constellation;//별자리 스피너
    Spinner sp_mbti;//MBTI 스피너

    ImageView img_menu;//메뉴바 이미지

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnComplete = findViewById(R.id.BT_complete);
        btnComplete.setOnClickListener(view -> finish());
        btnMbtiLink = findViewById(R.id.BT_mbti_link);
        btnMbtiLink.setOnClickListener(view -> {
            // MBTI 검사 창으로 이동을 알리는 다이얼로그 창 띄우기
            AlertDialog.Builder oDialog = new AlertDialog.Builder(RegisterActivity.this,
                    android.R.style.Theme_DeviceDefault_Light_Dialog);

            oDialog.setMessage("MBTI 검사를 받을 수 있는 링크로 이동하시겠습니까?")
                    .setTitle("")
                    .setPositiveButton("아니오", (dialog, which) -> Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_SHORT).show())
                    .setNegativeButton("예", (dialog, which) -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.16personalities.com/ko/"));
                        startActivity(intent);
                    })
                    .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
                    .show();
        });

        sp_bloodType = findViewById(R.id.SP_bloodType);
        sp_bloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString().trim();
                if (!item.equals("--")) {
                    Toast.makeText(getApplicationContext(), adapterView.getItemAtPosition(i) + " 선택", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_constellation = findViewById(R.id.SP_constellation);
        sp_constellation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString().trim();
                if (!item.equals("--")) {
                    Toast.makeText(getApplicationContext(), adapterView.getItemAtPosition(i) + " 선택", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_mbti = findViewById(R.id.SP_mbti);
        sp_mbti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString().trim();
                if (!item.equals("--")) {
                    Toast.makeText(getApplicationContext(), adapterView.getItemAtPosition(i) + " 선택", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}