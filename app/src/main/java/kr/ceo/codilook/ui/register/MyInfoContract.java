package kr.ceo.codilook.ui.register;

public interface MyInfoContract {
    interface View {
        void setPasswordError(Integer passwordError);

        void setNewPasswordError(Integer newPasswordError);

        void setNewPwConfirmError(Integer newPwConfirmError);

        void setDefaultData(String email, String bloodType, String constellation, String mbti);

        void goLogin();

        void goHome();
    }

    interface Presenter {
        void initView();

        boolean isPasswordValid(String password);

        boolean isNewPasswordValid(String newPassword);

        boolean isPwConfirmValid(String password, String pwConfirm);

        void updateInfo(String password, String newPassword, String newPwConfirm,
                        String bloodType, String constellation, String mbti);

        void withdraw(String password);
    }
}
