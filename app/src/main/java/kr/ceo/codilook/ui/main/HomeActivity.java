package kr.ceo.codilook.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.ArrayList;

import kr.ceo.codilook.BaseNavigationDrawerActivity;
import kr.ceo.codilook.R;
import kr.ceo.codilook.model.StorageRepository;
import kr.ceo.codilook.model.UserRepository;
import kr.ceo.codilook.model.fuzzy.Codi;
import kr.ceo.codilook.ui.codi.CodiActivity;
import kr.ceo.codilook.ui.register.MyInfoActivity;

public class HomeActivity extends BaseNavigationDrawerActivity implements HomeContract.View {

    private ImageView imgCodi;
    private GlideDrawableImageViewTarget gifImg;
    private HomeContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        presenter = new HomePresenter(this,
                UserRepository.getInstance(),
                StorageRepository.getInstance(getApplication()));

        // 추천받기 버튼
        findViewById(R.id.home_btn_recommend).setOnClickListener(view ->
                presenter.recommend(getResources().openRawResource(R.raw.membership)));

        imgCodi = findViewById(R.id.home_img_codi);
        gifImg = new GlideDrawableImageViewTarget(imgCodi);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setTimer();
    }

    @Override
    public void startCodiActivity(ArrayList<Codi> codiList) {
        Intent intent = new Intent(this, CodiActivity.class);
        intent.putParcelableArrayListExtra("Codi", codiList);
        startActivity(intent);
        finish();
    }

    @Override
    public void startMyInfoActivity() {
        Toast.makeText(this, R.string.set_user_info, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MyInfoActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.deleteTimer();
    }


    @Override
    public void setImage(Bitmap bitmap) {
        imgCodi.setImageBitmap(bitmap);
    }

    @Override
    public void setRandomImage() {
        Glide.with(this).load(R.drawable.random_codi_img).into(gifImg);
    }
}