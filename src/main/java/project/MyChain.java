package project;

import com.google.gson.GsonBuilder;
import project.transactions.Transaction;
import project.transactions.TransactionOutput;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

public class MyChain {

    public static ArrayList<Block> blockchain = new ArrayList<>();
    public static HashMap<String, TransactionOutput> UTXOs = new HashMap<>(); // list of all unspent transactions
    public static int difficulty = 3;
    public static float minimumTransaction = 0.1f;
    public static Wallet walletA;
    public static  Wallet walletB;
    public static Transaction genesisTransaction;

    public static void main(String[] args) {
        // Setup Bouncey castle as a Security Provider
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        // Create the wallets
        walletA = new Wallet();
        walletB = new Wallet();

        // Test public and private keys
        System.out.println("Private and public keys:");
        System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
        System.out.println(StringUtil.getStringFromKey(walletA.publicKey));

        // Create a test transaction from WalletA to WalletB
        Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
        transaction.generateSignature(walletA.privateKey);

        // Verify the signature works and verify it from the public key
        System.out.println("Is signature verified");
        System.out.println(transaction.verifySignature());
    }

    public static Boolean isChainValid(){
        Block currentBlock;
        Block previousBlock;

        for(int i=1; i< blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);

            // compare registrad hash and calculated hash
            if(!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current Hashes not equal");
                return false;
            }

            // compare previous hash and registered previous hash
            if(!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
        }
        return true;
    }
}