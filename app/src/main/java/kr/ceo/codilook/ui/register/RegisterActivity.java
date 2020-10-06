package kr.ceo.codilook.ui.register;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import kr.ceo.codilook.R;
import kr.ceo.codilook.model.UserRepository;
import kr.ceo.codilook.ui.codi.PrevCodiActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {
    private RegisterContract.Presenter presenter;
    private EditText etEmail; // Email 입력창
    private EditText etPassword; // PW 입력창
    private EditText etPwConfirm; // PW 확인 입력창
    private Spinner spBloodType; // 혈액형 스피너
    private Spinner spConstellation; // 별자리 스피너
    private Spinner spMbti; // MBTI 스피너

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        presenter = new RegisterPresenter(this, UserRepository.getInstance());
        etEmail = findViewById(R.id.register_et_email);
        etEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) presenter.isEmailValid(((EditText) v).getText().toString());
        });

        etPassword = findViewById(R.id.register_et_password);
        etPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) presenter.isPasswordValid(((EditText) v).getText().toString());
        });

        etPwConfirm = findViewById(R.id.register_et_pw_confirm);
        etPwConfirm.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus)
                presenter.isPwConfirmValid(etPassword.getText().toString(), ((EditText) v).getText().toString());
        });

        spBloodType = findViewById(R.id.register_sp_blood_type);
        spConstellation = findViewById(R.id.register_sp_constellation);
        spMbti = findViewById(R.id.register_sp_mbti);

        // MBTI검사 링크이동 버튼
        findViewById(R.id.register_btn_mbti_link).setOnClickListener(view -> openLinkDialog());

        // 회원가입 완료 버튼
        findViewById(R.id.register_btn_register).setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String pwConfirm = etPwConfirm.getText().toString();
            String bloodType = spBloodType.getSelectedItem().toString();
            String constellation = spConstellation.getSelectedItem().toString();
            String mbti = spMbti.getSelectedItem().toString();
            // TODO: Show Progress
            presenter.register(email, password, pwConfirm, bloodType, constellation, mbti);
        });
    }

    private void openLinkDialog() {
        // MBTI 검사 창으로 이동을 알리는 다이얼로그 창 띄우기
        new AlertDialog.Builder(RegisterActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog).setMessage(R.string.mbti_test)
                .setPositiveButton(R.string.yes, (dialog, which) ->
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.16personalities.com/ko/"))))
                .setNegativeButton(R.string.no, null)
                .create().show();
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
    public void setPwConfirmError(Integer pwConfirmError) {
        etPwConfirm.setError(pwConfirmError == null ? null : getString(pwConfirmError));
    }

    @Override
    public void onRegisterComplete(boolean success) {
        // TODO: Dismiss Progress
        if (success) {
            setResult(Activity.RESULT_OK);
//            Intent intent = new Intent(this, PrevCodiActivity.class);
//            startActivity(intent);
            finish();
        } else {
            setResult(Activity.RESULT_CANCELED);
        }
    }

    @Override
    public void showErrorMessage(Integer message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}