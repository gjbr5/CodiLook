package kr.ceo.codilook.ui.codi;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import kr.ceo.codilook.R;
import kr.ceo.codilook.model.StorageRepository;
import kr.ceo.codilook.model.fuzzy.Codi;

public class CodiActivity extends AppCompatActivity implements CodiContract.View {

    ImageView img_menubar;//메뉴바 이미지
    ImageButton imgBtnPrev;//앞으로 가기
    ImageView imgCodi;//코디 추천 이미지
    ImageButton imgBtnNext;//뒤로 가기

    RatingBar ratingBar;//코디 추천 별점

    CodiContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codi);
        presenter = new CodiPresenter(this, StorageRepository.getInstance(getApplication()));
        imgBtnPrev = findViewById(R.id.codi_img_btn_prev);
        imgBtnPrev.setEnabled(false);
        imgBtnPrev.setOnClickListener(v -> presenter.prevImage());

        imgCodi = findViewById(R.id.codi_img_codi);

        imgBtnNext = findViewById(R.id.codi_img_btn_next);
        imgBtnNext.setOnClickListener(v -> presenter.nextImage());
        @SuppressWarnings("unchecked")
        ArrayList<Codi> codiList = (ArrayList<Codi>) getIntent().getSerializableExtra("Codi");
        presenter.getImage(codiList);
    }

    @Override
    public void showImage(Bitmap bitmap) {
        imgCodi.setImageBitmap(bitmap);
    }

    @Override
    public void setPrevEnable(boolean enable) {
        imgBtnPrev.setEnabled(enable);
    }

    @Override
    public void setNextEnable(boolean enable) {
        imgBtnNext.setEnabled(enable);
    }
}