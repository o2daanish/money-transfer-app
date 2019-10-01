package task.money.transfer.api.defination;

import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;

import task.money.transfer.domain.Account;
import task.money.transfer.domain.Transaction;
import task.money.transfer.domain.Money;
import task.money.transfer.domain.ResponseData;
import task.money.transfer.domain.ResponseMessage;
import task.money.transfer.service.ITransferService;
import task.money.transfer.service.impl.TransferService;
import task.money.transfer.utils.ApplicationProperties;

/**
 * @author Danish Ahmad
 */
public class TransferAPI {

    public static void apiInit() {
        final ITransferService transferService = TransferService.getInstance();

        post(ApplicationProperties.INSTANCE.transfers(), (request, response) -> {
            response.type(APIConstants.RESPONSE_TYPE.toString());
            Transaction transaction=new Gson().fromJson(request.body(), Transaction.class);
            Account sender=transaction.getAccountFrom();
            Account receiver=transaction.getAccountTo();
            Money amount=transaction.getAmount();
            transferService.intiateFundTransfer(sender, receiver, amount);
            
            return new Gson().toJson(new ResponseData(ResponseMessage.SUCCESS));
        });


        get(ApplicationProperties.INSTANCE.transfersbyid(), (request, response) -> {
            response.type(APIConstants.RESPONSE_TYPE.toString());

            return new Gson().toJson(new ResponseData(ResponseMessage.SUCCESS, new Gson().toJsonTree(transferService.getTransfer(request.params(":id")))));
        });



    }
}
