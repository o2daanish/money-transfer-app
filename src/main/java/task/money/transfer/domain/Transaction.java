package task.money.transfer.domain;

import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import task.money.transfer.exception.InvalidAmountException;

/**
 * @author Danish Ahmad
 */
public class Transaction {
    private static AtomicInteger idSequence = new AtomicInteger();
    private String transactionId;
    private Money amount;
    private Account sender;
    private Account receiver;
    private String remarks;
    private TransferStatus transferStatus;
    private ZonedDateTime time;
    
    private Transaction(TransferBuilder builder) {
        transactionId=String.valueOf(idSequence.incrementAndGet());
        amount=builder.amount;
        sender=builder.accountFrom;
        receiver=builder.accountTo;
        remarks=builder.remarks;
        transferStatus=TransferStatus.CREATED;
        time=ZonedDateTime.now();
    }
 
    public static TransferBuilder newBuilder() {
        return new TransferBuilder();
    }
 
    public static TransferBuilder newBuilder(Transaction copy) {
    	TransferBuilder builder = new TransferBuilder();
    	builder.transactionId=copy.transactionId;
    	builder.amount=copy.amount;
    	builder.accountFrom=copy.sender;
    	builder.accountTo=copy.receiver;
    	builder.remarks=copy.remarks;
    	builder.transferStatus=TransferStatus.CREATED;;
    	builder.time=ZonedDateTime.now();
    	
        return builder;
    }
    

	public String getTransactionId() {
		return transactionId;
	}

	public Account getAccountFrom() {
		return sender;
	}

	public Account getAccountTo() {
		return receiver;
	}

	public String getRemarks() {
		return remarks;
	}

	public TransferStatus getTransferStatus() {
		return transferStatus;
	}

	public ZonedDateTime getTime() {
		return time;
	}

	public void proceed() throws InvalidAmountException {
        Money available = sender.getFundsAvailable();
        if (amount.isGreaterThan(available)) {
            this.transferStatus = TransferStatus.CANCELLED;
            return;
        }
        sender.withdrawMoney(amount);
        receiver.addMoney(amount);
        this.transferStatus = TransferStatus.PROCEEDED;
    }

    public Money getAmount() {
        return amount;
    }

    public String getNotes() {
        return remarks;
    }
    
    public TransferStatus getStatus() {
        return transferStatus;
    }


    public ZonedDateTime getCreatedAt() {
        return time;
    }

    public String getId() {
        return transactionId;
    }

    public static final class TransferBuilder{
        private String transactionId;
        private Money amount;
        private Account accountFrom;
        private Account accountTo;
        private String remarks;
        private TransferStatus transferStatus;
        private ZonedDateTime time;
        
        
        private TransferBuilder() {}
        
        public TransferBuilder id(int id) {
            this.transactionId = String.valueOf(id); ;
            return this;
        }
 
        public TransferBuilder amount(Money amount) {
            this.amount = amount;
            return this;
        }
 
        public TransferBuilder accountFrom(Account accountFrom) {
            this.accountFrom = accountFrom;
            return this;
        }
        
        public TransferBuilder accountTo(Account accountTo) {
            this.accountTo = accountTo;
            return this;
        }
        
        public TransferBuilder remarks(String remarks) {
            this.remarks = remarks;
            return this;
        }
        
        public TransferBuilder remarks(TransferStatus status) {
            this.transferStatus = status;
            return this;
        }
        
        public TransferBuilder time(ZonedDateTime time) {
            this.time = time;
            return this;
        }
        
        public Transaction build() {
            return new Transaction(this);
        }
    		
    }
}
