package kr.ceo.codilook.ui.register;

public interface MyInfoContract {
    interface View {
        void setPasswordError(Integer passwordError);
        void setPwConfirmError(Integer pwConfirmError);
        void setDefaultData(String email, String bloodType, String constellation, String mbti);
        void goHome();
        void goLogin();
        void makeToast(String str);
    }
    interface Presenter {
        void init();
        boolean isPasswordValid(String password);
        boolean isPwConfirmValid(String password, String pwConfirm);
        void characteristic(String email, String password, String uid, String bloodType, String constellation, String mbti);
        void pwReauth(String email, String password, String newPw, String newConfPw);
        void quitReauth(String email, String password);
    }
}
