/** Created by Logan J. Stoltz for CSCD 420 - Compilers and Automata, 12/4/2024 **/
import java.io.*;
import java.util.*;

public class ReadInput {

    public static List<Grammar> readInput(String filename) throws IOException {
        List<Grammar> grammars = new ArrayList<>(); // List to hold all grammars read from the file.
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        // Read the number of grammars in the file.
        int grammarCount = Integer.parseInt(reader.readLine().trim());
        reader.readLine(); // Skip the blank line after the grammar count.

        // Go through each grammar.
        for (int i = 0; i < grammarCount; i++) {
            Grammar grammar = new Grammar(); // Create new Grammar object for each.
            String line;

            // Step 1: Add production rules for the current grammar until blank line encountered.
            while (!(line = reader.readLine()).isEmpty()) {
                grammar.addProduction(line.trim());
            }

            // Step 2: Read the number of queries for the current grammar.
            int queryCount = Integer.parseInt(reader.readLine().trim());

            // Step 3: Read each query and add it to the grammar's query list.
            for (int j = 0; j < queryCount; j++) {
                grammar.queries.add(reader.readLine().trim());
            }

            // Add the fully constructed grammar to the list of grammars.
            grammars.add(grammar);

            // Skip the blank line separating grammars in the input file.
            reader.readLine();
        }
        reader.close();

        // Return the list of grammars.
        return grammars;
    }
}
