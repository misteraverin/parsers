package expression;

public abstract class BinaryOperation implements CommonExpression {

    private CommonExpression left, right;


    protected abstract int Function(int l, int r);

    protected abstract double Function(double l, double r);

    public BinaryOperation(CommonExpression left, CommonExpression right) {
        this.left = left;
        this.right = right;
    }


    public int evaluate(int x) {
        return Function(left.evaluate(x), right.evaluate(x));
    }

    public double evaluate(double x) {
        return Function(left.evaluate(x), right.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return Function(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }
}