/**
 * @author Paklepa Kacper S20263
 */

package zad1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Anagrams {

    ArrayList<String> wordsFromFile = new ArrayList<>();
    List<ArrayList<String>> anagrams = new ArrayList<>();
    String out;

    public Anagrams(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        while (scanner.hasNext()) {
            wordsFromFile.add(scanner.next());
        }
        scanner.close();
    }

    public List<ArrayList<String>> getSortedByAnQty() {
        while (!wordsFromFile.isEmpty()) {
            String first = wordsFromFile.get(0);
            wordsFromFile.remove(0);
            ArrayList<String> anagramsTmp = new ArrayList<>();
            anagramsTmp.add(first);
            char[] chars = first.toCharArray();
            Arrays.sort(chars);
            for (int i = 0; i < wordsFromFile.size(); i++) {
                char[] charstmp = wordsFromFile.get(i).toCharArray();
                Arrays.sort(charstmp);
                if (Arrays.equals(chars, charstmp)) {
                    anagramsTmp.add(wordsFromFile.get(i));
                    wordsFromFile.remove(i);
                    i--;
                }
            }
            anagrams.add(anagramsTmp);
            for (int i = 0; i < anagrams.size() - 1; i++) {
                if (anagramsTmp.size() > anagrams.get(anagrams.indexOf(anagramsTmp) - 1).size()) {
                    ArrayList<String> tmp = anagrams.get(anagrams.indexOf(anagramsTmp) - 1);
                    anagrams.set((anagrams.indexOf(anagramsTmp) - 1), anagramsTmp);
                    anagrams.set(anagrams.lastIndexOf(anagramsTmp), tmp);
                }
                else if (anagramsTmp.size() == anagrams.get(anagrams.indexOf(anagramsTmp) - 1).size()) {
                    if (anagrams.get(anagrams.indexOf(anagramsTmp) - 1).get(0).startsWith(anagramsTmp.get(0))) {
                        ArrayList<String> tmp = anagrams.get(anagrams.indexOf(anagramsTmp) - 1);
                        anagrams.set((anagrams.indexOf(anagramsTmp) - 1), anagramsTmp);
                        anagrams.set(anagrams.lastIndexOf(anagramsTmp), tmp);
                    }
                    else if (anagramsTmp.get(0).startsWith(anagrams.get(anagrams.indexOf(anagramsTmp) - 1).get(0))) {}
                    else {
                        int z = anagrams.indexOf(anagramsTmp) - 1;
                        int y = 0;
                        do{
                            if(anagramsTmp.get(0).charAt(y) < anagrams.get(z).get(0).charAt(y)){
                                ArrayList<String> tmp = anagrams.get(z);
                                anagrams.set((z), anagramsTmp);
                                anagrams.set(anagrams.lastIndexOf(anagramsTmp), tmp);
                            }
                            y++;
                        }while(anagrams.indexOf(anagramsTmp) > z);
                    }
                }
            }
        }
        return anagrams;
    }

    public String getAnagramsFor(String next) {
        out = next + ": ";
        for (ArrayList<String> anagram : anagrams) {
            if (anagram.contains(next)) {
                anagram.remove(next);
                out += anagram;
            }
        }
        if (out.equals(next + ": ")) {
            out += null;
        }
        return out;
    }
}