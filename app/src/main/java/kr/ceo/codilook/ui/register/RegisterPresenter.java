package kr.ceo.codilook.ui.register;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import kr.ceo.codilook.R;
import kr.ceo.codilook.model.LoginFormValidator;
import kr.ceo.codilook.model.LoginRepository;

public class RegisterPresenter implements RegisterContract.Presenter {
    RegisterContract.View view;
    LoginRepository loginRepository;

    public RegisterPresenter(RegisterContract.View view, LoginRepository loginRepository) {
        this.view = view;
        this.loginRepository = loginRepository;
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
    public boolean isPwConfirmValid(String password, String pwConfirm) {
        Integer error = LoginFormValidator.validatePwConfirm(password, pwConfirm);
        view.setPwConfirmError(error);
        return error == null;
    }

    @Override
    public void register(String email, String password, String pwConfirm,
                         String bloodType, String constellation, String mbti) {
        if (!isEmailValid(email)) return;
        if (!isPasswordValid(password)) return;
        if (!isPwConfirmValid(password, pwConfirm)) return;

        if (bloodType.equals("--")) bloodType = "";
        if (mbti.equals("--")) mbti = "";

        if (constellation.equals("--"))
            constellation = "";
        else
            constellation = constellation.substring(0, constellation.indexOf("("));

        final String finalBloodType = bloodType;
        final String finalConstellation = constellation;
        final String finalMbti = mbti;
        loginRepository.register(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String uid = task.getResult().getUser().getUid();
                loginRepository.setUserInfo(uid, finalBloodType, finalConstellation, finalMbti);
                view.onRegisterComplete(true);
            } else {
                Exception ex = task.getException();
                if (ex instanceof FirebaseAuthWeakPasswordException)
                    view.setPasswordError(R.string.invalid_password_format);
                else if (ex instanceof FirebaseAuthInvalidCredentialsException)
                    view.setEmailError(R.string.invalid_email_format);
                else if (ex instanceof FirebaseAuthUserCollisionException)
                    view.setEmailError(R.string.user_already_exists);
                else
                    view.showErrorMessage(R.string.unknown_error);
                view.onRegisterComplete(false);
            }
        });
    }
}
