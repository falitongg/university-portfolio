package cz.cvut.fel.omo.homeworks.refactor.transaction;

import cz.cvut.fel.omo.homeworks.common.client.LegacyPaymentClient;
import cz.cvut.fel.omo.homeworks.common.session.UserSession;

import java.util.Optional;
import java.util.UUID;

public class LegacyTransactionSystem extends AbstractTransactionSystem {

    private UserSession userSession = new UserSession();

    private static final LegacyPaymentClient legacyPaymentClient = new LegacyPaymentClient();

    public LegacyTransactionSystem(Long totalAmount, String currencyCode) {
        super(totalAmount, currencyCode);
    }

    @Override
    protected Optional<String> execute(String transaction) {
        if(totalAmount == null || currencyCode == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(String.valueOf(UUID.randomUUID()));
    }

    @Override
    protected Optional<String> buildTransaction() {
        if(totalAmount == null || currencyCode == null) {
            return Optional.empty();
        }
        return Optional.of(totalAmount + currencyCode);
    }


}
