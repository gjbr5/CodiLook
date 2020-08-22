package kr.ceo.codilook.ui.appinfo;

public class AppInfoPresenter implements AppInfoContract.Presenter {

    AppInfoContract.View view;

    public AppInfoPresenter(AppInfoContract.View view) {
        this.view = view;
    }

    @Override
    public void updateLatestVersion() {
        view.setLatestVersion("v1.0.0");
    }
}
