/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpleinMemorydb;

import java.util.HashMap;

/**
 *
 * @author alok
 * this class  creates tire that stores values and their count 
 */
public class Trie_Values {

    private static NodeValue rootForValueTrie;

    public Trie_Values() {
        rootForValueTrie = new NodeValue('\0');
    }

    public void addKey(String key) {
        addKey(rootForValueTrie, key);
    }
    /*
     *this method inserts a value in the value trie    
     */

    public void addKey(NodeValue rootForValueTrie, String key) {
        NodeValue tempNode = rootForValueTrie;
        NodeValue newKey;
        char charAt_i;
        HashMap<Character, NodeValue> tempChilds;
        for (int ind = 0; ind < key.length(); ind++) {
            tempChilds = tempNode.getChilds();
            charAt_i = key.charAt(ind);
            if (tempChilds.containsKey(charAt_i)) {
                tempNode = tempChilds.get(charAt_i);
            } else {
                newKey = new NodeValue(charAt_i);
                tempChilds.put(charAt_i, newKey);
                tempNode = newKey;
            }
        }
        tempNode.setCount(tempNode.getCount() + 1);
    }

    public int getCount(String key) {
        return getCount(rootForValueTrie, key);
    }

    public int getCount(NodeValue rootForValueTrie, String key) {
        NodeValue tempNode = rootForValueTrie;
        HashMap<Character, NodeValue> tempChilds;
        char charAt_i;
        boolean found = true;
        for (int ind = 0; ind < key.length(); ind++) {
            charAt_i = key.charAt(ind);
            tempChilds = tempNode.getChilds();
            if (!tempChilds.containsKey(charAt_i)) {
                found = false;
                break;
            }
            tempNode = tempChilds.get(charAt_i);
        }
        return (found && tempNode.getCount() > 0) ? tempNode.getCount() : 0;
    }
    
    public void unSet(String value) {
        NodeValue tempNode = rootForValueTrie;
        HashMap<Character, NodeValue> tempChilds;
        char charAt_i;
        boolean found = true;
        for (int ind = 0; ind < value.length(); ind++) {
            charAt_i = value.charAt(ind);
            tempChilds = tempNode.getChilds();
            if (!tempChilds.containsKey(charAt_i)) {
                found = false;
                break;
            }
            tempNode = tempChilds.get(charAt_i);
        }
        if (found && tempNode.getCount() > 0) {
            tempNode.setCount(tempNode.getCount() - 1);
        }
    }
}
