package kr.ceo.codilook.ui.main;

import android.graphics.Bitmap;

import java.io.InputStream;
import java.util.ArrayList;

import kr.ceo.codilook.model.fuzzy.Codi;

public interface HomeContract {
    interface Presenter {
        void recommend(InputStream inputStream);

        void setTimer();

        void deleteTimer();
    }

    interface View {
        void setImage(Bitmap bitmap);

        void setRandomImage();

        void startCodiActivity(ArrayList<Codi> codiList);

        void startMyInfoActivity();
    }
}
