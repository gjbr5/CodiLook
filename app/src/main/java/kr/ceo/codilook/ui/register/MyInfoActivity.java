package kr.ceo.codilook.ui.register;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.lang.reflect.Array;

import kr.ceo.codilook.BaseNavigationDrawerActivity;
import kr.ceo.codilook.R;
import kr.ceo.codilook.model.LoginRepository;
import kr.ceo.codilook.model.User;
import kr.ceo.codilook.model.fuzzy.Adjectivizable;
import kr.ceo.codilook.ui.login.LoginActivity;

public class MyInfoActivity extends BaseNavigationDrawerActivity implements MyInfoContract.View {

    MyInfoContract.Presenter presenter;
    User user;

    EditText etEmail;
    EditText etPassword;
    EditText etPwConfirm;

    Spinner spBloodType;
    Spinner spConstellation;
    Spinner spMbti;

    Button btnMbtiLink;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        etEmail = findViewById(R.id.my_info_et_email);
        etPassword = findViewById(R.id.my_info_et_password);
        etPwConfirm = findViewById(R.id.my_info_et_pw_confirm);

        spBloodType = findViewById(R.id.my_info_sp_blood_type);
        spConstellation = findViewById(R.id.my_info_sp_constellation);
        spMbti = findViewById(R.id.my_info_sp_mbti);

        btnMbtiLink = findViewById(R.id.my_info_btn_mbti_link);
        btnConfirm = findViewById(R.id.my_info_btn_confirm);

//        Adjectivizable[] adj = LoginRepository.getUser().getAdjectivizables();
//        for(int i = 0; i < 4; i++){
//            if(adj[0] == spBloodType.getItemAtPosition(i))
//
//        }
    }
}