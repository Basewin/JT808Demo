package com.zenchn.util;

public class BitOperator {
	  /**
     * ��һ�����θ�Ϊbyte
     * 
     * @param value
     * @return
     * @throws Exception
     */
    public byte integerTo1Byte(int value)
    {
        return (byte)(value & 0xFF);
    }

    /**
     * ��һ�����θ�Ϊ1λ��byte����
     * 
     * @param value
     * @return
     * @throws Exception
     */
    public static byte[] integerTo1Bytes(int value)
    {
        byte[] result = new byte[1];
        result[0] = (byte)(value & 0xFF);
        return result;
    }

    /**
     * ��һ�����θ�Ϊ2λ��byte����
     * 
     * @param value
     * @return
     * @throws Exception
     */
    public static byte[] integerTo2Bytes(int value)
    {
        byte[] result = new byte[2];
        result[0] = (byte)((value >> 8) & 0xFF);
        result[1] = (byte)(value & 0xFF);
        return result;
    }

    /**
     * ��һ�����θ�Ϊ3λ��byte����
     * 
     * @param value
     * @return
     * @throws Exception
     */
    public byte[] integerTo3Bytes(int value)
    {
        byte[] result = new byte[3];
        result[0] = (byte)((value >> 16) & 0xFF);
        result[1] = (byte)((value >> 8) & 0xFF);
        result[2] = (byte)(value & 0xFF);
        return result;
    }

    /**
     * ��һ�����θ�Ϊ4λ��byte����
     * 
     * @param value
     * @return
     * @throws Exception
     */
    public byte[] integerTo4Bytes(int value)
    {
        byte[] result = new byte[4];
        result[0] = (byte)((value >> 24) & 0xFF);
        result[1] = (byte)((value >> 16) & 0xFF);
        result[2] = (byte)((value >> 8) & 0xFF);
        result[3] = (byte)(value & 0xFF);
        return result;
    }

    /**
     * ��byte[]ת��λ����,ͨ��Ϊָ����
     * 
     * @param value
     * @return
     * @throws Exception
     */
    public int byteToInteger(byte[] value)
    {
        int result;
        if (value.length == 1)
        {
            result = oneByteToInteger(value[0]);
        }
        else if (value.length == 2)
        {
            result = twoBytesToInteger(value);
        }
        else if (value.length == 3)
        {
            result = threeBytesToInteger(value);
        }
        else if (value.length == 4)
        {
            result = fourBytesToInteger(value);
        }
        else
        {
            result = fourBytesToInteger(value);
        }
        return result;
    }

    /**
     * ��һ��byteת��λ����,ͨ��Ϊָ����
     * 
     * @param value
     * @return
     * @throws Exception
     */
    public int oneByteToInteger(byte value)
    {
        return (int)value & 0xFF;
    }

    /**
     * ��һ��2λ������ת��λ����
     * 
     * @param value
     * @return
     * @throws Exception
     */
    public int twoBytesToInteger(byte[] value)
    {
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        return ((temp0 << 8) + temp1);
    }

    /**
     * ��һ��3λ������ת��λ����
     * 
     * @param value
     * @return
     * @throws Exception
     */
    public int threeBytesToInteger(byte[] value)
    {
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        return ((temp0 << 16) + (temp1 << 8) + temp2);
    }

    /**
     * ��һ��4λ������ת��λ����,ͨ��Ϊָ����
     * 
     * @param value
     * @return
     * @throws Exception
     */
    public int fourBytesToInteger(byte[] value)
    {
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        int temp3 = value[3] & 0xFF;
        return ((temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
    }

    /**
     * ��һ��4λ������ת��λ����
     * 
     * @param value
     * @return
     * @throws Exception
     */
    public long fourBytesToLong(byte[] value)
    {
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        int temp3 = value[3] & 0xFF;
        return (((long)temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
    }

    /**
     * ��һ������ת��������
     * 
     * @param value
     * @return
     * @throws Exception
     */
    public long bytes2Long(byte[] value)
    {
        long result = 0;
        int len = value.length;
        int temp;
        for (int i = 0; i < len; i++)
        {
            temp = (len - 1 - i) * 8;
            if (temp == 0)
            {
                result += (value[i] & 0x0ff);
            }
            else
            {
                result += (value[i] & 0x0ff) << temp;
            }
        }
        return result;
    }


    /**
     * ��IP���λint����
     * 
     * @param ip
     * @return
     * @throws Exception
     */
    public int[] getIntIPValue(String ip)
    {
        String[] sip = ip.split(".");
        int[] intIP = { Integer.parseInt(sip[0]), Integer.parseInt(sip[1]), Integer.parseInt(sip[2]), Integer.parseInt(sip[3]) };
        return intIP;
    }

    /**
     * ��byte����IP��ַת��λ�ַ���
     * 
     * @param address
     * @return
     * @throws Exception
     */
    public String getStringIPValue(byte[] address)
    {
        int first = this.oneByteToInteger(address[0]);
        int second = this.oneByteToInteger(address[1]);
        int third = this.oneByteToInteger(address[2]);
        int fourth = this.oneByteToInteger(address[3]);

        return first + "." + second + "." + third + "." + fourth;
    }

}
