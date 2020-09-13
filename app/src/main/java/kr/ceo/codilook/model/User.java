package kr.ceo.codilook.model;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import kr.ceo.codilook.model.fuzzy.Adjectivizable;
import kr.ceo.codilook.model.fuzzy.BloodType;
import kr.ceo.codilook.model.fuzzy.Codi;
import kr.ceo.codilook.model.fuzzy.Constellation;
import kr.ceo.codilook.model.fuzzy.MBTI;

public class User {
    public User.UserData userData;
    public String uid;
    public String email;
    public Map<Codi, Integer> score;

    public User(@NonNull FirebaseUser firebaseUser) {
        this(firebaseUser.getUid(), firebaseUser.getEmail(), null, null, null);
    }

    public User(@NonNull FirebaseUser firebaseUser, BloodType bloodType, Constellation constellation, MBTI mbti) {
        this(firebaseUser.getUid(), firebaseUser.getEmail(), bloodType, constellation, mbti);
    }

    public User(String uid, String email, BloodType bloodType, Constellation constellation, MBTI mbti) {
        this.uid = uid;
        this.email = email;
        this.userData = new UserData(bloodType, constellation, mbti);
        this.score = new TreeMap<>();
    }

    public void addUserData(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot children : snapshot.getChildren()) {
            String key = Objects.requireNonNull(children.getKey());
            Object value = Objects.requireNonNull(children.getValue());
            switch (key) {
                case "BloodType":
                    userData.setBloodType((String) value);
                    break;
                case "Constellation":
                    userData.setConstellation((String) value);
                    break;
                case "MBTI":
                    userData.setMbti((String) value);
                    break;
                case "Score":
                    addUserData(children);
                    break;
                default:
                    score.put(Codi.valueOf(key), ((Long) value).intValue());
                    break;
            }
        }
    }

    public void addScore(@NonNull Codi codi, int score) {
        this.score.put(codi, score);
    }

    public void setUserData(String bloodType, String constellation, String mbti) {
        BloodType enumBloodType = bloodType.isEmpty() ? null : BloodType.valueOf(bloodType);
        Constellation enumConstellation = constellation.isEmpty() ? null : Constellation.valueOf(constellation);
        MBTI enumMbti = mbti.isEmpty() ? null : MBTI.valueOf(mbti);
        userData.setData(enumBloodType, enumConstellation, enumMbti);
    }

    public static class UserData {
        private BloodType bloodType;
        private Constellation constellation;
        private MBTI mbti;

        public UserData(BloodType bloodType, Constellation constellation, MBTI mbti) {
            setData(bloodType, constellation, mbti);
        }

        public Constellation getConstellation() {
            return constellation;
        }

        private void setConstellation(String constellation) {
            this.constellation = constellation == null || constellation.isEmpty() ? null : Constellation.valueOf(constellation);
        }

        public MBTI getMbti() {
            return mbti;
        }

        private void setMbti(String mbti) {
            this.mbti = mbti == null || mbti.isEmpty() ? null : MBTI.valueOf(mbti);
        }

        private void setData(BloodType bloodType, Constellation constellation, MBTI mbti) {
            this.bloodType = bloodType;
            this.constellation = constellation;
            this.mbti = mbti;
        }

        public BloodType getBloodType() {
            return bloodType;
        }

        private void setBloodType(String bloodType) {
            this.bloodType = bloodType == null || bloodType.isEmpty() ? null : BloodType.valueOf(bloodType);
        }

        public Adjectivizable[] toArray() {
            ArrayList<Adjectivizable> list = new ArrayList<>();
            if (bloodType != null) list.add(bloodType);
            if (constellation != null) list.add(constellation);
            if (mbti != null) list.add(mbti);
            return list.toArray(new Adjectivizable[0]);
        }
    }
}
