import java.util.*;

public class Grammar {
    private final String startSymbol;
    private final Map<String, List<String>> productions;
    private final List<String> queries;

    public Grammar(List<String> productionLines, List<String> queries) {
        this.productions = new LinkedHashMap<>();
        this.queries = queries;

        for (String line : productionLines) {
            String[] parts = line.split("-->");
            String lhs = parts[0].trim();
            String rhs = parts[1].trim();

            productions.computeIfAbsent(lhs, k -> new ArrayList<>()).add(rhs);
        }
        this.startSymbol = productionLines.get(0).split("-->")[0].trim();
    }

    public String getStartSymbol() {
        return startSymbol;
    }

    public Map<String, List<String>> getProductions() {
        return productions;
    }

    public List<String> getQueries() {
        return queries;
    }

    public Map<String, Set<String>> computeFirstSets() {
        Map<String, Set<String>> first = new HashMap<>();
        for (String nonTerminal : productions.keySet()) {
            first.put(nonTerminal, new HashSet<>());
        }

        boolean changed;
        do {
            changed = false;
            for (String nonTerminal : productions.keySet()) {
                for (String rhs : productions.get(nonTerminal)) {
                    String[] symbols = rhs.split(" ");
                    for (String symbol : symbols) {
                        if (!productions.containsKey(symbol)) { // Terminal
                            changed |= first.get(nonTerminal).add(symbol);
                            break;
                        } else {
                            Set<String> firstOfSymbol = first.get(symbol);
                            changed |= first.get(nonTerminal).addAll(firstOfSymbol);
                            if (!firstOfSymbol.contains("ε")) break;
                        }
                    }
                }
            }
        } while (changed);
        return first;
    }

    public Map<String, Set<String>> computeFollowSets(Map<String, Set<String>> first) {
        Map<String, Set<String>> follow = new HashMap<>();
        for (String nonTerminal : productions.keySet()) {
            follow.put(nonTerminal, new HashSet<>());
        }
        follow.get(startSymbol).add("$");

        boolean changed;
        do {
            changed = false;
            for (String nonTerminal : productions.keySet()) {
                for (String rhs : productions.get(nonTerminal)) {
                    String[] symbols = rhs.split(" ");
                    for (int i = 0; i < symbols.length; i++) {
                        if (productions.containsKey(symbols[i])) {
                            Set<String> followOfCurrent = follow.get(symbols[i]);
                            if (i + 1 < symbols.length) {
                                String nextSymbol = symbols[i + 1];
                                if (!productions.containsKey(nextSymbol)) {
                                    changed |= followOfCurrent.add(nextSymbol);
                                } else {
                                    Set<String> firstOfNext = first.get(nextSymbol);
                                    changed |= followOfCurrent.addAll(firstOfNext);
                                    followOfCurrent.remove("ε");
                                }
                            } else {
                                changed |= followOfCurrent.addAll(follow.get(nonTerminal));
                            }
                        }
                    }
                }
            }
        } while (changed);
        return follow;
    }

    public LL1Table createLL1Table(Map<String, Set<String>> first, Map<String, Set<String>> follow) {
        return new LL1Table(this, first, follow);
    }

    public List<String> toStringList() {
        List<String> output = new ArrayList<>();
        for (String nonTerminal : productions.keySet()) {
            for (String rhs : productions.get(nonTerminal)) {
                output.add(nonTerminal + " --> " + rhs);
            }
        }
        output.add(""); // Blank line after grammar rules
        return output;
    }
}
