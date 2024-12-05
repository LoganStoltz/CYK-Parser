/** Created by Logan J. Stoltz for CSCD 420 - Compilers and Automata, 12/4/2024 **/
import java.io.*;
import java.util.*;

public class OutputGenerator {

    public static void generateOutput(List<Grammar> grammars, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

        // Iterate through each grammar in the list.
        for (Grammar grammar : grammars) {
            // Step 1: Write the production rules of the current grammar.
            for (ProductionRule rule : grammar.rules) {
                writer.write(rule.toString()); // Convert the rule to a string and write it.
                writer.newLine(); // Add a new line after each rule.
            }

            writer.newLine(); // Add a blank line to separate grammar rules from query results.

            // Step 2: Write the results of the queries for the current grammar.
            for (String query : grammar.queries) {
                // call the CYK algorithm to check if the query is in the language.
                boolean result = CYKAlgorithm.parse(query, grammar);

                // Write either: "query is in the language." or "query is NOT in the language."
                writer.write(query + (result ? " is in the language." : " is NOT in the language."));
                writer.newLine();
            }
            writer.newLine(); // Add a blank line to separate outputs for different grammars.
        }
        writer.close();
    }
}
