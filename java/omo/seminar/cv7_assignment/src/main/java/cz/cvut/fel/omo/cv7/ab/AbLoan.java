package cz.cvut.fel.omo.cv7.ab;

import cz.cvut.fel.omo.cv7.Loan;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;

public class AbLoan implements Loan {
    private MonetaryAmount balance;
    private final double interestRate;
    private final int repaymentPeriod;

    public AbLoan(MonetaryAmount amount, double interestRate, int months) {
        this.balance = amount;
        this.interestRate = interestRate;
        this.repaymentPeriod = months;
    }

    @Override
    public MonetaryAmount getBalance() {
        return balance;
    }

    @Override
    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public MonetaryAmount getMonthlyPayment() {
        MonetaryAmount base = balance.divide(repaymentPeriod);
        MonetaryAmount interest = balance.multiply(interestRate).divide(12);
        MonetaryAmount fee = Money.of(10, "EUR");

        return base.add(interest).add(fee);
    }


    @Override
    public String toString() {
        return String.format(
                "Ab Loan - Balance: %s, InterestRate: %f, MonthlyPayment: %s", getBalance(), getInterestRate(), getMonthlyPayment()
        );
    }
}
