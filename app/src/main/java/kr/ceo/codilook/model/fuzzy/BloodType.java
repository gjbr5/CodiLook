package kr.ceo.codilook.model.fuzzy;

import java.util.HashSet;
import java.util.Set;

/**
 * 2020.08.12 스타일 표 기준
 */
public enum BloodType {
    A, B, O, AB;

    public Set<Adjective> toAdjective() {
        Set<Adjective> set = new HashSet<>();
        switch (this) {
            case A:
                set.add(Adjective.깔끔한_옷을_선호한다);
                set.add(Adjective.보수적이다);
                set.add(Adjective.여성스럽다);
                set.add(Adjective.의외로_유행에_민감하다);
                break;
            case B:
                set.add(Adjective.개방적이다);
                set.add(Adjective.개성이_강하다);
                set.add(Adjective.단순하다);
                set.add(Adjective.유행에_민감하다);
                set.add(Adjective.자유분방하다);
                break;
            case O:
                set.add(Adjective.발랄하다);
                set.add(Adjective.보수적이다);
                set.add(Adjective.의외로_유행에_민감하다);
                set.add(Adjective.털털하다);
                break;
            case AB:
                set.add(Adjective.개성이_강하다);
                set.add(Adjective.로맨틱한_패션을_선호한다);
                set.add(Adjective.스타일이_다양하다);
                set.add(Adjective.유행에_민감하다);
                break;
            default:
                break;
        }
        return set;
    }
}
