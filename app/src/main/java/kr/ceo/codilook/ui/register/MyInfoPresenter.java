package kr.ceo.codilook.ui.register;

import kr.ceo.codilook.model.LoginFormValidateHelper;
import kr.ceo.codilook.model.UserRepository;

public class MyInfoPresenter implements MyInfoContract.Presenter {
    MyInfoContract.View view;
    UserRepository userRepository;

    public MyInfoPresenter(MyInfoContract.View view, UserRepository loginRepository) {
        this.view = view;
        this.userRepository = loginRepository;
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

}
