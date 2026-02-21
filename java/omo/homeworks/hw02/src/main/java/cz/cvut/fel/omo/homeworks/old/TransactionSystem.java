package cz.cvut.fel.omo.homeworks.old;

import cz.cvut.fel.omo.homeworks.common.client.LegacyPaymentClient;
import cz.cvut.fel.omo.homeworks.common.session.UserSession;

public class TransactionSystem {

    private static final String TRANSACTION_FAILURE = "Transaction execution failed.";
    private final UserSession userSession = new UserSession();

    /**
     * Legacy system client
     */
    private final LegacyPaymentClient legacyPaymentClient = new LegacyPaymentClient();


    private Long totalAmount;
    private String currencyCode;

    /**
     * Use this method prepare attributes for execution using the <b>legacy</b> transaction system
     *
     * @param totalAmount  amount for transfer
     * @param currencyCode current for transfer
     */
    public void setTransactionParams(Long totalAmount, String currencyCode) {
        this.totalAmount = totalAmount;
        this.currencyCode = currencyCode;
    }


    /**
     * Execute transaction using the Legacy Gateway
     *
     * @return transaction ID from client
     */
    public String executeTransaction() {
        String legacyTransaction = buildLegacyTransaction();
        String senderIP = userSession.getIP();
        if (legacyTransaction != null) {
            return legacyPaymentClient.execute(legacyTransaction, senderIP);
        }
        return TRANSACTION_FAILURE;
    }

    private String buildLegacyTransaction() {
        if (currencyCode == null || totalAmount == null) {
            return null;
        }
        return String.join(";", currencyCode, totalAmount.toString());
    }

}
