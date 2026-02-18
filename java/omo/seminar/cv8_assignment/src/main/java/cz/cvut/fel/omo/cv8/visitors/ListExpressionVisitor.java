package cz.cvut.fel.omo.cv8.visitors;

import cz.cvut.fel.omo.cv8.expressions.*;

public interface ListExpressionVisitor {
    void visitIntList(IntList v);

    void visitVarList(VarList v);

    void visitRemove(Remove v);

    void visitConcatenate(Concatenate v);

    void visitUnique(Unique v);
}
