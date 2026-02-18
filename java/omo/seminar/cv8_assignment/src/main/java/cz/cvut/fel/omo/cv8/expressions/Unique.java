package cz.cvut.fel.omo.cv8.expressions;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.omo.cv8.visitors.ListExpressionVisitor;

import java.util.LinkedHashSet;

public class Unique implements ListExpression {

    private final ListExpression sub;

    public Unique(ListExpression sub) {
        this.sub = sub;
    }

    public ListExpression getSub() {
        return sub;
    }

    @Override
    public ImmutableList<Integer> evaluate(Context c) {
        ImmutableList<Integer> original = sub.evaluate(c);
        LinkedHashSet<Integer> set = new LinkedHashSet<>(original);
        return ImmutableList.copyOf(set);
    }

    @Override
    public void accept(ListExpressionVisitor v) {
        v.visitUnique(this);
    }
}