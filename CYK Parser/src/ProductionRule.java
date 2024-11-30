public class ProductionRule
{
    public String lhs;
    public String rhs;

    public ProductionRule(String lhs, String rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public String toString() {
        return lhs + " --> " + rhs;
    }
}