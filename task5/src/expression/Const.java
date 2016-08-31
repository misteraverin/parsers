package expression;

public class Const implements CommonExpression {

    private final double doubleValue;
    private final int intValue;

    public Const(int value) {
        intValue = value;
        doubleValue = value;
    }

    public Const(double value) {
        doubleValue = value;
        intValue = 0;
    }

    public int evaluate(int x) {
        return intValue;
    }

    public double evaluate(double x) {
        return doubleValue;
    }

    public int evaluate(int x, int y, int z) {
        return intValue;
    }
}