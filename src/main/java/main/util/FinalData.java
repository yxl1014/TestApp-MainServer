package main.util;


/**
 * @author yxl.testapp.domain.yxl
 * @date: 2022/9/12 下午1:37
 * 常量类
 */
public class FinalData {


    public static long LOGIN_EXPIRES = 3 * 24 * 60 * 60 * 1000;


    /**
     * 协议魔数
     */
    public static String MAGIC_NUMBER = "20011014";

    /**
     * 终止符
     */
    public static String END = "!end!";


    /**
     * jwt token 签发方
     */
    public static String TOKEN_ISSUER = "TestApp-LoginServer";


    /**
     * byte数组转int
     */
    public static int byteArrayToInt(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;
        }
        return value;
    }

    /**
     * int转byte数组
     */
    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }
}
