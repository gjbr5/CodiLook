package kr.ceo.codilook.model;

import androidx.annotation.Nullable;

public class LoginFormState {
    private Integer emailError;
    private Integer passwordError;
    private boolean isDataValid;

    public LoginFormState(boolean isDataValid) {
        this(null, null, isDataValid);
    }

    public LoginFormState(Integer emailError, Integer passwordError) {
        this(emailError, passwordError, false);
    }

    public LoginFormState(Integer emailError, Integer passwordError, boolean isDataValid) {
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.isDataValid = isDataValid;
    }

    @Nullable
    public Integer getEmailError() {
        return emailError;
    }

    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
