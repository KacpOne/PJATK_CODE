package zad1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProgLang<X, Y> {
    private LinkedHashMap<X, ArrayList<Y>> mapLanguages = new LinkedHashMap<>();
    private LinkedHashMap<X, ArrayList<Y>> mapProgrammers = new LinkedHashMap<>();

    public ProgLang(String path) {
        ArrayList<String> fromFile = new ArrayList<>();
        ArrayList<String> namesTmp = new ArrayList<>();
        ArrayList<String> languagesTmp;

        String[] tmpArr;

        String language;

        try {
            Scanner scan = new Scanner(new File(path));
            while (scan.hasNext()) {
                fromFile.add(scan.nextLine());
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        for (String word : fromFile) {
            tmpArr = word.split("\t");
            language = tmpArr[0];
            languagesTmp = new ArrayList<>();
            for (int j = 1; j < tmpArr.length; j++)
                if (!languagesTmp.contains(tmpArr[j]))
                    languagesTmp.add(tmpArr[j]);
            this.mapLanguages.put((X) language, (ArrayList<Y>) languagesTmp);
            for (int j = 1; j < tmpArr.length; j++)
                if (!namesTmp.contains(tmpArr[j]))
                    namesTmp.add(tmpArr[j]);
        }
        for (String word : namesTmp) {
            languagesTmp = new ArrayList<>();
            for (String line : fromFile) {
                tmpArr = line.split("\t");
                for (int z = 1; z < tmpArr.length; z++)
                    if (tmpArr[z].equals(word))
                        if (!languagesTmp.contains(tmpArr[0]))
                            languagesTmp.add(tmpArr[0]);
            }
            this.mapProgrammers.put((X) word, (ArrayList<Y>) languagesTmp);
        }
    }

    public static <G, E> Map<G, E> sorted(Map<G, E> mapIn, Comparator<Map.Entry<G, E>> comparator) {
        LinkedHashMap<G, E> mapOut = mapIn.entrySet().stream().sorted(comparator).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (entry1, entry2) -> entry1, LinkedHashMap::new));
        return mapOut;
    }

    public static <G, E> Map<G, E> filtered(Map<G, E> mapIn, Predicate<Map.Entry<G, E>> filtrator) {

        LinkedHashMap<G, E> mapOut = mapIn.entrySet().stream().filter(filtrator).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (entry1, entry2) -> entry1, LinkedHashMap::new));
        return mapOut;
    }

    public LinkedHashMap<X, ArrayList<Y>> getLangsMap() {
        return this.mapLanguages;
    }

    public LinkedHashMap<X, ArrayList<Y>> getProgsMap() {
        return this.mapProgrammers;
    }

    public LinkedHashMap<X, ArrayList<Y>> getLangsMapSortedByNumOfProgs() {

        return (LinkedHashMap<X, ArrayList<Y>>) getComparedValues(this.mapLanguages);
    }

    public LinkedHashMap<X, ArrayList<Y>> getProgsMapSortedByNumOfLangs() {

        return (LinkedHashMap<X, ArrayList<Y>>) getComparedValues(this.mapProgrammers);
    }

    public LinkedHashMap<X, ArrayList<Y>> getProgsMapForNumOfLangsGreaterThan(int n) {
        return (LinkedHashMap<X, ArrayList<Y>>) filtered(this.mapProgrammers, t -> (((t.getValue()).size())) > n);
    }


    private Map<X, ArrayList<Y>> getComparedValues(LinkedHashMap<X, ArrayList<Y>> mapLanguages) {
        return sorted(mapLanguages, (object1, object2) -> {
            if ((object1.getValue()).size() > (object2.getValue()).size())
                return -1   ;
            if ((object1.getValue()).size() < (object2.getValue()).size())
                return 1;
            return Integer.compare(0, (String.valueOf(object2.getKey())).compareTo(String.valueOf(object1.getKey())));
        });
    }
}