/** Created by Logan J. Stoltz for CSCD 420 - Compilers and Automata, 12/4/2024 **/
import java.util.ArrayList;
import java.util.List;

public class Grammar {

    // List of production rules defining the grammar.
    public List<ProductionRule> rules = new ArrayList<>();

    // List of queries (strings) to test against the grammar.
    public List<String> queries = new ArrayList<>();

    // The start symbol of the grammar.
    public String startSymbol = null;

    public void addProduction(String line) {
        // Split the production rule into left-hand side (LHS) and right-hand side (RHS).
        String[] parts = line.split(" --> ");
        String lhs = parts[0]; // Non-terminal on the left-hand side.
        String rhs = parts[1]; // Production on the right-hand side.

        // Add the prodution rule to the rule list.
        rules.add(new ProductionRule(lhs, rhs));

        // The start symbol is assumed to be the LHS of the first rule.
        if (startSymbol == null) {
            startSymbol = lhs;
        }
    }

    public List<String> getProducingNonTerminals(String rhs) {
        List<String> nonTerminals = new ArrayList<>();

        // Iterate over all production rules.
        for (ProductionRule rule : rules) {
            // If the RHS of the current rule matches the input RHS, add the LHS to the result.
            if (rule.rhs.equals(rhs)) {
                nonTerminals.add(rule.lhs);
            }
        }

        // Return the list of matching non-terminals.
        return nonTerminals;
    }
}
