package project.transactions;

import project.MyChain;
import project.StringUtil;

import java.security.*;
import java.util.ArrayList;

public class Transaction {
    public String transactionId; // This is also the hash of the transaction
    public PublicKey sender; // senders address/public key.
    public PublicKey reciepient; // Recipients address/public key.
    public float value;
    public byte[] signature; // this is to prevent anybody else from spending funds in our wallet.

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    private static int sequence = 0; // a rough count of how many transactions have been generated.

    // Constructor:
    public Transaction(PublicKey from, PublicKey to, float value,  ArrayList<TransactionInput> inputs) {
        this.sender = from;
        this.reciepient = to;
        this.value = value;
        this.inputs = inputs;
    }

    // This Calculates the transaction hash (which will be used as its Id)
    private String calulateHash() {
        sequence++; //increase the sequence to avoid 2 identical transactions having the same hash
        return StringUtil.applySha256(
                StringUtil.getStringFromKey(sender) +
                        StringUtil.getStringFromKey(reciepient) +
                        Float.toString(value) + sequence
        );
    }

    // Signs all the data we dont wish to be tampered with
    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value);
        signature = StringUtil.applyECDSASig(privateKey, data);
    }

    // Verifies the data we signed hasnt been tampered with
    public boolean verifySignature(){
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value);
        return StringUtil.verifyECDSASig(sender, data, signature);
    }

    public boolean processTransaction() {
        if(!verifySignature()) {
            System.out.println("#Transaction Signature failed to verify");
            return false;
        }

        // Gather transaction inputs (Make sure they are unspent)
        for(TransactionInput i : inputs) {
            i.UTXO = MyChain.UTXOs.get(i.transactionOutputId);
        }

        // Check if transaction is valid
        if(getInputsValue() < MyChain.minimumTransaction) {
            System.out.println("#Transaction Inputs to small: " + getInputsValue());
            return false;
        }

        // Generate transaction outputs:
        float leftOver = getInputsValue() - value; // get value of inputs then the leftover change:
        transactionId = calulateHash();
        outputs.add(new TransactionOutput(this.reciepient, value, transactionId)); // send value to recipient
        outputs.add(new TransactionOutput(this.reciepient, leftOver, transactionId)); // send the leftover 'change' back to sender

        // Add outputs to Unspent list
        for(TransactionOutput o : outputs) {
            MyChain.UTXOs.put(o.id, o);
        }

        // Remove transaction inputs from UTXO lists as spent:
        for(TransactionInput i : inputs) {
            if(i.UTXO == null) continue; // if transaction cant be found skip it
            MyChain.UTXOs.remove(i.UTXO.id);
        }
        return true;
    }

    // returns sum of inputs (UTXOs) values
    public float getInputsValue(){
        float total = 0;
        for(TransactionInput i : inputs) {
            if(i.UTXO == null) continue;
            total += i.UTXO.value;
        }
        return total;
    }

    // returns sum of outputs:
    public float getOutputsValue() {
        float total = 0;
        for(TransactionOutput o : outputs) {
            total += o.value;
        }
        return total;
    }
}
