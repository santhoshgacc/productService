Product Consumer Service 
=======================================================================
The service purpose is to consume message from the Queue - "ProductTransactionQueue".
If the DB goes down, the message is ROLLEDBACK to the same queue and Re-Delivery mechanism is initiated.
Re-Deliver happens at 5, 10, 20 seconds.
If all the re-delivery attempts gets failed, the message is considered as the DEAD LETTER and moved to DLQ - "ProductTransactionQueue.dlq"

A flag is available at the Service Layer to check the DB status.
Once the DB is up and running, the flag is flipped and a Thread is called to process the DLQ messages.

Application Flow:
=======================================================================
1) The Product transaction is sent to the REST API
2) The Rest Controller receives the message and validates the data
3) Once the validation is success, the message is pushed to the queue
4) The Queue Listener configured will consume the message and save it in Database
5) The DB Object may/may not exist in the DB as the JPA will take care of object creation/updation activities
6) If the DB is down, the message is ROLLEDBACK to QUEUE and the DB STATUS flag is set to "false"
7) The Re-Delivery process is performed
8) If Re-Delivery fails, the message is pushed to the DLQ
9) Once DB is up and a message is processed successfully, the DB STATUS flag is set to "true"
10) A thread is initiated to process the messages in the DLQ

REST APIs Uses:
=======================================================================
1) Sending Product Transaction - http://localhost:8080/product/send - POST

Req:
-----
{
    "prodId": "P1",
    "prodName": "Product Name - 1",
    "sellerId": "S1",
    "purchasedQty": 10
}

Res:
----
HTTPStatus 


2) Get the Products available in DB - http://localhost:8080/product/ - GET

Res:
----
[
    {
        "prodId": "P2",
        "prodName": "Product Name -2",
        "sellerId": "S2",
        "purchasedQty": 20
    },
    {
        "prodId": "P2",
        "prodName": "Product Name -2",
        "sellerId": "S2",
        "purchasedQty": 20
    }
]


3) Get the size of respective queue - http://localhost:8080/product/queueSize/ProductTransactionQueue - GET

Res:
----
Number of messages in queue

