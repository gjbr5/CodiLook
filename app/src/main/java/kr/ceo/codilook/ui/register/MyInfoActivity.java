package kr.ceo.codilook.ui.register;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import kr.ceo.codilook.BaseNavigationDrawerActivity;
import kr.ceo.codilook.R;
import kr.ceo.codilook.model.User;
import kr.ceo.codilook.model.UserRepository;

public class MyInfoActivity extends BaseNavigationDrawerActivity implements MyInfoContract.View {

    MyInfoContract.Presenter presenter;

    TextView tvEmail;

    EditText etPassword;
    EditText etNewPassword;
    EditText etNewPwConfirm;

    Spinner spBloodType;
    Spinner spConstellation;
    Spinner spMbti;

    Button btnMbtiLink;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        presenter = new MyInfoPresenter(this, UserRepository.getInstance());
        initView();
        presenter.init();
    }

    private void initView() {
        tvEmail = findViewById(R.id.my_info_tv_email);
        etPassword = findViewById(R.id.my_info_et_password);
        etNewPassword = findViewById(R.id.my_info_et_new_password);
        etNewPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus)
                presenter.isPasswordValid(((EditText) v).getText().toString());
        });
        etNewPwConfirm = findViewById(R.id.my_info_et_new_pw_confirm);
        etNewPwConfirm.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus)
                presenter.isPwConfirmValid(etPassword.getText().toString(), ((EditText) v).getText().toString());
        });

        spBloodType = findViewById(R.id.my_info_sp_blood_type);
        spConstellation = findViewById(R.id.my_info_sp_constellation);
        spMbti = findViewById(R.id.my_info_sp_mbti);

        btnMbtiLink = findViewById(R.id.my_info_btn_mbti_link);
        btnMbtiLink.setOnClickListener(view -> openLinkDialog());

        btnConfirm = findViewById(R.id.my_info_btn_confirm);
        btnConfirm.setOnClickListener(view -> {
            //수정하기 버튼
        });
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

    @Override
    public void setPasswordError(Integer passwordError) {
        etNewPassword.setError(passwordError == null ? null : getString(passwordError));
    }

    @Override
    public void setPwConfirmError(Integer pwConfirmError) {
        etNewPwConfirm.setError(pwConfirmError == null ? null : getString(pwConfirmError));
    }

    @Override
    public void setDefaultData(String email, String bloodType, String constellation, String mbti) {
        tvEmail.setText(email);
        for (int i = 1; i < 5; i++)
            if (bloodType.equals(spBloodType.getItemAtPosition(i))) spBloodType.setSelection(i);

        for (int i = 1; i < 13; i++) {
            String str = spConstellation.getItemAtPosition(i).toString();
            if (constellation.equals(str.substring(0, str.indexOf("("))))//별자리 뒤의 날짜 파싱
                spConstellation.setSelection(i);
        }
        for (int i = 1; i < 17; i++)
            if (mbti.equals(spMbti.getItemAtPosition(i))) spMbti.setSelection(i);
    }
}