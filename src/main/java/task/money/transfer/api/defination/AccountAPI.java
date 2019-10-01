package task.money.transfer.api.defination;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;
import static spark.Spark.put;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import task.money.transfer.domain.Account;
import task.money.transfer.domain.Money;
import task.money.transfer.domain.ResponseData;
import task.money.transfer.domain.ResponseMessage;
import task.money.transfer.domain.Transaction;
import task.money.transfer.domain.AmountTransfer;
import task.money.transfer.domain.TransferStatus;
import task.money.transfer.service.IAccountService;
import task.money.transfer.service.ITransferService;
import task.money.transfer.service.impl.AccountService;
import task.money.transfer.service.impl.TransferService;
import task.money.transfer.utils.ApplicationProperties;
import task.money.transfer.utils.CurrencyConvertor.Currencies;

/**
 * @author Danish
 * 
 * Account API to perform Account associated actions
 * 
 */
public class AccountAPI {
	private static final Logger logger = LoggerFactory.getLogger(AccountAPI.class);

	private static final String ACCOUNT_NUMBER = ":accountNumber";

	public static void apiInit() {

		logger.info("****Account Api Registration Intiated*****");

		final IAccountService accountService = AccountService.getInstance();
		final ITransferService transferService = TransferService.getInstance();

		get(ApplicationProperties.INSTANCE.accounts(), (request, response) -> {
			response.type(APIConstants.RESPONSE_TYPE.getType());

			return new Gson().toJson(
					new ResponseData(ResponseMessage.SUCCESS, new Gson().toJsonTree(accountService.getAccounts())));
		});

		get(ApplicationProperties.INSTANCE.accountsByAccountNumber(), (request, response) -> {
			response.type(APIConstants.RESPONSE_TYPE.getType());

			return new Gson().toJson(new ResponseData(ResponseMessage.SUCCESS,
					new Gson().toJsonTree(accountService.getAccount(request.params(ACCOUNT_NUMBER)))));
		});
		
		get(ApplicationProperties.INSTANCE.accountsByAccountNumberCreateTransfers(), (request, response) -> {
			response.type(APIConstants.RESPONSE_TYPE.getType());
			return new Gson().toJson(new ResponseData(ResponseMessage.SUCCESS,
					new Gson().toJson(accountService.getAccount(request.params(ACCOUNT_NUMBER)).getTransactions())));
		});

		post(ApplicationProperties.INSTANCE.accounts(), (request, response) -> {
			response.type(APIConstants.RESPONSE_TYPE.getType());

			Account account = new Gson().fromJson(request.body(), Account.class);
			account = accountService.addAccount(account);

			return new Gson().toJson(new ResponseData(ResponseMessage.SUCCESS, new Gson().toJson(account)));
		});

		put(ApplicationProperties.INSTANCE.accountsByAccountNumber(), (request, response) -> {
			response.type(APIConstants.RESPONSE_TYPE.getType());

			Account update = new Gson().fromJson(request.body(), Account.class);
			Account updatedAccount = accountService.updateAccount(update);

			if (updatedAccount != null) {
				return new Gson().toJson(new ResponseData(ResponseMessage.SUCCESS, new Gson().toJson(updatedAccount)));
			} else {
				return new Gson().toJson(
						new ResponseData(ResponseMessage.ERROR, new Gson().toJson("Account not exists to update")));
			}
		});

		post(ApplicationProperties.INSTANCE.accountsByAccountNumberCreateTransfers(), (request, response) -> {
			response.type(APIConstants.RESPONSE_TYPE.getType());
			AmountTransfer amountTransfer = new Gson().fromJson(request.body(), AmountTransfer.class);
			Account sender = accountService.getAccount(request.params(ACCOUNT_NUMBER));
			Account receiver = accountService.getAccount(amountTransfer.getAccountNumberTo());
			if (sender == null || receiver == null) {
				return new Gson()
						.toJson(new ResponseData(ResponseMessage.ERROR, new Gson().toJson("Wrong account number")));
			}
			if (!Currencies.contains(amountTransfer.getCurrency())) {
				return new Gson().toJson(new ResponseData(ResponseMessage.ERROR, new Gson().toJson("Wrong currency")));
			}
			Money amount;
			double fund;
			try {
				fund = Double.parseDouble(amountTransfer.getAmount());
				amount = new Money(fund, amountTransfer.getCurrency());
			} catch (IllegalArgumentException e) {
				return new Gson().toJson(new ResponseData(ResponseMessage.ERROR, new Gson().toJson("Wrong amount")));
			}

			Transaction transaction = transferService.intiateFundTransfer(sender, receiver, amount);
			transaction.proceed();
			if (transaction.getStatus().equals(TransferStatus.PROCEEDED))
				return new Gson().toJson(new ResponseData(ResponseMessage.SUCCESS, new Gson().toJsonTree(transaction)));
			else
				return new Gson().toJson(new ResponseData(ResponseMessage.ERROR, new Gson().toJsonTree(transaction.getStatus())));

		});

		delete(ApplicationProperties.INSTANCE.accountsByAccountNumber(), (request, response) -> {
			response.type(APIConstants.RESPONSE_TYPE.getType());
			accountService.deleteAccount(request.params(":accountNumber"));
			return new Gson().toJson(new ResponseData(ResponseMessage.SUCCESS, "Account Deleted Successfully"));
		});

		options(ApplicationProperties.INSTANCE.accountsByAccountNumber(), (request, response) -> {
			response.type(APIConstants.RESPONSE_TYPE.getType());
			return new Gson().toJson(new ResponseData(ResponseMessage.SUCCESS,
					(accountService.accountExist(request.params(ACCOUNT_NUMBER))) ? "Account Exists"
							: "Account does not exists"));
		});

		logger.info("****Account Api Registration Completed*****");

	}

}
