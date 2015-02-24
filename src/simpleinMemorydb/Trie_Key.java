/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpleinMemorydb;

import java.util.HashMap;

/**
 *@author alok
 * this class  creates tire that stores key value pair
 */
public class Trie_Key {

    private static NodeKey rootForKeyTrie;

    public Trie_Key() {
        rootForKeyTrie = new NodeKey('\0');
    }

    public void addKey(String key, String value) {
        addKey(rootForKeyTrie, key, value);
    }

    public void addKey(NodeKey rootForKeyTrie, String key, String value) {
        NodeKey tempNode = rootForKeyTrie;
        NodeKey newKey;
        char charAt_i;
        HashMap<Character, NodeKey> tempChilds;
        for (int ind = 0; ind < key.length(); ind++) {
            tempChilds = tempNode.getChildren();
            charAt_i = key.charAt(ind);
        
            if (tempChilds.containsKey(charAt_i)) {
                tempNode = tempChilds.get(charAt_i);
            } else {
                newKey = new NodeKey(charAt_i);
                tempChilds.put(charAt_i, newKey);
                tempNode = newKey;
            }
        }
        tempNode.setValue(value);
    }
    
    public String searchKey(String key) {
        return searchKey(rootForKeyTrie, key);
    }
    
    public String searchKey(NodeKey rootForKeyTrie, String key) {
        
        NodeKey tempNode = rootForKeyTrie;
        HashMap<Character, NodeKey> tempChilds;
        char charAt_i;
        boolean found = true;
        for (int ind = 0; ind < key.length(); ind++) {
            charAt_i = key.charAt(ind);
            tempChilds = tempNode.getChildren();
            if (!tempChilds.containsKey(charAt_i)) {
                found = false;
                break;
            }
            tempNode = tempChilds.get(charAt_i);
        }
        return (found && tempNode.getValue() != null) ? tempNode.getValue() : "NULL";
    }

    public void unsetKey(String key) {
        NodeKey tempNode = rootForKeyTrie;
        HashMap<Character, NodeKey> tempChilds;
        char charAt_i;
        boolean found = true;
        for (int ind = 0; ind < key.length(); ind++) {
            charAt_i = key.charAt(ind);
            tempChilds = tempNode.getChildren();
            if (!tempChilds.containsKey(charAt_i)) {
                found = false;
                break;
            }
            tempNode = tempChilds.get(charAt_i);
        }
        if(found && tempNode.getValue() != null)
            tempNode.setValue(null);

    }

}
