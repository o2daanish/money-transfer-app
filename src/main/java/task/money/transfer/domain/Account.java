package task.money.transfer.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import task.money.transfer.exception.InvalidAmountException;

/**
 * @author Danish Ahmad
 * 
 * Account Details
 * 
 */
public class Account {

    private String accountType;
    private String accountNumber;
    private Money currentAvailableAmount;
    private List<Transaction> transactions;
    private String currency;

    private static AtomicInteger accountNumberSeries = new AtomicInteger(1000000);
      
    public Account() {}

    public Account(String name) throws InvalidAmountException {
        this(name, "INR");
    }

    public Account(String name, String currency) throws InvalidAmountException {
        this(name, 10000, currency);

    }
    public Account(String accountType, double amount, String currency) throws InvalidAmountException {
        if (amount < 0)
            throw new InvalidAmountException("Amount Should not be in less than 0");
        if (accountType.isEmpty()) {
            throw new IllegalArgumentException("Account Type is not Valid");
        }
        this.accountNumber = generateAccountNumber();
        this.accountType = accountType;
        this.currency = currency;
        this.currentAvailableAmount = new Money(amount, currency);
        this.transactions = new ArrayList<>();
    }


    public String getAccountNumber() {
        return accountNumber;
    }


    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCurrency() {
        return currency;
    }

    private String generateAccountNumber() {
        return String.valueOf(accountNumberSeries.incrementAndGet());
    }

    public Money getFundsAvailable() {
        return currentAvailableAmount;
    }

    public void setFundsAvailable(Money fundsAvailable) {
        if (!currency.equals(fundsAvailable.getCurrency())) {
            throw new IllegalArgumentException("Invalid Currency");
        }
        this.currentAvailableAmount = fundsAvailable;
    }

    public void setFundsAvailable(double fundsAvailable) throws InvalidAmountException {
        this.currentAvailableAmount.setAmount(fundsAvailable);
    }

    public void setCurrency(String currency) throws InvalidAmountException {
        this.currency = currency;
        this.setFundsAvailable(Money.convert(currentAvailableAmount, currency));
    }

    public void withdrawMoney(Money amount) throws InvalidAmountException {
    	currentAvailableAmount = currentAvailableAmount.sub(amount);
    }

    public void addMoney(Money amount) throws InvalidAmountException {
    	currentAvailableAmount = currentAvailableAmount.add(amount);
    }

}
