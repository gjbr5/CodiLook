package kr.ceo.codilook.ui.codi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

import kr.ceo.codilook.BaseNavigationDrawerActivity;
import kr.ceo.codilook.R;
import kr.ceo.codilook.model.StorageRepository;
import kr.ceo.codilook.model.fuzzy.Codi;
import kr.ceo.codilook.ui.main.HomeActivity;

public class CodiActivity extends BaseNavigationDrawerActivity implements CodiContract.View {

    ImageView codi_img_GoHome;

    ImageButton imgBtnPrev;//앞으로 가기
    ImageView imgCodi;//코디 추천 이미지
    ImageButton imgBtnNext;//뒤로 가기

    RatingBar ratingBar;//코디 추천 별점

    CodiContract.Presenter presenter;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codi);

        presenter = new CodiPresenter(this, StorageRepository.getInstance(getApplication()),
                (ArrayList<Codi>) getIntent().getSerializableExtra("Codi"));

        imgBtnPrev = findViewById(R.id.codi_img_btn_prev);
        imgBtnPrev.setEnabled(false);
        imgBtnPrev.setOnClickListener(v -> presenter.prevImage());

        imgCodi = findViewById(R.id.codi_img_codi);

        imgBtnNext = findViewById(R.id.codi_img_btn_next);
        imgBtnNext.setOnClickListener(v -> presenter.nextImage());

        codi_img_GoHome = findViewById(R.id.ly_codi_img_GoHome);

        codi_img_GoHome.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        });

    }

    @Override
    public void showImage(Bitmap bitmap) {
        imgCodi.setImageBitmap(bitmap);
    }

    @Override
    public void showDescription(String title, String description) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_description, null);
        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.CustomAlertDialog).setView(dialogView).create();
        ((TextView) dialogView.findViewById(R.id.description_tv_title)).setText(title);
        ((TextView) dialogView.findViewById(R.id.description_tv_description)).setText(description);
        ((Button) dialogView.findViewById(R.id.description_btn_ok)).setOnClickListener(v -> alertDialog.dismiss());
        alertDialog.show();
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