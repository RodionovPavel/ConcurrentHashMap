
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
        customHashMap.put("Key1", 1);
        customHashMap.put("Key2", 2);
        customHashMap.put("Key3", 3);
        customHashMap.put("Key4", 4);
        customHashMap.put("Key5", 5);
        customHashMap.put("Key6", 6);
        customHashMap.put("Key7", 7);
        customHashMap.put("Key8", 8);
        customHashMap.put("Key9", 9);
        customHashMap.put("Key10", 10);

    }

    @AfterEach
    public void clearAll() {
        customHashMap.clear();
    }

    @Test
    public void put() {
        customHashMap.put("newKey", 1);
        assertEquals(1, customHashMap.get("newKey"));
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
        assertEquals(10, customHashMap.size());
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

        assertEquals(10, customHashMap.size());
    }
}
