package Stockmarket.types;

public enum Currency {
	USD, LKR;
	
	/**
	 * 
	 * @param currency
	 * @return
	 */
	public Currency toCurrencyEnum(String currency) {
		
		for (Currency currEnum :  Currency.values()) {
			if(currEnum.equals(currency)) {
				return currEnum;
			}
		}
		
		return null;
	}
}
