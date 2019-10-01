package task.money.transfer.service;

import java.util.Collection;

import task.money.transfer.domain.Account;
import task.money.transfer.exception.InvalidAmountException;


public interface IAccountService {
    public Account addAccount(Account account);

    public void addAll(Iterable<Account> accounts);

    public Collection<Account> getAccounts();

    public Account getAccount(String id);

    public Account updateAccount(Account account)
            throws  InvalidAmountException;

    public void deleteAccount(String id);

    public boolean accountExist(String id);
}
