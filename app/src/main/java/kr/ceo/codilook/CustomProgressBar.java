package kr.ceo.codilook;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.TextView;

public class CustomProgressBar extends Dialog {

    TextView tvTitle;

    public CustomProgressBar(Activity activity) {
        super(activity);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_progress_bar);

        tvTitle = findViewById(R.id.cpb_title);
    }

    public void changeTitle(Integer title) {
        tvTitle.setText(title);
    }
}
