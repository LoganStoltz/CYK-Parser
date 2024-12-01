import java.io.*;
import java.util.*;

public class ReadInput {

    public static List<Grammar> readInput(String filename) throws IOException{
        List<Grammar> grammars = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        int grammarCount = Integer.parseInt(reader.readLine().trim());
        reader.readLine(); // Skip blank line

        for (int i = 0; i < grammarCount; i++) {
            Grammar grammar = new Grammar();
            String line;

            // Read grammar rules
            while (!(line = reader.readLine()).isEmpty()) {
                grammar.addProduction(line.trim());
            }

            // Read queries
            int queryCount = Integer.parseInt(reader.readLine().trim());
            for (int j = 0; j < queryCount; j++) {
                grammar.queries.add(reader.readLine().trim());
            }

            grammars.add(grammar);
            reader.readLine(); // Skip blank line
        }

        reader.close();
        return grammars;
    }
}
