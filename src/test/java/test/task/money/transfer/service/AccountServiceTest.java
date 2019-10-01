package test.task.money.transfer.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import task.money.transfer.domain.Account;
import task.money.transfer.exception.InvalidAmountException;
import task.money.transfer.service.IAccountService;
import task.money.transfer.service.impl.AccountService;

public class AccountServiceTest {
	private IAccountService accountService = AccountService.getInstance();

	@Test
	public void testAddAccount_WithValidData() throws InvalidAmountException {
		Account account = accountService.addAccount(new Account("Savings Account with curreny as GBP", "GBP"));
		Assert.assertTrue(accountService.accountExist(account.getAccountNumber()));
	}
	
	@After
	public void cleanup(){
		accountService.getAccounts().clear();
	}

	@Test(expected = InvalidAmountException.class)
	public void testAccountWithNegativeFund() throws InvalidAmountException {
		Account account = accountService.addAccount(new Account("Dummy Account", "RUB"));
		account.setFundsAvailable(-100000);
		accountService.updateAccount(account);
	}
	
	@Test
	public void testAddMultipleAccounts_WithValidData() throws InvalidAmountException {
		Account account1 = new Account("Savings Account with curreny as GBP", "GBP");
		Account account2 = new Account("Savings Account with curreny as INR", "INR");
		Account account3 = new Account("Savings Account with curreny as USD", "USD");
		List<Account> listOfAccounts = new ArrayList<>();
		listOfAccounts.add(account1);
		listOfAccounts.add(account2);
		listOfAccounts.add(account3);
		Assert.assertEquals(0, accountService.getAccounts().size());
		accountService.addAll(listOfAccounts);
		Assert.assertEquals(3, accountService.getAccounts().size());
	}

	@Test
	public void testGetAccounts() throws InvalidAmountException {
		Account account1 = new Account("Savings Account with curreny as GBP", "GBP");
		Account account2 = new Account("Savings Account with curreny as INR", "INR");
		Account account3 = new Account("Savings Account with curreny as USD", "USD");
		List<Account> listOfAccounts = new ArrayList<>();
		listOfAccounts.add(account1);
		listOfAccounts.add(account2);
		listOfAccounts.add(account3);
		accountService.addAll(listOfAccounts);
		Collection<Account> accounts = accountService.getAccounts();
		for (Account account : accountService.getAccounts())
			Assert.assertTrue(accounts.contains(account));
	}

	@Test
	public void testGetAccount() throws InvalidAmountException {
		Account account = new Account("Savings Account with curreny as GBP", "GBP");
		accountService.addAccount(account);
		Account accountFromService = accountService.getAccount(account.getAccountNumber());
		Assert.assertEquals(account.getAccountNumber(), accountFromService.getAccountNumber());
	}

	@Test
	public void testUpdateAccount() throws InvalidAmountException {
		Account account = accountService.addAccount(new Account("Dummy Account", "RUB"));
		account.setAccountType("Current");
		account.setCurrency("USD");
		account.setFundsAvailable(100000);
		accountService.updateAccount(account);
		account = accountService.getAccount(account.getAccountNumber());
		Assert.assertEquals("Current", account.getAccountType());
		Assert.assertEquals("USD", account.getCurrency());
	}

	@Test
	public void testDeleteAccount() throws InvalidAmountException {
		Account account = accountService.addAccount(new Account("Dummy account", "RUB"));
		accountService.deleteAccount(account.getAccountNumber());
		Assert.assertNull(accountService.getAccount(account.getAccountNumber()));
	}

	@Test
	public void testAccountExist() throws InvalidAmountException {
		Account account = accountService.addAccount(new Account("Dummy account", "RUB"));
		Assert.assertTrue(accountService.accountExist(account.getAccountNumber()));
	}

	@Test
	public void testAccountNotExists() throws InvalidAmountException {
		Account account = accountService.addAccount(new Account("Dummy account", "RUB"));
		accountService.deleteAccount(account.getAccountNumber());
		Assert.assertFalse(accountService.accountExist(account.getAccountNumber()));
	}

}
