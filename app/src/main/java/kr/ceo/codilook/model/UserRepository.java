package kr.ceo.codilook.model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    protected static FirebaseAuth auth;
    protected static User user;
    protected static FirebaseDatabase db;
    private static class SingletonHolder {
        private static final UserRepository instance = new UserRepository();
    }

    static {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser() == null ? null : new User(auth.getCurrentUser());
        db = FirebaseDatabase.getInstance();
    }

    private UserRepository() {
        // For Singleton
    }

    public static UserRepository getInstance() {
        return SingletonHolder.instance;
    }

    public static User getUser() {
        return user;
    }

    public void applyScore() {
        DatabaseReference ref = db.getReference("/users/" + user.uid);
        ref = ref.child("Score");
        for (Map.Entry<Codi, Integer> score : user.score.entrySet()) {
            ref.child(score.getKey().name()).setValue(score.getValue());
        }

    }

    public void getUserInfo(OnSuccessListener<Boolean> listener) {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) return;
        user = new User(currentUser);
        DatabaseReference ref = db.getReference("/users/" + user.uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot children : snapshot.getChildren()) {
                    try {
                        switch (children.getKey()) {
                            case "BloodType":
                                user.bloodType = BloodType.valueOf((String) children.getValue());
                                break;
                            case "Constellation":
                                user.constellation = Constellation.valueOf((String) children.getValue());
                                break;
                            case "MBTI":
                                user.mbti = MBTI.valueOf((String) children.getValue());
                                break;
                            case "Score":
                                for (DataSnapshot score : children.getChildren())
                                    user.addScore(Codi.valueOf(score.getKey()), Integer.parseInt((String) score.getValue()));
                                break;
                        }
                    } catch (Exception e) {
                        if (listener != null) listener.onSuccess(false);
                    }
                }
                if (listener != null) listener.onSuccess(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (listener != null) listener.onSuccess(false);
            }
        });
    }

    public void login(String email, String password, OnSuccessListener<AuthResult> onSuccessListener, OnFailureListener onFailureListener) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
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
}
