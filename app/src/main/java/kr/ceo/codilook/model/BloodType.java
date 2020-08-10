package kr.ceo.codilook.model;

/**
 * 2020.07.21 스타일 표 기준
 */
public enum BloodType {
    A, B, O, AB;

    public Adjective[] toAdjective() {
        switch (this) {
            case A:
                return new Adjective[]{Adjective.보수적이다, Adjective.깔끔한_옷을_선호한다, Adjective.의외로_유행에_민감하다, Adjective.여성스럽다};
            case B:
                return new Adjective[]{Adjective.개성이_강하다, Adjective.유행에_민감하다, Adjective.개방적이다, Adjective.자유분방하다, Adjective.단순하다};
            case O:
                return new Adjective[]{Adjective.보수적이다, Adjective.의외로_유행에_민감하다, Adjective.털털하다, Adjective.발랄하다};
            case AB:
                return new Adjective[]{Adjective.개성이_강하다, Adjective.유행에_민감하다, Adjective.로맨틱한_패션을_선호한다, Adjective.스타일이_다양하다};
            default:
                return new Adjective[]{};
        }
    }
}
