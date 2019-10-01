package task.money.transfer.utils;


import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum ApplicationProperties {
	INSTANCE;

	private final Properties properties;

	ApplicationProperties() {
		properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	public String accountHolders() {
		return properties.getProperty("accountholders");
	}
	
	public String accountHoldersById() {
		return properties.getProperty("accountholdersbyid");
	}
	
	public String accountholdersAccountsById() {
		return properties.getProperty("accountholdersaccountsbyid");
	}
	
	public String accounts() {
		return properties.getProperty("accounts");
	}
	
	public String accountsByAccountNumber() {
		return properties.getProperty("accountsbyaccountNumber");
	}
	
	public String accountsByAccountNumberCreateTransfers() {
		return properties.getProperty("accountsbyaccountnumbercreatetransfers");
	}
	
	public String transfers() {
		return properties.getProperty("transfers");
	}
	
	public String transfersbyid() {
		return properties.getProperty("transfersbyid");
	}
	
	
	
	
}
