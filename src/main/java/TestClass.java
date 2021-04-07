import annotation.AfterSuite;
import annotation.Test;

public class TestClass {

    @BeforeSuite
    public static void setUp() {
        System.out.println("SetUp");
    }
    @AfterSuite
    public  static  void tearDown(){
        System.out.println("tearDown");
    }

    @Test(priority = 4)
    public static void test1(){
        System.out.println("Test1");
    }

    @Test(priority = 3)
    public static void test2(){
        System.out.println("Test2");
    }

    @Test(priority = 2)
    public static void test3(){
        System.out.println("Test3");
    }

    @Test(priority = 1)
    public static void test4(){
        System.out.println("Test4");
    }

}
