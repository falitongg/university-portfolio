package cz.cvut.fel.omo.homeworks.refactor.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.omo.homeworks.common.client.ModernPaymentClient;

import java.util.Optional;
import java.util.UUID;

public class ModernTransactionSystem extends AbstractTransactionSystem {

    private ObjectMapper objectMapper;
    private static final String surl = "Success!";
    private static final String furl = "Failure!";
    private final ModernPaymentClient modernPaymentClient = new ModernPaymentClient();

    public ModernTransactionSystem(Long totalAmount, String currencyCode, String surl, String furl) {
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
