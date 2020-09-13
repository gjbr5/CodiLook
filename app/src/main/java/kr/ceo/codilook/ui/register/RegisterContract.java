package kr.ceo.codilook.ui.register;

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
