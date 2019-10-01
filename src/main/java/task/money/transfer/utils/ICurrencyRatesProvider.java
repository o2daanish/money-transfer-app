package task.money.transfer.utils;

import java.math.BigDecimal;

public interface ICurrencyRatesProvider {
	
	BigDecimal getCurrencyRate(Currency from, Currency to);

}
