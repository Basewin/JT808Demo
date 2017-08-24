package com.zenchn.filter;

import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.zenchn.util.ByteUtil;

public class MessageDecoder extends CumulativeProtocolDecoder {
	
	private static Logger logger = Logger.getLogger(MessageDecoder.class); 
	
	@Override
	protected boolean doDecode(IoSession session, IoBuffer buffer,
			ProtocolDecoderOutput out) throws Exception {
        // ��֤����
        if (buffer.hasRemaining()) {
        	String msg = buffer.getString(Charset.forName("GBK").newDecoder());
    		if(msg.indexOf(" ") > -1){
    			msg = msg.replaceAll(" +","");
    		}
    		byte[] msgByte = ByteUtil.toByteArray(msg);
            if (ByteUtil.FLAG_BIT == msgByte[0] && ByteUtil.FLAG_BIT == msgByte[msgByte.length-1]) {//У��ͷβ�ı�ʶλ
            	//��֤��Ϣ���� 
            	int lengthB = msgByte[4];//��Ϣ�峤��
            	if(lengthB == (msgByte.length-ByteUtil.FLAG_BIT_HEAD_LENGTH-ByteUtil.FLAG_BIT_CHECK_LENGTH)){
            		//��֤У����
            		byte code = ByteUtil.checkCode(msgByte,2,msgByte.length-2);
            		if(code == msgByte[msgByte.length-2]){
     					out.write(msgByte);
     					return true;
            		}else{
            			logger.info("У���벻��ȷ��"+msg);
            			return false;
            		}
            	}else{
        			logger.info("��Ϣ�峤�Ȳ���ȷ��"+msg);
        			return false;
        		}
            }else{
    			logger.info("��Ϣͷβ��ʶλ����ȷ��"+msg);
    			return false;
    		}
        }
        return false;
	}
}
