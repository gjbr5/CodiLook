package kr.ceo.codilook.ui.appinfo;

import kr.ceo.codilook.model.StorageRepository;

public class AppInfoPresenter implements AppInfoContract.Presenter {

    AppInfoContract.View view;
    StorageRepository storageRepository;

    public AppInfoPresenter(AppInfoContract.View view, StorageRepository storageRepository) {
        this.view = view;
        this.storageRepository = storageRepository;
    }

    @Override
    public void updateLatestVersion() {
        view.setLatestVersion("v1.0.0");
    }

    @Override
    public void getReference() {
        storageRepository.getReference(reference -> view.showInformation("Reference", reference));
    }

    @Override
    public void getUpdateList() {
        storageRepository.getUpdateList(list -> view.showInformation("Update List", list));
    }
}
