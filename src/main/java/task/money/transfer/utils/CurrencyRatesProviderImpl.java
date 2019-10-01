package task.money.transfer.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CurrencyRatesProviderImpl implements ICurrencyRatesProvider {

	private static class CacheKey {
		String curFrom;
		String curTo;

		public CacheKey(String curFrom, String curTo) {
			super();
			this.curFrom = curFrom;
			this.curTo = curTo;
		}

		public String getCurFrom() {
			return curFrom;
		}

		public String getCurTo() {
			return curTo;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((curFrom == null) ? 0 : curFrom.hashCode());
			result = prime * result + ((curTo == null) ? 0 : curTo.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CacheKey other = (CacheKey) obj;
			if (curFrom == null) {
				if (other.curFrom != null)
					return false;
			} else if (!curFrom.equals(other.curFrom))
				return false;
			if (curTo == null) {
				if (other.curTo != null)
					return false;
			} else if (!curTo.equals(other.curTo))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "CacheKey [curFrom=" + curFrom + ", curTo=" + curTo + "]";
		}

	}

	private final Map<CacheKey, BigDecimal> currentyRates;

	{
		currentyRates = new HashMap<>();
		currentyRates.put(new CacheKey("USD", "INR"), new BigDecimal("70.83"));
		currentyRates.put(new CacheKey("INR", "USD"), new BigDecimal("0.014"));
		currentyRates.put(new CacheKey("INR", "RUB"), new BigDecimal("0.92"));
		currentyRates.put(new CacheKey("RUB", "INR"), new BigDecimal("1.92"));
		currentyRates.put(new CacheKey("GBP", "INR"), new BigDecimal("87.02"));
		currentyRates.put(new CacheKey("INR", "GBP"), new BigDecimal("0.011"));
		currentyRates.put(new CacheKey("EUR", "USD"), new BigDecimal("1.09"));
		currentyRates.put(new CacheKey("USD", "EUR"), new BigDecimal("0.92"));
		currentyRates.put(new CacheKey("RUB", "USD"), new BigDecimal("56.12"));
		currentyRates.put(new CacheKey("EUR", "INR"), new BigDecimal("77.32"));
	}

	@Override
	public BigDecimal getCurrencyRate(Currency from, Currency to) {
		if (from.equals(to)) {
            return BigDecimal.ONE;
        }
        BigDecimal rate = currentyRates.get(new CacheKey(from.getCode(), to.getCode()));
        if (rate == null) {
            throw new IllegalArgumentException("No conversion rates found for (" + from.getCode() + "," + to.getCode() + ")");
        }
        return rate;
	}
}
