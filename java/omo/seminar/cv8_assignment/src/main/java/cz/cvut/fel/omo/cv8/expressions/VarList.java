package cz.cvut.fel.omo.cv8.expressions;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.omo.cv8.visitors.ListExpressionVisitor;

public class VarList implements ListExpression {

    private final String name;

    public VarList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public ImmutableList<Integer> evaluate(Context c) {
        return c.get(name);
    }

    @Override
    public void accept(ListExpressionVisitor v) {
        v.visitVarList(this);
    }
}
