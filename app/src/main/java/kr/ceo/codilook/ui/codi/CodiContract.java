package kr.ceo.codilook.ui.codi;

import android.graphics.Bitmap;

public interface CodiContract {
    interface View {
        void showImage(Bitmap bitmap, Float score);

        void showDescription(String title, String description);

        void showTitle(String title);

        void setPrevEnable(boolean enable);

        void setNextEnable(boolean enable);

        void setRating(Float rating);
    }

    interface Presenter {
        void prevImage();

        void nextImage();

        void onRatingChanged(float rating);

        void onImageClicked();

        void applyChanges();

    }
}
