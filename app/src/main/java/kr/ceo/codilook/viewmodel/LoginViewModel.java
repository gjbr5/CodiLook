package kr.ceo.codilook.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import kr.ceo.codilook.R;
import kr.ceo.codilook.model.LoginFormState;
import kr.ceo.codilook.model.LoginRepository;
import kr.ceo.codilook.model.User;

public class LoginViewModel extends AndroidViewModel {

    protected LoginRepository loginRepository = new LoginRepository();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public Integer isEmailValid(String email) {
        if (email == null || !email.contains("@"))
            return R.string.input_email_address;
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return null;
        else
            return R.string.invalid_email_format;
    }

    public Integer isPasswordValid(String password) {
        if (password == null || password.trim().length() < 6)
            return R.string.invalid_password_format;
        else
            return null;
    }

    public MutableLiveData<LoginFormState> login(String email, String password) {
        if (getUser() != null)
            logout();
        return loginRepository.login(email, password);
    }

    public void logout() {
        loginRepository.logout();
    }

    public User getUser() {
        return LoginRepository.getUser();
    }

    public boolean getAutoLogin() {
        SharedPreferences sp = getApplication().getSharedPreferences("AutoLogin", Context.MODE_PRIVATE);
        return sp.getBoolean("autoLogin", false) && LoginRepository.getUser() != null;
    }

    public void setAutoLogin(boolean autoLogin) {
        SharedPreferences sp = getApplication().getSharedPreferences("AutoLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("autoLogin", autoLogin);
        editor.apply();
    }
}
