package example.java8.base64;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base64Demo
 *
 * @author ：liuxing
 * @since ：2015-08-03 01:03
 */
public class Base64Test {

    /**
     * base64编码
     * @param text
     * @return
     */
    public static String encode(String text){
        if (StringUtils.isEmpty(text)){
            return "";
        }

        return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * base64解码
     * @param text
     * @return
     */
    public static String decode(String text){
        if (StringUtils.isEmpty(text)){
            return "";
        }

        return new String(Base64.getDecoder().decode(text), StandardCharsets.UTF_8);
    }

    @Test
    public void testEncode() throws Exception {
        encode("测试base64编码");
    }

    @Test
    public void testDecode() throws Exception {
        String text = "测试base64解码";
        String encodeChar = encode(text);
        String decodeChar = decode(encodeChar);
        Assert.assertEquals(text, decodeChar);
    }

}
