package com.zenchn.server;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class IOHandler extends IoHandlerAdapter {

	public static Logger log = Logger.getLogger(IOHandler.class);

	 @Override
	 public void sessionCreated(IoSession session) throws Exception {
		 log.info("�������ͻ��˴�������...");
	 }

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		log.info("�������ͻ������Ӵ�...");
		super.sessionOpened(session);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		 System.out.println("��NOTE��>>>>>> �յ��ͻ��˵����ݣ�"+message.toString());
		 
		 String strToClient = "Hello������Server���ҵ�ʱ�����"+System.currentTimeMillis();
         byte[] res = strToClient.getBytes("UTF-8");
         // ��ͻ���д����
         session.write(strToClient);
         
		// System.out.println("����վ��ip����������������"+session.getRemoteAddress());
		//byte[] line = (byte[]) message;
		// System.out.println(line.length +"�ҽ��ܵ������ݵĳ����ǰ� ��������������������������������");
		// ByteUtil.out(); // ���Խ��յ����ݰ���
//		ServerMessage serverMessage = new ServerMessage();
		// ��÷�����������
//		if (null != line) {
//			ServerData serverData = new ServerData();
//			ReqMessage posReqMessage = (ReqMessage) serverData.setMessage(line); // У��ͷ����Ϣ ����ReqMessage����
//			if (null != posReqMessage) {
//				posReqMessage.setIoSession(session);
//				byte[] result = serverMessage.getResponse(posReqMessage); // ҵ����  ������ǰ̨�����Ӧ
//				if (null != result) {
//					session.write(IoBuffer.wrap(result));// ������Ϣ �����Ƿ����ֽ�������ص�
//				}else{
//					log.info("resultΪ�գ���������������");
//				}
//			} else {
//				log.info("posReqMessage��ϢΪ�գ���������");
//			}
//		} else {
//			log.info("line��ϢΪ�գ���������");
//		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
//		session.write(message);
		 log.info("����˷�����Ϣ�ɹ�:"+message);
	}

	// @Override
	// public void sessionClosed(IoSession session) throws Exception {
	//		
	// }

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		// if (session != null && session.isConnected()) {
		// session.close(true);
		// }
		// log.info("����˽������״̬...");
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		if (session != null && session.isConnected()) {
			session.close(true);
		}
		log.error("����˷����쳣...", cause);
	}
}
