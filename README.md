# Money-Transfer App!
A RESTful API for money transfers between accounts.

# Solution
 - Java is used for the implementation.
 - Used libraries:
    -   log4j- For Logging
    -   Spark- To Write Micro API
    -   Junit - framework for unit tests

## To Build the Application

Checkout the project from this repository, then run

mvn clean install

# To run the application

java -jar task.money.transfer-0.0.1-SNAPSHOT-jar-with-dependencies.jar

## API Description


GET  `/accountholders`  - get all account holders
GET  `/accountholders/:id`  - get account holders by input id  
POST  `/accountholders`  - create new account holders
PUT  `/accountholders/:id`  - update existing account holders
DELETE  `/accountholders/:id`  - delete account holders

GET  `/accounts`  - get all accounts  
GET  `/accounts/:accountNumber`  - get account by its input
POST  `/accounts`  - create new accounts  
PUT  `/accounts/:accountNumber`  - update existing accounts  
DELETE  `/accounts/:accountNumber`  - delete accounts

GET  `/accounts/:accountNumber/transfers`  - get transfers history for account  
GET  `/accounts/:accountNumber/transfers/:id`  - get transfer by its number  
POST  `/accounts/:accountNumber/transfers`  - create new transfer


**Account Holder**

Create an account holder

The following request creates an account holder and returns it:

```
POST localhost:4567/accountholders
 {
	"firstName":"Jason",
	"lastName":"Statham",
	"address":{},
	"contact":{"email":"Jason.Statham@dummy.com","mobileNo":"099988776767"},
	"listOfAccounts":[{"accountType":"Default account",
	"accountNumber":"100006778",
	"currentAvailableAmount":{"amount":"150000","currency":"INR"},
	"transactions":[{}],
	"currency":"USD"}]
	}
```
Update an account

The following request updates an account and returns it:

```
    PUT localhost:4567/accountholders
    	{
	"customerId": "Jason123",
	"firstName":"Jason",
	"lastName":"Statham",
	"address":{},
	"contact":{"email":"mydummyaccount@dummy.com","mobileNo":"099988776767"},
	"listOfAccounts":[{"accountType":"Default account",
	"accountNumber":"1000012",
	"currentAvailableAmount":{"amount":"150000","currency":"INR"},
	"transactions":[{}],
	"currency":"INR"}]
	}
```
-   customerId is mandatory to provide while updating the field


Delete an acccount holder by id

The following request deletes an account:

```
    DELETE localhost:4567/accountholders/:customerId
```

Retrieve all account holders

The following request retrieves all account holder

```
    GET localhost:4567/accountholders

```
Retrieve one account

The following request retrieves one account holder 

```
    GET localhost:4567/accountholder/:customerId

```

**Accounts API**

Retrieve accounts by account number

 ```
 
    GET localhost:4567/accounts/:accountnumber
 
 ```

Retrieve all the accounts in the system

 ```
 
 GET localhost:4567/accounts
 
 ```


Retrieve all the transfers for given account number

 ```
 
 GET localhost:4567/accounts/:accountNumber/transfers
 
 ```

Update the account number

 ```
 
 PUT localhost:4567/accounts/:accountNumber/
 	{
	"accountType":"Default account",
	"accountNumber":"1000008",
	"currentAvailableAmount":{"amount":"1009090","currency":"INR"},
	"transactions":[{}],
	"currency":"INR"
     }
     
 ```

Create the transfers between accounts

 ```
 POST localhost:4567/accounts/:accountNumber/transfers
 	{
     "accountNumberTo":"1000003",
     "amount":"100",
     "currency":"RUB"
}
 ```

Delete the accounts number

```
 DELETE localhost:4567/accounts/:accountNumber
 ```

**Transfers**

Create a Fund transfer

The following request creates a transfer and returns it:

```
    POST localhost:4567/api/transfers
{
  "amount": {
    "amount": "100",
    "currency": "INR"
  },
  "sender": {
    "accountType": "Savings account",
    "accountNumber": "1000008",
    "currentAvailableAmount": {
      "amount": "1009090",
      "currency": "INR"
    },
    "transactions": [
      {}
    ],
    "currency": "INR"
  },
  "receiver": {
    "accountType": "Savings account",
    "accountNumber": "1000008",
    "currentAvailableAmount": {
      "amount": "1009090",
      "currency": "INR"
    },
    "transactions": [
      {}
    ],
    "currency": "INR"
  },
  "remarks": "rent"
}
```

A transfer will get successfully completed if:

-   The source and destination accounts exists
-   There is a sufficient balance on the source account    
-   Below are conversion it is supporting from one account to one account

      "USD", "INR"
      "INR", "USD"
      "INR", "RUB"
      "RUB", "INR"
      "GBP", "INR"
      "INR", "GBP"
      "EUR", "USD"
      "USD", "EUR"
      "RUB", "USD"
      "EUR", "INR"
