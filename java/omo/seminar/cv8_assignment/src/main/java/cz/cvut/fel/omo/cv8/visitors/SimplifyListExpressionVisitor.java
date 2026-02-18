package cz.cvut.fel.omo.cv8.visitors;

import cz.cvut.fel.omo.cv8.expressions.*;

public class SimplifyListExpressionVisitor implements ListExpressionVisitor {

    private ListExpression value;

    public ListExpression getValue() {
        return value;
    }

    @Override
    public void visitIntList(IntList v) {
        value = v;
    }

    @Override
    public void visitVarList(VarList v) {
        value = v;
    }

    @Override
    public void visitRemove(Remove v) {
        value = v;
    }

    @Override
    public void visitConcatenate(Concatenate v) {
        value = v;
    }

    @Override
    public void visitUnique(Unique v) {
        value = v;
    }


}
