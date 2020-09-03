package kr.ceo.codilook.ui.appinfo;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.TextView;

import kr.ceo.codilook.BaseNavigationDrawerActivity;
import kr.ceo.codilook.R;
import kr.ceo.codilook.model.StorageRepository;

public class AppInfoActivity extends BaseNavigationDrawerActivity implements AppInfoContract.View {

    AppInfoContract.Presenter presenter;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        presenter = new AppInfoPresenter(this, StorageRepository.getInstance(getApplication()));
        presenter.updateLatestVersion();
        findViewById(R.id.app_info_btn_update_list)
                .setOnClickListener(v -> presenter.getUpdateList());
        findViewById(R.id.app_info_btn_reference)
                .setOnClickListener(v -> presenter.getReference());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton(R.string.ok, null);
        dialog = builder.create();
    }

    @Override
    public void setLatestVersion(String latestVersion) {
        TextView tvLatestVersion = findViewById(R.id.app_info_tv_latest_version);
        tvLatestVersion.setText(latestVersion);
    }

    @Override
    public void showInformation(String title, String content) {
        dialog.setTitle(title);
        dialog.setMessage(content);
        dialog.show();
    }
}