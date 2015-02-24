
package simpleinMemorydb;

import java.util.HashMap;

/**
 *@author alok
 * class for Node structure of trie that stores value and its count 
 */
public class NodeValue {
    
    private char keyForValue;
    private int count;
    private HashMap<Character, NodeValue> children;

    public NodeValue(char keyForValue) {
         this.keyForValue = keyForValue;
         count = 0;
         children = new HashMap<>();
    }

    public char getKeyForValue() {
        return keyForValue;
    }

    public void setKeyForValue(char keyForValue) {
        this.keyForValue = keyForValue;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public HashMap<Character, NodeValue> getChilds() {
        return children;
    }

    public void setChilds(HashMap<Character, NodeValue> childs) {
        this.children = childs;
    }
}


