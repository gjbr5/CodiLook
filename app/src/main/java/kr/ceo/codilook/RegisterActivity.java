package kr.ceo.codilook;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    Button btn_mbti_link;//MBTI검사 링크이동 버튼
    Button btn_complete;//회원가입 완료 버튼

    EditText et_id;//ID 입력창
    EditText et_pw;//PW 입력창
    EditText et_pw_confirm;//PW 일치확인 버튼
    EditText et_email;//email 입력창

    Spinner sp_bloodType;//혈액형 스피너
    Spinner sp_constellation;//별자리 스피너
    Spinner sp_mbti;//MBTI 스피너

    ImageView img_menu;//메뉴바 이미지

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn_complete = findViewById(R.id.BT_complete);
        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view)  {
                finish();
            }
        });



        btn_mbti_link = findViewById(R.id.BT_mbti_link);
        btn_mbti_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MBTI 검사 창으로 이동을 알리는 다이얼로그 창 띄우기
                AlertDialog.Builder oDialog = new AlertDialog.Builder(RegisterActivity.this,
                        android.R.style.Theme_DeviceDefault_Light_Dialog);

                oDialog.setMessage("MBTI 검사를 받을 수 있는 링크로 이동하시겠습니까?")
                        .setTitle("")
                        .setPositiveButton("아니오", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNeutralButton("예", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.16personalities.com/ko/%EB%AC%B4%EB%A3%8C-%EC%84%B1%EA%B2%A9-%EC%9C%A0%ED%98%95-%EA%B2%80%EC%82%AC"));
                                startActivity(intent);
                            }
                        })
                        .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
                        .show();
            }
        });

        sp_bloodType = findViewById(R.id.SP_bloodType);
        sp_bloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString().trim();
                if(!item.equals("--")) {
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
                if(!item.equals("--")) {
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
                if(!item.equals("--")) {
                    Toast.makeText(getApplicationContext(), adapterView.getItemAtPosition(i) + " 선택", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}