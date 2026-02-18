package cz.cvut.fel.omo.cv8.expressions;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.omo.cv8.visitors.ListExpressionVisitor;

import java.beans.Expression;
import java.util.ArrayList;
import java.util.List;

public class Concatenate implements ListExpression {

    private final ListExpression left;
    private final ListExpression right;

    public Concatenate(ListExpression left, ListExpression right) {
        this.left = left;
        this.right = right;
    }

    public ListExpression getLeft() {
        return left;
    }

    public ListExpression getRight() {
        return right;
    }

    @Override
    public ImmutableList<Integer> evaluate(Context c) {
        ImmutableList<Integer> l = left.evaluate(c);
        ImmutableList<Integer> r = right.evaluate(c);

        List<Integer> all = new ArrayList<Integer>(l.size() + r.size());

        all.addAll(l);
        all.addAll(r);

        return ImmutableList.copyOf(all);
    }

    @Override
    public void accept(ListExpressionVisitor v) {
        v.visitConcatenate(this);
    }
}