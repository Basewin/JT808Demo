package com.zenchn.server;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.zenchn.util.ByteUtil;

public class IOHandler extends IoHandlerAdapter {

	public static Logger logger = Logger.getLogger(IOHandler.class);

	 @Override
	 public void sessionCreated(IoSession session) throws Exception {
		 logger.info("�������ͻ���:"+session.getRemoteAddress()+" ��������...");
	 }

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.info("�������ͻ���:"+session.getRemoteAddress()+" ���Ӵ�...");
		super.sessionOpened(session);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		System.out.println("�յ��ͻ��˵����ݣ�"+message.toString());
		String msg = message.toString();
		if(msg.indexOf(" ") > -1){
 			msg = msg.replaceAll(" +","");
 		}
 		byte[] msgByte = ByteUtil.toByteArray(msg);
		ServerMessage serverMessage = new ServerMessage();
		ServerData serverData = new ServerData();
		ReqMessage posReqMessage = (ReqMessage) serverData.setMessage(msgByte);
		// ҵ����  ������ǰ̨�����Ӧ
		byte[] result = serverMessage.getResponse(posReqMessage); 
		
		session.write(ByteUtil.toHexString(result));
		
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
		logger.info("����˷�����Ϣ�ɹ�:"+message);
	}

//	 @Override
//	 public void sessionClosed(IoSession session) throws Exception {
//		 logger.info("����˹ر�����"+session.getRemoteAddress()+"������");
//	 }

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
//		 if (session != null && session.isConnected()) {
//		 session.close(true);
//		 }
//		 logger.info("����˽������״̬...");
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		if (session != null && session.isConnected()) {
			session.close(true);
		}
		logger.error("����˷����쳣...", cause);
	}
}
