package cz.cvut.fel.omo.cv8.expressions;


import com.google.common.collect.ImmutableList;
import cz.cvut.fel.omo.cv8.visitors.ListExpressionVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Remove implements ListExpression {

    private final ListExpression sub;
    private int element;

    public Remove(ListExpression sub, int element) {
        this.sub = sub;
        this.element = element;
    }

    public ListExpression getSub() {
        return sub;
    }

    public int getElement() {
        return element;
    }

    @Override
    public ImmutableList<Integer> evaluate(Context c) {
        ImmutableList<Integer> original = sub.evaluate(c);

        // ImmutableList -> ArrayList (mutable)
        List<Integer> list = new ArrayList<>(original);

        list.removeAll(Collections.singleton(element));

        return ImmutableList.copyOf(list);
    }

    @Override
    public void accept(ListExpressionVisitor v) {
        v.visitRemove(this);
    }
}
