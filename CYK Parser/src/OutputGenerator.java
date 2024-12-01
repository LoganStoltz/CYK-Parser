
import java.io.*;
import java.util.*;

public class OutputGenerator {

    public static void generateOutput(List<Grammar> grammars, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

        for (Grammar grammar : grammars) {
            // Write grammar rules
            for (ProductionRule rule : grammar.rules) {
                writer.write(rule.toString());
                writer.newLine();
            }

            writer.newLine();

            // Write query results
            for (String query : grammar.queries) {
                boolean result = CYKAlgorithm.parse(query, grammar);
                writer.write(query + (result ? " is in the language." : " is NOT in the language."));
                writer.newLine();
            }

            writer.newLine();
        }

        writer.close();
    }
}

