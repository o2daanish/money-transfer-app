package task.money.transfer.service.impl;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import task.money.transfer.domain.Account;
import task.money.transfer.exception.InvalidAmountException;
import task.money.transfer.service.IAccountService;

/**
 * @author Danish Ahmad
 */
public class AccountService implements IAccountService {
	private Map<String, Account> storage;
	private static AccountService instance;

	public static IAccountService getInstance() {
		if (instance == null) {
			synchronized (AccountService.class) {
				if (instance == null) 
					instance = new AccountService();
			}
		}
		return instance;
	}

	private AccountService() {
		storage = new ConcurrentHashMap<>();
	}

	@Override
	public Account addAccount(Account account) {
		storage.put(account.getAccountNumber(), account);
		return account;
	}

	@Override
	public void addAll(Iterable<Account> accounts) {
		accounts.forEach(account -> storage.put(account.getAccountNumber(), account));
	}

	@Override
	public Collection<Account> getAccounts() {
		return storage.values();
	}

	@Override
	public Account getAccount(String accountNumber) {
		return storage.get(accountNumber);
	}

	@Override
	public Account updateAccount(Account accountToUpdate) throws InvalidAmountException {
		if (accountToUpdate.getAccountNumber() == null)
			throw new IllegalArgumentException("AccountNumber cannot be blank");
		Account toEdit = storage.get(accountToUpdate.getAccountNumber());
		if (toEdit == null)
			throw new IllegalArgumentException("Account not found");
		if (accountToUpdate.getAccountType() != null) 
			toEdit.setAccountType(accountToUpdate.getAccountType());
		
		if (accountToUpdate.getCurrency() != null) 
			toEdit.setCurrency(accountToUpdate.getCurrency());
		
		if (accountToUpdate.getFundsAvailable() != null) 
			toEdit.setFundsAvailable(accountToUpdate.getFundsAvailable());
		
		return toEdit;
	}

	@Override
	public void deleteAccount(String accountNumber) {
		storage.remove(accountNumber);
	}

	@Override
	public boolean accountExist(String id) {
		return storage.containsKey(id);
	}

}
