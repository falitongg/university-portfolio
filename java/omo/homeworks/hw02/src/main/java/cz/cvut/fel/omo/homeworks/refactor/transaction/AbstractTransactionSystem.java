package cz.cvut.fel.omo.homeworks.refactor.transaction;

import java.util.Optional;
import java.util.UUID;

public abstract class AbstractTransactionSystem implements TransactionSystem {

    protected Long totalAmount;
    protected String currencyCode;

    protected AbstractTransactionSystem(Long totalAmount, String currencyCode) {
        this.totalAmount = totalAmount;
        this.currencyCode = currencyCode;
    }

    @Override
    public String executeTransaction() {
        Optional<String> transaction = buildTransaction();
        if (transaction.isEmpty()) {
            return "Transaction execution failed.";
        }
        Optional<String> executed = execute(transaction.get());
        if (executed.isPresent()) {
            return executed.get();
        }
        return "Transaction execution failed.";

    }

    protected abstract Optional<String> execute(String transaction);

    protected abstract Optional<String> buildTransaction();
}
