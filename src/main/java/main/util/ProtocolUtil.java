package main.util;

import pto.TestProto;

import java.nio.ByteBuffer;

/**
 * @author yxl.testapp.domain.yxl
 * @date: 2022/9/12 下午1:53
 * 自定义协议工具类
 * 4byte    4byte   4byte   payload     5byte
 * 数据长度  魔数     types   protobuf    终止符
 */

public class ProtocolUtil {
    /**
     * 协议解析
     */
    public byte[] decodeProtocol(byte[] data) {
        byte[] temp = new byte[4];
        System.arraycopy(data, 0, temp, 0, 4);


        int len = FinalData.byteArrayToInt(temp);
        System.arraycopy(data, 4, temp, 0, 4);


        String magicNumber = String.valueOf(FinalData.byteArrayToInt(temp));
        if (!FinalData.MAGIC_NUMBER.equals(magicNumber)) {
            return null;
        }
        byte[] end = new byte[5];
        System.arraycopy(data, 4 + 4 + 4 + len, end, 0, 5);
        if (!FinalData.END.equals(new String(end))) {
            return null;
        }
        byte[] result = new byte[len];
        System.arraycopy(data, 4 + 4 + 4, result, 0, len);
        return result;
    }

    public int getTypes(byte[] data) {
        byte[] temp = new byte[4];
        System.arraycopy(data, 4 + 4, temp, 0, 4);
        return FinalData.byteArrayToInt(temp);
    }

    public byte[] encodeProtocol(byte[] data, int len, TestProto.Types type) {
        ByteBuffer buf = ByteBuffer.allocate(len + 17);
        buf.putInt(len);
        buf.putInt(Integer.parseInt(FinalData.MAGIC_NUMBER));
        buf.putInt(type.getNumber());
        buf.put(data);
        buf.put(FinalData.END.getBytes());
        return buf.array();
    }
}
