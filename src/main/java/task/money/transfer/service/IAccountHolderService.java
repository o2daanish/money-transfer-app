package task.money.transfer.service;

import java.util.Collection;

import task.money.transfer.domain.AccountHolder;
import task.money.transfer.exception.AccountHolderException;
import task.money.transfer.exception.InvalidAmountException;

public interface IAccountHolderService {

	public AccountHolder addAccountHolder(AccountHolder accountHolder)
			throws InvalidAmountException, AccountHolderException;

	public Collection<AccountHolder> getAccountHolders();

	public AccountHolder getAccountHolder(String id);

	public AccountHolder updateAccountHolder(AccountHolder accountHolder) throws AccountHolderException;

	public void deleteAccountHolder(String id);

	public boolean accountHolderExist(String id);
}