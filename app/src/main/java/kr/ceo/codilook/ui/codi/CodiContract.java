package kr.ceo.codilook.ui.codi;

import android.graphics.Bitmap;

import java.util.ArrayList;

import kr.ceo.codilook.model.fuzzy.Codi;

public interface CodiContract {
    interface View {
        void showImage(Bitmap bitmap);
        void setPrevEnable(boolean enable);
        void setNextEnable(boolean enable);
    }
    interface Presenter {
        void getImage(ArrayList<Codi> codiList);
        void prevImage();
        void nextImage();
    }
}
