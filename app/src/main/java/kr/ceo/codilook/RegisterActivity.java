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
import androidx.lifecycle.ViewModelProvider;

import kr.ceo.codilook.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {
    RegisterViewModel registerViewModel;
    ImageView imgMenu; // 메뉴바 이미지
    EditText etEmail; // Email 입력창
    EditText etPassword; // PW 입력창
    EditText etPwConfirm; // PW 확인 입력창
    Spinner spBloodType; // 혈액형 스피너
    Spinner spConstellation; // 별자리 스피너
    Spinner spMbti; // MBTI 스피너
    Button btnMbtiLink; // MBTI검사 링크이동 버튼
    Button btnComplete; // 회원가입 완료 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(RegisterViewModel.class);

        etEmail = findViewById(R.id.ET_email);
        etPassword = findViewById(R.id.ET_password);
        etPwConfirm = findViewById(R.id.ET_pw_confirm);

        spBloodType = findViewById(R.id.SP_blood_type);
        spConstellation = findViewById(R.id.SP_constellation);
        spMbti = findViewById(R.id.SP_mbti);

        btnMbtiLink = findViewById(R.id.BT_mbti_link);
        btnComplete = findViewById(R.id.BT_complete);

        etEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) checkEmail();
        });

        etPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) checkPassword();
        });
        etPwConfirm.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) checkPasswordConfirm();
        });

        btnMbtiLink.setOnClickListener(view -> openLinkDialog());

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString().trim();
                if (!item.equals("--")) {
                    Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position) + " 선택", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        spBloodType.setOnItemSelectedListener(itemSelectedListener);
        spConstellation.setOnItemSelectedListener(itemSelectedListener);
        spMbti.setOnItemSelectedListener(itemSelectedListener);
        btnComplete.setOnClickListener(v -> register());
    }

    private void checkEmail() {
        String email = etEmail.getText().toString();
        Integer error = registerViewModel.isEmailValid(email);
        if (error != null)
            etEmail.setError(getString(error));
        else
            registerViewModel.checkUserCollision(etEmail.getText().toString()).observe(this, aBoolean -> {
                if (aBoolean)
                    etEmail.setError(getString(R.string.user_already_exists));
                else
                    etEmail.setError(null);
            });
    }

    private void checkPassword() {
        Integer error = registerViewModel.isPasswordValid(etPassword.getText().toString());
        if (error != null)
            etPassword.setError(getString(error));

    }

    private void checkPasswordConfirm() {
        if (!etPassword.getText().toString().equals(etPwConfirm.getText().toString()))
            etPwConfirm.setError(getString(R.string.password_does_not_match));
        else
            etPwConfirm.setError(null);
    }

    private void register() {
        checkEmail();
        checkPassword();
        checkPasswordConfirm();
        if (etEmail.getError() != null) {
            etEmail.requestFocus();
            return;
        } else if (etPassword.getError() != null) {
            etPassword.requestFocus();
            return;
        } else if (etPwConfirm.getError() != null) {
            etPwConfirm.requestFocus();
            return;
        }
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String bloodType = spBloodType.getSelectedItem().toString();
        String constellation = spConstellation.getSelectedItem().toString();
        if (constellation.contains("("))
            constellation = constellation.substring(0, constellation.indexOf("("));
        String mbti = spMbti.getSelectedItem().toString();

        registerViewModel.register(email, password, bloodType, constellation, mbti).observe(this, loginFormState -> {
            if (loginFormState.isDataValid()) {
                Intent intent = new Intent();
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                setResult(RESULT_OK, intent);
                finish();
                return;
            }
            Integer error = loginFormState.getEmailError();
            if (error != null) {
                etEmail.setError(getString(error));
                etEmail.requestFocus();
                return;
            }
            error = loginFormState.getPasswordError();
            if (error != null) {
                etPassword.setError(getString(error));
                etPassword.requestFocus();
                return;
            }
            Toast.makeText(RegisterActivity.this, getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
        });
    }

    private void openLinkDialog() {
        // MBTI 검사 창으로 이동을 알리는 다이얼로그 창 띄우기
        new AlertDialog.Builder(RegisterActivity.this,
                android.R.style.Theme_DeviceDefault_Light_Dialog)
                .setMessage("MBTI 검사를 받을 수 있는 링크로 이동하시겠습니까?")
                .setPositiveButton("아니오", (dialog, which) -> Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_SHORT).show())
                .setNegativeButton("예", (dialog, which) -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.16personalities.com/ko/"));
                    startActivity(intent);
                })
                .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
                .show();
    }

}