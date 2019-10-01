package task.money.transfer.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import task.money.transfer.exception.InvalidAmountException;
import task.money.transfer.service.impl.AccountService;

/**
 * 
 * @author Danish Ahmad
 *
 */
public class AccountHolder {
	private static AtomicInteger id = new AtomicInteger(10000);
	private String customerId;
	private String firstName;
	private String lastName;
	private Address address;
	private Contact contact;
	private List<Account> listOfAccounts;

	public AccountHolder(String firstName, String lastName, Contact contact, List<Account> listOfAccounts)
			throws InvalidAmountException {
		this(firstName, lastName, contact);
		this.listOfAccounts = listOfAccounts;
		this.customerId=CustomerId();
	}

	public AccountHolder(String firstName, String lastName, Contact contact) throws InvalidAmountException {
		this.firstName = firstName;
		this.lastName = lastName;
		this.contact = contact;
		Account account = new Account("Savings account", 1000, "INR");
		AccountService.getInstance().addAccount(account);
		this.listOfAccounts = new ArrayList<>();
		listOfAccounts.add(account);
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Account> getListOfAccounts() {
		return listOfAccounts;
	}

	public void setListOfAccounts(List<Account> listOfAccounts) {
		this.listOfAccounts = listOfAccounts;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Contact getContact() {
		return contact;
	}

	public void setEmail(Contact contact) {
		this.contact = contact;
	}

	public List<Account> getAccounts() {
		return listOfAccounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.listOfAccounts = accounts;
	}

	public void addAccount(Account account) {
		this.listOfAccounts.add(account);
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String CustomerId() {
		return this.customerId = String.valueOf(id.incrementAndGet())+firstName.substring(1).trim();
	}
}