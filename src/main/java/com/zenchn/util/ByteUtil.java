package com.zenchn.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ByteUtil {
	
	public static final int TITLE_CHAR_LEN = 6;
	public static final String CHARSET = "UTF-8";   
	public final static byte START_CODE1 = (byte) 0xAA; //��Ϣͷ1
	public final static byte START_CODE2 = 0x55; //��Ϣͷ2
	public final static byte START_CODE3 = 0x67; //��Ϣͷ3
	public final static byte START_CODE4 = 0x74; //��Ϣͷ4
	
	public final static byte HEARTBEAT_CODE1 = (byte) 0x80; //����Э��������1
	public final static byte HEARTBEAT_CODE2 = 0x00; //����Э��������2
	
	public final static byte HEARTBEAT_BACK_CODE1 = 0x40; //����Э��������Ӧ��1
	public final static byte HEARTBEAT_BACK_CODE2 = 0x00; //����Э��������Ӧ��2
	
	public final static byte TAKEBIKE_CODE1 = (byte) 0x80; //�⳵Э��������1
	public final static byte TAKEBIKE_CODE2 = 0x01; //�⳵Э��������2
	
	public final static byte TAKEBIKE_BACK_CODE1 = 0x40; //�⳵Э��������Ӧ��1
	public final static byte TAKEBIKE_BACK_CODE2 = 0x01; //�⳵Э��������Ӧ��2
	
	public final static byte BACKBIKE_CODE1 =  (byte) 0x80; //����Э��������1
	public final static byte BACKBIKE_CODE2 =  0x02; //������Э��������2
	
	public final static byte BACKBIKE_BACK_CODE1 =  0x40; //����Э��������Ӧ��1
	public final static byte BACKBIKE_BACK_CODE2 =  0x02; //����Э��������Ӧ��2
	
	public final static byte TAKEBIKE_YC_CODE1 =  (byte) 0x80; //�쳣�⳵Э��������1
	public final static byte TAKEBIKE_YC_CODE2 =   0x03; //�쳣�⳵Э��������2
	
	public final static byte TAKEBIKE_YC_BACK_CODE1 =   0x40; //�쳣�⳵Э��������Ӧ��1
	public final static byte TAKEBIKE_YC_BACK_CODE2 =   0x03; //�쳣�⳵Э��������Ӧ��1
	
	public final static byte BACKBIKE_YC_CODE1 =  (byte) 0x80; //�쳣����Э��������1
	public final static byte BACKBIKE_YC_CODE2 =  0x04; //�쳣����Э��������2
	
	public final static byte BACKBIKE_YC_BACK_CODE1 =  0x40; //�쳣����Э��������Ӧ��1
	public final static byte BACKBIKE_YC_BACK_CODE2 =  0x04; //�쳣����Э��������Ӧ��2
	
	public final static byte TAKEBIKE_RECOVER_CODE1 =  (byte) 0x80; //�⳵�ָ�������1
	public final static byte TAKEBIKE_RECOVER_CODE2 =  0x05; //�⳵�ָ�������2
	
	public final static byte TAKEBIKE_RECOVER_BACK_CODE1 =  0x40; //�⳵�ָ�������Ӧ��1
	public final static byte TAKEBIKE_RECOVER_BACK_CODE2 =  0x05; //�⳵�ָ�������Ӧ��2
	
	public final static byte BACKBIKE_RECOVER_CODE1 =  (byte) 0x80; //�����ָ�������1
	public final static byte BACKBIKE_RECOVER_CODE2 =  0x06; //�����ָ�������2
	
	public final static byte BACKBIKE_RECOVER_BACK_CODE1 =  0x40; //�����ָ�������Ӧ��1
	public final static byte BACKBIKE_RECOVER_BACK_CODE2 =  0x06; //�����ָ�������Ӧ��2
	
	public final static byte BIKEPILE_STATE_INFOMATION_CODE1 = (byte) 0x80; //��λʵʱ��Ϣ������1
	public final static byte BIKEPILE_STATE_INFOMATION_CODE2 = 0x07; //��λʵʱ��Ϣ������2
		
	public final static byte BIKEPILE_STATE_INFOMATION_BACK_CODE1 = 0x40; //��λʵʱ��Ϣ������Ӧ��1
	public final static byte BIKEPILE_STATE_INFOMATION_BACK_CODE2 = 0x07; //��λʵʱ��Ϣ������Ӧ��2
	
	public final static byte IPC_ERROR_INFOMATION_CODE1 = (byte) 0x80; //���ػ�������Ϣ������1
	public final static byte IPC_ERROR_INFOMATION_CODE2 = 0x08; //���ػ�������Ϣ������2
			
	public final static byte IPC_ERROR_INFOMATION_BACK_CODE1 =  0x40; //���ػ�������Ϣ������Ӧ��1 	
	public final static byte IPC_ERROR_INFOMATION_BACK_CODE2 =  0x08; //���ػ�������Ϣ������Ӧ��2
	
	public final static byte BIKE_UP_DOWN_SHELF_CODE1 = (byte) 0x80; //�������¼�������1
	public final static byte BIKE_UP_DOWN_SHELF_CODE2 = 0x09; //�������¼�������2
	
	public final static byte BIKE_UP_DOWN_SHELF_BACK_CODE1 = 0x40; //�������¼�������Ӧ��1
	public final static byte BIKE_UP_DOWN_SHELF_BACK_CODE2 = 0x09; //�������¼�������Ӧ��2
	
	public final static byte IPC_RECONCILIATION_CODE1 = (byte) 0x80; //���ػ�����������1
	public final static byte IPC_RECONCILIATION_CODE2 = 0x0a; //���ػ�����������1
	
	public final static byte IPC_RECONCILIATION_BACK_CODE1 = 0x40; //���ػ�����������Ӧ��1
	public final static byte IPC_RECONCILIATION_BACK_CODE2 = 0x0a; //���ػ�����������Ӧ��1
	
	public final static byte IPC_UPLOAD_FILE_CODE1 = (byte) 0x80; // ���ػ��ϴ��ļ�������1
	public final static byte IPC_UPLOAD_FILE_CODE2 =  0x0b; // ���ػ��ϴ��ļ�������2
	
	public final static byte IPC_UPLOAD_FILE_BACK_CODE1 = 0x40; //���ػ��ϴ��ļ�������Ӧ��1
	public final static byte IPC_UPLOAD_FILE_BACK_CODE2 = 0x0b;//���ػ��ϴ��ļ�������Ӧ��2
	
	public final static byte BACK_BIKE_WHITOUT_MONEY_CODE1 = (byte) 0x80; //����δ�շ�������1
	public final static byte BACK_BIKE_WHITOUT_MONEY_CODE2 = 0x0c; //����δ�շ�������2
	
	public final static byte BACK_BIKE_WHITOUT_MONEY_BACK_CODE1 = (byte) 0x40; //����δ�շ�������Ӧ��1
	public final static byte BACK_BIKE_WHITOUT_MONEY_BACK_CODE2 = (byte) 0x0C; //����δ�շ�������Ӧ��2

	public final static byte RENEWAL_FEES_CODE1 = (byte) 0x80; //Ƿ�ѣ����������շ�)������1
	public final static byte RENEWAL_FEES_CODE2 = 0x0d; //Ƿ�ѣ����������շ�)������2
	
	public final static byte RENEWAL_FEES_BACK_CODE1 = 0x40; //Ƿ�ѣ����������շ�)������Ӧ��1
	public final static byte RENEWAL_FEES_BACK_CODE2 = 0x0d;//Ƿ�ѣ����������շ�)������Ӧ��2
	
	public final static byte DIY_COLLECT_CODE1 = 0x00; // �ֶ��ɼ�������1
	public final static byte DIY_COLLECT_CODE2 = 0x01; // �ֶ��ɼ�������2

	public final static byte DIY_COLLECT_BACK_CODE1 = 0x0c; // �ֶ��ɼ�������Ӧ��1
	public final static byte DIY_COLLECT_BACK_CODE2 = 0x01; // �ֶ��ɼ�������Ӧ��2 
	
	public final static byte POS_EXACT_CODE1 = (byte) 0x80; // 	Pos���쳣����������1
	public final static byte POS_EXACT_CODE2 = 0x0e; // 	Pos���쳣����������2

	public final static byte POS_EXACT_BACK_CODE1 = 0x40; // Pos���쳣����������Ӧ��1
	public final static byte POS_EXACT_BACK_CODE2 = 0x0e; // Pos���쳣����������Ӧ��2 
	
 
	/**
	 * 2���������
	 * @date Aug 10, 2011 10:19:27 AM 
	 * @param no
	 * @param dateb
	 * @return
	 * @author zhangh
	 */
	public  static byte[] plus2Bytes(byte no[],byte dateb[]){
		if(null == no || null == dateb){
			return null;
		}
		byte all[] = new byte[no.length+dateb.length];
		
		for(int j = 0;j<no.length;j++){
			all[j] = no[j];
		}
		for(int k = no.length;k<all.length;k++){
			all[k] = dateb[k-no.length];
		}
		return all;
	}
	/**
	  * �����ֽ�����ת��Ϊint
	  * @param b byte[]
	  * @return int
	  */
	public static long hBytesToInt(byte[] b) {
		long s = 0;
	  for (int i = 0; i < 3; i++) {
	    if (b[i] >= 0) {
	    s = s + b[i];
	    } else {
	    s = s + 256 + b[i];
	    }
	    s = s * 256;
	  }
	  if (b[3] >= 0) {
	    s = s + b[3];
	  } else {
	    s = s + 256 + b[3];
	  }
	  return s;
	} 

	 /**  
     * ���������д�ַ��� �ַ��� �ṹ Ϊ һ��ָ���ַ����ֽڳ��ȵĶ�����+ʵ���ַ���  
     *   
     * @param os  
     * @param str  
     * @throws IOException  
     */ 
	public static byte[] writeUTF(String input){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();   
        DataOutputStream os = new DataOutputStream(baos);     
        
        try {
        	byte[] data = input.getBytes(CHARSET);   
//            os.writeShort(Short.reverseBytes((short) data.length));  
            os.write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
        byte[] b = baos.toByteArray();  
        return b;
	}
	
	/**
	 * 2λ���ֽ�����ת��
	 * @date Aug 10, 2011 5:23:21 PM 
	 * @param b
	 * @return
	 * @author zhangh
	 */
	public static int lBytes2ToInt(byte[] b) {
		  int s = 0;
		  for (int i = 0; i < 1; i++) {
		    if (b[1-i] >= 0) {
		    s = s + b[1-i];
		    } else {
		    s = s + 256 + b[1-i];
		    }
		    s = s * 256;
		  }
		  if (b[0] >= 0) {
		    s = s + b[0];
		  } else {
		    s = s + 256 + b[0];
		  }
		  return s;
		} 
	/**
	 * ���ֽ�����ת��long
	 * @date Aug 10, 2011 3:00:25 PM 
	 * @param b
	 * @return
	 * @author zhangh
	 */
	public static long lBytesToLong(byte[] b) {
		  long s = 0;
		  for (int i = 0; i < 3; i++) {
		    if (b[3-i] >= 0) {
		    s = s + b[3-i];
		    } else {
		    s = s + 256 + b[3-i];
		    }
		    s = s * 256;
		  }
		  if (b[0] >= 0) {
		    s = s + b[0];
		  } else {
		    s = s + 256 + b[0];
		  }
		  return s;
		} 
	/**
	 * 
	 * ��������:str2Bcd
	 * ��������: �ַ���ת��Ϊbcd��
	 * @param asc
	 * @return
	 * Aug 17, 2011 
	 *
	 * @author<a href=zhanghuaup@126.com>�Ż�</a>
	 * @version 1.0
	 *
	 */
	public static byte[] str2Bcd(String asc) {
	    int len = asc.length();
	    int mod = len % 2;

	    if (mod != 0) {
	     asc = "0" + asc;
	     len = asc.length();
	    }

	    byte abt[] = new byte[len];
	    if (len >= 2) {
	     len = len / 2;
	    }

	    byte bbt[] = new byte[len];
	    abt = asc.getBytes();
	    int j, k;

	    for (int p = 0; p < asc.length()/2; p++) {
	     if ( (abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
	      j = abt[2 * p] - '0';
	     } else if ( (abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
	      j = abt[2 * p] - 'a' + 0x0a;
	     } else {
	      j = abt[2 * p] - 'A' + 0x0a;
	     }

	     if ( (abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
	      k = abt[2 * p + 1] - '0';
	     } else if ( (abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
	      k = abt[2 * p + 1] - 'a' + 0x0a;
	     }else {
	      k = abt[2 * p + 1] - 'A' + 0x0a;
	     }

	     int a = (j << 4) + k;
	     byte b = (byte) a;
	     bbt[p] = b;
	    }
	    return bbt;
	}
	
	/**
	 * ������д������
	 * @date Aug 10, 2011 5:22:06 PM 
	 * @param input
	 * @return
	 * @author zhangh
	 */ 
	
	public static byte[] writeInt(int input){
		
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();   
	     DataOutputStream os = new DataOutputStream(baos); 
	     try {
	    	 os.writeInt(Integer.reverseBytes(input)); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	     byte[] b = baos.toByteArray();  
	     return b;
	}
	/**
	 * 
	 * @date Aug 11, 2011 5:23:06 PM 
	 * @param b
	 * @param cut
	 * @return
	 * @author zhangh
	 */
//	   public static byte[] byteCut(byte[] b, byte cut)
//       {
//           List<byte> list = new List<byte>();
//           list.AddRange(b);
//           for (int i = list.Count - 1; i >= 0; i--)
//           {
//               if (list[i] == cut)
//                   list.RemoveAt(i);
//           }
//           byte[] lastbyte = new byte[list.Count];
//           for (int i = 0; i < list.Count; i++)
//           {
//               lastbyte[i] = list[i];
//           }
//           return lastbyte;
//       }

	//ȥ������0x00��ֵ
	public static byte[] byteCut(byte[] b) {
		int count = 0;
		for (int i = 0; i < b.length; i++) {
			if (0x00 != b[i]) {
				count++;
			}
		}
		byte[] byteN = new byte[count];
		count = 0;
		for (int i = 0; i < b.length; i++) {
			if (0x00 != b[i]) {
				byteN[count] = b[i];
				count++;
			}
		}
		return byteN;
	}
	public synchronized static void out(){
		Constats.flag = Constats.flag + 1;
		System.out.println("��>>>>>"+Constats.flag+"<<<������");
	}
	public synchronized static void outOther(){
		Constats.flag1 = Constats.flag1 + 1;
		System.out.println("��>>>>>����������"+Constats.flag1+"����������<<<������");
	}
}
