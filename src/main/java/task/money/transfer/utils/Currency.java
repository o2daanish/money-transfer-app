package task.money.transfer.utils;

import task.money.transfer.utils.CurrencyConvertor.Currencies;

public enum Currency {
    RUB("RUB", 2),
    EUR("EUR", 3),
    USD("USD", 2),
	INR("INR", 2),
	GBP("GBP", 3);
   
    private final String code;
    private final int minorDigits;
    
    public static boolean contains(String test) {

		for (Currencies c : Currencies.values()) {
			if (c.name().equals(test)) {
				return true;
			}
		}

		return false;
	}
    
	private Currency(String code, int minorDigits) {
		this.code = code;
		this.minorDigits = minorDigits;
	}
	public String getCode() {
		return code;
	}
	public int getMinorDigits() {
		return minorDigits;
	}
    
}