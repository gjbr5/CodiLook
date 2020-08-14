package kr.ceo.codilook.model.fuzzy;

import java.util.HashSet;
import java.util.Set;

/**
 * 2020.08.12 스타일 표 기준
 */
@SuppressWarnings("SpellCheckingInspection")
public enum MBTI {
    ENFJ, ENFP, ENTJ, ENTP, ESFJ, ESFP, ESTJ, ESTP, INFJ, INFP, INTJ, INTP, ISFJ, ISFP, ISTJ, ISTP;

    public Set<Adjective> toAdjective() {
        Set<Adjective> set = new HashSet<>();

        switch (this) {
            case ENFJ:
                set.add(Adjective.로맨틱한_패션을_선호한다);
                set.add(Adjective.유행에_민감하다);
                set.add(Adjective.활동적이다);
                break;
            case ENFP:
                set.add(Adjective.고정관념에_얽매이지_않는다);
                set.add(Adjective.스타일이_다양하다);
                set.add(Adjective.유행에_민감하다);
                break;
            case ENTJ:
                set.add(Adjective.깔끔한_옷을_선호한다);
                break;
            case ENTP:
                set.add(Adjective.고정관념에_얽매이지_않는다);
                set.add(Adjective.남성적이다);
                set.add(Adjective.스타일이_다양하다);
                break;
            case ESFP:
                set.add(Adjective.고정관념에_얽매이지_않는다);
                set.add(Adjective.활동적이다);
                break;
            case ESTJ:
                set.add(Adjective.발랄하다);
                break;
            case ESTP:
                set.add(Adjective.스타일이_다양하다);
                set.add(Adjective.자유분방하다);
                break;
            case INFJ:
            case INTJ:
                set.add(Adjective.개성이_강하다);
                set.add(Adjective.고정관념에_얽매이지_않는다);
                break;
            case INFP:
                set.add(Adjective.고정관념에_얽매이지_않는다);
                set.add(Adjective.로맨틱한_패션을_선호한다);
                break;
            case INTP:
                set.add(Adjective.개방적이다);
                set.add(Adjective.패션감각이_둔하다);
                set.add(Adjective.활동적이다);
                break;
            case ISFJ:
                set.add(Adjective.보수적이다);
                break;
            case ISFP:
                set.add(Adjective.개성이_강하다);
                set.add(Adjective.예술가적_기질이_있다);
                break;
            case ISTJ:
                set.add(Adjective.보수적이다);
                set.add(Adjective.스타일이_다양하다);
                break;
            case ISTP:
                set.add(Adjective.깔끔한_옷을_선호한다);
                set.add(Adjective.단순하다);
                break;
            case ESFJ:
            default:
                break;
        }
        return set;
    }
}
