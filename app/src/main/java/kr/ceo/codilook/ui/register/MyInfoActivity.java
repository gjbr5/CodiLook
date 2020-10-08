package kr.ceo.codilook.ui.register;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import kr.ceo.codilook.BaseNavigationDrawerActivity;
import kr.ceo.codilook.R;
import kr.ceo.codilook.model.UserRepository;
import kr.ceo.codilook.ui.login.LoginActivity;
import kr.ceo.codilook.ui.main.HomeActivity;

public class MyInfoActivity extends BaseNavigationDrawerActivity implements MyInfoContract.View {

    private MyInfoContract.Presenter presenter;

    private TextView tvEmail;

    private EditText etPassword;
    private EditText etNewPassword;
    private EditText etNewPwConfirm;

    private Spinner spBloodType;
    private Spinner spConstellation;
    private Spinner spMbti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        presenter = new MyInfoPresenter(this, UserRepository.getInstance());

        tvEmail = findViewById(R.id.my_info_tv_email);
        etPassword = findViewById(R.id.my_info_et_password);
        etPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) presenter.isPasswordValid(etPassword.getText().toString());
        });

        etNewPassword = findViewById(R.id.my_info_et_new_password);
        etNewPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) presenter.isNewPasswordValid(etNewPassword.getText().toString());
        });

        etNewPwConfirm = findViewById(R.id.my_info_et_new_pw_confirm);
        etNewPwConfirm.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus)
                presenter.isPwConfirmValid(etNewPassword.getText().toString(),
                        etNewPwConfirm.getText().toString());
        });

        spBloodType = findViewById(R.id.my_info_sp_blood_type);
        spConstellation = findViewById(R.id.my_info_sp_constellation);
        spMbti = findViewById(R.id.my_info_sp_mbti);

        //findViewById(R.id.my_info_btn_mbti_link).setOnClickListener(view -> openLinkDialog());

        //수정하기 버튼
        findViewById(R.id.my_info_btn_confirm).setOnClickListener(view -> {
            String password = etPassword.getText().toString();
            String newPassword = etNewPassword.getText().toString();
            String newPwConfirm = etNewPwConfirm.getText().toString();
            String bloodType = spBloodType.getSelectedItem().toString();
            String constellation = spConstellation.getSelectedItem().toString();
            String mbti = spMbti.getSelectedItem().toString();
            presenter.updateInfo(password, newPassword, newPwConfirm, bloodType, constellation, mbti);
        });

        findViewById(R.id.my_info_btn_quit).setOnClickListener(view -> openQuitDialog());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.initView();
        spBloodType.setEnabled(false);
        spConstellation.setEnabled(false);
        spMbti.setEnabled(false);
    }

    private void openLinkDialog() {
        // MBTI 검사 창으로 이동을 알리는 다이얼로그 창 띄우기
        new AlertDialog.Builder(MyInfoActivity.this,
                android.R.style.Theme_DeviceDefault_Light_Dialog)
                .setMessage(R.string.mbti_test)
                .setPositiveButton(R.string.yes, (dialog, which) ->
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.16personalities.com/ko/"))))
                .setNegativeButton(R.string.no, null)
                .show();
    }

    private void openQuitDialog() {//회원 탈퇴시 회원 재인증을 하는 다이얼로그 창 띄우기
        new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog)
                .setMessage(R.string.prompt_quit)
                .setPositiveButton(R.string.yes, (dialog, which) -> presenter.withdraw(etPassword.getText().toString()))
                .setNegativeButton(R.string.no, null)
                .show();
    }

    @Override
    public void setPasswordError(Integer passwordError) {
        etPassword.setError(passwordError == null ? null : getString(passwordError));
    }

    @Override
    public void setNewPasswordError(Integer newPasswordError) {
        etNewPassword.setError(newPasswordError == null ? null : getString(newPasswordError));
    }

    @Override
    public void setNewPwConfirmError(Integer newPwConfirmError) {
        etNewPwConfirm.setError(newPwConfirmError == null ? null : getString(newPwConfirmError));
    }

    @Override
    public void setDefaultData(String email, String bloodType, String constellation, String mbti) {
        tvEmail.setText(email);

        for (int i = 0; i < spBloodType.getCount(); i++) {
            String item = spBloodType.getItemAtPosition(i).toString();
            if (item.startsWith(bloodType)) {
                spBloodType.setSelection(i);
                break;
            }
        }

        for (int i = 0; i < spConstellation.getCount(); i++) {
            String item = spConstellation.getItemAtPosition(i).toString();
            if (item.startsWith(constellation)) {
                spConstellation.setSelection(i);
                break;
            }
        }

        for (int i = 0; i < spMbti.getCount(); i++) {
            if (spMbti.getItemAtPosition(i).equals(mbti)) {
                spMbti.setSelection(i);
                break;
            }
        }
    }

    public void goHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(this, "회원 정보 수정 완료", Toast.LENGTH_SHORT).show();
    }

    public void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Toast.makeText(this, "회원 탈퇴 완료", Toast.LENGTH_SHORT).show();
        finishAffinity();
        startActivity(intent);
    }
}