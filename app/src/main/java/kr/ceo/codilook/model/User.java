package kr.ceo.codilook.model;

public class User {
    public String uid;
    public String email;
    public String bloodType;
    public String constellation;
    public String mbti;

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
}
