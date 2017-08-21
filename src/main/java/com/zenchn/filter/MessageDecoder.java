package com.zenchn.filter;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.zenchn.util.ByteUtil;
import com.zenchn.util.IoBufferTOString;

public class MessageDecoder extends CumulativeProtocolDecoder {
	
	@Override
	protected boolean doDecode(IoSession session, IoBuffer buffer,
			ProtocolDecoderOutput out) throws Exception {
		System.out.println("�ͻ��˷��͵����ݳ��ȣ�"+buffer.limit());
		System.out.println("hasRemaining:"+buffer.hasRemaining());
		System.out.println("remaining:"+buffer.remaining());
		System.out.println("buffer:"+buffer.getString(Charset.forName("GBK").newDecoder()));
		buffer.getString(Charset.forName("GBK").newDecoder());
		System.out.println("");
        // ��ȡ������
        if (buffer.hasRemaining() && buffer.limit() >=  ByteUtil.TITLE_CHAR_LEN && buffer.remaining() >=  ByteUtil.TITLE_CHAR_LEN) {
            byte[] start = new byte[1]; //��ʼ��ʶλ
            byte[] end = new byte[1]; //������ʶλ
            buffer.get(start, 0, 1);
//            buffer.get(end, buffer.limit()-1, buffer.limit());
            System.out.println("start:"+start[0]);
            System.out.println("end:"+end[0]);
            /*if (ByteUtil.START_CODE1 == STX[0]&& ByteUtil.START_CODE2 == STX[1]&& ByteUtil.START_CODE3 == STX[2]&& ByteUtil.START_CODE4 == STX[3]) {//У��ͷ��Ϣ
            	  byte[] lengthB = new byte[2];
                  buffer.get(lengthB, 0, 2);
                  int LEN = ByteUtil.lBytes2ToInt(lengthB); //�õ�У����+��Ϣ�峤��
				if (buffer.remaining() >= LEN + 2) {
					// ��ʼλ������6λ
					buffer.position(buffer.position() - ByteUtil.TITLE_CHAR_LEN);
					// ������
					byte[] MESSAGE = new byte[LEN + ByteUtil.TITLE_CHAR_LEN + 2];
					buffer.get(MESSAGE, 0, MESSAGE.length);
					out.write(MESSAGE);
					return true;
				} else {
					//���ֶΰ� �����ȴ�
					buffer.position(buffer.position() - ByteUtil.TITLE_CHAR_LEN);
					return false;
				}
            }*/
        }
        return false;
	}
}
