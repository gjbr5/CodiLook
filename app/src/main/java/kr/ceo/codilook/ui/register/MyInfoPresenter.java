package kr.ceo.codilook.ui.register;

import android.util.Log;

import kr.ceo.codilook.R;
import kr.ceo.codilook.model.LoginFormValidateHelper;
import kr.ceo.codilook.model.User;
import kr.ceo.codilook.model.UserRepository;

public class MyInfoPresenter implements MyInfoContract.Presenter {
    MyInfoContract.View view;
    UserRepository userRepository;

    public MyInfoPresenter(MyInfoContract.View view, UserRepository userRepository) {
        this.view = view;
        this.userRepository = userRepository;
    }

    @Override
    public void initView() {
        User user = userRepository.getUser();
        String email = user.email;
        User.UserData data = user.userData;
        String bloodType = data.getBloodType() == null ? "--" : data.getBloodType().name();
        String constellation = data.getConstellation() == null ? "--" : data.getConstellation().name();
        String mbti = data.getMbti() == null ? "--" : data.getMbti().name();
        view.setDefaultData(email, bloodType, constellation, mbti);
    }

    @Override
    public boolean isPasswordValid(String password) {
        Integer error = LoginFormValidateHelper.validatePassword(password);
        view.setPasswordError(error);
        return error == null;
    }

    @Override
    public boolean isNewPasswordValid(String newPassword) {
        Integer error = LoginFormValidateHelper.validatePassword(newPassword);
        view.setNewPasswordError(error);
        return error == null;
    }

    @Override
    public boolean isPwConfirmValid(String password, String pwConfirm) {
        Integer error = LoginFormValidateHelper.validatePwConfirm(password, pwConfirm);
        view.setNewPwConfirmError(error);
        return error == null;
    }

    @Override
    public void updateInfo(String password, String newPassword, String newPwConfirm, String bloodType, String constellation, String mbti) {
        if (!isPasswordValid(password)) return;
        if (!newPassword.isEmpty() && !isNewPasswordValid(newPassword)) return;
        if (!newPwConfirm.isEmpty() && !isPwConfirmValid(newPassword, newPwConfirm)) return;

        if ("--".equals(bloodType)) bloodType = "";
        else bloodType = bloodType.replace("형", "");

        if ("--".equals(mbti)) mbti = "";

        if ("--".equals(constellation)) constellation = "";
        else constellation = constellation.substring(0, constellation.indexOf("("));

        String finalBloodType = bloodType;
        String finalConstellation = constellation;
        String finalMbti = mbti;

        userRepository.reAuth(password, aVoid -> {
            //userRepository.updateInfo(finalBloodType, finalConstellation, finalMbti);
            if (!newPassword.isEmpty())
                userRepository.updatePassword(newPassword);
            view.goHome();
        }, e -> view.setPasswordError(R.string.wrong_password));
    }

    @Override
    public void withdraw(String password) {
        if (!isPasswordValid(password)) return;
        userRepository.withdraw(password,
                aVoid -> view.goLogin(),
                e -> {
                    Log.e("MyInfoPresenter.withdraw", e.toString());
                    view.setPasswordError(R.string.wrong_password);
                });
    }

}
