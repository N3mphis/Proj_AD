package grid;

public class Counter {
    private static long count = 0;

    public static void increaseCount(){
        count++;
    }

    public static long getCount(){
        return count;
    }
}
