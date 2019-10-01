package task.money.transfer.service;

import java.util.Collection;

import task.money.transfer.domain.Account;
import task.money.transfer.domain.Money;
import task.money.transfer.domain.Transaction;


public interface ITransferService {
    Transaction getTransfer(String id);

    Transaction intiateFundTransfer(Account sender, Account receiver, Money amount);

    void deleteTransfer(String id);

	Collection<Transaction> getTransfers();
}
