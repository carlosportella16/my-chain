package project;

import project.transactions.Transaction;

import java.util.ArrayList;
import java.util.Date;

public class Block {
    public String hash;
    public String previousHash;
    public String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<>(); // our data will be a simple message
    private final long timeStamp;
    private int nonce;

    public Block(String previousHash) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();

        this.hash = calculateHash(); // Making sure we do this after we set the other values
    }

    // Calculate new hash based on blocks contents
    public String calculateHash() {
        return StringUtil.applySha256(
                previousHash +
                        timeStamp +
                        nonce +
                        merkleRoot
        );
    }

    // Increases nonce value until hash target is reached
    public void mineBlock(int difficulty) {
        merkleRoot = StringUtil.getMerkleRoot(transactions);
        String target = new String(new char[difficulty]).replace('\0', '0'); // Create a string with difficulty * '0'
        while(!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash );
    }

    // Add transactions to this block
    public void addTransaction(Transaction transaction) {
        // process transaction and check if valid, unless block is genesis block then ignore
        if(transaction == null) return;
        if(!"0".equals(previousHash)) {
            if((!transaction.processTransaction())) {
                System.out.println("Transaction failed to process. Discarded.");
                return;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
    }
}