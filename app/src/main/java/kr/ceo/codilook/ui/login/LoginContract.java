package kr.ceo.codilook.ui.login;

public interface LoginContract {

    interface View {
        void waitForLogin();

        void setEmailError(Integer emailError);

        void setPasswordError(Integer passwordError);

        void onLoginComplete(boolean success);

        void showErrorMessage(Integer message);

        void setProgressMessage(Integer message);
    }

    interface Presenter {
        void login(String email, String password, boolean autoLogin);

        void logout();

        boolean isEmailValid(String email);

        boolean isPasswordValid(String password);

        void autoLogin();
    }
}
