# Event Sourcing with Axon and CQRS in Spring Boot

This is a demo project for learning event sourcing with axon framework and cqrs desing pattern.
Swagger api doucmentation framework is also implementated for learning purpose.

## What is event Sourcing?
Event sourcing is a way to store events that occur in a business domain. For instance, In this demo project it is solving a banking domain problem. So, every events that occur in the domain needs to be stored for later review of a transaction or audit. Event sourcing helps in such conditions, it store every event in a formatted order for recalculation or re-evaluation. So perfectly naming all the event is crucial in this type of design.

## What is CQRS?
The full form of CQRS means Command Query Responsibility Segragation. It means that a business solution should have two separate service one should only change the state of the domain and other is only responsible to provide the view of the current state of the data. Commands are responsible for the state changes in a domain and Queries are responsible to provide view data to the end user.

## What is Axon?
Axon is a framework for solving such programming challenges. It helps programmer by leveraging all the configuration and abstract them. We as a spring boot developer just have to use annotation to solve the connection puzzle. To make it more easier axon now also provide their own axon server so that we can use that server as event store(Event store is the place/database where all the events reside).

## What is Swagger?
[Swagger](https://swagger.io/) is a api documentation genaration framework. We can easily create documentation of our rest api with the help of swagger dependency in spring boot. We can also use swagger's index.html page to view and call our controller methods from the browser.

This is the Swagger front page for the project.

![SwaggerIndexHtml](https://github.com/T4puSD/CQRS-EventSourcing-With-Axon/raw/master/commons/Swaggerui.png "SwaggerUI")

This page is pretty much self explanatory. We have 3 method for executing commands and 2 method for query.
* `POST bank-accounts/` endpoint is to create a new account
* Other two endpoint in the command section are for credit and debit ammount from a account
* The `GET bank-accounts/{accountNumber}` endpoint gets data from h2 database. 
* and `GET bank-accounts/{accountNumber}/events` endpoint gets data from event store that is stored into axon server.

## How this project is designed?
As this project is a command query responsibility segragation project. We have to separate all the command and events for the sake of it.

**It has 3 commands:**
1. CreateAccountCommand
2. CreditMoneyCommand
3. DebitMoneyCommand

**And 5 events:**
1. AccountActivatedEvent
2. AccountCreatedEvent
3. AccountHeldEvent
4. MoneyCreditedEvent
5. MoneyDebitedEvent

Every time a new command gets executed a new associated event gets generated for that command. For example when a new account gets created it trigger two events Account Created Event and Account Activated Event. Account Held event gets triggered if a user's account have negative balance. CreditMoneyCommand trigger the MoneyCreditedEvent and so forth.

## The Program Flow
When a new command is triggered through the controller it declare the command and event listener generate appropriate event for that command and it the the responsibility of the events to oprate on the domain model. For instance if CreditMoneyCommand is triggered then MoneyCreditEvent gets generated and operates on the entity model and increase the account balance. Axon framework will store this event into the axon server's event store.

## A Simple Example Of TransactionEvents
If a person open a bank accont with 20 BDT. Then debit 15 BDT for a BallPen. Again if he want to debit 10 BDT for pencil but as his balance will be now in negative in the bank the bankput a hold status in his account number. To reactivate the account he puts 50 BDT in his bank account. So his account will now have 45 BDT. But there was a series of events happned at the bank server. As we are using event sourcing we can see all the events happned on his account. 
Bhis is the result of `GET bank-accounts/{accountNumber}/envents` endpoint: 
```json
[
  {
    "id": "60d9c622-d434-4ad3-9fdd-64bf1bfa865b",
    "accountBalance": 20,
    "currency": "BDT"
  },
  {
    "id": "60d9c622-d434-4ad3-9fdd-64bf1bfa865b",
    "status": "ACTIVATED"
  },
  {
    "id": "60d9c622-d434-4ad3-9fdd-64bf1bfa865b",
    "debitAmount": 15,
    "currency": "BDT"
  },
  {
    "id": "60d9c622-d434-4ad3-9fdd-64bf1bfa865b",
    "debitAmount": 10,
    "currency": "BDT"
  },
  {
    "id": "60d9c622-d434-4ad3-9fdd-64bf1bfa865b",
    "status": "HOLD"
  },
  {
    "id": "60d9c622-d434-4ad3-9fdd-64bf1bfa865b",
    "creditAmount": 50,
    "currency": "BDT"
  },
  {
    "id": "60d9c622-d434-4ad3-9fdd-64bf1bfa865b",
    "status": "ACTIVATED"
  }
]
``` 

This is the event viewer built into axon server console page. 
![AxonServerConsole](https://github.com/T4puSD/CQRS-EventSourcing-With-Axon/raw/master/commons/AxonServerEvents.png "AxonServer")

We can see each event is stored in a formatted manner.

But the aggregated data can also be seen from a second database( for this project from h2-database) that his aggegated balance is now 45 BDT. 
Bellow is the result from `GET bank-accounts/{accountNumber}` endpoint: 
```json
{
  "id": "60d9c622-d434-4ad3-9fdd-64bf1bfa865b",
  "accountBalance": 45,
  "currency": "BDT",
  "status": "ACTIVATED"
}
```
This is the picture of the h2-console:   
![H2Database](https://github.com/T4puSD/CQRS-EventSourcing-With-Axon/raw/master/commons/h2-console.png "H2DatabaseAggregatedResult")

if we can match the last row's account number with our example then we can see that account has 45 BDT after all of the transactions.
