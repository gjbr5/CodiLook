package kr.ceo.codilook;

import android.util.Patterns;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginModel {
    FirebaseAuth auth = FirebaseAuth.getInstance();

    public Integer isEmailValid(String email) {
        if (email == null || !email.contains("@"))
            return R.string.input_email_address;
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return null;
        else
            return R.string.invalid_email_format;
    }

    public Integer isPasswordValid(String password) {
        if (password == null || password.trim().length() < 6)
            return R.string.invalid_password_format;
        else
            return null;
    }

    public void login(String email, String password, OnCompleteListener<AuthResult> onCompleteListener) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(onCompleteListener);
    }

    public FirebaseUser getUser() {
        return auth.getCurrentUser();
    }
}
