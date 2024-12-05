import java.util.*;

public class LL1Table {
    private final Map<String, Map<String, String>> table;
    private boolean isLL1;

    public LL1Table(Grammar grammar, Map<String, Set<String>> first, Map<String, Set<String>> follow) {
        this.table = new HashMap<>();
        this.isLL1 = true;

        for (String nonTerminal : grammar.getProductions().keySet()) {
            table.put(nonTerminal, new HashMap<>());
        }

        for (String nonTerminal : grammar.getProductions().keySet()) {
            for (String production : grammar.getProductions().get(nonTerminal)) {
                Set<String> firstSet = computeFirstSetForProduction(production, first);

                for (String terminal : firstSet) {
                    if (!terminal.equals("ε")) {
                        if (table.get(nonTerminal).containsKey(terminal)) {
                            isLL1 = false;
                        }
                        table.get(nonTerminal).put(terminal, production);
                    }
                }

                if (firstSet.contains("ε")) {
                    for (String followSymbol : follow.get(nonTerminal)) {
                        if (table.get(nonTerminal).containsKey(followSymbol)) {
                            isLL1 = false;
                        }
                        table.get(nonTerminal).put(followSymbol, production);
                    }
                }
            }
        }
    }

    private Set<String> computeFirstSetForProduction(String production, Map<String, Set<String>> first) {
        Set<String> result = new HashSet<>();
        String[] symbols = production.split(" ");

        for (String symbol : symbols) {
            if (!first.containsKey(symbol)) {
                result.add(symbol);
                break;
            }

            Set<String> firstSet = first.get(symbol);
            result.addAll(firstSet);
            if (!firstSet.contains("ε")) break;
        }

        return result;
    }

    public boolean evaluateQuery(String query) {
        String[] tokens = (query + " $").split(" ");
        Stack<String> stack = new Stack<>();
        stack.push("$");
        stack.push(table.keySet().iterator().next());

        int index = 0;
        while (!stack.isEmpty()) {
            String top = stack.pop();
            String currentToken = tokens[index];

            if (top.equals(currentToken)) {
                index++;
            } else if (table.containsKey(top)) {
                Map<String, String> row = table.get(top);
                if (row.containsKey(currentToken)) {
                    String production = row.get(currentToken);
                    String[] rhs = production.split(" ");
                    for (int i = rhs.length - 1; i >= 0; i--) {
                        if (!rhs[i].equals("ε")) {
                            stack.push(rhs[i]);
                        }
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return index == tokens.length;
    }

    public boolean isLL1() {
        return isLL1;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LL(1) Parsing Table:\n");

        Set<String> terminals = new HashSet<>();
        for (Map<String, String> row : table.values()) {
            terminals.addAll(row.keySet());
        }
        List<String> terminalList = new ArrayList<>(terminals);
        Collections.sort(terminalList);

        builder.append(String.format("%-15s", ""));
        for (String terminal : terminalList) {
            builder.append(String.format("%-15s", terminal));
        }
        builder.append("\n");

        for (String nonTerminal : table.keySet()) {
            builder.append(String.format("%-15s", nonTerminal));
            for (String terminal : terminalList) {
                String production = table.get(nonTerminal).get(terminal);
                builder.append(String.format("%-15s", production == null ? "" : nonTerminal + " → " + production));
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
