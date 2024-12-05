/** Created by Logan J. Stoltz for CSCD 420 - Compilers and Automata, 12/4/2024 **/
import java.util.ArrayList;
import java.util.List;

public class CYKAlgorithm {

    public static boolean parse(String query, Grammar grammar) {
        int n = query.length();
        // Creates a 2D table to store sets of non-terminals for substrings.
        List<String>[][] table = new ArrayList[n][n];

        // Step 1: Initialize the table for single-character substrings.
        for (int i = 0; i < n; i++) {
            // Fill the diagonal of the table with non-terminals that produce query[i].
            table[i][i] = grammar.getProducingNonTerminals(String.valueOf(query.charAt(i)));
        }

        // Step 2: Fill the table for larger substrings of length 2 to n.
        // Iterate over all possible substring lengths, starting from 2.
        for (int length = 2; length <= n; length++) {
            // Iterate over all possible starting indices for substrings of the given length.
            for (int i = 0; i <= n - length; i++) {
                int j = i + length - 1;
                table[i][j] = new ArrayList<>(); // Initialize the cell for the current substring.

                // Try all possible split points to divide the substring into two parts.
                for (int k = i; k < j; k++) {
                    // Iterate over left part of the split.
                    for (String B : table[i][k]) {
                        // Iterate over right part of the split.
                        for (String C : table[k + 1][j]) {
                            // Find and add non-terminals that produce the concatenation B + C.
                            table[i][j].addAll(grammar.getProducingNonTerminals(B + C));
                        }
                    }
                }
            }
        }

        // Step 3: Check if the start symbol of the grammar produces the entire string.
        return table[0][n - 1].contains(grammar.startSymbol);
    }
}
