/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpleinMemorydb;

import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author alok this class runs the basic shell which takes inputs either from
 * command prompt or from a file and prints output
 */
public class InmemoryDB {

    private static ArrayList<BufferForTransaction> transaction;
    private static Trie trie;
    private static int numberOfTransactions;

    /**
     *this method runs the basic shell
     *@param scanner : scanner object
     *@param inputType
     *@inputType : 0 for keyboard and 1 for file
     */
    
    public static void runShell(Scanner scanner, int inputType) {
        String command = "" ;
        numberOfTransactions = 1;
        trie = new Trie();
        transaction = new ArrayList<>();

        while (true) {
            System.out.print("");
            if (inputType == 0) {
                command = scanner.nextLine();
            }else{
                if(scanner.hasNext())
                    command = scanner.nextLine();
                else
                    exit(0);
            }
            if (command.length() == 0) {
                continue;
            }
            String[] tokens = command.trim().split("\\s+");
            if (tokens[0].equalsIgnoreCase("SET")) {
                processSetCommand(tokens);
            } else if (tokens[0].equalsIgnoreCase("UNSET")) {
                processUnSetCommand(tokens);
            } else if (tokens[0].equalsIgnoreCase("GET")) {
                processGetCommand(tokens);
            } else if (tokens[0].equalsIgnoreCase("NUMEQUALTO")) {
                int count = processNumEqualToCommand(tokens);
                if (count <= 0) {
                    System.out.println(0);
                } else {
                    System.out.println(count);
                }
            } else if (tokens[0].equalsIgnoreCase("END")) {
                exit(0);
            } else if (tokens[0].equalsIgnoreCase("BEGIN")) {
                processBeginCommand(tokens);
            } else if (tokens[0].equalsIgnoreCase("COMMIT")) {
                processCommitCommand(tokens);
            } else if (tokens[0].equalsIgnoreCase("ROLLBACK")) {
                processRollBackCommand(tokens);
            } else {
                System.out.println("Unknown Command...Please check the command you entered...");
            }
        }
    }
    
    /**
     * method to process set command
     * @param setCommandTokens 
     */
    
    public static void processSetCommand(String[] setCommandTokens) {
        if (setCommandTokens.length != 3) {
            System.out.println("Invalid argument for SET command...usage : SET <key> <value>");
            return;
        }
//        case when no transaction is running
//        numberOfTransaction = 1 means 
        if (numberOfTransactions == 1) {
            trie.setKey(setCommandTokens[1], setCommandTokens[2]);
        } else {
            BufferForTransaction currentTransaction = transaction.get(numberOfTransactions - 2);
            transaction.get(numberOfTransactions - 2).setKey(setCommandTokens[1], setCommandTokens[2]);

//                if key doesn't exist in trie
            if (trie.getValue(setCommandTokens[1]).equalsIgnoreCase("NULL")) {
                return;
//                if <key , value pair already existe in trie>
            } else if (trie.getValue(setCommandTokens[1]).equals(setCommandTokens[2])) {
                transaction.get(numberOfTransactions - 2).unsetValue(setCommandTokens[2]);
//                if key exists in trie with different value
            } else if (!trie.getValue(setCommandTokens[1]).equals(setCommandTokens[2])) {
                String valueToBeDecremented = trie.getValue(setCommandTokens[1]);
                transaction.get(numberOfTransactions - 2).unsetValue(valueToBeDecremented);
            }
        }
    }

    public static void processUnSetCommand(String[] unSetCommandTokens) {
        if (unSetCommandTokens.length != 2) {
            System.out.println("Invalid argument for SET command...usage : UNSET <key>");
            return;
        }
        if (numberOfTransactions == 1) {
            trie.unset(unSetCommandTokens[1]);
        } else {
            transaction.get(numberOfTransactions - 2).unSet(unSetCommandTokens[1], trie.getValue(unSetCommandTokens[1]));
        }
    }
    /*
     this method returns value corresponding to a requested key
     the way it works is that it checks if any transaction is running
     or not and if transaction is running then it checks if the current buffer has the requested key
     if yes then it returns value from there otherwise it reurns value from main DB
     in this way we get the latest values for a key
     */

