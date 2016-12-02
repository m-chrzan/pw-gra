package testy;

public class Testing {
    public static void beginTest(String name){
        System.out.println("Testing " + name + ".");
    }

    public static void finishTest(){
        System.out.println("Done with test.");
    }

    public static void checkTrue(boolean test, String msg){
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
}
