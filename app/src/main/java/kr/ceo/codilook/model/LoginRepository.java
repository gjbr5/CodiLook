package kr.ceo.codilook.model;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import kr.ceo.codilook.R;

public class LoginRepository {
    protected FirebaseAuth auth = FirebaseAuth.getInstance();

    public MutableLiveData<LoginFormState> login(String email, String password) {
        MutableLiveData<LoginFormState> result = new MutableLiveData<>();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
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

    public MutableLiveData<Boolean> checkUserCollision(String email) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
            result.setValue(!Objects.requireNonNull(Objects.requireNonNull(
                    task.getResult()).getSignInMethods()).isEmpty());
        });
        return result;
    }

    private void addUserInfo(String bloodType, String constellation, String mbti) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
    }

    public FirebaseUser getUser() {
        return auth.getCurrentUser();
    }
}
