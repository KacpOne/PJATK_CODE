/**
 * @author Paklepa Kacper S20263
 */

package zad1;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {

        LinkedHashMap<String, ArrayList<String>> map = new LinkedHashMap<>();
        LinkedHashMap<String, ArrayList<String>> out = new LinkedHashMap<>();
        InputStream is = new URL("http://wiki.puzzlers.org/pub/wordlists/unixdict.txt").openConnection()
                .getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        Stream<String> stream = bufferedReader.lines();
        stream.forEach(e -> toMap(e, map));

        do {
            String tmp = map.entrySet().stream().max(Comparator.comparingInt(entry -> entry.getValue().size())).get()
                    .getKey();
            ArrayList<String> tmp1 = map.get(tmp);
            out.put(tmp, tmp1);
            map.remove(tmp);
        } while (out.get(out.entrySet().stream().max(Comparator.comparingInt(entry -> entry.getValue().size())).get()
                .getKey()).size() == map.get(map.entrySet().stream().max(Comparator.comparingInt(entry -> entry
                .getValue().size())).get().getKey()).size());
        out.forEach((key, value) -> {
            for (int i = 0; i < out.get(key).size(); i++) {
                System.out.print(out.get(key).get(i) + " ");
            }
            System.out.println();
        });
    }

    public static void toMap(String e, LinkedHashMap<String, ArrayList<String>> map) {
        char[] tmp = e.toCharArray();
        Arrays.sort(tmp);
        String tmp1 = Arrays.toString(tmp);
        if (!map.containsKey(tmp1)) {
            map.put(tmp1, new ArrayList<>());
        }
        map.get(tmp1).add(e);
    }
}
