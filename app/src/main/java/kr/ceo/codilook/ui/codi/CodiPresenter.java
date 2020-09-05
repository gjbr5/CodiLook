package kr.ceo.codilook.ui.codi;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import kr.ceo.codilook.model.StorageRepository;
import kr.ceo.codilook.model.UserRepository;
import kr.ceo.codilook.model.fuzzy.Codi;

public class CodiPresenter implements CodiContract.Presenter {
    private int codiNum = 0;
    private int imgNum = 0;
    private CodiContract.View view;
    private UserRepository userRepository;
    private StorageRepository storageRepository;
    private Map<Integer, ImageList> imgList = new TreeMap<>();

    public CodiPresenter(CodiContract.View view, StorageRepository storageRepository, UserRepository userRepository, ArrayList<Codi> codiList) {
        this.view = view;
        this.storageRepository = storageRepository;
        this.userRepository = userRepository;
        for (int i = 0; i < 3; i++) {
            addList(i, codiList.get(i));
        }
    }

    private void showCodi() {
        try {
            ImageList list = imgList.get(codiNum);
            Integer score = userRepository.getUser().score.get(list.getCodi());
            Bitmap image = list.getImage(imgNum);
            if (score != null)
                view.showImage(image, integerRatingToFloatRating(score));
            else
                view.showImage(image, null);
            view.showTitle(list.getCodi().name());
        } catch (NullPointerException | IndexOutOfBoundsException ignore) {
        }
    }

    @Override
    public void onImageClicked() {
        ImageList list = imgList.get(codiNum);
        String title = list.getCodi().name();
        String description = list.getDescription(imgNum);
        view.showDescription(title, description);
    }

    private void addList(int idx, Codi codi) {
        ImageList imageList = new ImageList(codi);
        imgList.put(idx, imageList); // Init imgList

        storageRepository.getCodiList(codi, listResult -> {
            Random random = new Random();
            List<StorageReference> listReference = listResult.getItems();
            while (listReference.size() > 3)
                listReference.remove(random.nextInt(listReference.size()));
            Log.d("getCodiList", listReference.toString());
            for (StorageReference reference : listReference) {
                int number = Integer.parseInt(reference.getName().replace(codi.name(), "").replace(".png", ""));
                storageRepository.getCodiDescription(codi, number, snapshot -> {
                    String desc = (String) snapshot.child("Description").getValue();
                    String src = (String) snapshot.child("Source").getValue();
                    Log.d("getCodiDescription", desc == null ? "null" : desc);
                    storageRepository.getImage(reference, bitmap -> {
                        Log.d("getImage", bitmap.toString());
                        imageList.add(bitmap, src + "\n" + desc);
                        showCodi();
                    });
                });
            }
        });
    }

    @Override
    public void prevImage() {
        if (imgNum == 0) {
            codiNum -= 1;
            imgNum = 2;
        } else
            imgNum -= 1;

        view.setNextEnable(true);
        if (codiNum == 0 && imgNum == 0) {
            view.setPrevEnable(false);
        }
        showCodi();
    }

    @Override
    public void nextImage() {
        if (imgNum == 2) {
            codiNum += 1;
            imgNum = 0;
        } else
            imgNum += 1;
        view.setPrevEnable(true);
        if (codiNum == 2 && imgNum == 2)
            view.setNextEnable(false);
        showCodi();
    }

    @Override
    public void onRatingChanged(float rating) {
        view.setRatingText("Score: " + rating);
        int integerRating = floatRatingToIntegerRating(rating);
        userRepository.getUser().addScore(imgList.get(codiNum).getCodi(), integerRating);
    }

    @Override
    public void applyChanges() {
        userRepository.applyScore();
    }

    private Float integerRatingToFloatRating(@Nullable Integer rating) {
        if (rating == null || rating < -5 || rating > 5) return null;
        else return 2.5f + (0.5f * rating);
    }

    private Integer floatRatingToIntegerRating(Float rating) {
        if (rating == null || rating <= -0.25f || rating > 5.25f) return null;
        return (int) (rating * 2) - 5;
    }

    private static class ImageList {
        private List<Bitmap> imgList;
        private List<String> descList;
        private Codi codi;

        public ImageList(Codi codi) {
            this.codi = codi;
            imgList = new ArrayList<>();
            descList = new ArrayList<>();
        }

        public Codi getCodi() {
            return codi;
        }

        public Bitmap getImage(int index) {
            return imgList.get(index);
        }

        public String getDescription(int index) {
            return descList.get(index);
        }

        void add(Bitmap image, String description) {
            imgList.add(image);
            descList.add(description);
        }
    }
}
