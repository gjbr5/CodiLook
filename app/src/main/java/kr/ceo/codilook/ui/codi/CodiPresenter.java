package kr.ceo.codilook.ui.codi;

import android.util.Log;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import kr.ceo.codilook.model.StorageRepository;
import kr.ceo.codilook.model.fuzzy.Codi;

public class CodiPresenter implements CodiContract.Presenter {
    public CodiContract.View view;
    StorageRepository storageRepository;
    private ArrayList<Codi> codiList = null;
    private int codiNum = 0;
    private int imgNum = 1;

    public CodiPresenter(CodiContract.View view, StorageRepository storageRepository) {
        this.view = view;
        this.storageRepository = storageRepository;
    }

    private void getImageFromRepository() {
        try {
            view.showImage(storageRepository.getImageFromFile(codiList.get(codiNum), imgNum));
        } catch (FileNotFoundException ex) {
            storageRepository.getImageFromFirebase(codiList.get(codiNum), imgNum).addOnSuccessListener(taskSnapshot -> {
                try {
                    view.showImage(storageRepository.getImageFromFile(codiList.get(codiNum), imgNum));
                } catch (FileNotFoundException e) {
                    Log.e("getImageFromRepository", e.toString());
                }
            });
        }
    }

    @Override
    public void getImage(ArrayList<Codi> codiList) {
        this.codiList = codiList;
        imgNum = 1;
        getImageFromRepository();
    }

    @Override
    public void prevImage() {
        if (imgNum == 1) {
            codiNum -= 1;
            imgNum = 5;
        } else
            imgNum -= 1;

        view.setNextEnable(true);
        if (codiNum == 0 && imgNum == 1) {
            view.setPrevEnable(false);
        }
        getImageFromRepository();
    }

    @Override
    public void nextImage() {
        if (imgNum == 5) {
            codiNum += 1;
            imgNum = 1;
        } else
            imgNum += 1;
        view.setPrevEnable(true);
        if (codiNum == 5 && imgNum == 5)
            view.setNextEnable(false);
        getImageFromRepository();
    }
}
