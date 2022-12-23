import java.util.*;

public class Main {

    public static String[][] trainingSet = {
            {"Sunny", "Hot", "High", "False", "No"},
            {"Sunny", "Hot", "High", "True", "No"},
            {"Overcast", "Hot", "High", "False", "Yes"},
            {"Rainy", "Mild", "High", "False", "Yes"},
            {"Rainy", "Cool", "Normal", "False", "Yes"},
            {"Rainy", "Cool", "Normal", "True", "No"},
            {"Overcast", "Cool", "Normal", "True", "Yes"},
            {"Sunny", "Mild", "High", "False", "No"},
            {"Sunny", "Cool", "Normal", "False", "Yes"},
            {"Rainy", "Mild", "Normal", "False", "Yes"},
            {"Sunny", "Mild", "Normal", "True", "Yes"},
            {"Overcast", "Mild", "High", "True", "Yes"},
            {"Overcast", "Hot", "Normal", "False", "Yes"},
            {"Rainy", "Mild", "High", "True", "No"}
    };

    public static String[][] testingSet = {
            {"Overcast", "Hot", "High", "True"},
            {"Sunny", "Cool", "Normal", "True"},
            {"Rainy", "Cool", "High", "False"}
    };

    public static Map<String, Double> answers = countDecisionAtr();

    public static void main(String[] args) {


        for (String[] testing : testingSet) {
            System.out.println("Test data: " + Arrays.asList(testing));
            System.out.println("Answer: " + classify(testing));
        }

        Scanner in = new Scanner(System.in);
        String s = "";
        String[] userInput = new String[trainingSet[0].length - 1];
        while (true) {
            System.out.println();
            System.out.println("0 - input new data");
            System.out.println("1 - end application");

            s = in.nextLine();
            switch (s) {
                case "0":
                    try {
                        for (int i = 0; i < trainingSet[0].length - 1; i++) {
                            System.out.println("Select attribute: ");
                            List<String> states = showPossibleAnswers(i);
                            for (int j = 0; j < states.size(); j++) {
                                System.out.println(j + ". " + states.get(j));
                            }
                            s = in.nextLine();

                            userInput[i] = states.get(Integer.parseInt(s));
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.err.println("Incorrect command");
                        break;
                    }

                    System.out.println("Test data: " + Arrays.asList(userInput));
                    System.out.println("Answer: " + classify(userInput));

                    break;
                case "1":
                    System.out.println("End");
                    System.exit(0);
                    break;
                default: {
                    System.err.println("Incorrect command");
                }
            }

        }

    }

    private static String classify(String[] testingData) {
        double bestValue = 0;
        String answer = "";
        for (Map.Entry<String, Double> entry : answers.entrySet()) {
            double value = naiveBayes(testingData, entry.getKey());
            if (bestValue < value) {
                bestValue = value;
                answer = entry.getKey();
            }
        }
        return answer;
    }

    public static double naiveBayes(String[] input, String decision) {
        double result = 1;

        for (int i = 0; i < input.length; i++) {
            result *= createFraction(input[i], i, decision);
        }

        return result * (answers.get(decision) / trainingSet.length);
    }

    public static double createFraction(String input, int pointer, String decision) {
        double numerator = 0;
        double denominator = answers.get(decision);

        for (int i = 0; i < trainingSet.length; i++) {
            String[] line = trainingSet[i];
            if (line[pointer] == input && line[line.length - 1] == decision) {
                numerator++;
            }
        }
        if (numerator == 0) {
            return laplace(denominator, pointer);
        }
        return numerator / denominator;
    }

    public static double laplace(double denominator, int pointer) {
        List<String> possibleAnswers = showPossibleAnswers(pointer);
        double newDenominator = denominator + possibleAnswers.size();

        return 1 / newDenominator;
    }

    public static Map<String, Double> countDecisionAtr() {
        Map<String, Double> result = new HashMap<>();

        for (int i = 0; i < trainingSet.length; i++) {
            String[] line = trainingSet[i];
            String atr = line[line.length - 1];
            if (!result.containsKey(atr)) {
                result.put(atr, 1.0);
            } else {
                double lastValue = result.get(atr) + 1;
                result.replace(atr, lastValue);
            }
        }
        return result;
    }

    public static List<String> showPossibleAnswers(int pointer) {
        List<String> possibleAnswers = new ArrayList<>();
        for (int i = 0; i < trainingSet.length; i++) {
            if (!possibleAnswers.contains(trainingSet[i][pointer])) {
                possibleAnswers.add(trainingSet[i][pointer]);
            }
        }
        return possibleAnswers;
    }

}
