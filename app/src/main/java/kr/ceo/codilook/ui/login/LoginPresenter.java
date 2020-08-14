package kr.ceo.codilook.ui.login;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import kr.ceo.codilook.R;
import kr.ceo.codilook.model.LoginFormValidator;
import kr.ceo.codilook.model.LoginRepository;
import kr.ceo.codilook.model.PreferenceRepository;

public class LoginPresenter implements LoginContract.Presenter {

    protected LoginContract.View view;
    protected LoginRepository loginRepository;
    protected PreferenceRepository preferenceRepository;

    public LoginPresenter(LoginContract.View view, LoginRepository loginRepository, PreferenceRepository preferenceRepository) {
        this.view = view;
        this.loginRepository = loginRepository;
        this.preferenceRepository = preferenceRepository;
    }

    @Override
    public void login(String email, String password, boolean autoLogin) {
        if (!isEmailValid(email)) return;
        if (!isPasswordValid(password)) return;
        view.waitForLogin();
        loginRepository.login(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                loginRepository.getUserInfo();
                preferenceRepository.setAutoLogin(autoLogin);
                view.onLoginComplete(true);
            } else {
                Exception ex = task.getException();
                if (ex instanceof FirebaseAuthInvalidUserException)
                    view.setEmailError(R.string.user_not_found);
                else if (ex instanceof FirebaseAuthInvalidCredentialsException)
                    view.setPasswordError(R.string.wrong_password);
                else
                    view.showErrorMessage(R.string.unknown_error);
                view.onLoginComplete(false);
            }
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
            view.onLoginComplete(true);
    }
}
