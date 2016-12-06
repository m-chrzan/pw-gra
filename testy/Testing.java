package testy;

public class Testing {
    public static void beginTest(String name){
        System.out.println("Testing " + name + ":");
    }

    public static void checkTrue(boolean test, String msg){
        System.out.print("  ");
        if(test){
            System.out.print("Test ok.");
        } else{
            System.out.print("TEST FAILED!");
        }
        System.out.println(" " + msg + ".");
    }

    public static void checkFalse(boolean test, String msg){
        checkTrue(!test, msg);
    }

    public static <T> void checkEqual(T t1, T t2, String msg) {
        checkTrue(t1.equals(t2), msg);
    }

    public static <T> void checkNotEqual(T t1, T t2, String msg) {
        checkFalse(t1.equals(t2), msg);
    }

    public static void checkExceptionThrown(Runnable runnable, String msg) {
        boolean thrown = false;
        try {
            runnable.run();
        } catch (Exception e) {
            thrown = true;
        }

        Testing.checkTrue(thrown, msg);
    }

    public static <T extends Throwable> void checkExceptionThrown(
            Runnable runnable, T exception, String msg) {
        boolean thrown = false;
        try {
            runnable.run();
        } catch (Exception e) {
            if (exception.getClass().isInstance(e)) {
                thrown = true;
            }
        }

        Testing.checkTrue(thrown, msg);
    }
}
