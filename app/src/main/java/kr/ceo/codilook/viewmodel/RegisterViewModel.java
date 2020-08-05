package kr.ceo.codilook.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import kr.ceo.codilook.model.LoginFormState;

public class RegisterViewModel extends LoginViewModel {

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<LoginFormState> register(String email, String password,
                                                    String bloodType, String constellation, String mbti) {
        return loginRepository.register(email, password, bloodType, constellation, mbti);
    }

    public MutableLiveData<Boolean> checkUserCollision(String email) {
        return loginRepository.checkUserCollision(email);
    }
}
