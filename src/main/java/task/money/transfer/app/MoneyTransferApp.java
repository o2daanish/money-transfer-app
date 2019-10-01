package task.money.transfer.app;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import task.money.transfer.api.defination.AccountAPI;
import task.money.transfer.api.defination.AccountHolderAPI;
import task.money.transfer.api.defination.TransferAPI;
import task.money.transfer.domain.Account;
import task.money.transfer.domain.AccountHolder;
import task.money.transfer.domain.Contact;
import task.money.transfer.exception.AccountHolderException;
import task.money.transfer.exception.InvalidAmountException;
import task.money.transfer.service.IAccountHolderService;
import task.money.transfer.service.IAccountService;
import task.money.transfer.service.impl.AccountHolderService;
import task.money.transfer.service.impl.AccountService;

/**
 * 
 * @author Danish Ahmad 
 * Money-Transfer App
 *
 */
public class MoneyTransferApp {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(MoneyTransferApp.class);

	public static void main(String[] args) throws InvalidAmountException, AccountHolderException {

		AccountHolderAPI.apiInit();
		AccountAPI.apiInit();
		TransferAPI.apiInit();
		initData();
	}

	public static void initData() throws InvalidAmountException, AccountHolderException {
		IAccountHolderService accountHolderService = AccountHolderService.getInstance();
		IAccountService accountService = AccountService.getInstance();
		Account account1 = new Account("My first account for INR", 100000, "INR");
		Account account2 = new Account("EUR savings for Europe trips", 1000, "EUR");

		List<Account> accounts = new ArrayList<>();
		accounts.add(account1);
		accounts.add(account2);

		accountService.addAll(accounts);
		
		AccountHolder accountHolder1=new AccountHolder("Danish", "Ahmad", new Contact("Danish.Ahmad@gmail.com", "1111111222"),accounts);
		AccountHolder accountHolder2=new AccountHolder("Rahul", "Roy", new Contact("Rahul.Roy@gmail.com", "1112222"),accounts);
		AccountHolder accountHolder3=new AccountHolder("Deepak", "Nair", new Contact("Deepak.Nair@gmail.com", "66778889"),accounts);


		accountHolderService.addAccountHolder(accountHolder1);
		accountHolderService.addAccountHolder(accountHolder2);
		accountHolderService.addAccountHolder(accountHolder3);

		logger.info("App Started");
	}
}
