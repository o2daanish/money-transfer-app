package test.task.money.transfer.service;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import task.money.transfer.domain.Account;
import task.money.transfer.domain.AccountHolder;
import task.money.transfer.domain.Contact;
import task.money.transfer.domain.Transaction;
import task.money.transfer.domain.Money;
import task.money.transfer.domain.TransferStatus;
import task.money.transfer.exception.AccountHolderException;
import task.money.transfer.exception.InvalidAmountException;
import task.money.transfer.service.IAccountHolderService;
import task.money.transfer.service.IAccountService;
import task.money.transfer.service.ITransferService;
import task.money.transfer.service.impl.AccountHolderService;
import task.money.transfer.service.impl.AccountService;
import task.money.transfer.service.impl.TransferService;

public class TransferServiceTest {
	private static IAccountHolderService accountHolderService = AccountHolderService.getInstance();
	private static IAccountService accountService = AccountService.getInstance();
	private static ITransferService transferService = TransferService.getInstance();
	private static AccountHolder accountHolder1;
	private static Account account1;
	private static Account account2;
	private static Account account3;
	private static AccountHolder accountHolder2;

	@BeforeClass
	public static void init() throws InvalidAmountException, AccountHolderException {
		account1 = new Account("Savings Account with curreny as RUB", "RUB");
		account2 = new Account("Savings Account with curreny as RUB", "RUB");
		account3 = new Account("Savings Account with curreny as USD", "USD");

		accountHolder1 = accountHolderService.addAccountHolder(
				new AccountHolder("Danish", "Ahmad", new Contact("Danish.Ahmad@gmail.com", "1111111222")));
		accountHolder1.CustomerId();

		accountHolder2 = accountHolderService.addAccountHolder(
				new AccountHolder("Kumar", "Abhishek", new Contact("Kumar.Abhishek@gmail.com", "111781222")));

		accountService.addAccount(account1);
		accountHolder1.addAccount(account1);

		accountService.addAccount(account2);
		accountHolder2.addAccount(account2);

		accountService.addAccount(account3);
		accountHolder2.addAccount(account2);
	}

	@Test
	public void testTransfer() throws InvalidAmountException {
		Money account1Funds = new Money(500, "RUB");
		Money account2Funds = new Money(100, "RUB");
		Money payment = new Money(100, "RUB");
		account1.setFundsAvailable(account1Funds);
		account2.setFundsAvailable(account2Funds);
		Transaction transfer = transferService.intiateFundTransfer(account1, account2, payment);
		transfer.proceed();
		Assert.assertEquals(transfer.getStatus(), TransferStatus.PROCEEDED);
	}

	@Test
	public void transferWhenNotEnoughFunds() throws InvalidAmountException {
		double account1Funds = 500;
		double account2Funds = 100;
		double payment = 1000000;
		account1.setFundsAvailable(account1Funds);
		account2.setFundsAvailable(account2Funds);
		Transaction transfer = transferService.intiateFundTransfer(account1, account2, new Money(payment, "RUB"));
		transfer.proceed();

		Assert.assertEquals(transfer.getStatus(), TransferStatus.CANCELLED);
	}

	@Test
	public void transfersInSameTime() throws InvalidAmountException {
		Money account1Funds = new Money(500, "RUB");
		Money account2Funds = new Money(100, "RUB");
		Money payment = new Money(300, "RUB");
		account1.setFundsAvailable(account1Funds);
		account2.setFundsAvailable(account2Funds);
		Transaction transfer1 = transferService.intiateFundTransfer(account1, account2, payment);
		Transaction transfer2 = transferService.intiateFundTransfer(account1, account2, payment);
		transfer1.proceed();
		transfer2.proceed();

		Assert.assertEquals(transfer1.getStatus(), TransferStatus.PROCEEDED);
		Assert.assertEquals(transfer2.getStatus(), TransferStatus.CANCELLED);
		Assert.assertEquals(account1.getFundsAvailable(), account1Funds.sub(payment));
		Assert.assertEquals(account2.getFundsAvailable(), account2Funds.add(payment));

		account1.setFundsAvailable(account1Funds);
		account2.setFundsAvailable(account2Funds);
	}

	@AfterClass
	public static void cleanup() {
		accountHolderService.deleteAccountHolder(accountHolder1.getCustomerId());
		accountHolderService.deleteAccountHolder(accountHolder2.getCustomerId());
		for (Transaction transaction : account1.getTransactions())
			transferService.deleteTransfer(transaction.getId());

		for (Transaction t : account2.getTransactions())
			transferService.deleteTransfer(t.getId());

		accountService.deleteAccount(account1.getAccountNumber());
		accountService.deleteAccount(account2.getAccountNumber());
	}
}
