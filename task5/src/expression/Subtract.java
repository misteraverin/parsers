package expression;


public class Subtract extends BinaryOperation {

    public Subtract(CommonExpression left, CommonExpression right) {
        super(left, right);
    }

    protected int Function(int l, int r) {
        return l - r;
    }

    protected double Function(double l, double r) {
        return l - r;
    }
}
