package kr.ceo.codilook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import kr.ceo.codilook.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private static final int REGISTER_REQ_CODE = 100;
    LoginViewModel loginViewModel;
    ImageView imgMenu;
    EditText etEmail;
    EditText etPassword;
    CheckBox cbLogin;
    Button btnLogin;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(LoginViewModel.class);

        if (loginViewModel.getAutoLogin()) {
            startHomeActivity();
        }

        imgMenu = findViewById(R.id.IMG_menu);
        etEmail = findViewById(R.id.ET_email);
        etPassword = findViewById(R.id.ET_password);
        cbLogin = findViewById(R.id.CB_login);
        btnLogin = findViewById(R.id.BT_login);
        btnRegister = findViewById(R.id.BT_register);

        btnLogin.setOnClickListener(view -> login());
        btnRegister.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivityForResult(intent, REGISTER_REQ_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode);
        if (requestCode != REGISTER_REQ_CODE)
            return;
        if (resultCode == Activity.RESULT_OK && data != null) {
            etEmail.setText(data.getStringExtra("email"));
            etPassword.setText(data.getStringExtra("password"));
        }
    }

    private void login() {
        String email = etEmail.getText().toString();
        Integer error = loginViewModel.isEmailValid(email);
        if (error != null) {
            etEmail.setError(getString(error));
            etEmail.requestFocus();
            return;
        }
        String password = etPassword.getText().toString();
        error = loginViewModel.isPasswordValid(password);
        if (error != null) {
            etPassword.setError(getString(error));
            etPassword.requestFocus();
            return;
        }

        loginViewModel.login(etEmail.getText().toString(), etPassword.getText().toString()).observe(this, result -> {
            if (result == null)
                return;
            if (result.isDataValid()) {
                Toast.makeText(LoginActivity.this, "Hello, " + loginViewModel.getUser().getEmail(), Toast.LENGTH_SHORT).show();
                loginViewModel.setAutoLogin(cbLogin.isChecked());
                startHomeActivity();
            } else {
                Integer emailError = result.getEmailError();
                Integer passwordError = result.getPasswordError();
                if (emailError != null)
                    etEmail.setError(getString(emailError));
                if (passwordError != null)
                    etPassword.setError(getString(passwordError));
                if (emailError == null && passwordError == null)
                    Toast.makeText(LoginActivity.this, getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startHomeActivity() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
