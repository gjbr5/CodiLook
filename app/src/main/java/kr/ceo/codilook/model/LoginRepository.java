package kr.ceo.codilook.model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import kr.ceo.codilook.model.fuzzy.BloodType;
import kr.ceo.codilook.model.fuzzy.Constellation;
import kr.ceo.codilook.model.fuzzy.MBTI;

public class LoginRepository {
    protected static FirebaseAuth auth;
    protected static User user;
    protected static FirebaseDatabase db;
    private static LoginRepository instance;

    static {
        instance = null;
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser() == null ? null : new User(auth.getCurrentUser());
        db = FirebaseDatabase.getInstance();
    }

    private LoginRepository() {
        // For Singleton
    }

    public synchronized static LoginRepository getInstance() {
        if (instance == null)
            instance = new LoginRepository();
        return instance;
    }

    public static User getUser() {
        return user;
    }

    public void getUserInfo() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) return;
        DatabaseReference ref = db.getReference("/users/" + currentUser.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot children : snapshot.getChildren()) {
                    String userInfo = Objects.requireNonNull(children.getKey());
                    switch (userInfo) {
                        case "BloodType":
                            user.bloodType = BloodType.valueOf((String) children.getValue());
                            break;
                        case "Constellation":
                            user.constellation = Constellation.valueOf((String) children.getValue());
                            break;
                        case "MBTI":
                            user.mbti = MBTI.valueOf((String) children.getValue());
                            break;
                        default:
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public Task<AuthResult> login(String email, String password) {
        return auth.signInWithEmailAndPassword(email, password);
    }

    public void logout() {
        auth.signOut();
        user = null;
    }

    public Task<AuthResult> register(String email, String password) {
        return auth.createUserWithEmailAndPassword(email, password);
    }

    public void setUserInfo(String uid, String bloodType, String constellation, String mbti) {
        DatabaseReference ref = db.getReference("/users/" + uid);
        ref.child("BloodType").setValue(bloodType);
        ref.child("Constellation").setValue(constellation);
        ref.child("MBTI").setValue(mbti);
    }

}
