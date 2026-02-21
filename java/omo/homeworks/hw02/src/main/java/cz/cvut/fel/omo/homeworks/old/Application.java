package cz.cvut.fel.omo.homeworks.old;

import java.util.UUID;

public class Application {

    public static void main(String[] args) {
        TransactionSystem transactionSystem = new TransactionSystem();
        transactionSystem.setTransactionParams(1000L, "CZK");
        System.out.println(transactionSystem.executeTransaction());

        String test = UUID.randomUUID().toString();
        System.out.println(test.length());
    }
}
