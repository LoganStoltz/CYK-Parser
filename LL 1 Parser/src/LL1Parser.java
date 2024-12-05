import java.io.*;
import java.util.*;

public class LL1Parser {
    public static void main(String[] args) throws IOException {
        List<Grammar> grammars = parseInput("input.txt");
        List<String> output = new ArrayList<>();

        for (Grammar grammar : grammars) {
            output.addAll(grammar.toStringList());

            Map<String, Set<String>> firstSets = grammar.computeFirstSets();
            Map<String, Set<String>> followSets = grammar.computeFollowSets(firstSets);

            output.addAll(formatFirstAndFollowSets(firstSets, followSets));

            LL1Table table = grammar.createLL1Table(firstSets, followSets);
            output.add(table.toString());

            if (table.isLL1()) {
                output.add("This grammar is LL(1).");
                for (String query : grammar.getQueries()) {
                    output.add(query + " is " +
                            (table.evaluateQuery(query) ? "in" : "NOT in") +
                            " the language defined by the above grammar.");
                }
            } else {
                output.add("This grammar is not LL(1).");
            }

            output.add(""); // Blank line between grammars
        }

        writeOutput("output.txt", output);
    }

    private static List<Grammar> parseInput(String filename) throws IOException {
        List<Grammar> grammars = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isBlank()) continue;

                int numProductions = Integer.parseInt(line.trim());
                List<String> productions = new ArrayList<>();
                for (int i = 0; i < numProductions; i++) {
                    productions.add(scanner.nextLine().trim());
                }

                scanner.nextLine(); // Skip blank line

                int numQueries = Integer.parseInt(scanner.nextLine().trim());
                List<String> queries = new ArrayList<>();
                for (int i = 0; i < numQueries; i++) {
                    queries.add(scanner.nextLine().trim());
                }

                grammars.add(new Grammar(productions, queries));
                if (scanner.hasNextLine()) scanner.nextLine(); // Skip blank line
            }
        }
        return grammars;
    }

    private static void writeOutput(String filename, List<String> output) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : output) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    private static List<String> formatFirstAndFollowSets(Map<String, Set<String>> firstSets,
                                                         Map<String, Set<String>> followSets) {
        List<String> output = new ArrayList<>();
        for (String nonTerminal : firstSets.keySet()) {
            output.add("FIRST(" + nonTerminal + ") = " + firstSets.get(nonTerminal));
        }
        for (String nonTerminal : followSets.keySet()) {
            output.add("FOLLOW(" + nonTerminal + ") = " + followSets.get(nonTerminal));
        }
        output.add(""); // Blank line after FIRST/FOLLOW sets
        return output;
    }
}
