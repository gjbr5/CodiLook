package kr.ceo.codilook.ui.register;

import kr.ceo.codilook.model.fuzzy.BloodType;
import kr.ceo.codilook.model.fuzzy.Constellation;
import kr.ceo.codilook.model.fuzzy.MBTI;

public interface RegisterContract {
    interface View {
        void setEmailError(Integer emailError);

        void setPasswordError(Integer passwordError);

        void setPwConfirmError(Integer pwConfirmError);

        void onRegisterComplete(boolean success);
        void showErrorMessage(Integer message);
    }

    interface Presenter {
        boolean isEmailValid(String email);
        boolean isPasswordValid(String password);
        boolean isPwConfirmValid(String password, String pwConfirm);

        void register(String email, String password, String pwConfirm,
                      String bloodType, String constellation, String mbti);
    }
}
