package com.zenchn.util;

import org.apache.mina.core.buffer.IoBuffer;

public class IoBufferTOString {
	public static String ioBufferToString(IoBuffer iobuffer){    
		       System.out.println("message = " + iobuffer + iobuffer.limit());    
		        iobuffer.flip();    //����buffer��ǰλ�ã�������ǰλ�����ó�0    
		       byte[] b = new byte[iobuffer.limit()];    
		       iobuffer.get(b);    
		        //�˴���stringbuffer����Ϊ��String�����ַ����������ǲ��ɸ��ĵĳ�������StringBuffer���ַ������������Ķ����ǿ���������޸ĵġ�     
		        StringBuffer stringBuffer = new StringBuffer();    
		            
		        for(int i = 0; i < b.length; i++){    
		           System.out.println("====" + b[i]);    
		           stringBuffer.append((Byte) b[i]); //���Ը�����Ҫ�Լ��ı�����    
		           System.out.println(b[i] +"---------" +i);    
		        }    
		        return stringBuffer.toString();    
		    }   
}
