package kr.ceo.codilook.ui.appinfo;

import android.os.Bundle;
import android.widget.TextView;

import kr.ceo.codilook.BaseNavigationDrawerActivity;
import kr.ceo.codilook.R;

public class AppInfoActivity extends BaseNavigationDrawerActivity implements AppInfoContract.View {

    AppInfoContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        presenter = new AppInfoPresenter(this);
        presenter.updateLatestVersion();
    }

    @Override
    public void setLatestVersion(String latestVersion) {
        TextView tvLatestVersion = findViewById(R.id.app_info_tv_latest_version);
        tvLatestVersion.setText(latestVersion);
    }
}