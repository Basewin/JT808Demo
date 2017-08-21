package com.zenchn.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.zenchn.filter.MessageCodecFactory;

public class TCPServer {
	
	private static Logger logger = Logger.getLogger(TCPServer.class); 
	private ExecutorService filterExecutor = new OrderedThreadPoolExecutor();
	IoAcceptor acceptor = null;
	public  TCPServer(int port) {
		
		acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors()+1); //���ݽ����߳���cpu+1
	    acceptor.getFilterChain().addLast("threadPool",new ExecutorFilter(filterExecutor));//�����̳߳أ���֧�ֶ��߳�
	    IoBuffer.setUseDirectBuffer(false); //��������С�ɸ���
	    IoBuffer.setAllocator(new SimpleBufferAllocator()); //���÷�������ʹ���µĻ�����
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);// ��дͨ��10�����޲����������״̬
		acceptor.getSessionConfig().setWriteTimeout(30000);//���ó�ʱʱ��
		acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new MessageCodecFactory())); //����Զ��������
		acceptor.setHandler(new IOHandler());// ���߼�������
		try {
			acceptor.bind(new InetSocketAddress(port)); //�󶨶˿�
			logger.info("����������ɹ�... �˿ں�Ϊ��" + port);
		} catch (IOException e) {
			logger.error("����������쳣....", e);
			e.printStackTrace();
		}
	}

}

