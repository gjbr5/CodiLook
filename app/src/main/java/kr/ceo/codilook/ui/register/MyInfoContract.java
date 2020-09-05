package kr.ceo.codilook.ui.register;

public interface MyInfoContract {
    interface View {
        void setPasswordError(Integer passwordError);
        void setPwConfirmError(Integer pwConfirmError);
        void setDefaultData(String email, String bloodType, String constellation, String mbti);
    }
    interface Presenter {
        void init();
        boolean isPasswordValid(String password);
        boolean isPwConfirmValid(String password, String pwConfirm);
    }
}
