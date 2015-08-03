package example.java8.function;

/**
 * 接口
 *
 * @author ：liuxing
 * @since ：2015-08-04 02:01
 */
public class FunctionTest {

    public interface Radio {
        void play();
    }

    public void testUsb() {
        Radio radio = new Radio() {

            @Override
            public void play() {
                System.out.println("播放广播");
            }
        };

        Radio radio2 = () -> System.out.println("播放广播");
    }

}
