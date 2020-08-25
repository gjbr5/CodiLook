package kr.ceo.codilook.model;

import com.google.firebase.auth.FirebaseUser;

import kr.ceo.codilook.model.fuzzy.Adjectivizable;
import kr.ceo.codilook.model.fuzzy.BloodType;
import kr.ceo.codilook.model.fuzzy.Constellation;
import kr.ceo.codilook.model.fuzzy.MBTI;

public class User {
    public BloodType bloodType;
    public Constellation constellation;
    public MBTI mbti;
    private String uid;
    private String email;

    public User(FirebaseUser firebaseUser) {
        this(firebaseUser.getUid(), firebaseUser.getEmail());
    }

    public User(String uid, String email) {
        this(uid, email, null, null, null);
    }

    public User(String uid, String email, BloodType bloodType, Constellation constellation, MBTI mbti) {
        this.uid = uid;
        this.email = email;
        this.bloodType = bloodType;
        this.constellation = constellation;
        this.mbti = mbti;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public Adjectivizable[] getAdjectivizables() {
        return new Adjectivizable[]{bloodType, constellation, mbti};
    }
}
