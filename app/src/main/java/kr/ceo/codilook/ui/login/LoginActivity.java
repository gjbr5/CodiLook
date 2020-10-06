package kr.ceo.codilook.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.Random;

import kr.ceo.codilook.CustomProgressBar;
import kr.ceo.codilook.R;
import kr.ceo.codilook.model.PreferenceRepository;
import kr.ceo.codilook.model.StorageRepository;
import kr.ceo.codilook.model.UserRepository;
import kr.ceo.codilook.model.fuzzy.Codi;
import kr.ceo.codilook.ui.codi.PrevCodiActivity;
import kr.ceo.codilook.ui.main.HomeActivity;
import kr.ceo.codilook.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private static final int REGISTER_REQ_CODE = 100;

    private LoginContract.Presenter presenter;
    private CustomProgressBar progressBar;

    private EditText etEmail;
    private EditText etPassword;
    private CheckBox chkAutoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter(this,
                UserRepository.getInstance(),
                PreferenceRepository.getInstance(getApplication()));
        initView();
        if (getIntent().getBooleanExtra("logout", false))
            presenter.logout();
        else
            presenter.autoLogin();
    }


    private void initView() {
        progressBar = new CustomProgressBar(this);

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
            //onLoginComplete(true);
            Intent intent = new Intent(this, PrevCodiActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void waitForLogin() {
        Window window = progressBar.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            progressBar.setCancelable(false);
        }
        setProgressMessage(R.string.progress_login);
        progressBar.show();
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
        if (progressBar.isShowing())
            progressBar.dismiss();
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

    @Override
    public void setProgressMessage(Integer message) {
        progressBar.changeTitle(message);
    }
}
