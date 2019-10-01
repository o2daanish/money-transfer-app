package task.money.transfer.api.defination;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;
import static spark.Spark.put;

import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import task.money.transfer.domain.AccountHolder;
import org.javamoney.moneta.Money;
import task.money.transfer.domain.ResponseData;
import task.money.transfer.domain.ResponseMessage;
import task.money.transfer.service.IAccountHolderService;
import task.money.transfer.service.impl.AccountHolderService;
import task.money.transfer.utils.ApplicationProperties;

/**
 * @author Danish Ahmad
 * 
 */

public class AccountHolderAPI {

	private static final Logger logger = LoggerFactory.getLogger(AccountHolderAPI.class);

	private static final String CUSTOMER_ID = ":customerId";

	public static void apiInit() {

		logger.info("****AccountHolderAPI Registration Intiated*****");

		final IAccountHolderService accountHolderService = AccountHolderService.getInstance();

		post(ApplicationProperties.INSTANCE.accountHolders(), (request, response) -> {
			response.type(APIConstants.RESPONSE_TYPE.getType());
			AccountHolder requestBody = new Gson().fromJson(request.body(), AccountHolder.class);
			accountHolderService.addAccountHolder(requestBody);
			return new Gson().toJson(new ResponseData(ResponseMessage.SUCCESS));
		});

		get(ApplicationProperties.INSTANCE.accountHolders(), (request, response) -> {
			response.type(APIConstants.RESPONSE_TYPE.getType());
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Money.class, new JsonSerializer<Money>() {
				@Override
				public JsonElement serialize(Money src, Type typeOfSrc, JsonSerializationContext context) {
					JsonObject obj = new JsonObject();
					 obj.addProperty("amount", src.getNumber().doubleValueExact());
					 obj.addProperty("currency", src.getCurrency().getCurrencyCode());
					 return obj;
				}
			});
			return gsonBuilder.create().toJson(new ResponseData(ResponseMessage.SUCCESS,
					new Gson().toJsonTree(accountHolderService.getAccountHolders())));
		});

		get(ApplicationProperties.INSTANCE.accountHoldersById(), (request, response) -> {
			response.type(APIConstants.RESPONSE_TYPE.getType());
			return new Gson().toJsonTree(new ResponseData(ResponseMessage.SUCCESS,
					new Gson().toJsonTree(accountHolderService.getAccountHolder(request.params(CUSTOMER_ID)))));
		});

		get(ApplicationProperties.INSTANCE.accountholdersAccountsById(), (request, response) -> {
			response.type(APIConstants.RESPONSE_TYPE.getType());

			return new Gson().toJson(new ResponseData(ResponseMessage.SUCCESS, new Gson()
					.toJsonTree(accountHolderService.getAccountHolder(request.params(CUSTOMER_ID)).getAccounts())));
		});

		put(ApplicationProperties.INSTANCE.accountHolders(), (request, response) -> {
			response.type(APIConstants.RESPONSE_TYPE.getType());

			AccountHolder update = new Gson().fromJson(request.body(), AccountHolder.class);
			AccountHolder updateAccount = accountHolderService.updateAccountHolder(update);

			if (updateAccount != null) {
				return new Gson()
						.toJson(new ResponseData(ResponseMessage.SUCCESS, new Gson().toJsonTree(updateAccount)));
			} else {
				return new Gson().toJson(new ResponseData(ResponseMessage.ERROR,
						new Gson().toJson("AccountHolder not exists to update")));
			}
		});

		delete(ApplicationProperties.INSTANCE.accountHoldersById(), (request, response) -> {
			response.type(APIConstants.RESPONSE_TYPE.getType());

			accountHolderService.deleteAccountHolder(request.params(CUSTOMER_ID));
			return new Gson().toJson(new ResponseData(ResponseMessage.SUCCESS, "Account Holder Deletion Completed"));
		});

		options(ApplicationProperties.INSTANCE.accountHoldersById(), (request, response) -> {
			response.type(APIConstants.RESPONSE_TYPE.getType());
			return new Gson().toJson(new ResponseData(ResponseMessage.SUCCESS,
					(accountHolderService.accountHolderExist(request.params(CUSTOMER_ID))) ? "Account Holder  exists"
							: "Account Holder  does not exists"));
		});

		logger.info("****Account Api Registration Completed*****");
	}

}