import java.util.ArrayList;
import java.util.List;

public class CYKAlgorithm {

    public static boolean parse(String query, Grammar grammar) {
        int n = query.length();
        List<String>[][] table = new ArrayList[n][n];

        // Initialize table for single characters
        for (int i = 0; i < n; i++) {
            table[i][i] = grammar.getProducingNonTerminals(String.valueOf(query.charAt(i)));
        }

        // Fill the table for larger substrings
        for (int length = 2; length <= n; length++) {
            for (int i = 0; i <= n - length; i++) {
                int j = i + length - 1;
                table[i][j] = new ArrayList<>();

                for (int k = i; k < j; k++) {
                    for (String B : table[i][k]) {
                        for (String C : table[k + 1][j]) {
                            table[i][j].addAll(grammar.getProducingNonTerminals(B + C));
                        }
                    }
                }
            }
        }

        // Check if the start symbol derives the entire string
        return table[0][n - 1].contains(grammar.startSymbol);
    }
}