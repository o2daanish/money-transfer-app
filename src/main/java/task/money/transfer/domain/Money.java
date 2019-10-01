package task.money.transfer.domain;

import task.money.transfer.exception.InvalidAmountException;
import task.money.transfer.utils.CurrencyConvertor;

import java.util.Objects;

import javax.money.MonetaryAmount;

/**
 * @author Danish Ahmad
 */
public class Money {

	private double amount;
	private String currency;

	public Money(double amount, String currency) throws InvalidAmountException {
		if (amount < 0)
			throw new InvalidAmountException("Negative amount of money");
		this.amount = amount;
		this.currency = currency;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) throws InvalidAmountException {
		if (amount < 0)
			throw new InvalidAmountException("Negative amount of money");
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	MonetaryAmount toMonetaryAmount() {
		return org.javamoney.moneta.Money.of(amount, currency);
	}

	public static Money fromMonetaryAmount(MonetaryAmount monetaryAmount) throws InvalidAmountException {
		return new Money(monetaryAmount.getNumber().doubleValueExact(), monetaryAmount.getCurrency().getCurrencyCode());
	}

	public static Money convert(Money money, String newCurrency) throws InvalidAmountException {
		if (newCurrency.equals(money.getCurrency()))
			return money;
		double conversionRate = CurrencyConvertor.getConversionRate(money.getCurrency(), newCurrency);
		return new Money(money.getAmount() * conversionRate, newCurrency);
	}

	public Money add(Money money) throws InvalidAmountException {
		double conversionRate = CurrencyConvertor.getConversionRate(money.getCurrency(), this.currency);
		return new Money(amount + money.getAmount() * conversionRate, currency);
	}

	public Money sub(Money money) throws InvalidAmountException {
		double conversionRate = CurrencyConvertor.getConversionRate(money.getCurrency(), this.currency);
		return new Money(amount - money.getAmount() * conversionRate, currency);
	}

	public boolean isGreaterThan(Money money) throws InvalidAmountException {
		return amount > convert(money, currency).getAmount();
	}

	public boolean isLesserThan(Money money) throws InvalidAmountException {
		return amount < convert(money, currency).getAmount();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Money that = (Money) o;
		return Double.compare(that.amount, amount) == 0 && Objects.equals(currency, that.currency);
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, currency);
	}

	@Override
	public String toString() {
		return "Money{" + "amount=" + amount + ", currency='" + currency + '\'' + '}';
	}
}
