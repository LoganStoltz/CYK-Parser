import java.util.ArrayList;
import java.util.List;

public class Grammar {
    public List<ProductionRule> rules = new ArrayList<>();
    public List<String> queries = new ArrayList<>();
    public String startSymbol = null;

    public void addProduction(String line) {
        String[] parts = line.split(" --> ");
        String lhs = parts[0];
        String rhs = parts[1];
        rules.add(new ProductionRule(lhs, rhs));

        if (startSymbol == null) {
            startSymbol = lhs;
        }
    }

    public List<String> getProducingNonTerminals(String rhs) {
        List<String> nonTerminals = new ArrayList<>();
        for (ProductionRule rule : rules) {
            if (rule.rhs.equals(rhs)) {
                nonTerminals.add(rule.lhs);
            }
        }
        return nonTerminals;
    }
}