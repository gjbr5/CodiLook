package kr.ceo.codilook;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider.Factory;

public class LoginViewModelFactory implements Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class))
            return (T) new LoginViewModel();
        else
            throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
