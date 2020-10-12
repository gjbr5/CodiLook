package kr.ceo.codilook.model.fuzzy;

import java.util.HashSet;
import java.util.Set;

/**
 * 2020.08.12 스타일 표 기준
 */
@SuppressWarnings("SpellCheckingInspection")
public enum MBTI implements Adjectivizable {
    ENFJ {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.로맨틱한_패션을_선호한다);
            set.add(Adjective.유행에_민감하다);
            set.add(Adjective.활동적이다);
            return set;
        }
    }, ENFP {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.고정관념에_얽매이지_않는다);
            set.add(Adjective.스타일이_다양하다);
            set.add(Adjective.유행에_민감하다);
            return set;
        }
    }, ENTJ {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.깔끔한_옷을_선호한다);
            return set;
        }
    }, ENTP {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.고정관념에_얽매이지_않는다);
            set.add(Adjective.남성적이다);
            set.add(Adjective.스타일이_다양하다);
            return set;
        }
    }, ESFJ {
        @Override
        public Set<Adjective> toAdjective() {
            return new HashSet<>();
        }
    }, ESFP {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.고정관념에_얽매이지_않는다);
            set.add(Adjective.활동적이다);
            return set;
        }
    }, ESTJ {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.발랄하다);
            return set;
        }
    }, ESTP {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.스타일이_다양하다);
            set.add(Adjective.자유분방하다);
            return set;
        }
    }, INFJ {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.개성이_강하다);
            set.add(Adjective.고정관념에_얽매이지_않는다);
            return set;
        }
    }, INFP {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.고정관념에_얽매이지_않는다);
            set.add(Adjective.로맨틱한_패션을_선호한다);
            return set;
        }
    }, INTJ {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.개성이_강하다);
            set.add(Adjective.고정관념에_얽매이지_않는다);
            return set;
        }
    }, INTP {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.개방적이다);
            set.add(Adjective.패션감각이_둔하다);
            set.add(Adjective.활동적이다);
            return set;
        }
    }, ISFJ {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.보수적이다);
            return set;
        }
    }, ISFP {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.개성이_강하다);
            set.add(Adjective.예술가적_기질이_있다);
            return set;
        }
    }, ISTJ {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.보수적이다);
            set.add(Adjective.스타일이_다양하다);
            return set;
        }
    }, ISTP {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.깔끔한_옷을_선호한다);
            set.add(Adjective.단순하다);
            return set;
        }
    };

    @Override
    public String getType() {
        return "MBTI";
    }
}
