package kr.ceo.codilook.ui.login;

import android.app.ProgressDialog;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import kr.ceo.codilook.CustomProgressBar;
import kr.ceo.codilook.R;
import kr.ceo.codilook.model.LoginFormValidator;
import kr.ceo.codilook.model.LoginRepository;
import kr.ceo.codilook.model.PreferenceRepository;

public class LoginPresenter implements LoginContract.Presenter {

    protected LoginContract.View view;
    protected LoginRepository loginRepository;
    protected PreferenceRepository preferenceRepository;
    CustomProgressBar cpb;

    public LoginPresenter(LoginContract.View view, LoginRepository loginRepository, PreferenceRepository preferenceRepository, CustomProgressBar customProgressBar) {
        this.view = view;
        this.loginRepository = loginRepository;
        this.preferenceRepository = preferenceRepository;
        this.cpb = customProgressBar;
    }

    @Override
    public void login(String email, String password, boolean autoLogin) {
        if (!isEmailValid(email)) return;
        if (!isPasswordValid(password)) return;
        view.waitForLogin();
        cpb.changeTitle("로그인 중...");
        loginRepository.login(email, password).addOnSuccessListener(authResult -> {
            cpb.changeTitle("사용자 정보 가져오는 중...");
            preferenceRepository.setAutoLogin(autoLogin);
            loginRepository.getUserInfo(view::onLoginComplete);
            cpb.changeTitle("로그인 완료");
        }).addOnFailureListener(e -> {
            if (e instanceof FirebaseAuthInvalidUserException)
                view.setEmailError(R.string.user_not_found);
            else if (e instanceof FirebaseAuthInvalidCredentialsException)
                view.setPasswordError(R.string.wrong_password);
            else
                view.showErrorMessage(R.string.unknown_error);
            view.onLoginComplete(false);
        });
    }

    @Override
    public boolean isEmailValid(String email) {
        Integer error = LoginFormValidator.validateEmail(email);
        view.setEmailError(error);
        return error == null;
    }

    @Override
    public boolean isPasswordValid(String password) {
        Integer error = LoginFormValidator.validatePassword(password);
        view.setPasswordError(error);
        return error == null;
    }

    @Override
    public void autoLogin() {
        if (preferenceRepository.getAutoLogin() && LoginRepository.getUser() != null)
            loginRepository.getUserInfo(view::onLoginComplete);
    }
}
