package transactionProcessing;

import java.time.LocalDateTime;

class Transaction{
    private String id;
    private String source;
    private double amount;
    private LocalDateTime timestamp;

    public Transaction(String transactionId, String Source, double amount){
        this.id = transactionId;
        this.source = Source;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    //method to get time
    public LocalDateTime getTimestamp(){
        return timestamp;
    }

    public void getTransactionDetails(String id){
        //logic implementation later
    }
}