package kr.ceo.codilook.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import kr.ceo.codilook.BaseNavigationDrawerActivity;
import kr.ceo.codilook.R;
import kr.ceo.codilook.model.User;
import kr.ceo.codilook.model.UserRepository;
import kr.ceo.codilook.model.StorageRepository;
import kr.ceo.codilook.model.fuzzy.Codi;
import kr.ceo.codilook.model.fuzzy.Fuzzy;
import kr.ceo.codilook.ui.codi.CodiActivity;

public class HomeActivity extends BaseNavigationDrawerActivity {

    Button btnRecommend;//추천받기 버튼
    Codi codi;
    ImageView imgCodi;
    public StorageRepository storageRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        storageRepository = StorageRepository.getInstance(getApplication());
        btnRecommend = findViewById(R.id.home_btn_recommend);
        btnRecommend.setOnClickListener(view -> {
            Fuzzy fuzzy = new Fuzzy(getResources().openRawResource(R.raw.membership));
            User user = UserRepository.getUser();
            startCodiActivity(fuzzy.getCodiList(user.getAdjectivizables(), user.score));
        });

        imgCodi = findViewById(R.id.home_img_codi);
        GlideDrawableImageViewTarget gifImg = new GlideDrawableImageViewTarget(imgCodi);
        Glide.with(this).load(R.drawable.random_codi_img).into(gifImg);


        Timer timer = new Timer(true);
        Handler handler = new Handler();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.postDelayed(() -> {
                    //이미지 한장 짜리 코드
                    Random r = new Random();
                    int num = r.nextInt(Codi.values().length);
                    randomImg(num);
                }, 2000);
                handler.post(() -> Glide.with(getApplicationContext()).load(R.drawable.random_codi_img).into(gifImg));

            }
        }, 0, 4000); //시작지연시간 0, 주기 3초

    }

    private void startCodiActivity(ArrayList<Codi> codiList) {
        Intent intent = new Intent(HomeActivity.this, CodiActivity.class);
        intent.putExtra("Codi", codiList);
        startActivity(intent);
        finish();
    }

    private Codi getCodi(int i){
        int j = 0;
        for(Codi type : Codi.values()){
            if(i == j)
                return type;
            j++;
        }
        return codi;
    }

    private void randomImg(int num){
        storageRepository.getList(getCodi(num), listResult -> {
            List<StorageReference> listReference = listResult.getItems();
            Random r = new Random();
            int random = r.nextInt(listReference.size());
            StorageReference ref = listReference.get(random);
            storageRepository.getImage(ref, bitmap -> imgCodi.setImageBitmap(bitmap));
        });
    }

}