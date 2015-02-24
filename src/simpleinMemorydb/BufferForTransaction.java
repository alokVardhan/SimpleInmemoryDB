package simpleinMemorydb;

import java.util.HashMap;

/**
 *
 * @author alok this class is used as a buffer to store data for new
 * transactions
 */
public class BufferForTransaction {

    private HashMap<String, String> bufferForKeyValue;
    private HashMap<String, Integer> bufferForValueCount;

    public BufferForTransaction() {
        bufferForKeyValue = new HashMap<>();
        bufferForValueCount = new HashMap<>();
    }

    /**
     * this method is called to set value corresponding to a key
     *
     * @param key
     * @param value
     */
    public void setKey(String key, String value) {
        if (bufferForKeyValue.containsKey(key) && bufferForKeyValue.get(key).equalsIgnoreCase(value)) {
            return;
        }
        if (bufferForKeyValue.containsKey(key)) {
            String valueToBeDecremented = bufferForKeyValue.get(key);
            bufferForKeyValue.put(key, value);
            int tempcount = (bufferForValueCount.get(value) == null) ? 1 : bufferForValueCount.get(value) + 1;
            bufferForValueCount.put(value, tempcount);
            unsetValue(valueToBeDecremented);
        } else {
            bufferForKeyValue.put(key, value);
            setValue(value);
        }
    }

    /**
     * this method is used to set the count corresponding to a value
     *
     * @param value
     */
    public void setValue(String value) {
        if (bufferForValueCount.containsKey(value)) {
            bufferForValueCount.put(value, bufferForValueCount.get(value) + 1);
        } else {
            bufferForValueCount.put(value, 1);
        }
    }

    /**
     * this method unsets a value to reflect the deletion of a key value pair
     *
     * @param value
     */
    public void unsetValue(String value) {

        if (bufferForValueCount.containsKey(value)) {
            bufferForValueCount.put(value, bufferForValueCount.get(value) - 1);
        } else {
            bufferForValueCount.put(value, - 1);
        }
    }

    /**
     * this method is used to fetch the value corresponding to a key
     *
     * @param key : input key
     * @return : value or NULL depending upon the existance of key value pair
     */
    public String getKey(String key) {
        return bufferForKeyValue.containsKey(key) ? bufferForKeyValue.get(key) : "NULL";
    }

    /**
     * this method returns the count of a value in buffer
     *
     * @param value : value to be looked up
     * @return : count
     */
    public int getCount(String value) {
        return bufferForValueCount.containsKey(value) ? bufferForValueCount.get(value) : 0;
    }

    /**
     *this method is used to unset a key value pair
     * 
     * @param key
     * @param valueFromTrie
     */
    
    public void unSet(String key, String valueFromTrie) {
        String value = bufferForKeyValue.get(key);
        if (value == null && valueFromTrie.equalsIgnoreCase("NULL")) {
            return;
        }
        bufferForKeyValue.put(key, "NULL");
        if (value != null) {
            unsetValue(value);
        } else {
            if (!valueFromTrie.equalsIgnoreCase("NULL")) {
                unsetValue(valueFromTrie);
            }
        }
    }

    public HashMap<String, String> getBufferForKeyValue() {
        return bufferForKeyValue;
    }

    public void setBufferForKeyValue(HashMap<String, String> bufferForKeyValue) {
        this.bufferForKeyValue = bufferForKeyValue;
    }

    public HashMap<String, Integer> getBufferForValueCount() {
        return bufferForValueCount;
    }

    public void setBufferForValueCount(HashMap<String, Integer> bufferForValueCount) {
        this.bufferForValueCount = bufferForValueCount;
    }

}
