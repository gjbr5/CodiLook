package kr.ceo.codilook.ui.codi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.Locale;

import kr.ceo.codilook.BaseNavigationDrawerActivity;
import kr.ceo.codilook.R;
import kr.ceo.codilook.model.StorageRepository;
import kr.ceo.codilook.model.UserRepository;
import kr.ceo.codilook.model.fuzzy.Codi;
import kr.ceo.codilook.ui.main.HomeActivity;

public class CodiActivity extends BaseNavigationDrawerActivity implements CodiContract.View {

    private ImageButton imgBtnPrev;//앞으로 가기
    private ImageView imgCodi;//코디 추천 이미지
    private ImageButton imgBtnNext;//뒤로 가기

    private RatingBar ratingCodiScore;//코디 추천 별점

    private TextView tvCodiName;
    private TextView tvRating;
    private CodiContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codi);

        ArrayList<Codi> codiList = getIntent().getParcelableArrayListExtra("Codi");
        presenter = new CodiPresenter(this,
                StorageRepository.getInstance(getApplication()),
                UserRepository.getInstance(), codiList);

        imgBtnPrev = findViewById(R.id.codi_img_btn_prev);
        setPrevEnable(false);
        imgBtnPrev.setOnClickListener(v -> presenter.prevImage());

        imgCodi = findViewById(R.id.codi_img_codi);
        imgCodi.setOnClickListener(v -> presenter.onImageClicked());

        imgBtnNext = findViewById(R.id.codi_img_btn_next);
        imgBtnNext.setOnClickListener(v -> presenter.nextImage());

        ImageView codi_img_GoHome = findViewById(R.id.ly_codi_img_GoHome);

        codi_img_GoHome.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        });

        tvCodiName = findViewById(R.id.codi_tv_codi_name);
        ratingCodiScore = findViewById(R.id.codi_rating_codi_score);
        ratingCodiScore.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) presenter.onRatingChanged(rating);
        });
        tvRating = findViewById(R.id.codi_tv_rating);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.applyChanges();
    }

    @Override
    public void setRating(Float rating) {
        if (rating == null) {
            tvRating.setText(R.string.score);
            ratingCodiScore.setRating(0);
        } else {
            tvRating.setText(String.format(Locale.getDefault(), "Score: %f", rating));
            ratingCodiScore.setRating(rating);
        }
    }

    @Override
    public void showTitle(String title) {
        tvCodiName.setText(title);
    }

    @Override
    public void showImage(Bitmap bitmap, Float score) {
        imgCodi.setImageBitmap(bitmap);
        setRating(score);
    }

    @Override
    public void showDescription(String title, String description) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_description, null);
        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.CustomAlertDialog).setView(dialogView).create();
        ((TextView) dialogView.findViewById(R.id.description_tv_title)).setText(title);
        ((TextView) dialogView.findViewById(R.id.description_tv_description)).setText(description);
        dialogView.findViewById(R.id.description_btn_ok).setOnClickListener(v -> alertDialog.dismiss());
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