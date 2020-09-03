package kr.ceo.codilook.ui.register;

public interface MyInfoContract {
    interface View {
        void setPasswordError(Integer passwordError);
        void setPwConfirmError(Integer pwConfirmError);
    }
    interface Presenter {
        boolean isPasswordValid(String password);
        boolean isPwConfirmValid(String password, String pwConfirm);
    }
}
