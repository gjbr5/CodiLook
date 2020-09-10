package kr.ceo.codilook.ui.register;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kr.ceo.codilook.BaseNavigationDrawerActivity;
import kr.ceo.codilook.R;
import kr.ceo.codilook.model.User;
import kr.ceo.codilook.model.UserRepository;
import kr.ceo.codilook.ui.login.LoginActivity;
import kr.ceo.codilook.ui.main.HomeActivity;

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
    Button btnQuit;

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
        btnConfirm.setOnClickListener(view -> { modify(); });

        btnQuit = findViewById(R.id.my_info_btn_quit);
        btnQuit.setOnClickListener(view -> { quitDialog(UserRepository.getInstance().getUser().email); });
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

    private void quitDialog(String email){//회원 탈퇴시 회원 재인증을 하는 다이얼로그 창 띄우기
        final EditText et = new EditText(MyInfoActivity.this);
        new AlertDialog.Builder(MyInfoActivity.this,
                android.R.style.Theme_DeviceDefault_Light_Dialog)
                .setMessage("비밀번호를 입력하시오")
                .setView(et)
                .setPositiveButton("탈퇴", (dialog, which) ->{
                    presenter.quitReauth(email, et.getText().toString());
                })
                .setNegativeButton("취소", null)
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

    public void modify(){
        String uid = UserRepository.getInstance().getUser().uid;
        String bloodType = spBloodType.getSelectedItem().toString();
        String constellation = spConstellation.getSelectedItem().toString();
        String mbti = spMbti.getSelectedItem().toString();

        if(etPassword.getText().toString().equals("") &&//비번은 안바꿈 사용자 특성만 바꿈
                etNewPassword.getText().toString().equals("") &&
                etNewPwConfirm.getText().toString().equals("")){
            presenter.characteristic(uid, bloodType, constellation, mbti);
        }
        else{//비번도 바꿈
            presenter.pwReauth(UserRepository.getInstance().getUser().email, etPassword.getText().toString(),
                    etNewPassword.getText().toString(), etNewPwConfirm.getText().toString());
        }
    }

    public void goHome(){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(getApplicationContext(), "회원 정보 수정 완료", Toast.LENGTH_SHORT).show();
    }

    public void goLogin(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(getApplicationContext(), "회원 탈퇴 완료", Toast.LENGTH_SHORT).show();
    }
}