package task.money.transfer.service.impl;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import task.money.transfer.domain.Account;
import task.money.transfer.domain.Money;
import task.money.transfer.domain.Transaction;
import task.money.transfer.service.ITransferService;

/**
 * @author Danish Ahmad
 */
public class TransferService implements ITransferService {
    private Map<String, Transaction> storage;
    private static TransferService instance;
    
    private TransferService() {
        storage = new ConcurrentHashMap<>();
    }

    public static ITransferService getInstance() {

		if (instance == null) {
			synchronized (TransferService.class) {
				if (instance == null)
					instance = new TransferService();
			}
		}
		return instance;
	}

    @Override
    public Transaction getTransfer(String id) {
        return storage.get(id);
    }

    @Override
    public Transaction intiateFundTransfer(Account from, Account to, Money amount) {
        Transaction transfer = Transaction.newBuilder().accountFrom(from).accountTo(to).amount(amount).build();
        storage.put(transfer.getId(), transfer);
        return transfer;
    }
    
	@Override
	public Collection<Transaction> getTransfers() {
		return storage.values();
	}


    @Override
    public void deleteTransfer(String id) {
        storage.remove(id);
    }


}
