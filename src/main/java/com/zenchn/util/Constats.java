package com.zenchn.util;

import java.util.Map;
import java.util.Vector;

public class Constats {
	
	public final static int SET_ACT_LOG = 3; //�ϴ�ҵ������
	public static final String IS_UPDATE = "1"; //���ύ����
	public final static int UPDATE_BIKEPILE_STATE = 4; //׮λ����
	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "ERROR";
	public static final String 	NOCARD = "��Ա��������";
	public static final String 	NOBIKE = "����������";
	//��Ҫ�����ļ����Ͱ汾��Ϣ��ֵ��
	public static Map<String, byte[]> fileVersionHashtable;
	
	//�⻹��ʱ�޼�ֵ��
	public static Map<String, String[]> timeHashtable;
	
	//�豸id վ��id�޼�ֵ��
	public static Map<String, String> equipmentIdWebsiteIdHashtable;
	
	//��ַ�����
	public static Vector<String[]> v = new Vector<String[]>();
	public static Vector<String> v1 = new Vector<String>(); //������
	
	public final static int HAND_TOP_SIZE = 1000; //VectorԪ�ظ������� 
	
	public static  String PROT; //���ն˶˿ں�
	public static  String FTP_URL; //ftp��������ַ
	public static  String FTP_USER; //ftp�������û���
	public static  String FTP_PASSWORD; //ftp����������
	public static  String FTP_FILEPATH; //ftp�������ļ�Ŀ¼
	
	
	//������
	public static int flag = 0;
	//������
	public static int flag1 = 0;
	
	
}
