package kr.ceo.codilook.ui.register;

import android.widget.Toast;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import kr.ceo.codilook.model.LoginFormValidateHelper;
import kr.ceo.codilook.model.User;
import kr.ceo.codilook.model.UserRepository;

public class MyInfoPresenter implements MyInfoContract.Presenter {
    MyInfoContract.View view;
    UserRepository userRepository;

    public MyInfoPresenter(MyInfoContract.View view, UserRepository loginRepository) {
        this.view = view;
        this.userRepository = loginRepository;
    }

    @Override
    public void init() {
        String email = userRepository.getUser().email;
        User.UserData data = userRepository.getUser().userData;
        String blood = data.bloodType + "형";
        String constellation = data.constellation.toString();
        String mbti = data.mbti.toString();
        view.setDefaultData(email, blood, constellation, mbti);
        System.out.println("blood : " + blood + ", cons : " + constellation + ", mbti : " + mbti);
    }

    @Override
    public boolean isPasswordValid(String password) {
        Integer error = LoginFormValidateHelper.validatePassword(password);
        view.setPasswordError(error);
        return error == null;
    }

    @Override
    public boolean isPwConfirmValid(String password, String pwConfirm) {
        Integer error = LoginFormValidateHelper.validatePwConfirm(password, pwConfirm);
        view.setPwConfirmError(error);
        return error == null;
    }

    @Override
    public void characteristic(String uid, String bloodType, String constellation, String mbti){
        if (bloodType.equals("--")) bloodType = "";
        else bloodType = bloodType.replace("형", "");

        if (mbti.equals("--")) mbti = "";

        if (constellation.equals("--")) constellation = "";
        else constellation = constellation.substring(0, constellation.indexOf("("));

        final String finalBloodType = bloodType;
        final String finalConstellation = constellation;
        final String finalMbti = mbti;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/users/" + uid);
        System.out.println("ref : " + ref + "blood : " + finalBloodType);
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("BloodType", finalBloodType);
        childUpdates.put("Constellation", finalConstellation);
        childUpdates.put("MBTI", finalMbti);

        ref.updateChildren(childUpdates);

        view.goHome();
    }

    @Override
    public void pwReauth(String email, String password, String newPw, String newConfPw){

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        System.out.println("firebaseUser : " + firebaseUser);
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        firebaseUser.reauthenticate(credential).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                System.out.println("로그인 성공");
                if (!isPasswordValid(newPw)) return;
                if (!isPwConfirmValid(newPw, newConfPw)) return;
                try{firebaseUser.updatePassword(newPw);
                    System.out.println("비밀번호 변경 성공");
                    view.goHome();
                }catch(Exception e){
                    System.out.println("비밀번호 변경 실패");
                }
            }else if(task.getException() != null){
                System.out.println("로그인 실패");
            }
        });
    }

    @Override
    public void quitReauth(String email, String password){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        firebaseUser.reauthenticate(credential).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                FirebaseAuth.getInstance().getCurrentUser().delete();
                view.goLogin();
            }else if(task.getException() != null){
                System.out.println("인증 실패");
            }
        });
    }

}
