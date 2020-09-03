package kr.ceo.codilook.ui.appinfo;

public interface AppInfoContract {
    interface View {
        void setLatestVersion(String latestVersion);
        void showInformation(String title, String content);
    }

    interface Presenter {
        void updateLatestVersion();
        void getReference();
        void getUpdateList();
    }
}
