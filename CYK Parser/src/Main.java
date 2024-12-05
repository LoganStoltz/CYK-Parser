/** Created by Logan J. Stoltz for CSCD 420 - Compilers and Automata, 12/4/2024 **/
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // Read grammars and queries from the input file.
        List<Grammar> grammars = ReadInput.readInput("input.txt");

        // Generate the output file with grammar rules and query results.
        OutputGenerator.generateOutput(grammars, "output.txt");
    }
}
