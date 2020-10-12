package kr.ceo.codilook.model.fuzzy;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

import kr.ceo.codilook.model.User;

public class Fuzzy {

    private Map<Adjective, Map<Codi, Integer>> memberships;

    public Fuzzy(InputStream inputStream) {
        memberships = new TreeMap<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("euc-kr")));
        try {
            String[] codis = br.readLine().split(",");
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Map<Codi, Integer> membership = new TreeMap<>();
                for (int i = 1; i < values.length; i++) {
                    membership.put(Codi.valueOf(codis[i]), Integer.parseInt(values[i]));
                }
                memberships.put(Adjective.valueOf(values[0]), membership);
            }
        } catch (IOException ex) {
            Log.e("Fuzzy.Fuzzy", ex.toString());
        }
    }

    public ArrayList<Codi> getCodiList(User.UserData userData, Map<Codi, Integer> scores, Map<Codi, Integer> otherScores) {
        TreeMap<Codi, Double> total = new TreeMap<>();
        for (Adjectivizable adjectivizable : userData.toArray()) {
            for (Adjective adjective : adjectivizable.toAdjective()) {
                Map<Codi, Integer> membership = Objects.requireNonNull(memberships.get(adjective));
                membership.forEach((codi, value) -> total.merge(codi, value.doubleValue(), Double::sum));
            }
        }
        // 10 ~ 50 범위로 정규화
        int nUserAdjects = userData.toArray().length;
        total.replaceAll((codi, value) -> value * 40 / (21 * nUserAdjects) + 10);

        // -35 ~ 35 범위로 정규화해 더함
        scores.forEach((k, v) ->
                total.compute(k, (codi, prev) ->
                        (prev == null ? 0.0 : prev) + (v + 5) * 7.0 - 35));

        // -15 ~ 15 범위로 정규화해 더함(비슷한 사용자)
        otherScores.forEach((k, v) ->
                total.compute(k, (codi, prev) ->
                        (prev == null ? 0.0 : prev) + (v + 5) * 3.0 - 15));

        return total.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private double scoreToWeight(int score) {
        return 1.0 + (score * 0.1);
    }
}
