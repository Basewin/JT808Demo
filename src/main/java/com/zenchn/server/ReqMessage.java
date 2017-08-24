package com.zenchn.server;

import org.apache.log4j.Logger;

import com.zenchn.util.ByteUtil;
import com.zenchn.util.ReqMessageUtil;
import com.zenchn.vo.MsgHeader;

public class ReqMessage extends Message {

	private static Logger log = Logger.getLogger(ReqMessage.class);

	// ͷ����Ϣ��֤ ��Ϣ��ȷ��
	public void setMessage(byte[] reqMes) {
		if (reqMes == null) {
			log.info("reqMes Ϊ�ա�������");
		}
		
		if (reqMes.length <= ByteUtil.TITLE_CHAR_LEN) {
			log.info("�������ݴ��󣬳���С��" + ByteUtil.TITLE_CHAR_LEN + "���ֽ�");
		}
		
		msgHeader = new MsgHeader();
		
		//��Ϣid
		byte[] m = new byte[2];
		m[0] = reqMes[1];
		m[1] = reqMes[2];
		msgHeader.setMsgId(Integer.parseInt(ByteUtil.toHexString(m), 16));
		
		//�ն��ֻ���
		byte[] p = new byte[6];
		p[0] = reqMes[5];
		p[1] = reqMes[6];
		p[2] = reqMes[7];
		p[3] = reqMes[8];
		p[4] = reqMes[9];
		p[5] = reqMes[10];
		msgHeader.setTerminalPhone(ByteUtil.toHexString(p));
		
		//��Ϣ��ˮ��
		byte[] f = new byte[2];
		f[0] = reqMes[11];
		f[1] = reqMes[12];
		msgHeader.setFlowId(Integer.parseInt(ByteUtil.toHexString(f), 16));
		
		//��Ϣ����
		int length = reqMes[4];
		msgHeader.setMsgBodyLength(length);
		
//		messageBody = new byte[length];
		// ���ݵĳ��ȣ���������ݳ��ȴ�0ʱ���л�ȡ���ݽ���У��
		if (length > 0
				&& length == (reqMes.length - ByteUtil.FLAG_BIT_HEAD_LENGTH - ByteUtil.FLAG_BIT_CHECK_LENGTH)) {
//			for (int i = 0; i < length; i++) { // ҵ������messageBody
//				messageBody[i] = reqMes[i + ByteUtil.FLAG_BIT_HEAD_LENGTH];
//			}
			messageBody = reqMes;
		} else {
			log.info("setMessage:����");
		}
	}

	/**
	 * ��������:getResponse 
	 * ��������: ҵ����
	 * @return
	 * @author chenbaoyuan
	 * @version 1.0
	 */
	public byte[] getResponse() {
		if (ByteUtil.REGISTER == msgHeader.getMsgId()) {
			log.info("�ն�ע��>>>>>>>>>��Ϣid��"+msgHeader.getMsgId()+";�ն��ֻ��ţ�"+msgHeader.getTerminalPhone()+";��ˮ�ţ�"+msgHeader.getFlowId()+">>>>>");
			return ReqMessageUtil.doRegisterMsg(messageBody, msgHeader); // ע��Э��
		} else {
			log.info("δ֪Э��>>>>>>>>>");
		}
		return null;
	}

}