    public static void processGetCommand(String[] getCommandTokens) {
        if (getCommandTokens.length != 2) {
            System.out.println("Invalid argument for SET command...usage : SET <key> <value>");
            return;
        }
        if (numberOfTransactions == 1) {
            System.out.println(trie.getValue(getCommandTokens[1]));
        } else {
//looking up for given key in buffer if not available in buffer then returning value from trie
            if (transaction.get(numberOfTransactions - 2).getBufferForKeyValue().containsKey(getCommandTokens[1])) {
                System.out.println(transaction.get(numberOfTransactions - 2).getBufferForKeyValue().get(getCommandTokens[1]));
            } else {
                System.out.println(trie.getValue(getCommandTokens[1]));
            }
        }
    }
    /*
     this method returns the count for a value requested by a user
     the way it works is that it sums the value of count from the trie and current buffer after some checks
     the count of a value in current buffer could be -1 if user unsets the variable without setting it in current transaction
    
     */

    public static int processNumEqualToCommand(String[] numEqualTokens) {
        int count;
        if (numEqualTokens.length != 2) {
            System.out.println("Invalid argument for SET command...usage : SET <key> <value>");
            return -1;
        }
        count = trie.getCountOfValue(numEqualTokens[1]);
        if (numberOfTransactions > 1) {
            int temp = transaction.get(numberOfTransactions - 2).
                    getCount(numEqualTokens[1]);
            count += temp;
        }
        return count;
    }
    /*
     this method adds a new transaction 
     */

    public static void processBeginCommand(String[] beginCommandTokens) {

        if (beginCommandTokens.length != 1) {
            System.out.println("Invalid no. of arguments...usage : BEGIN");
            return;
        }
        transaction.add(new BufferForTransaction());
        numberOfTransactions++;
    }
    /*
     this method removes the current transaction after doing some checks
     */

    public static void processRollBackCommand(String[] rollbackCommandTokens) {

        if (transaction.isEmpty()) {
            System.out.println("NO TRANSACTION");
            return;
        }
        transaction.remove(transaction.size() - 1);
        numberOfTransactions--;
    }
    
    /*
     this method commits the data from all the transaction into DB(which is a trie)
     */

    public static void processCommitCommand(String[] commitCommandTokens) {

        if (transaction.isEmpty()) {
            System.out.println("NO TRANSACTION");
            return;
        }

        if (transaction.size() == 1) {
            processCommitCommand(transaction.get(0).getBufferForKeyValue());
            transaction.remove(0);
            numberOfTransactions = 1;
        } else {                            //consolidating values from all the running transactions
            processCommitCommand(consolidateTransactions());
            transaction = new ArrayList<>();
            numberOfTransactions = 1;
        }

    }
    
    /*
     this method consolidates the data from all the transactions into one Map
     the way it consolidates data is from first transaction to the latest one
     and thus the latest values are reflected in DB
     */

    public static HashMap<String, String> consolidateTransactions() {
        if (numberOfTransactions == 1) {
            return transaction.get(0).getBufferForKeyValue();
        }
        HashMap<String, String> consolidatedMap = transaction.get(0).getBufferForKeyValue();
        HashMap<String, String> currentTransactionMap = new HashMap<>();
        for (int transactionId = 1; transactionId < numberOfTransactions - 1; transactionId++) {
            currentTransactionMap = transaction.get(transactionId).getBufferForKeyValue();
            consolidatedMap.putAll(currentTransactionMap);
        }
        return consolidatedMap;
    }
    
    /*
     this method persists the data from the transactions into DB
     */

    public static void processCommitCommand(HashMap<String, String> consolidatedMap) {
        Set<String> keys = consolidatedMap.keySet();
        for (String key : keys) {
            trie.setKey(key, consolidatedMap.get(key));
        }
    }
}
