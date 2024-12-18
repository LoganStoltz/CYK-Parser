/** Created by Logan J. Stoltz for CSCD 420 - Compilers and Automata, 12/4/2024 **/
import java.util.ArrayList;
import java.util.List;

public class CYKAlgorithm {

    public static boolean parse(String query, Grammar grammar) {
        int n = query.length();
        // create a 2D tabel to store sets of non-terminals for substrings.
        List<String>[][] table = new ArrayList[n][n];

        // Step 1: initialize the table for single-character substrings.
        for (int i = 0; i < n; i++) {
            // fill the diagonal.
            table[i][i] = grammar.getProducingNonTerminals(String.valueOf(query.charAt(i)));
        }

        // Step 2: fill the table for larger substrings of length 2 to n.
        for (int length = 2; length <= n; length++) {
            for (int i = 0; i <= n - length; i++) {
                int j = i + length - 1;
                table[i][j] = new ArrayList<>();

                // try all possible split points to divide the substring into two parts.
                for (int k = i; k < j; k++) {
                    // left part of the split.
                    for (String B : table[i][k]) {
                        // right par tof the split.
                        for (String C : table[k + 1][j]) {
                            // find and add non-terminals that produce the concatenation B + C.
                            table[i][j].addAll(grammar.getProducingNonTerminals(B + C));
                        }
                    }
                }
            }
        }

        // Step 3: check if the start symbol of the grammar produces the entire string.
        return table[0][n - 1].contains(grammar.startSymbol);
    }
}
