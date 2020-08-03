package kr.ceo.codilook;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {

    private LoginModel loginModel = new LoginModel();

    private Integer emailError = null;
    private Integer passwordError = null;
    private Integer loginError = null;

    private MutableLiveData<Boolean> result = new MutableLiveData<>();

    public void login(String email, String password) {
        emailError = loginModel.isEmailValid(email);
        passwordError = loginModel.isPasswordValid(password);
        if (emailError != null || passwordError != null) {
            result.setValue(false);
            return;
        }

        loginModel.login(email, password, task -> {
            if (task.isSuccessful()) {
                result.setValue(true);
                return;
            }
            FirebaseAuthException ex = (FirebaseAuthException) task.getException();
            if (ex == null) {
                result.setValue(false);
                return;
            }
            String errorCode = ex.getErrorCode();
            Log.e("LoginError", errorCode);
            switch (errorCode) {
                case "ERROR_USER_NOT_FOUND":
                    loginError = R.string.user_not_found;
                    break;
                case "ERROR_WRONG_PASSWORD":
                    loginError = R.string.wrong_password;
                    break;
                default:
                    loginError = R.string.unknown_error;
            }
            result.setValue(false);
        });
    }

    public MutableLiveData<Boolean> getResult() {
        return result;
    }

    public Integer getEmailError() {
        Integer msg = emailError;
        emailError = null;
        return msg;
    }

    public Integer getPasswordError() {
        Integer msg = passwordError;
        passwordError = null;
        return msg;
    }

    public Integer getLoginError() {
        Integer msg = loginError;
        loginError = null;
        return msg;
    }

    public FirebaseUser getUser() {
        return loginModel.getUser();
    }
}
