package kr.ceo.codilook.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import kr.ceo.codilook.ui.main.HomeActivity;
import kr.ceo.codilook.R;
import kr.ceo.codilook.model.LoginRepository;
import kr.ceo.codilook.model.PreferenceRepository;
import kr.ceo.codilook.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private static final int REGISTER_REQ_CODE = 100;

    LoginContract.Presenter presenter;

    EditText etEmail;
    EditText etPassword;
    CheckBox chkAutoLogin;
    Button btnLogin;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter(this,
                LoginRepository.getInstance(), PreferenceRepository.getInstance(getApplication()));
        initView();
        presenter.autoLogin();
    }

    private void initView() {
        etEmail = findViewById(R.id.login_et_email);
        etEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) presenter.isEmailValid(((EditText) v).getText().toString());
        });

        etPassword = findViewById(R.id.login_et_password);
        etPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) presenter.isPasswordValid(((EditText) v).getText().toString());
        });

        chkAutoLogin = findViewById(R.id.login_chk_auto_login);

        btnLogin = findViewById(R.id.login_btn_login);
        btnLogin.setOnClickListener(view -> presenter.login(
                etEmail.getText().toString(), etPassword.getText().toString(), chkAutoLogin.isChecked()));

        btnRegister = findViewById(R.id.login_btn_register);
        btnRegister.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivityForResult(intent, REGISTER_REQ_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTER_REQ_CODE && resultCode == Activity.RESULT_OK && data != null) {
            etEmail.setText(data.getStringExtra("email"));
            etPassword.setText(data.getStringExtra("password"));
        }
    }

    @Override
    public void waitForLogin() {
        // TODO: Show Progress View
    }

    @Override
    public void setEmailError(Integer emailError) {
        etEmail.setError(emailError == null ? null : getString(emailError));
    }

    @Override
    public void setPasswordError(Integer passwordError) {
        etPassword.setError(passwordError == null ? null : getString(passwordError));
    }

    @Override
    public void onLoginComplete(boolean success) {
        // TODO: Hide Progress View
        if (success) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void showErrorMessage(Integer message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
