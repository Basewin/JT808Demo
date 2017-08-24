package com.zenchn.server;

import org.apache.log4j.Logger;

import com.zenchn.vo.MsgHeader;

public class Message {
	private Logger log = Logger.getLogger(Message.class);

	private boolean flag = true;
	
	/**ԭʼ�ַ���**/
	protected String message;
	
	/**��Ϣͷ**/
	protected MsgHeader msgHeader;
	
	/**��Ϣ��**/
	protected byte[] messageBody;// ��Ϣ��

	/**������**/
	protected int mesCode;// ��ϢЧ����
	
	public void errorMessage(String str) {
		log.error("������Ϣ��" + str);
		flag = false;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public byte[] getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(byte[] messageBody) {
		this.messageBody = messageBody;
	}

	public int getMesCode() {
		return mesCode;
	}

	public void setMesCode(int mesCode) {
		this.mesCode = mesCode;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
