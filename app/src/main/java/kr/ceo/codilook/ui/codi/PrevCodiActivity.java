package kr.ceo.codilook.ui.codi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Locale;

import kr.ceo.codilook.BaseNavigationDrawerActivity;
import kr.ceo.codilook.R;
import kr.ceo.codilook.model.StorageRepository;
import kr.ceo.codilook.model.UserRepository;
import kr.ceo.codilook.ui.main.HomeActivity;

public class PrevCodiActivity extends BaseNavigationDrawerActivity implements PrevCodiContract.View {
    private ImageView imgCodi;//코디 추천 이미지

    private RatingBar ratingCodiScore;//코디 추천 별점

    private TextView tvCodiName;
    private TextView tvRating;
    private PrevCodiContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prev_codi);

        presenter = new PrevCodiPresenter(this,
                StorageRepository.getInstance(getApplication()),
                UserRepository.getInstance());

        imgCodi = findViewById(R.id.prev_img_codi);

        //뒤로 가기
        ImageButton imgBtnNext = findViewById(R.id.prev_img_btn_next);
        imgBtnNext.setOnClickListener(v -> presenter.nextImage());

        tvCodiName = findViewById(R.id.prev_tv_codi_name);
        ratingCodiScore = findViewById(R.id.prev_rating_codi_score);
        ratingCodiScore.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) presenter.onRatingChanged(rating);
        });
        tvRating = findViewById(R.id.prev_tv_rating);

        presenter.showCodi();
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
    public void goHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void setImage(Bitmap bitmap, Float score) {
        imgCodi.setImageBitmap(bitmap);
        setRating(score);
    }
}
