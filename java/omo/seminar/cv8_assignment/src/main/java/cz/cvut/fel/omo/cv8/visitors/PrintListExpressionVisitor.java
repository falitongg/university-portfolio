package cz.cvut.fel.omo.cv8.visitors;

import cz.cvut.fel.omo.cv8.expressions.*;

public class PrintListExpressionVisitor implements ListExpressionVisitor {

    @Override
    public void visitIntList(IntList v) {
        System.out.print(v.getList());
    }

    @Override
    public void visitVarList(VarList v) {
        System.out.print(v.getName());
    }

    @Override
    public void visitRemove(Remove v) {
        System.out.print("R(");
        v.getSub().accept(this);
        System.out.print(", " + v.getElement());
        System.out.print(")");
    }

    @Override
    public void visitConcatenate(Concatenate v) {
        System.out.print("C(");
        v.getLeft().accept(this);
        System.out.print(", ");
        v.getRight().accept(this);
        System.out.print(")");
    }

    @Override
    public void visitUnique(Unique v) {
        System.out.print("U(");
        v.getSub().accept(this);
        System.out.print(")");
    }
}