package kr.ceo.codilook.ui.login;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import kr.ceo.codilook.R;
import kr.ceo.codilook.model.LoginFormValidateHelper;
import kr.ceo.codilook.model.PreferenceRepository;
import kr.ceo.codilook.model.StorageRepository;
import kr.ceo.codilook.model.UserRepository;

public class LoginPresenter implements LoginContract.Presenter {

    protected LoginContract.View view;
    protected UserRepository userRepository;
    protected PreferenceRepository preferenceRepository;
    private StorageRepository storageRepository;

    public LoginPresenter(LoginContract.View view, UserRepository userRepository, PreferenceRepository preferenceRepository) {
        this.view = view;
        this.userRepository = userRepository;
        this.preferenceRepository = preferenceRepository;
    }

    @Override
    public void login(String email, String password, boolean autoLogin) {
        if (!isEmailValid(email)) return;
        if (!isPasswordValid(password)) return;
        view.waitForLogin();
        userRepository.login(email, password, isGetUserData -> {
            if (isGetUserData)
                view.onLoginComplete(true);
            else {
                view.setProgressMessage(R.string.progress_getting_info);
                preferenceRepository.setAutoLogin(autoLogin);
            }
        }, e -> {
            if (e instanceof FirebaseAuthInvalidUserException)
                view.setEmailError(R.string.user_not_found);
            else if (e instanceof FirebaseAuthInvalidCredentialsException)
                view.setPasswordError(R.string.wrong_password);
            else {
                view.showErrorMessage(R.string.unknown_error);
                Log.e("LoginPresenter.login", e.toString());
            }
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
    public void logout() {
        userRepository.logout();
    }

    @Override
    public void autoLogin() {
        if (preferenceRepository.getAutoLogin() && userRepository.getUser() != null) {
            view.waitForLogin();
            view.setProgressMessage(R.string.progress_getting_info);
            userRepository.getUserData(aBoolean -> view.onLoginComplete(aBoolean),
                    e -> Log.w("LoginPresenter.autoLogin", e.toString()));
        }
    }
}
