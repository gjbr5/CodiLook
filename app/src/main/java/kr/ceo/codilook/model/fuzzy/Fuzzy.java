package kr.ceo.codilook.model.fuzzy;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Fuzzy {

    private static Map<Adjective, int[]> scores;
    private static Codi[] codis = Codi.values();

    static {
        scores = new TreeMap<>();
        scores.put(Adjective.개방적이다, new int[]{5, 11, 15, 0, 6, 0, 4, 0, 2, 5, 1, 8, 8, 10, 3, 6, 3, 0, 4, 6, 6, 8, 4, 1, 2, 12, 2, 1, 10});
        scores.put(Adjective.개성이_강하다, new int[]{2, 9, 10, 8, 10, 1, 6, 2, 0, 14, 5, 9, 10, 4, 5, 7, 5, 0, 9, 8, 9, 13, 1, 6, 6, 14, 0, 6, 12});
        scores.put(Adjective.고급지향적이다, new int[]{4, 1, 1, 4, 0, 10, 0, 14, 1, 0, 1, 0, 0, 3, 1, 0, 1, 13, 1, 4, 1, 1, 0, 0, 10, 1, 8, 7, 0});
        scores.put(Adjective.고정관념에_얽매이지_않는다, new int[]{2, 3, 9, 7, 8, 1, 9, 0, 1, 10, 6, 10, 7, 4, 5, 8, 5, 1, 3, 4, 9, 8, 1, 4, 3, 9, 0, 2, 7});
        scores.put(Adjective.깔끔한_옷을_선호한다, new int[]{12, 2, 0, 7, 0, 6, 8, 13, 15, 2, 5, 2, 2, 1, 6, 4, 8, 17, 4, 4, 1, 1, 13, 6, 4, 2, 5, 11, 2});
        scores.put(Adjective.남성적이다, new int[]{11, 8, 0, 11, 3, 0, 14, 1, 4, 1, 3, 2, 1, 0, 0, 5, 2, 2, 6, 8, 2, 1, 0, 0, 1, 5, 0, 0, 0});
        scores.put(Adjective.단순하다, new int[]{8, 2, 1, 5, 1, 2, 8, 3, 15, 3, 3, 5, 4, 0, 5, 8, 7, 11, 6, 2, 5, 0, 13, 4, 1, 3, 2, 5, 3});
        scores.put(Adjective.로맨틱한_패션을_선호한다, new int[]{0, 0, 7, 2, 1, 14, 0, 3, 1, 0, 1, 0, 0, 6, 0, 0, 1, 0, 0, 0, 2, 1, 0, 3, 3, 0, 11, 0, 2});
        scores.put(Adjective.반항적인_기질, new int[]{2, 8, 7, 0, 5, 0, 2, 0, 0, 5, 1, 6, 2, 2, 0, 6, 0, 0, 2, 7, 2, 2, 0, 0, 2, 12, 0, 0, 2});
        scores.put(Adjective.발랄하다, new int[]{3, 3, 2, 0, 7, 2, 2, 3, 0, 1, 0, 8, 5, 5, 9, 8, 1, 0, 4, 1, 1, 2, 10, 10, 3, 2, 2, 6, 5});
        scores.put(Adjective.밝고_화려한_것을_즐기는, new int[]{0, 2, 6, 0, 2, 9, 1, 1, 0, 4, 0, 2, 9, 7, 2, 0, 1, 1, 0, 2, 1, 6, 4, 3, 12, 3, 9, 1, 12});
        scores.put(Adjective.보수적이다, new int[]{4, 1, 0, 2, 2, 3, 3, 7, 4, 1, 3, 1, 0, 0, 2, 0, 1, 8, 2, 2, 1, 0, 1, 5, 6, 0, 1, 9, 1});
        scores.put(Adjective.사치스럽다, new int[]{1, 2, 0, 1, 0, 7, 0, 4, 0, 0, 0, 0, 0, 4, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 6, 2, 3, 1, 0});
        scores.put(Adjective.스타일이_다양하다, new int[]{1, 5, 6, 9, 8, 1, 3, 0, 1, 9, 6, 6, 8, 0, 4, 0, 3, 0, 1, 4, 5, 7, 3, 3, 5, 3, 3, 1, 9});
        scores.put(Adjective.아름다움을_추구, new int[]{0, 2, 7, 2, 3, 14, 2, 8, 1, 1, 0, 1, 3, 11, 1, 0, 1, 3, 0, 1, 2, 4, 1, 0, 8, 0, 16, 2, 5});
        scores.put(Adjective.여성스럽다, new int[]{1, 2, 9, 0, 1, 13, 0, 12, 1, 2, 0, 1, 0, 12, 3, 0, 4, 4, 0, 0, 1, 5, 2, 3, 7, 0, 17, 2, 3});
        scores.put(Adjective.예술가적_기질이_있다, new int[]{0, 2, 3, 4, 3, 1, 1, 0, 0, 11, 7, 7, 4, 0, 3, 0, 9, 0, 2, 3, 4, 9, 1, 6, 5, 6, 2, 1, 7});
        scores.put(Adjective.우아하다, new int[]{1, 0, 3, 3, 0, 13, 1, 15, 1, 0, 0, 0, 0, 5, 1, 0, 5, 6, 0, 1, 0, 4, 0, 0, 5, 0, 16, 3, 0});
        scores.put(Adjective.유행에_민감하다, new int[]{0, 2, 5, 4, 6, 2, 0, 3, 0, 6, 2, 7, 10, 3, 3, 0, 5, 1, 1, 2, 2, 2, 3, 2, 3, 1, 2, 0, 1});
        scores.put(Adjective.의외로_유행에_민감하다, new int[]{1, 1, 4, 4, 3, 2, 4, 0, 2, 0, 2, 2, 2, 1, 3, 1, 5, 1, 0, 3, 5, 3, 4, 3, 1, 3, 0, 1, 2});
        scores.put(Adjective.자유분방하다, new int[]{1, 7, 7, 4, 6, 2, 3, 1, 2, 5, 4, 5, 6, 3, 7, 8, 5, 0, 7, 4, 9, 6, 8, 7, 3, 8, 0, 1, 7});
        scores.put(Adjective.전통적인_것을_현대적인_느낌으로_리폼, new int[]{0, 0, 0, 0, 10, 0, 1, 2, 0, 0, 6, 0, 0, 1, 7, 0, 2, 0, 3, 7, 0, 3, 0, 7, 3, 0, 0, 2, 5});
        scores.put(Adjective.주위와의_조화를_추구한다, new int[]{2, 0, 0, 3, 0, 4, 2, 2, 2, 2, 1, 2, 3, 0, 2, 1, 6, 7, 2, 2, 2, 3, 0, 4, 3, 1, 1, 3, 2});
        scores.put(Adjective.털털하다, new int[]{6, 9, 1, 4, 8, 0, 8, 0, 7, 1, 8, 8, 4, 0, 4, 11, 3, 1, 9, 7, 8, 4, 10, 8, 0, 10, 0, 1, 3});
        scores.put(Adjective.패션감각이_둔하다, new int[]{3, 0, 2, 1, 0, 0, 2, 1, 3, 1, 1, 0, 2, 0, 2, 5, 0, 4, 5, 1, 2, 1, 1, 1, 2, 1, 1, 2, 2});
        scores.put(Adjective.활동적이다, new int[]{4, 8, 3, 3, 4, 0, 3, 2, 8, 6, 4, 10, 9, 2, 5, 12, 1, 2, 14, 6, 9, 3, 10, 4, 1, 9, 1, 2, 2});
    }

    @SuppressWarnings("ConstantConditions")
    private static void addResult(Map<Codi, Integer> total, Set<Adjective> adjectives) {
        for (Adjective adj : adjectives) {
            int[] score = scores.get(adj);
            for (int i = 0; i < score.length; i++) {
                total.put(codis[i], total.get(codis[i]) + score[i]);
            }
        }
    }

    public static List<Map.Entry<Codi, Integer>> getCodi(BloodType bloodType, Constellation constellation, MBTI mbti) {
        Map<Codi, Integer> total = new TreeMap<>();

        for (Codi codi : codis) {
            total.put(codi, 0);
        }
        if (bloodType != null) addResult(total, bloodType.toAdjective());
        if (constellation != null) addResult(total, constellation.toAdjective());
        if (mbti != null) addResult(total, mbti.toAdjective());
        List<Map.Entry<Codi, Integer>> list = new ArrayList<>(total.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return list;
    }
}
