package com.zenchn.server;

import org.apache.log4j.Logger;

import com.zenchn.util.ByteUtil;


public class ServerData {
	
	public static Logger log = Logger.getLogger(ServerData.class);
	
	public ServerData(){
		
	}
	
	/**
	 * @param reqMes
	 * @return
	 */
	public Message setMessage(byte[] reqMes){

		if(reqMes.length<=0){
			log.info("reqMes������Ϊ��");
			return null;
		}
		//У��ͷβ�ı�ʶλ
		 if (ByteUtil.FLAG_BIT == reqMes[0] && ByteUtil.FLAG_BIT == reqMes[reqMes.length-1]) {
			//��֤��Ϣ���� 
         	int lengthB = reqMes[4];//��Ϣ������
         	if(lengthB == (reqMes.length-ByteUtil.FLAG_BIT_HEAD_LENGTH-ByteUtil.FLAG_BIT_CHECK_LENGTH)){
         		//��֤У����
         		byte code = ByteUtil.checkCode(reqMes,2,reqMes.length-2);
         		if(code == reqMes[reqMes.length-2]){
         			ReqMessage posReqMessage = new ReqMessage();
         			posReqMessage.setMessage(reqMes);
         			return posReqMessage;
         		}else{
         			log.info("��ϢУ���벻��ȷ");
         		}
         	}else{
         		log.info("��Ϣ���Ȳ���ȷ");
         	}
		}else{
			log.info("��ʶλ����ȷ");
		}
		return null;
	}

}
