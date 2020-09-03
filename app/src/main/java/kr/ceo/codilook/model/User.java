package kr.ceo.codilook.model;

import com.google.firebase.auth.FirebaseUser;

import java.util.Map;
import java.util.TreeMap;

import kr.ceo.codilook.model.fuzzy.Adjectivizable;
import kr.ceo.codilook.model.fuzzy.BloodType;
import kr.ceo.codilook.model.fuzzy.Codi;
import kr.ceo.codilook.model.fuzzy.Constellation;
import kr.ceo.codilook.model.fuzzy.MBTI;

public class User {
    public BloodType bloodType;
    public Constellation constellation;
    public MBTI mbti;
    public String uid;
    public String email;
    public Map<Codi, Integer> score;

    public User(FirebaseUser firebaseUser) {
        this(firebaseUser.getUid(), firebaseUser.getEmail(), null, null, null);
    }

    public User(FirebaseUser firebaseUser, BloodType bloodType, Constellation constellation, MBTI mbti) {
        this(firebaseUser.getUid(), firebaseUser.getEmail(), bloodType, constellation, mbti);
    }

    public User(String uid, String email, BloodType bloodType, Constellation constellation, MBTI mbti) {
        this.uid = uid;
        this.email = email;
        this.bloodType = bloodType;
        this.constellation = constellation;
        this.mbti = mbti;
        this.score = new TreeMap<>();
    }

    public Adjectivizable[] getAdjectivizables() {
        return new Adjectivizable[]{bloodType, constellation, mbti};
    }

    public void addScore(Codi codi, int value) {
        score.put(codi, value);
    }
}
