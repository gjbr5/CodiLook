package kr.ceo.codilook.model;

import android.util.Patterns;

import kr.ceo.codilook.R;

public class LoginFormValidator {
    public static Integer validateEmail(String email) {
        Integer error = null;
        if (email == null || !email.contains("@"))
            error = R.string.input_email_address;
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            error = R.string.invalid_email_format;
        return error;
    }

    public static Integer validatePassword(String password) {
        Integer error = null;
        if (password == null || password.trim().length() < 6)
            error = R.string.invalid_password_format;
        return error;
    }

    public static Integer validatePwConfirm(String password, String pwConfirm) {
        return null;
    }

}
