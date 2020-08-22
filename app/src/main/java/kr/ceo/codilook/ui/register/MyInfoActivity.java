package kr.ceo.codilook.ui.register;

import android.os.Bundle;

import kr.ceo.codilook.BaseNavigationDrawerActivity;
import kr.ceo.codilook.R;

public class MyInfoActivity extends BaseNavigationDrawerActivity implements MyInfoContract.View {

    MyInfoContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

    }
}