package kr.ceo.codilook.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import kr.ceo.codilook.R;
import kr.ceo.codilook.model.fuzzy.Adjectivizable;
import kr.ceo.codilook.model.fuzzy.BloodType;
import kr.ceo.codilook.model.fuzzy.Codi;
import kr.ceo.codilook.model.fuzzy.Constellation;
import kr.ceo.codilook.model.fuzzy.Fuzzy;
import kr.ceo.codilook.model.fuzzy.MBTI;
import kr.ceo.codilook.ui.codi.CodiActivity;

public class HomeActivity extends AppCompatActivity {

    Button btn_recommend;//추천받기 버튼

    ImageView img_menubar;//메뉴바 이미지

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn_recommend = findViewById(R.id.home_btn_recommend);
        btn_recommend.setOnClickListener(view -> {
            // For test
            Fuzzy fuzzy = new Fuzzy(getResources().openRawResource(R.raw.membership));
            Adjectivizable[] adjectivizables = new Adjectivizable[]{
                    BloodType.A, Constellation.물고기자리, MBTI.ISTJ
            };
            startCodiActivity(fuzzy.getCodiList(adjectivizables));
        });
    }

    private void startCodiActivity(ArrayList<Codi> codiList) {
        Intent intent = new Intent(HomeActivity.this, CodiActivity.class);
        intent.putExtra("Codi", codiList);
        startActivity(intent);
    }
}