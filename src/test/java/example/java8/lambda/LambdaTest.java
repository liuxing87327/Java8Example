package example.java8.lambda;

import org.junit.Test;

/**
 * LambdaTest
 *
 * @author ：liuxing
 * @since ：2015-08-03 01:39
 */
public class LambdaTest {

    @Test
    public void testThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("我是线程");
            }
        }).start();

        new Thread(() -> {
            System.out.println("我是Lambda创建的线程");
        }).start();
    }

}
