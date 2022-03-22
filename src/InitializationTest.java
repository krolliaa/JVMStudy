public class InitializationTest {
    private static int a = 10;

    static {
        a = 20;
    }

    public static void main(String[] args) {
        System.out.println(a);
    }
}
