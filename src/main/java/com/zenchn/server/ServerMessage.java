package com.zenchn.server;

import org.apache.log4j.Logger;

public class ServerMessage {
	
	Logger log=Logger.getLogger(ServerMessage.class);
	private ReqMessage reqMessage;
	public byte [] getResponse(Message message) {
		// ��� message �Ƿ�ΪPosReqMessage�Ķ���
		if(message instanceof ReqMessage){
			reqMessage =(ReqMessage) message;
			//��������Ӧ ������Ϣ
			return reqMessage.getResponse();
		}
		else
		{
			log.debug("message ����ͬһ������ ");
			return null;
		}
	}

}
