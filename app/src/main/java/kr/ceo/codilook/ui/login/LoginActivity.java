package kr.ceo.codilook.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import kr.ceo.codilook.CustomProgressBar;
import kr.ceo.codilook.R;
import kr.ceo.codilook.model.UserRepository;
import kr.ceo.codilook.model.PreferenceRepository;
import kr.ceo.codilook.ui.main.HomeActivity;
import kr.ceo.codilook.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private static final int REGISTER_REQ_CODE = 100;

    private LoginContract.Presenter presenter;
    private CustomProgressBar CPB;

    private EditText etEmail;
    private EditText etPassword;
    private CheckBox chkAutoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        CPB = new CustomProgressBar(this);
        presenter = new LoginPresenter(this,
                UserRepository.getInstance(), PreferenceRepository.getInstance(getApplication()), CPB);
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

        findViewById(R.id.login_btn_login).setOnClickListener(view ->
                presenter.login(etEmail.getText().toString(),
                        etPassword.getText().toString(), chkAutoLogin.isChecked()));

        findViewById(R.id.login_btn_register).setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivityForResult(intent, REGISTER_REQ_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTER_REQ_CODE && resultCode == Activity.RESULT_OK) {
            onLoginComplete(true);
        }
    }

    @Override
    public void waitForLogin() {
        // TODO: Show Progress View
        if (CPB.getWindow() != null) {
            CPB.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            CPB.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            CPB.setCancelable(false);
        }
        CPB.show();
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
