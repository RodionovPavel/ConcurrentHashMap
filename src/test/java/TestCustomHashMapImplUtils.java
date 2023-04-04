
import service.CustomHashMap;
import service.impl.CustomHashMapImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class TestCustomHashMapImplUtils {

    CustomHashMap<String, Integer> customHashMap = new CustomHashMapImpl<>();

    @BeforeEach
    public void init() {
        customHashMap.put("Hi1", 3);
        customHashMap.put("Hi2", 6);
        customHashMap.put("Hi3", 7);
        customHashMap.put("Hi4", 8);
        customHashMap.put("Hi5", 9);
        customHashMap.put("Hi6", 10);
        customHashMap.put("Hi7", 11);
        customHashMap.put("Hi8", 6);
        customHashMap.put("Hi9", 7);
    }

    @AfterEach
    public void clearAll() {
        customHashMap.clear();
    }

    @Test
    public void put() {
        customHashMap.put("D", 3);
        assertEquals(3, customHashMap.get("D"));
    }

    @Test
    public void isEmpty() {
        CustomHashMap<String, Integer> emptyHashMap = new CustomHashMapImpl<>();
        emptyHashMap.put("Element", 1);
        emptyHashMap.remove("Element");
        var result = emptyHashMap.isEmpty();
        CustomHashMap<String, Integer> notEmptyHashMap = new CustomHashMapImpl<>();
        notEmptyHashMap.put("Element", 1);
        var wrongResult = notEmptyHashMap.isEmpty();

        assertTrue(result);
        assertFalse(wrongResult);
    }

    @Test
    public void remove() {
        customHashMap.put("Element", 1);
        var result = customHashMap.remove("Element");
        assertTrue(result);
        assertFalse(customHashMap.containsKey("Element"));
    }

    @Test
    public void get() {
        customHashMap.put("Element", 1);
        assertEquals(1, customHashMap.get("Element"));
    }

    @Test
    public void size() {
        assertEquals(9, customHashMap.size());
    }

    @Test
    public void clear() {
        customHashMap.clear();
        assertEquals(0, customHashMap.size());
    }

    @Test
    public void concurrency() {
        List<Integer> listOfIntegers = new ArrayList<>();
        IntStream.range(0, 100).forEach(listOfIntegers::add);

        listOfIntegers.parallelStream().forEach(i -> {
            customHashMap.put(String.valueOf(i), 1);
            customHashMap.remove(String.valueOf(i));
        });

        assertEquals(9, customHashMap.size());
    }
}
