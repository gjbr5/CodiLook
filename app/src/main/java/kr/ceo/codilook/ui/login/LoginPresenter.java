package kr.ceo.codilook.ui.login;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import kr.ceo.codilook.CustomProgressBar;
import kr.ceo.codilook.R;
import kr.ceo.codilook.model.LoginFormValidateHelper;
import kr.ceo.codilook.model.UserRepository;
import kr.ceo.codilook.model.PreferenceRepository;

public class LoginPresenter implements LoginContract.Presenter {

    protected LoginContract.View view;
    protected UserRepository userRepository;
    protected PreferenceRepository preferenceRepository;
    CustomProgressBar cpb;

    public LoginPresenter(LoginContract.View view, UserRepository userRepository, PreferenceRepository preferenceRepository, CustomProgressBar customProgressBar) {
        this.view = view;
        this.userRepository = userRepository;
        this.preferenceRepository = preferenceRepository;
        this.cpb = customProgressBar;
    }

    @Override
    public void login(String email, String password, boolean autoLogin) {
        if (!isEmailValid(email)) return;
        if (!isPasswordValid(password)) return;
        view.waitForLogin();
        cpb.changeTitle("로그인 중...");
        userRepository.login(email, password, authResult -> {
            cpb.changeTitle("사용자 정보 가져오는 중...");
            preferenceRepository.setAutoLogin(autoLogin);
            userRepository.getUserInfo(view::onLoginComplete);
            cpb.changeTitle("로그인 완료");
        }, e -> {
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
        Integer error = LoginFormValidateHelper.validateEmail(email);
        view.setEmailError(error);
        return error == null;
    }

    @Override
    public boolean isPasswordValid(String password) {
        Integer error = LoginFormValidateHelper.validatePassword(password);
        view.setPasswordError(error);
        return error == null;
    }

    @Override
    public void autoLogin() {
        if (preferenceRepository.getAutoLogin() && UserRepository.getUser() != null)
            userRepository.getUserInfo(view::onLoginComplete);
    }
}
