import service.CustomHashMap;
import service.impl.CustomHashMapImpl;

public class Main {
    public static void main(String[] args) {
        CustomHashMap customHashMap = new CustomHashMapImpl<String, Integer>();

        customHashMap.put(null, 3);

        for (int i = 0; i < 12; i++) {
            customHashMap.put(Integer.toString(i), 1);
        }
        customHashMap.display();
        System.out.println("");
        System.out.println(customHashMap.size());
    }
}
