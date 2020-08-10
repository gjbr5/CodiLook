package kr.ceo.codilook.model;

/**
 * 2020.07.21 스타일 표 기준
 */
@SuppressWarnings("SpellCheckingInspection")
public enum MBTI {
    ENFJ, ENFP, ENTJ, ENTP, ESFJ, ESFP, ESTJ, ESTP, INFJ, INFP, INTJ, INTP, ISFJ, ISFP, ISTJ, ISTP;

    public Adjective[] toAdjective() {
        switch (this) {
            case ENFJ:
                return new Adjective[]{Adjective.로맨틱한_패션을_선호한다, Adjective.활동적이다, Adjective.유행에_민감하다};
            case ENFP:
                return new Adjective[]{Adjective.유행에_민감하다, Adjective.고정관념에_얽매이지_않는다, Adjective.스타일이_다양하다};
            case ENTJ:
                return new Adjective[]{Adjective.깔끔한_옷을_선호한다};
            case ENTP:
                return new Adjective[]{Adjective.고정관념에_얽매이지_않는다, Adjective.남성적이다, Adjective.스타일이_다양하다};
            case ESFP:
                return new Adjective[]{Adjective.활동적이다, Adjective.고정관념에_얽매이지_않는다};
            case ESTJ:
                return new Adjective[]{Adjective.발랄하다};
            case ESTP:
                return new Adjective[]{Adjective.자유분방하다, Adjective.스타일이_다양하다};
            case INFJ:
            case INTJ:
                return new Adjective[]{Adjective.개성이_강하다, Adjective.고정관념에_얽매이지_않는다};
            case INFP:
                return new Adjective[]{Adjective.로맨틱한_패션을_선호한다, Adjective.고정관념에_얽매이지_않는다};
            case INTP:
                return new Adjective[]{Adjective.활동적이다, Adjective.패션감각이_둔하다, Adjective.개방적이다};
            case ISFJ:
                return new Adjective[]{Adjective.보수적이다};
            case ISFP:
                return new Adjective[]{Adjective.개성이_강하다, Adjective.예술가적_기질이_있다};
            case ISTJ:
                return new Adjective[]{Adjective.보수적이다, Adjective.스타일이_다양하다};
            case ISTP:
                return new Adjective[]{Adjective.깔끔한_옷을_선호한다, Adjective.단순하다};
            case ESFJ:
            default:
                return new Adjective[]{};
        }
    }
}
