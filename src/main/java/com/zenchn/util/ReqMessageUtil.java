package com.zenchn.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.zenchn.vo.MsgHeader;

public class ReqMessageUtil {
	
	public static long currTime = System.currentTimeMillis();
	
	/**
	 * ��������:doRegisterMsg
	 * ��������:����ע��Э��
	 * @param messageBody
	 * @param msgHeader
	 * @return
	 */
	public static byte[] doRegisterMsg(byte[] messageBody ,MsgHeader msgHeader){
		
		//Ӧ����ˮ�š��������Ȩ��
		byte[] b = new byte[3];
		b[0] = messageBody[11];
		b[1] = messageBody[12];
		b[2] = ByteUtil.REGISTER_RESULT;//������ɹ�
		//��Ȩ��
		byte[] t = ByteUtil.REPLY_TOKEN.getBytes();
		
		byte[] body = ByteUtil.plus2Bytes(b, t);
		
		//��Ϣͷ��ϢID����Ϣ�����ԡ��ն��ֻ��š���Ϣ��ˮ��
		//��Ϣ������
		byte[] l = BitOperator.integerTo2Bytes(body.length);
		
		//��ϢID
		byte[] i = BitOperator.integerTo2Bytes(ByteUtil.REGISTER_ANSWER);
		
		//�ն��ֻ���
		byte[] p = new byte[6];
		p[0] = messageBody[5];
		p[1] = messageBody[6];
		p[2] = messageBody[7];
		p[3] = messageBody[8];
		p[4] = messageBody[9];
		p[5] = messageBody[10];
		
		//��ˮ��
		byte[] f = new byte[2];
		f[0] = messageBody[11];
		f[1] = messageBody[12];
		byte[] pf = ByteUtil.plus2Bytes(p,f);
				
		byte[] il = ByteUtil.plus2Bytes(i,l);
		
		byte[] header = ByteUtil.plus2Bytes(il,pf);
		
		return BuildMessage(header, body);
	}
	
	/**
	 * ��������:BuildMessage
	 * ��������: ���Ӧ����
	 */
	public static byte[] BuildMessage(byte[] header,byte[] body){

		byte[] cbody = ByteUtil.plus2Bytes(header, body);
		
		//У���������
		byte code = ByteUtil.checkCode(cbody,0,cbody.length);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream os = new DataOutputStream(baos);
		try {
			os.writeByte(ByteUtil.FLAG_BIT);
			os.write(cbody);
			os.writeByte(code);
			os.writeByte(ByteUtil.FLAG_BIT);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
	


}
