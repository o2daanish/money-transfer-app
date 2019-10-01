package task.money.transfer.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import task.money.transfer.domain.AccountHolder;
import task.money.transfer.domain.Contact;
import task.money.transfer.exception.AccountHolderException;
import task.money.transfer.exception.InvalidAmountException;
import task.money.transfer.service.IAccountHolderService;

public class AccountHolderService implements IAccountHolderService {

	private Map<String, AccountHolder> accountHolder;
	private static AccountHolderService instance;

	public static AccountHolderService getInstance() {

		if (instance == null) {
			synchronized (AccountHolderService.class) {
				if (instance == null)
					instance = new AccountHolderService();
			}
		}
		return instance;
	}

	private AccountHolderService() {
		accountHolder = new HashMap<>();
	}

	@Override
	public AccountHolder addAccountHolder(AccountHolder accountholder)
			throws InvalidAmountException, AccountHolderException {
		accountHolder.put(accountholder.CustomerId(), accountholder);
		return accountholder;
	}

	@Override
	public Collection<AccountHolder> getAccountHolders() {
		return accountHolder.values();
	}

	@Override
	public AccountHolder getAccountHolder(String id) {
		return accountHolder.get(id);
	}

	@Override
	public AccountHolder updateAccountHolder(AccountHolder forEdit) throws AccountHolderException {

		try {
			if (forEdit.getCustomerId() == null)
				throw new AccountHolderException("ID cannot be blank");

			AccountHolder toEdit = accountHolder.get(forEdit.getCustomerId());

			if (toEdit == null)
				throw new AccountHolderException("Account Holder not found");

			if (forEdit.getContact().getEmail() != null)
				toEdit.setContact(new Contact(forEdit.getContact().getEmail(), forEdit.getContact().getMobileNo()));

			if (forEdit.getFirstName() != null)
				toEdit.setFirstName(forEdit.getFirstName());

			if (forEdit.getLastName() != null)
				toEdit.setLastName(forEdit.getLastName());

			if (forEdit.getCustomerId() != null)
				toEdit.setCustomerId(forEdit.getCustomerId());

			return toEdit;
		} catch (Exception ex) {
			throw new AccountHolderException(ex.getMessage());
		}
	}

	@Override
	public void deleteAccountHolder(String id) {
		accountHolder.remove(id);
	}

	@Override
	public boolean accountHolderExist(String id) {
		return accountHolder.containsKey(id);
	}

}