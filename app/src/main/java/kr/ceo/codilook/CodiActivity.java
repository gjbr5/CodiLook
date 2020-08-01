package kr.ceo.codilook;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

public class CodiActivity extends AppCompatActivity {

    ImageView img_menubar;//메뉴바 이미지
    ImageView img_rew;//앞으로 가기
    ImageView img_codi;//코디 추천 이미지
    ImageView img_ff;//뒤로 가기

    RatingBar ratingBar;//코디 추천 별점

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codi);
    }
}