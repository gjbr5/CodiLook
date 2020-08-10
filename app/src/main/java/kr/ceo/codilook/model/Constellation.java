package kr.ceo.codilook.model;

/**
 * 2020.07.21 스타일 표 기준
 */
@SuppressWarnings("NonAsciiCharacters")
public enum Constellation {
    양자리, 황소자리, 쌍둥이자리, 게자리, 사자자리, 처녀자리, 천칭자리, 전갈자리, 궁수자리, 염소자리, 물병자리, 물고기자리;

    public Adjective[] toAdjective() {
        switch (this) {
            case 양자리:
                return new Adjective[]{Adjective.남성적이다, Adjective.유행에_민감하다, Adjective.활동적이다, Adjective.개방적이다};
            case 황소자리:
                return new Adjective[]{Adjective.보수적이다, Adjective.아름다움을_추구, Adjective.주위와의_조화를_추구한다, Adjective.고급지향적이다};
            case 쌍둥이자리:
                return new Adjective[]{Adjective.자유분방하다, Adjective.개성이_강하다, Adjective.스타일이_다양하다};
            case 게자리:
                return new Adjective[]{Adjective.여성스럽다, Adjective.유행에_민감하다, Adjective.전통적인_것을_현대적인_느낌으로_리폼};
            case 사자자리:
                return new Adjective[]{Adjective.개성이_강하다, Adjective.개방적이다, Adjective.로맨틱한_패션을_선호한다, Adjective.밝고_화려한_것을_즐기는, Adjective.사치스럽다};
            case 처녀자리:
                return new Adjective[]{Adjective.자유분방하다, Adjective.여성스럽다, Adjective.유행에_민감하다, Adjective.개방적이다, Adjective.깔끔한_옷을_선호한다};
            case 천칭자리:
                return new Adjective[]{Adjective.자유분방하다, Adjective.유행에_민감하다, Adjective.주위와의_조화를_추구한다, Adjective.로맨틱한_패션을_선호한다, Adjective.발랄하다};
            case 전갈자리:
                return new Adjective[]{Adjective.보수적이다, Adjective.깔끔한_옷을_선호한다, Adjective.의외로_유행에_민감하다};
            case 궁수자리:
                return new Adjective[]{Adjective.자유분방하다, Adjective.활동적이다, Adjective.반항적인_기질, Adjective.개방적이다};
            case 염소자리:
                return new Adjective[]{Adjective.여성스럽다, Adjective.보수적이다, Adjective.깔끔한_옷을_선호한다, Adjective.우아하다};
            case 물병자리:
                return new Adjective[]{Adjective.자유분방하다, Adjective.개성이_강하다, Adjective.활동적이다, Adjective.반항적인_기질, Adjective.고정관념에_얽매이지_않는다};
            case 물고기자리:
                return new Adjective[]{Adjective.자유분방하다, Adjective.여성스럽다, Adjective.스타일이_다양하다, Adjective.개방적이다, Adjective.보수적이다};
            default:
                return new Adjective[]{};
        }
    }
}
