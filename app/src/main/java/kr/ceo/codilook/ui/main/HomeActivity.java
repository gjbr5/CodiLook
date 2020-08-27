package kr.ceo.codilook.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kr.ceo.codilook.BaseNavigationDrawerActivity;
import kr.ceo.codilook.R;
import kr.ceo.codilook.model.LoginRepository;
import kr.ceo.codilook.model.fuzzy.Codi;
import kr.ceo.codilook.model.fuzzy.Fuzzy;
import kr.ceo.codilook.ui.codi.CodiActivity;

public class HomeActivity extends BaseNavigationDrawerActivity {

    Button btn_recommend;//추천받기 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn_recommend = findViewById(R.id.home_btn_recommend);
        btn_recommend.setOnClickListener(view -> {
            // For test
            Fuzzy fuzzy = new Fuzzy(getResources().openRawResource(R.raw.membership));
            startCodiActivity(fuzzy.getCodiList(LoginRepository.getUser().getAdjectivizables()));
        });
    }

    private void startCodiActivity(ArrayList<Codi> codiList) {
        Intent intent = new Intent(HomeActivity.this, CodiActivity.class);
        intent.putExtra("Codi", codiList);
        startActivity(intent);
        finish();
    }

}