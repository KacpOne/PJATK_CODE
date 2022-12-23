import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main{
    public static void main(String[] args) {
        File file = new File("src/huffman.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            List<Huffman> list= new ArrayList<>();
            int n = Integer.parseInt(line);
            Map<String,String> map = new HashMap<>();
            while ((line = br.readLine()) != null) {
                String[] split = line.split(" ");
                Huffman huffman = new Huffman();
                huffman.letter =split[0];
                huffman.count=Integer.parseInt(split[1]);
                list.add(huffman);
                map.put(split[0],"");
            }
            String finalString="";
            list.sort(Comparator.comparing(Huffman::getCount).thenComparing(Huffman::getLetter));
            while(finalString.length()<n){
                String newString=list.get(0).letter+list.get(1).letter;
                finalString=newString;
                int newCount=list.get(0).count+list.get(1).count;
                for(Map.Entry m: map.entrySet()){
                    if(list.get(0).letter.contains(m.getKey().toString())){
                        m.setValue(m.getValue()+"0");
                    }
                    if(list.get(1).letter.contains(m.getKey().toString())){
                        m.setValue(m.getValue()+"1");
                    }
                }
                list.get(0).setLetter(newString);
                list.get(0).setCount(newCount);
                list.remove(1);
                list.sort(Comparator.comparing(Huffman::getCount).thenComparing(Huffman::getLetter));
            }
            System.out.println();
            for(Map.Entry m : map.entrySet()){
                m.setValue(new StringBuilder(m.getValue().toString()).reverse().toString());
            }
            map.entrySet().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class Huffman {
    String letter;
    int count;

    public Huffman() {}

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "{" +
                "letter='" + letter + '\'' +
                ", count=" + count +
                '}';
    }
}