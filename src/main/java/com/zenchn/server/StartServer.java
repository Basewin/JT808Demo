package com.zenchn.server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.zenchn.util.Constats;


public class StartServer {
	public static void main(String[] args) {
		loadInit(); //���س�ʼ����Ϣ
		if(null != Constats.PROT){ 
			new TCPServer(Integer.parseInt(Constats.PROT));
		}else{
			System.out.println("���config/��config.properties�ļ�");
		}
	}
	
	/**
	 * ���������ļ���ʼ���˿�
	 */
	public static void loadInit(){
		String path=System.getProperty("user.dir") + System.getProperty("file.separator") + "/config/config.properties";
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream(new File(path)));
			Properties pro = new Properties();
			pro.load(in);
			Constats.PROT = pro.getProperty("port");
			in.close();
		} catch (Exception e) {
			System.out.println("���س�ʼ��Ϣ�������config/��config.properties�ļ�");
			e.printStackTrace();
			
		}
	}
}
