package com.zenchn.util;


public class ByteUtil {

	/** ��ʶλ **/
	public static final byte FLAG_BIT = 0X7E;
	/** ��ʶλ+��Ϣͷ�ĳ��� **/
	public static final int FLAG_BIT_HEAD_LENGTH = 13;
	/** У����+��ʶλ���� **/
	public static final int FLAG_BIT_CHECK_LENGTH = 2;
	/** ��Ϣͷ���� **/
	public static final int TITLE_CHAR_LEN = 12;
	/**��Ȩ��**/
	public static String REPLY_TOKEN = "1234567890Z";
	
	/**�ն�ע��**/
	public final static int REGISTER = 0X0100;
	/**�ն�ע��Ӧ��**/
	public final static int REGISTER_ANSWER = 0X8100;
	/**�ն�ע����0���ɹ���1�������ѱ�ע�᣻2�����ݿ����޸ó�����3���ն��ѱ�ע�᣻4�����ݿ����޸��ն�**/
	public final static byte REGISTER_RESULT = 00;
	
	/**
	 * 2���������
	 * 
	 * @date Aug 10, 2011 10:19:27 AM
	 * @param no
	 * @param dateb
	 * @return
	 * @author zhangh
	 */
	public static byte[] plus2Bytes(byte no[], byte dateb[]) {
		if (null == no || null == dateb) {
			return null;
		}
		byte all[] = new byte[no.length + dateb.length];

		for (int j = 0; j < no.length; j++) {
			all[j] = no[j];
		}
		for (int k = no.length; k < all.length; k++) {
			all[k] = dateb[k - no.length];
		}
		return all;
	}

	/**
	 * ����У����(У����ָ����Ϣͷ��ʼ��ͬ��һ�ֽ����ֱ��У����ǰһ���ֽڣ�ռ��һ���ֽڡ�)
	 * @param datas
	 * @return
	 */
	public static byte checkCode(byte[] datas,int start,int end){
	    byte temp = datas[1];
	    for (int i = start; i < end; i++) {
	        temp ^= datas[i];
	    }
	    return temp;
	}

	/**
	 * 16���Ƶ��ַ�����ʾת���ֽ�����
	 *
	 * @param hexString 16���Ƹ�ʽ���ַ���            
	 * @return ת������ֽ�����
	 **/
	public static byte[] toByteArray(String hexString) {
	    if (hexString == null || "".equals(hexString))
	       return null;

	    hexString = hexString.toLowerCase();
	    final byte[] byteArray = new byte[hexString.length() / 2];
	    int k = 0;
	    for (int i = 0; i < byteArray.length; i++) {//��Ϊ��16���ƣ����ֻ��ռ��4λ��ת�����ֽ���Ҫ����16���Ƶ��ַ�����λ����
	        byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
	        byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
	        byteArray[i] = (byte) (high << 4 | low);
	        k += 2;
	    }
	    return byteArray;
	}
	
	/**
	 * �ֽ�����ת��16���Ʊ�ʾ��ʽ���ַ���
	 *
	 * @param byteArray Ҫת�����ֽ�����
	 * @return 16���Ʊ�ʾ��ʽ���ַ���
	 **/
	public static String toHexString(byte[] byteArray) {
	    if (byteArray == null || byteArray.length < 1)
	      return ""; 

	    final StringBuilder hexString = new StringBuilder();
	    for (int i = 0; i < byteArray.length; i++) {
	        if ((byteArray[i] & 0xff) < 0x10)//0~Fǰ�治��
	            hexString.append("0");
	        hexString.append(Integer.toHexString(0xFF & byteArray[i]));
	    }
	    return hexString.toString().toUpperCase();
	}
	
	
}
