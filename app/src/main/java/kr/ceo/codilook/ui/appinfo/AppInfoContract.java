package kr.ceo.codilook.ui.appinfo;

public interface AppInfoContract {
    interface View {
        void setLatestVersion(String latestVersion);
    }

    interface Presenter {
        void updateLatestVersion();
    }
}
