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
 * this class stores the node structure for trie which stores key value pairs
 */
public class NodeKey {

    private  char key;
    private  String value;
    private  HashMap<Character, NodeKey> children;
    
    public NodeKey(char key) {
         this.key = key;
         value = null;
         children = new HashMap<>();
    }

    public char getKey() {
        return key;
    }

    public void setKey(char key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public HashMap<Character, NodeKey> getChildren() {
        return children;
    }

    public void setChildren(HashMap<Character, NodeKey> children) {
        this.children = children;
    }

}
