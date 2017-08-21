package com.zenchn.server;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.zenchn.util.ByteUtil;
import com.zenchn.util.ByteUtil;
import com.zenchn.util.ReqMessageUtil;


public class ReqMessage extends BikeMessage{
	
	private static Logger log = Logger.getLogger(ReqMessage.class);
	private IoSession ioSession;
	//ͷ����Ϣ��֤ ��Ϣ��ȷ��
	public void setMessage(byte[] reqMes) {
		if (reqMes == null) {
			log.info("reqMes Ϊ�ա�������");
		}
		if (reqMes.length <= ByteUtil.TITLE_CHAR_LEN) {
			log.info("�������ݴ��󣬳���С��" + ByteUtil.TITLE_CHAR_LEN + "���ֽ�");
		}
		for (int i = 0; i < ByteUtil.TITLE_CHAR_LEN; i++) {
			super.mes[i] = reqMes[i];
		}
		int length = reqMes.length - 8 ;
		byte[] mesCodeB = new byte[2];
		mesCodeB[0] = reqMes[ByteUtil.TITLE_CHAR_LEN + length]; //У����
		mesCodeB[1] = reqMes[ByteUtil.TITLE_CHAR_LEN + length + 1]; //У����
		messageBody = new byte[length];
		if (length > 0 && (reqMes.length == ByteUtil.TITLE_CHAR_LEN + 2 +length)) {// ���ݵĳ��ȣ���������ݳ��ȴ�0ʱ���л�ȡ���ݽ���У��
			for (int i = 0; i < reqMes.length - ByteUtil.TITLE_CHAR_LEN - 2; i++) { //ҵ������messageBody
				messageBody[i] = reqMes[i + ByteUtil.TITLE_CHAR_LEN]; 
			}
			int mes = CLibrary.INSTANCE.CRC16_Table(messageBody,(short) messageBody.length); //����У����
			byte[] bcB = ByteUtil.writeInt(mes); 
			if (bcB[0] == mesCodeB[0] && bcB[1] == mesCodeB[1]) {
				log.info("У������ȷ��");
			} else {
				log.info("У�������");
			}
		} else {
			log.info("����");
		}		
	}

	public IoSession getIoSession() {
		return ioSession;
	}

	public void setIoSession(IoSession ioSession) {
		this.ioSession = ioSession;
	}
	/**
	 * 
	 * ��������:getResponse
	 * ��������: ҵ����
	 * @return
	 * Sep 27, 2011 
	 *
	 * @author<a href=zhanghuaup@126.com>�Ż�</a>
	 * @version 1.0
	 *
	 */
	 
	public byte[] getResponse() {	
		if(ByteUtil.HEARTBEAT_CODE1 == messageBody[1] && ByteUtil.HEARTBEAT_CODE2 == messageBody[0]){ 
			return ReqMessageUtil.doHeatBeat(messageBody, ioSession); //����Э��
		}else if(ByteUtil.TAKEBIKE_CODE1 == messageBody[1] && ByteUtil.TAKEBIKE_CODE2 == messageBody[0]){
			return ReqMessageUtil.doTakeBike(messageBody, ioSession); //�⳵Э��
		}else if(ByteUtil.BACKBIKE_CODE1 == messageBody[1] && ByteUtil.BACKBIKE_CODE2 == messageBody[0]){
			return ReqMessageUtil.doBackBike(messageBody, ioSession); //����Э��
		}else if(ByteUtil.TAKEBIKE_YC_CODE1 == messageBody[1] && ByteUtil.TAKEBIKE_YC_CODE2 == messageBody[0]){
			return ReqMessageUtil.doYcTakeBike(messageBody, ioSession);	//�⳵�쳣
		}else if(ByteUtil.BACKBIKE_YC_CODE1 == messageBody[1] && ByteUtil.BACKBIKE_YC_CODE2 == messageBody[0]){
			return ReqMessageUtil.doYcBackBike(messageBody, ioSession); //�����쳣
		}else if(ByteUtil.TAKEBIKE_RECOVER_CODE1 == messageBody[1] && ByteUtil.TAKEBIKE_RECOVER_CODE2 == messageBody[0]){
			return ReqMessageUtil.doTakeBikeRecover(messageBody, ioSession); //�⳵�ָ�
		}else if(ByteUtil.BACKBIKE_RECOVER_CODE1 == messageBody[1] && ByteUtil.BACKBIKE_RECOVER_CODE2 == messageBody[0]){
			return ReqMessageUtil.doBackBikeRecover(messageBody, ioSession); //�����ָ�
		}else if(ByteUtil.BIKEPILE_STATE_INFOMATION_CODE1 == messageBody[1] && ByteUtil.BIKEPILE_STATE_INFOMATION_CODE2 == messageBody[0]){
			return ReqMessageUtil.doBikePileStateInfomation(messageBody, ioSession); //׮λ��Ϣ
		}else if(ByteUtil.IPC_ERROR_INFOMATION_CODE1 == messageBody[1] && ByteUtil.IPC_ERROR_INFOMATION_CODE2 == messageBody[0]){
			return ReqMessageUtil.doIPCErrorInfomation(messageBody, ioSession); //���ػ�������Ϣ
		}else if(ByteUtil.BIKE_UP_DOWN_SHELF_CODE1 == messageBody[1] && ByteUtil.BIKE_UP_DOWN_SHELF_CODE2 == messageBody[0]){
			return ReqMessageUtil.doBikeUpDownShelf(messageBody, ioSession);//	�������¼�
		}else{
			log.info("Э�������󣡣���������1111>>>>>>>>>" +messageBody[0]+ "<<<<<<<<<<<<<1  2>>>>>>" + messageBody[1]+"<<<<<<<<<22");
		}
		return null;
	}
	
	// ����c��dll�ļ�
	public interface CLibrary extends Library {
		CLibrary INSTANCE = (CLibrary) Native.loadLibrary(
				(Platform.isWindows() ? "crc16" : "c"), CLibrary.class);
		int CRC16_Table(byte[] body, short count);
	}

}
