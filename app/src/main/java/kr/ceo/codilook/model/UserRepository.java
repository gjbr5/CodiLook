package kr.ceo.codilook.model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    private void addUserData(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot children : snapshot.getChildren()) {
            switch (Objects.requireNonNull(children.getKey())) {
                case "BloodType":
                    user.userData.bloodType = BloodType.valueOf((String) children.getValue());
                    break;
                case "Constellation":
                    user.userData.constellation = Constellation.valueOf((String) children.getValue());
                    break;
                case "MBTI":
                    user.userData.mbti = MBTI.valueOf((String) children.getValue());
                    break;
                case "Score":
                    for (DataSnapshot score : children.getChildren())
                        user.addScore(Codi.valueOf(score.getKey()), Integer.parseInt((String) score.getValue()));
                    break;
            }
        }
    }

    public void getUserData(OnSuccessListener<Boolean> loginSuccessListener, OnFailureListener loginFailureListener) {
        user = new User(Objects.requireNonNull(auth.getCurrentUser()));
        loginSuccessListener.onSuccess(false);
        DatabaseReference ref = db.getReference("/users/" + user.uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addUserData(snapshot);
                loginSuccessListener.onSuccess(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loginFailureListener.onFailure(error.toException());
            }
        });

    }

    public void login(String email, String password, OnSuccessListener<Boolean> loginSuccessListener, OnFailureListener loginFailureListener) {
        Task<AuthResult> loginTask = auth.signInWithEmailAndPassword(email, password);
        loginTask.addOnFailureListener(loginFailureListener);
        loginTask.addOnSuccessListener(authResult ->
                getUserData(loginSuccessListener, loginFailureListener));
    }

    public void logout() {
        auth.signOut();
        user = null;
    }

    public void register(String email, String password, BloodType bloodType,
                         Constellation constellation, MBTI mbti,
                         OnSuccessListener<User> onSuccessListener,
                         OnFailureListener onFailureListener) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            user = new User(Objects.requireNonNull(auth.getCurrentUser()), bloodType, constellation, mbti);
            DatabaseReference ref = db.getReference("/users/" + user.uid);
            ref.child("BloodType").setValue(bloodType);
            ref.child("Constellation").setValue(constellation);
            ref.child("MBTI").setValue(mbti);
            onSuccessListener.onSuccess(user);
        }).addOnFailureListener(onFailureListener);
    }

    private static class SingletonHolder {
        private static final UserRepository instance = new UserRepository();
    }
}
