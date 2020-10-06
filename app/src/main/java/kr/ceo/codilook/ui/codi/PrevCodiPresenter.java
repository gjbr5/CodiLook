package kr.ceo.codilook.ui.codi;

import com.google.firebase.storage.StorageReference;
import java.util.List;
import java.util.Random;
import kr.ceo.codilook.model.StorageRepository;
import kr.ceo.codilook.model.UserRepository;
import kr.ceo.codilook.model.fuzzy.Codi;

public class PrevCodiPresenter implements PrevCodiContract.Presenter {
    private int codiNum = 0;
    private int imgNum = 0;
    private PrevCodiContract.View view;
    private UserRepository userRepository;
    private StorageRepository storageRepository;
    public Codi codi;

    public PrevCodiPresenter(PrevCodiContract.View view, StorageRepository storageRepository, UserRepository userRepository) {
        this.view = view;
        this.storageRepository = storageRepository;
        this.userRepository = userRepository;
    }

    public void showCodi() {
        Random r = new Random();
        codiNum = r.nextInt(Codi.values().length);
        codi = Codi.values()[codiNum];
        view.showTitle(codi.name());
        storageRepository.getCodiList(codi, listResult -> {
            List<StorageReference> listReference = listResult.getItems();
            int random = new Random().nextInt(listReference.size());
            StorageReference ref = listReference.get(random);
            storageRepository.getImage(ref, bitmap -> view.setImage(bitmap, null));
        });
    }

    @Override
    public void nextImage() {
        if (imgNum < 2) {
            imgNum += 1;
            showCodi();
        }
        else if (imgNum == 2) {
            userRepository.applyScore();
            view.goHomeActivity();
        }
    }

    @Override
    public void onRatingChanged(float rating) {
        view.setRating(rating);
        int integerRating = floatRatingToIntegerRating(rating);
        userRepository.getUser().addScore(codi, integerRating);
    }

    @Override
    public void applyChanges() {
        userRepository.applyScore();
    }

    private Integer floatRatingToIntegerRating(Float rating) {
        if (rating == null || rating <= -0.25f || rating > 5.25f) return null;
        return (int) (rating * 2) - 5;
    }
}
