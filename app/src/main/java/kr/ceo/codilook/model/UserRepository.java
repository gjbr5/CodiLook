package kr.ceo.codilook.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import kr.ceo.codilook.model.fuzzy.Adjectivizable;
import kr.ceo.codilook.model.fuzzy.BloodType;
import kr.ceo.codilook.model.fuzzy.Codi;
import kr.ceo.codilook.model.fuzzy.Constellation;
import kr.ceo.codilook.model.fuzzy.MBTI;

public class UserRepository {
    private static FirebaseAuth auth = FirebaseAuth.getInstance();
    private static FirebaseDatabase db = FirebaseDatabase.getInstance();

    private User user;
    private Map<Codi, Integer> otherScore = new TreeMap<>();

    private UserRepository() {
        user = auth.getCurrentUser() == null ? null : new User(auth.getCurrentUser());
    }

    public static UserRepository getInstance() {
        return SingletonHolder.instance;
    }

    public Map<Codi, Integer> getOtherScore() {
        return otherScore;
    }

    public User getUser() {
        return user;
    }

    public void applyScore(Map<String, Integer> toUpdate) {
        DatabaseReference ref = db.getReference("/users/").child(user.uid).child("Score");
        ref.updateChildren(new TreeMap<>(toUpdate));

        db.getReference("/UserScore").runTransaction(new Transaction.Handler() {

            private void update(MutableData currentData) {
                for (Map.Entry<String, Integer> entry : toUpdate.entrySet()) {
                    String codi = entry.getKey();
                    Integer score = entry.getValue();

                    MutableData countData = currentData.child(codi).child("Count");
                    MutableData totalData = currentData.child(codi).child("Total");

                    Integer count = countData.getValue(Integer.class);
                    Integer total = totalData.getValue(Integer.class);
                    count = count == null ? 0 : count;
                    total = total == null ? 0 : total;

                    if (user.score.containsKey(Codi.valueOf(codi)))
                        total -= Objects.requireNonNull(user.score.get(Codi.valueOf(codi)));
                    else
                        count += 1;
                    total += score;
                    countData.setValue(count);
                    totalData.setValue(total);
                }
            }

            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                for (Adjectivizable adj : user.userData.toArray()) {
                    update(currentData.child(adj.getType() + "/" + adj.name()));
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                toUpdate.forEach((s, integer) -> user.score.put(Codi.valueOf(s), integer));
                toUpdate.clear();
            }
        });

    }

    public void getUserData(@NonNull OnSuccessListener<Boolean> loginSuccessListener, OnFailureListener loginFailureListener) {
        user = new User(Objects.requireNonNull(auth.getCurrentUser()));
        loginSuccessListener.onSuccess(false);
        DatabaseReference ref = db.getReference("/users/" + user.uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user.addUserData(snapshot);
                setOtherScore();
                loginSuccessListener.onSuccess(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loginFailureListener.onFailure(error.toException());
            }
        });
    }

    private void setOtherScore() {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot codiSnapshot : snapshot.getChildren()) {
                    String codiName = codiSnapshot.getKey();
                    Integer count = codiSnapshot.child("Count").getValue(Integer.class);
                    Integer total = codiSnapshot.child("Total").getValue(Integer.class);
                    count = count == null ? 0 : count;
                    total = total == null ? 0 : total;
                    if (count != 0) {
                        float avg = total / (float) count;
                        otherScore.compute(Codi.valueOf(codiName), (codi, prev) -> prev == null ? Math.round(avg) : Math.round((prev + avg) / 2));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {/**/}
        };
        for (Adjectivizable adj : user.userData.toArray()) {
            db.getReference("/UserScore/" + adj.getType() + "/" + adj.name())
                    .addListenerForSingleValueEvent(listener);
        }
    }

    public void updatePassword(String newPassword) {
        FirebaseUser firebaseUser = Objects.requireNonNull(auth.getCurrentUser());
        firebaseUser.updatePassword(newPassword);
    }

    private void withdrawUser(OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        FirebaseUser firebaseUser = Objects.requireNonNull(auth.getCurrentUser());
        firebaseUser.delete()
                .addOnSuccessListener(aVoid -> {
                    user = null;
                    onSuccessListener.onSuccess(aVoid);
                })
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
            ref.child(bloodType.getType()).setValue(bloodType);
            ref.child(constellation.getType()).setValue(constellation);
            ref.child(mbti.getType()).setValue(mbti);
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
