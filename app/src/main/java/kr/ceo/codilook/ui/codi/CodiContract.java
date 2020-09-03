package kr.ceo.codilook.ui.codi;

import android.graphics.Bitmap;

import java.util.ArrayList;

import kr.ceo.codilook.model.fuzzy.Codi;

public interface CodiContract {
    interface View {
        void showImage(Bitmap bitmap);
        void showDescription(String title, String description);
        void showTitle(String title);
        void setPrevEnable(boolean enable);
        void setNextEnable(boolean enable);
        void setDefaultRating(float rating);
        void setRatingText(String ratingText);
    }
    interface Presenter {
        void prevImage();
        void nextImage();
        void onRatingChanged(float rating);
        void applyChanges();
    }
}
