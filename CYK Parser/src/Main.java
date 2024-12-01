import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Grammar> grammars = ReadInput.readInput("input.txt");
        OutputGenerator.generateOutput(grammars, "output.txt");
    }
}