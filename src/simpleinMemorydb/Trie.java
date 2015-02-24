package simpleinMemorydb;

/**
 * @author alok this class is the wrapper around Trie_Key and Trie_Value and
 * object of this class is used to do the operations on DB
 */
public class Trie {

//object of trie which stores key value pair
    private Trie_Key trieForKeyTrie;

//    object of trie which stores value and their count
    private Trie_Values trieForValueTrie;

    public Trie() {
        trieForKeyTrie = new Trie_Key();
        trieForValueTrie = new Trie_Values();
    }

    /**
     * method to insert <key ,value> pair
     *
     */
    public void setKey(String key, String Value) {

        if (trieForKeyTrie.searchKey(key).equalsIgnoreCase(Value)) {
            return;
        }
        if (trieForKeyTrie.searchKey(key).equalsIgnoreCase("NULL")) {
            trieForKeyTrie.addKey(key, Value);
            trieForValueTrie.addKey(Value);
        } else {
            String valueToBeDecremented = trieForKeyTrie.searchKey(key);
            trieForKeyTrie.addKey(key, Value);
            trieForValueTrie.addKey(Value);
            trieForValueTrie.unSet(valueToBeDecremented);
        }
    }

    /**
     * method to get the count for a given value
*
     */
    public int getCountOfValue(String value) {
        return trieForValueTrie.getCount(value);
    }

    public String getValue(String key) {
        return trieForKeyTrie.searchKey(key);
    }

    /**
     * method to unset a key
*
     */
    public void unset(String Key) {
        String value = trieForKeyTrie.searchKey(Key);
        trieForKeyTrie.unsetKey(Key);
        trieForValueTrie.unSet(value);
    }
}
