package kr.ceo.codilook.model.fuzzy;

import java.util.HashSet;
import java.util.Set;

/**
 * 2020.08.12 스타일 표 기준
 */
@SuppressWarnings("NonAsciiCharacters")
public enum Constellation implements Adjectivizable {
    양자리 {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.개방적이다);
            set.add(Adjective.남성적이다);
            set.add(Adjective.유행에_민감하다);
            set.add(Adjective.활동적이다);
            return set;

        }
    }, 황소자리 {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.고급지향적이다);
            set.add(Adjective.보수적이다);
            set.add(Adjective.아름다움을_추구);
            set.add(Adjective.주위와의_조화를_추구한다);
            return set;
        }
    }, 쌍둥이자리 {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.개성이_강하다);
            set.add(Adjective.스타일이_다양하다);
            set.add(Adjective.자유분방하다);
            return set;
        }
    }, 게자리 {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.여성스럽다);
            set.add(Adjective.유행에_민감하다);
            set.add(Adjective.전통적인_것을_현대적인_느낌으로_리폼);
            return set;
        }
    }, 사자자리 {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.개방적이다);
            set.add(Adjective.개성이_강하다);
            set.add(Adjective.로맨틱한_패션을_선호한다);
            set.add(Adjective.밝고_화려한_것을_즐기는);
            set.add(Adjective.사치스럽다);
            return set;
        }
    }, 처녀자리 {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.개방적이다);
            set.add(Adjective.깔끔한_옷을_선호한다);
            set.add(Adjective.여성스럽다);
            set.add(Adjective.유행에_민감하다);
            set.add(Adjective.자유분방하다);
            return set;
        }
    }, 천칭자리 {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.로맨틱한_패션을_선호한다);
            set.add(Adjective.발랄하다);
            set.add(Adjective.유행에_민감하다);
            set.add(Adjective.자유분방하다);
            set.add(Adjective.주위와의_조화를_추구한다);
            return set;
        }
    }, 전갈자리 {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.깔끔한_옷을_선호한다);
            set.add(Adjective.보수적이다);
            set.add(Adjective.의외로_유행에_민감하다);
            return set;
        }
    }, 궁수자리 {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.개방적이다);
            set.add(Adjective.반항적인_기질);
            set.add(Adjective.자유분방하다);
            set.add(Adjective.활동적이다);
            return set;
        }
    }, 염소자리 {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.깔끔한_옷을_선호한다);
            set.add(Adjective.보수적이다);
            set.add(Adjective.여성스럽다);
            set.add(Adjective.우아하다);
            return set;
        }
    }, 물병자리 {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.개성이_강하다);
            set.add(Adjective.고정관념에_얽매이지_않는다);
            set.add(Adjective.반항적인_기질);
            set.add(Adjective.자유분방하다);
            set.add(Adjective.활동적이다);
            return set;
        }
    }, 물고기자리 {
        @Override
        public Set<Adjective> toAdjective() {
            Set<Adjective> set = new HashSet<>();
            set.add(Adjective.개방적이다);
            set.add(Adjective.보수적이다);
            set.add(Adjective.스타일이_다양하다);
            set.add(Adjective.여성스럽다);
            set.add(Adjective.자유분방하다);
            return set;
        }
    };
    public static final String DB_KEY = "Constellation";
}
