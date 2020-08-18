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
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Fuzzy {

    private static Codi[] codis = Codi.values();

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
            Log.e("ReadingCSV", ex.toString());
        }
    }


    @SuppressWarnings("ConstantConditions")
    private void addResult(Map<Codi, Integer> total, Adjectivizable adjectivizable) {
        for (Adjective adj : adjectivizable.toAdjective()) {
            Map<Codi, Integer> membership = memberships.get(adj);
            for (Codi codi : membership.keySet()) {
                Integer prev = total.get(codi);
                total.put(codi, (prev == null ? 0 : prev) + membership.get(codi));
            }
        }
    }

    public ArrayList<Codi> getCodiList(Adjectivizable[] adjectivizables) {
        Map<Codi, Integer> total = new TreeMap<>();

        for (Adjectivizable adjectivizable : adjectivizables) {
            addResult(total, adjectivizable);
        }

        return total.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
