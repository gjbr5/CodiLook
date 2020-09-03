package kr.ceo.codilook.ui.register;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import kr.ceo.codilook.R;
import kr.ceo.codilook.model.LoginFormValidateHelper;
import kr.ceo.codilook.model.UserRepository;
import kr.ceo.codilook.model.fuzzy.BloodType;
import kr.ceo.codilook.model.fuzzy.Constellation;
import kr.ceo.codilook.model.fuzzy.MBTI;

public class RegisterPresenter implements RegisterContract.Presenter {
    RegisterContract.View view;
    UserRepository userRepository;

    public RegisterPresenter(RegisterContract.View view, UserRepository userRepository) {
        this.view = view;
        this.userRepository = userRepository;
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
    public boolean isPwConfirmValid(String password, String pwConfirm) {
        Integer error = LoginFormValidateHelper.validatePwConfirm(password, pwConfirm);
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
        else bloodType = bloodType.replace("형", "");

        if (mbti.equals("--")) mbti = "";

        if (constellation.equals("--")) constellation = "";
        else constellation = constellation.substring(0, constellation.indexOf("("));

        userRepository.register(email, password, BloodType.valueOf(bloodType),
                Constellation.valueOf(constellation), MBTI.valueOf(mbti),
                user -> view.onRegisterComplete(true),
                e -> {
                    if (e instanceof FirebaseAuthWeakPasswordException)
                        view.setPasswordError(R.string.invalid_password_format);
                    else if (e instanceof FirebaseAuthInvalidCredentialsException)
                        view.setEmailError(R.string.invalid_email_format);
                    else if (e instanceof FirebaseAuthUserCollisionException)
                        view.setEmailError(R.string.user_already_exists);
                    else
                        view.showErrorMessage(R.string.unknown_error);
                    view.onRegisterComplete(false);
                }
        );
    }
}
