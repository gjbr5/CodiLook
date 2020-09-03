package kr.ceo.codilook.ui.register;

import kr.ceo.codilook.model.LoginFormValidator;
import kr.ceo.codilook.model.LoginRepository;

public class MyInfoPresenter implements MyInfoContract.Presenter {
    MyInfoContract.View view;
    LoginRepository loginRepository;

    public MyInfoPresenter(MyInfoContract.View view, LoginRepository loginRepository) {
        this.view = view;
        this.loginRepository = loginRepository;
    }

    @Override
    public boolean isPasswordValid(String password) {
        Integer error = LoginFormValidator.validatePassword(password);
        view.setPasswordError(error);
        return error == null;
    }

    @Override
    public boolean isPwConfirmValid(String password, String pwConfirm) {
        Integer error = LoginFormValidator.validatePwConfirm(password, pwConfirm);
        view.setPwConfirmError(error);
        return error == null;
    }

}
