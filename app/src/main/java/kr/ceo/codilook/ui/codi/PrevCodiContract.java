package kr.ceo.codilook.ui.codi;

import android.graphics.Bitmap;

public interface PrevCodiContract {
    interface View {
        void showTitle(String title);

        void goHomeActivity();

        void setRating(Float rating);

        void setImage(Bitmap bitmap, Float score);
    }
    interface Presenter {
        void nextImage();

        void onRatingChanged(float rating);

        void applyChanges();

        void showCodi();
    }
}
