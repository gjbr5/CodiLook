package kr.ceo.codilook.model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import kr.ceo.codilook.model.fuzzy.BloodType;
import kr.ceo.codilook.model.fuzzy.Codi;
import kr.ceo.codilook.model.fuzzy.Constellation;
import kr.ceo.codilook.model.fuzzy.MBTI;

public class UserRepository {
    private static FirebaseAuth auth = FirebaseAuth.getInstance();
    private static FirebaseDatabase db = FirebaseDatabase.getInstance();

    private User user;

    private UserRepository() {
        user = auth.getCurrentUser() == null ? null : new User(auth.getCurrentUser());
    }

    public static UserRepository getInstance() {
        return SingletonHolder.instance;
    }

    public User getUser() {
        return user;
    }

    public void applyScore() {
        DatabaseReference ref = db.getReference("/users/" + user.uid);
        ref = ref.child("Score");
        for (Map.Entry<Codi, Integer> score : user.score.entrySet()) {
            ref.child(score.getKey().name()).setValue(score.getValue());
        }
    }

    public void getUserData(@NonNull OnSuccessListener<Boolean> loginSuccessListener, OnFailureListener loginFailureListener) {
        user = new User(Objects.requireNonNull(auth.getCurrentUser()));
        loginSuccessListener.onSuccess(false);
        DatabaseReference ref = db.getReference("/users/" + user.uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user.addUserData(snapshot);
                loginSuccessListener.onSuccess(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loginFailureListener.onFailure(error.toException());
            }
        });
    }

    public void updatePassword(String newPassword) {
        FirebaseUser firebaseUser = Objects.requireNonNull(auth.getCurrentUser());
        firebaseUser.updatePassword(newPassword);
    }

    public void updateInfo(String bloodType, String constellation, String mbti) {
        user.setUserData(bloodType, constellation, mbti);
        DatabaseReference ref = getUserInfoReference();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(BloodType.DB_KEY, bloodType);
        childUpdates.put(Constellation.DB_KEY, constellation);
        childUpdates.put(MBTI.DB_KEY, mbti);
        ref.updateChildren(childUpdates);
    }

    private void withdrawUser(OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        FirebaseUser firebaseUser = Objects.requireNonNull(auth.getCurrentUser());
        firebaseUser.delete()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void withdraw(String password, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        OnSuccessListener<Void> onReauthSuccessListener = aVoid -> getUserInfoReference().removeValue()
                .addOnFailureListener(onFailureListener)
                .addOnSuccessListener(aVoid1 -> withdrawUser(onSuccessListener, onFailureListener));
        reAuth(password, onReauthSuccessListener, onFailureListener);
    }

    public void reAuth(String password, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        FirebaseUser firebaseUser = Objects.requireNonNull(auth.getCurrentUser());
        AuthCredential credential = EmailAuthProvider.getCredential(user.email, password);
        firebaseUser.reauthenticate(credential)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void login(String email, String password, OnSuccessListener<Boolean> loginSuccessListener, OnFailureListener loginFailureListener) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnFailureListener(loginFailureListener)
                .addOnSuccessListener(authResult ->
                        getUserData(loginSuccessListener, loginFailureListener));
    }

    public void logout() {
        auth.signOut();
        user = null;
    }

    public void register(String email, String password,
                         BloodType bloodType, Constellation constellation, MBTI mbti,
                         OnSuccessListener<User> onSuccessListener,
                         OnFailureListener onFailureListener) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            user = new User(Objects.requireNonNull(auth.getCurrentUser()), bloodType, constellation, mbti);
            DatabaseReference ref = getUserInfoReference();
            ref.child(BloodType.DB_KEY).setValue(bloodType);
            ref.child(Constellation.DB_KEY).setValue(constellation);
            ref.child(MBTI.DB_KEY).setValue(mbti);
            onSuccessListener.onSuccess(user);
        }).addOnFailureListener(onFailureListener);
    }

    private DatabaseReference getUserInfoReference() {
        return db.getReference("/users/" + user.uid);
    }

    private static class SingletonHolder {
        private static final UserRepository instance = new UserRepository();
    }
}
