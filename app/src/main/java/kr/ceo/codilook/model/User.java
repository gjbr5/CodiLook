package kr.ceo.codilook.model;

import com.google.firebase.auth.FirebaseUser;

public class User {
    public String uid;
    public String email;
    public String bloodType;
    public String constellation;
    public String mbti;

    public User(FirebaseUser firebaseUser) {
        this(firebaseUser.getUid(), firebaseUser.getEmail());
    }

    public User(String uid, String email) {
        this(uid, email, "", "", "");
    }

    public User(String uid, String email, String bloodType, String constellation, String mbti) {
        this.uid = uid;
        this.email = email;
        this.bloodType = bloodType;
        this.constellation = constellation;
        this.mbti = mbti;
    }

    public String getEmail() {
        return email;
    }
}
