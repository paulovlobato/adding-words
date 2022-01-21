package core;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Link to the problem: https://open.kattis.com/problems/addingwords
 */

public class ProblemA {
    private final static String UNKNOWN = "unknown";
    private final static String OP_MINUS = "-";
    private final static String OP_SUM = "+";

    /**
     * Main method. It handles some basic operations, like dictionary filling;
     * and data flow between auxiliary methods.
     *
     * We're using System.getProperty("line.separator") to let the System detect which
     * line separator will be added to the EOL
     */
    public static void main(String[] args) {
//        Debug options. Replacing the scanner with the following one
//        allows the code to run inside the IDE of choice, and it speeded up my development
//        instead of manually compiling the java class on terminal and manually inputing the sample file.

        // REMOVE WHEN SUBMITING SOLUTION
//        File file = new File("sample.in");
//        Scanner scanner = new Scanner(file);
        // REMOVE WHEN SUBMITING SOLUTION

        Scanner scanner = new Scanner(System.in);
        HashMap<String, Integer> dictionary = new HashMap<>();

        while (scanner.hasNextLine()) {
            StringBuilder stringBuilder = new StringBuilder();
            String[] line = scanner.nextLine().split(" ");
            String action = line[0];

            switch (action) {
                case "def":
//                If action is def, insert into dict the first token after 'def' and second value after 'def'
                    dictionary.put(line[1], Integer.parseInt(line[2]));
                    break; // end case
                case "clear":
//                Clears the whole dict
                    dictionary.clear();
                    break; // end case
                case "calc":
                    for (int i = 1; i < line.length; i++) {
                        stringBuilder.append(line[i]).append(" ");
                    }

//                Check if the words are inserted into our dict
                    boolean isInserted = true;
                    for (int i = 1; i < line.length; i += 2) {
                        if (!dictionary.containsKey(line[i])) {
                            isInserted = false;
                            break;
                        }
                    }
                    if (!isInserted) {
//                    If the words are not in our dict, then append "unknown" to the EOL.
                        stringBuilder.append(UNKNOWN).append(System.getProperty("line.separator"));
                    } else {
//                        If the words are in our dict
//                        Then handles the math operations
                        int result = calcHandler(dictionary, line);
                        resultHandler(dictionary, result, stringBuilder);
                    }
                    break; // end case
            }
            System.out.print(stringBuilder);
        }
        scanner.close();
    }

    /**
     * Auxiliary method to handle sum or subtraction of next number according to the operator
     * @param dictionary dictionary containing the placeholders of the values
     * @param line currently line being read
     * @return integer, the final value of the calculation
     */
    public static int calcHandler(HashMap<String, Integer> dictionary, String[] line) {
        int result = dictionary.get(line[1]);

//        Iterates line from two to two, adding or subtracting the next value.
        for (int i = 2; i < line.length - 1; i += 2) {
            int nextValue = dictionary.get(line[i + 1]);
            result += (line[i].equals(OP_SUM)) ? nextValue : -nextValue;
        }
        return result;
    }

    /**
     * Result handler, it basically outputs the line separator or the "unknown" word to the EOL
     * if the result is not defined in the dictionary
     *
     * It's a void method, so it will modify the stringBuilder object of the class instead of creating a new one
     * @param dictionary dictionary containing the placeholders of the values
     * @param result integer containing the sum of the currently line
     * @param _stringBuilder the stringBuilder object containing the response of the operations
     */
    public static void resultHandler(HashMap<String, Integer> dictionary, Integer result, StringBuilder _stringBuilder) {
        boolean isUnknown = true;

        for (Map.Entry<String, Integer> s : dictionary.entrySet()) {
//            Looping the entire dictionary to output new unknown when
//            there is not a word for the result
            if (result.equals(s.getValue())) {
                _stringBuilder.append(s.getKey()).append(System.getProperty("line.separator"));
                isUnknown = false;
            }
        }
        if (isUnknown) {
            _stringBuilder.append(UNKNOWN).append(System.getProperty("line.separator"));
        }
    }
}
