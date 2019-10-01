package task.money.transfer.utils;

import java.math.BigDecimal;

public class CurrencyConvertor {

	private static final CurrencyRatesProviderImpl ratesProvider = new CurrencyRatesProviderImpl();

	public static double getConversionRate(String fromCurrencyCode, String toCurrencyCode) {
		if (fromCurrencyCode.equals(toCurrencyCode)) {
			return 1;
		}

		Currency fromCurrency = Enum.valueOf(Currency.class, fromCurrencyCode);
		Currency toCurrency = Enum.valueOf(Currency.class, toCurrencyCode);

		if ((fromCurrencyCode != null && !fromCurrencyCode.isEmpty())
				&& (toCurrencyCode != null && !toCurrencyCode.isEmpty())) {

			BigDecimal rate = ratesProvider.getCurrencyRate(fromCurrency, toCurrency);

			double conversionRate = rate.doubleValue();

			return conversionRate;
		}
		return 0.0;
	}

	public enum Currencies {

		AUD, BGN, BRL, CAD, CHF, CNY, CZK, DKK, GBP, HKD, HRK, EUR, HUF, IDR, ILS, INR, ISK, JPY, KRW, MXN, MYR, NOK,
		NZD, PLN, RON, RUB, SEK, SGD, THB, TRY, USD;

		public static boolean contains(String test) {

			for (Currencies c : Currencies.values()) {
				if (c.name().equals(test)) {
					return true;
				}
			}

			return false;
		}
	}

}