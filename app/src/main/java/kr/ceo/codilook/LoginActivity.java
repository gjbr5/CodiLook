package kr.ceo.codilook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class LoginActivity extends AppCompatActivity {

    LoginViewModel loginViewModel;
    EditText etEmail; // 이메일 입력창
    EditText etPassword; // 비밀번호 입력창
    Button btnLogin; // 로그인 버튼
    Button btnRegister; // 회원가입 버튼
    ImageView btnMenu; // 메뉴바 버튼
    CheckBox cb_login;// 로그인 유지 체크박스

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        etEmail = findViewById(R.id.ET_email);
        etPassword = findViewById(R.id.ET_password);

        btnMenu = findViewById(R.id.BTN_menu);
        cb_login = findViewById(R.id.CB_login);

        btnRegister = findViewById(R.id.BT_register);
        btnRegister.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });

        btnLogin = findViewById(R.id.BT_login);
        btnLogin.setOnClickListener(view ->
                loginViewModel.login(etEmail.getText().toString(), etPassword.getText().toString()));

        loginViewModel.getResult().observe(this, result -> {
            if (result == null)
                return;
            if (result) {
                Toast.makeText(LoginActivity.this, loginViewModel.getUser().toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Integer emailError = loginViewModel.getEmailError();
                Integer passwordError = loginViewModel.getPasswordError();
                Integer loginError = loginViewModel.getLoginError();
                if (emailError != null)
                    etEmail.setError(getString(emailError));
                if (passwordError != null)
                    etPassword.setError(getString(passwordError));
                if (loginError != null) {
                    Toast.makeText(LoginActivity.this, getString(loginError), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}