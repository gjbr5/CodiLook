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

    public ArrayList<Codi> getCodiList(User.UserData userData, Map<Codi, Integer> scores) {
        Map<Codi, Integer> total = new TreeMap<>();
        for (Adjectivizable adjectivizable : userData.toArray()) {
            for (Adjective adjective : adjectivizable.toAdjective()) {
                Objects.requireNonNull(memberships.get(adjective)).forEach((codi, integer) ->
                        total.merge(codi, integer, Integer::sum));
            }
        }

        scores.forEach((codi, score) ->
                total.put(codi, (int) (Objects.requireNonNull(total.get(codi)) * scoreToWeight(score))));

        return total.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private double scoreToWeight(int score) {
        switch (score) {
            case -5:
                return 0.5;
            case -4:
                return 0.6;
            case -3:
                return 0.7;
            case -2:
                return 0.8;
            case -1:
                return 0.9;
            case 1:
                return 1.1;
            case 2:
                return 1.2;
            case 3:
                return 1.3;
            case 4:
                return 1.4;
            case 5:
                return 1.5;
            default:
                return 1.0;
        }
    }
}
