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

	private Map<String, AccountHolder> userEntity;
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
		userEntity = new HashMap<>();
	}

	@Override
	public AccountHolder addAccountHolder(AccountHolder accountholder)
			throws InvalidAmountException, AccountHolderException {
		// AccountHolder
		// ah=AccountHolder.newBuilder().id(String.valueOf(id.incrementAndGet())).build();
		// accountholder.builder().id(String.valueOf(id.incrementAndGet()));
		// idSequence.incrementAndGet())+
		
		userEntity.put(accountholder. CustomerId(), accountholder);
		return accountholder;
	}

	/*
	 * @Override public Collection<AccountHolder> getUsers() {
	 * System.out.println(userEntity.entrySet()); return userEntity.values(); }
	 */
	@Override
	public Collection<AccountHolder> getAccountHolders() {
		return userEntity.values();
	}

	@Override
	public AccountHolder getAccountHolder(String id) {
		return userEntity.get(id);
	}

	@Override
	public AccountHolder updateAccountHolder(AccountHolder forEdit) throws AccountHolderException {
		
		try {
            if (forEdit.getCustomerId() == null)
                throw new AccountHolderException("ID cannot be blank");

            AccountHolder toEdit = userEntity.get(forEdit.getCustomerId());

            if (toEdit == null)
                throw new AccountHolderException("Account Holder not found");

            if (forEdit.getContact().getEmail() != null) {
                toEdit.setContact(new Contact(forEdit.getContact().getEmail(),forEdit.getContact().getMobileNo() ));
            }
            if (forEdit.getFirstName() != null) {
                toEdit.setFirstName(forEdit.getFirstName());
            }
            if (forEdit.getLastName() != null) {
                toEdit.setLastName(forEdit.getLastName());
            }
            if (forEdit.getCustomerId() != null) {
                toEdit.setCustomerId(forEdit.getCustomerId());
            }

            return toEdit;
        } catch (Exception ex) {
            throw new AccountHolderException(ex.getMessage());
        }
		
		
		
		/*
		 * 
		 * try {
		 * 
		 * if (forEdit.getCustomerId() == null) throw new
		 * AccountHolderException("ID Cannot Be Blank");
		 * 
		 * 
		 * AccountHolder toEdit = userEntity.get(forEdit.getCustomerId());
		 * 
		 * if (toEdit == null) throw new
		 * AccountHolderException("Account Holder not found");
		 * 
		 * String customerId=forEdit.getCustomerId(); String
		 * firstName=forEdit.getFirstName(); String lastName=forEdit.getLastName();
		 * Contact contact=new
		 * Contact(forEdit.getContact().getEmail(),forEdit.getContact().getMobileNo());
		 * 
		 * userEntity.put(forEdit.getCustomerId(), AccountHolder.newBuilder().
		 * firstName(firstName).lastName(lastName).contact(contact).build());
		 * 
		 * //toEdit=AccountHolder.newBuilder().customerId(customerId).firstName(
		 * firstName).lastName(lastName).contact(contact).build();
		 * 
		 * 
		 * 
		 * if (forEdit.getContact().getEmail() != null) {
		 * 
		 * //AccountHolder.newBuilder().contact(new
		 * Contact(forEdit.getContact().getEmail(),forEdit.getContact().getMobileNo())).
		 * build();
		 * 
		 * //toEdit.builder().contact(new
		 * Contact(forEdit.getContact().getEmail())).build();
		 * toEdit.setContact(contact); } if (firstName != null) {
		 * 
		 * //AccountHolder.newBuilder().firstName(forEdit.getFirstName()).build();
		 * 
		 * //toEdit.builder().firstName(forEdit.getFirstName()).build();
		 * toEdit.setFirstName(firstName); } if (forEdit.getLastName() != null) {
		 * 
		 * //AccountHolder.newBuilder().lastName(forEdit.getLastName()).build();
		 * 
		 * toEdit.setLastName(lastName); } if (customerId != null) {
		 * 
		 * //AccountHolder.newBuilder().customerId(forEdit.getCustomerId()).build();
		 * 
		 * toEdit.setCustomerId(customerId); }
		 * 
		 * userEntity.put(customerId, toEdit);
		 * 
		 * return toEdit; } catch (Exception ex) { throw new
		 * AccountHolderException(ex.getMessage()); }
		 */}

	@Override
	public void deleteAccountHolder(String id) {
		userEntity.remove(id);
	}

	@Override
	public boolean accountHolderExist(String id) {
		return userEntity.containsKey(id);
	}

}