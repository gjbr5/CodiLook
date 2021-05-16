package kr.ceo.codilook.ui.main;

import android.os.Handler;

import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import kr.ceo.codilook.model.StorageRepository;
import kr.ceo.codilook.model.User;
import kr.ceo.codilook.model.UserRepository;
import kr.ceo.codilook.model.fuzzy.Codi;
import kr.ceo.codilook.model.fuzzy.Fuzzy;

public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View view;
    private final UserRepository userRepository;
    private final StorageRepository storageRepository;

    private Timer timer;

    public HomePresenter(HomeContract.View view, UserRepository userRepository, StorageRepository storageRepository) {
        this.view = view;
        this.userRepository = userRepository;
        this.storageRepository = storageRepository;
    }

    @Override
    public void recommend(InputStream inputStream) {
        Fuzzy fuzzy = new Fuzzy(inputStream);
        User user = userRepository.getUser();
        if (user.userData.toArray().length == 0) {
            view.startMyInfoActivity();
            return;
        }
        view.startCodiActivity(fuzzy.getCodiList(user.userData, user.score, userRepository.getOtherScore()));
    }

    @Override
    public void setTimer() {
        timer = new Timer();
        Handler handler = new Handler();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.postDelayed(() -> {
                    // 이미지 한장 짜리 코드
                    Random r = new Random();
                    int num = r.nextInt(Codi.values().length);
                    Codi codi = Codi.values()[num];
                    storageRepository.getCodiList(codi, listResult -> {
                        List<StorageReference> listReference = listResult.getItems();
                        int random = new Random().nextInt(listReference.size());
                        StorageReference ref = listReference.get(random);
                        storageRepository.getImage(ref, view::setImage);
                    });
                }, 2000);
                handler.post(view::setRandomImage);
            }
        }, 0, 4000); //시작지연시간 0, 주기 3초
    }

    @Override
    public void deleteTimer() {
        timer.cancel();
        timer.purge();
    }

}
