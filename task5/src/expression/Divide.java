package expression;


public class Divide extends BinaryOperation {
    public Divide(CommonExpression left, CommonExpression right) {
        super(left, right);
    }

    protected int Function(int l, int r) {
        return l / r;
    }

    protected double Function(double l, double r) {
        return l / r;
    }
}
