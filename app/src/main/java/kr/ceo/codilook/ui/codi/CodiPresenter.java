package kr.ceo.codilook.ui.codi;

import android.graphics.Bitmap;

import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.TreeMap;

import kr.ceo.codilook.model.StorageRepository;
import kr.ceo.codilook.model.fuzzy.Codi;

public class CodiPresenter implements CodiContract.Presenter {
    private int codiNum = 0;
    private int imgNum = 0;
    private CodiContract.View view;
    private StorageRepository storageRepository;
    private Map<Integer, ImageList> imgList = new TreeMap<>();

    public CodiPresenter(CodiContract.View view, StorageRepository storageRepository, ArrayList<Codi> codiList) {
        this.view = view;
        this.storageRepository = storageRepository;
        for (int i = 0; i < 3; i++) {
            addList(i, codiList.get(i));
        }
    }

    private void addList(int idx, Codi codi) {
        ImageList imageList = new ImageList(codi);
        imgList.put(idx, imageList); // Init imgList

        storageRepository.getList(codi).addOnSuccessListener(listResult -> {
            Random random = new Random();
            List<StorageReference> listReference = listResult.getItems();
            while (listReference.size() > 3)
                listReference.remove(random.nextInt(listReference.size()));
            if (idx == 0) {
                for (int i = 0; i < listReference.size(); i++) {
                    StorageReference ref = listReference.get(i);
                    if (i == 0)
                        storageRepository.getImage(ref, bitmap -> {
                            imageList.add(bitmap);
                            view.showImage(bitmap);
                            view.showTitle(imageList.getCodi().name());
                        });
                    else
                        storageRepository.getImage(ref, imageList::add);
                }
            } else {
                for (StorageReference ref : listReference) {
                    storageRepository.getImage(ref, imageList::add);
                }
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
        view.showImage(imgList.get(codiNum).get(imgNum));
        ImageList list = Objects.requireNonNull(imgList.get(codiNum));
        view.showDescription(list.getCodi().name(), list.get(imgNum).toString());
        view.showTitle(list.getCodi().name());
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
        view.showImage(imgList.get(codiNum).get(imgNum));
        ImageList list = Objects.requireNonNull(imgList.get(codiNum));
        view.showDescription(list.getCodi().name(), list.get(imgNum).toString());
        view.showTitle(list.getCodi().name());
    }

    private static class ImageList {
        private List<Bitmap> imgList;
        private Codi codi;

        public ImageList(Codi codi) {
            this.codi = codi;
            imgList = new ArrayList<>();
        }

        public Codi getCodi() {
            return codi;
        }


        public Bitmap get(int index) {
            return imgList.get(index);
        }

        void add(Bitmap image) {
            imgList.add(image);
        }
    }
}
