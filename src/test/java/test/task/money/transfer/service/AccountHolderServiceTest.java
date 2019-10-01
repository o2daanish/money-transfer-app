package test.task.money.transfer.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import task.money.transfer.domain.Account;
import task.money.transfer.domain.AccountHolder;
import task.money.transfer.domain.Contact;
import task.money.transfer.exception.AccountHolderException;
import task.money.transfer.exception.InvalidAmountException;
import task.money.transfer.service.IAccountHolderService;
import task.money.transfer.service.impl.AccountHolderService;



public class AccountHolderServiceTest {

	private IAccountHolderService accountHolderService = AccountHolderService.getInstance();

	@Test
	public void testAddAccountHolder() throws InvalidAmountException, AccountHolderException {
		List<Account> listOfAccounts = new ArrayList<>();
		listOfAccounts.add(new Account("Savings Account with curreny as GBP", "GBP"));

		AccountHolder accountHolder = new AccountHolder("Danish", "Ahmad",
				new Contact("Danish.Ahmad@gmail.com", "1111111222"), listOfAccounts);
		accountHolder.CustomerId();

		accountHolder = accountHolderService.addAccountHolder(accountHolder);
		Assert.assertTrue(accountHolderService.accountHolderExist(accountHolder.getCustomerId()));
	}

	@Test
	public void testGetAccountHolders() throws InvalidAmountException, AccountHolderException {
		Account account1 = new Account("My first account for rubles", 1000, "RUB");
		Account account2 = new Account("EUR savings for Europe trips", 500, "EUR");

		List<Account> accounts = new ArrayList<>();
		accounts.add(account1);
		accounts.add(account2);

		AccountHolder accountHolder1 = new AccountHolder("Danish", "Ahmad",
				new Contact("Danish.Ahmad@gmail.com", "1111111222"), accounts);
		accountHolder1.CustomerId();
		AccountHolder accountHolder2 = new AccountHolder("Rahul", "Roy", new Contact("Rahul.Roy@gmail.com", "1112222"),
				accounts);
		accountHolder2.CustomerId();
		AccountHolder accountHolder3 = new AccountHolder("Deepak", "Nair",
				new Contact("Deepak.Nair@gmail.com", "66778889"), accounts);
		accountHolder3.CustomerId();

		accountHolder1 = accountHolderService.addAccountHolder(accountHolder1);
		accountHolder2 = accountHolderService.addAccountHolder(accountHolder2);
		accountHolder3 = accountHolderService.addAccountHolder(accountHolder3);

		for (AccountHolder user : accountHolderService.getAccountHolders())
			System.out.println(user.getCustomerId());

		Assert.assertEquals(3, accountHolderService.getAccountHolders().size());
		;
	}

	@Test
	public void testGetAccountHolder() throws InvalidAmountException, AccountHolderException {
		Account account = new Account("My first account for rubles", 1000, "RUB");
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(account);

		AccountHolder accountHolder = new AccountHolder("Danish", "Ahmad",
				new Contact("Danish.Ahmad@gmail.com", "1111111222"), accounts);
		accountHolder.CustomerId();

		accountHolder = accountHolderService.addAccountHolder(accountHolder);
		String expectedValue = account.getAccountNumber();
		String actualValue = null;
		for (Account val : accountHolder.getAccounts())
			actualValue = val.getAccountNumber();
		Assert.assertEquals(expectedValue, actualValue);
	}

	@Test
	public void testUpdateAccountHolder() throws AccountHolderException, InvalidAmountException {
		Account account = new Account("My first account for rubles", 1000, "RUB");
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(account);
		AccountHolder accountHolder = new AccountHolder("AAAAAA", "Ahmad", new Contact("ASDFF@gmail.com", "1111111222"),
				accounts);
		accountHolder.CustomerId();

		accountHolder = accountHolderService.addAccountHolder(accountHolder);
		accountHolder.setFirstName("Danish");
		accountHolder.setContact(new Contact("Danish.Ahmad@gmail.com", "1234567789"));

		accountHolder = accountHolderService.updateAccountHolder(accountHolder);

		Assert.assertEquals("Danish", accountHolder.getFirstName());
		Assert.assertEquals("Danish.Ahmad@gmail.com", accountHolder.getContact().getEmail());
	}

	@Test
	public void testDeleteAccountHolder() throws InvalidAmountException, AccountHolderException {
		Account account = new Account("My first account for rubles", 1000, "RUB");
		List<Account> accounts = new ArrayList<>();
		accounts.add(account);
		AccountHolder accountHolder = new AccountHolder("Danish", "Ahmad",
				new Contact("Danish.Ahmad@gmail.com", "1111111222"), accounts);

		accountHolder = accountHolderService.addAccountHolder(accountHolder);
		accountHolderService.deleteAccountHolder(account.getAccountNumber());
		Assert.assertNull(accountHolderService.getAccountHolder(account.getAccountNumber()));
	}

	@Test
	public void testAccountHolderExist() throws InvalidAmountException, AccountHolderException {
		Account account = new Account("My first account for rubles", 1000, "RUB");
		List<Account> accounts = new ArrayList<>();
		accounts.add(account);
		AccountHolder accountHolder = new AccountHolder("Danish", "Ahmad",
				new Contact("Danish.Ahmad@gmail.com", "1111111222"), accounts);

		accountHolder = accountHolderService.addAccountHolder(accountHolder);

		Assert.assertTrue(accountHolderService.accountHolderExist(accountHolder.getCustomerId()));
	}

}
