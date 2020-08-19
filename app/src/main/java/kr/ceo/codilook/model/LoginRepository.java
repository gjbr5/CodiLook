package kr.ceo.codilook.model;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import kr.ceo.codilook.R;

public class LoginRepository {
    protected static FirebaseAuth auth;
    protected static User user;

    static {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser() == null ? null : new User(auth.getCurrentUser());
    }

    public static User getUser() {
        return user;
    }

    public MutableLiveData<LoginFormState> login(String email, String password) {
        MutableLiveData<LoginFormState> result = new MutableLiveData<>();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                user = new User(Objects.requireNonNull(auth.getCurrentUser()));
                result.setValue(new LoginFormState(true));
                return;
            }
            Exception ex = task.getException();
            if (ex instanceof FirebaseAuthInvalidUserException)
                result.setValue(new LoginFormState(R.string.user_not_found, null));
            else if (ex instanceof FirebaseAuthInvalidCredentialsException)
                result.setValue(new LoginFormState(null, R.string.wrong_password));
            else
                result.setValue(new LoginFormState(false));
        });
        return result;
    }

    public void logout() {
        auth.signOut();
        user = null;
    }

    public MutableLiveData<LoginFormState> register(String email, String password,
                                                    String bloodType, String constellation, String mbti) {
        MutableLiveData<LoginFormState> result = new MutableLiveData<>();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                addUserInfo(bloodType, constellation, mbti);
                result.setValue(new LoginFormState(true));
                return;
            }
            Exception ex = task.getException();
            if (ex instanceof FirebaseAuthWeakPasswordException)
                result.setValue(new LoginFormState(null, R.string.invalid_password_format));
            else if (ex instanceof FirebaseAuthInvalidCredentialsException)
                result.setValue(new LoginFormState(R.string.invalid_email_format, null));
            else if (ex instanceof FirebaseAuthUserCollisionException)
                result.setValue(new LoginFormState(R.string.user_already_exists, null));
            else
                result.setValue(new LoginFormState(false));
        });
        return result;
    }

    @SuppressWarnings("ConstantConditions")
    public MutableLiveData<Boolean> checkUserCollision(String email) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(task ->
                result.setValue(!task.getResult().getSignInMethods().isEmpty()));
        return result;
    }

    private void addUserInfo(String bloodType, String constellation, String mbti) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
    }

}
