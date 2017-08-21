package com.zenchn.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import org.apache.mina.core.session.IoSession;

import com.zenchn.server.ReqMessage.CLibrary;

public class ReqMessageUtil {
	
	public static long currTime = System.currentTimeMillis();
	/**
	 * 
	 * ��������:doHeatBeat
	 * ��������: ����Э��
	 * @param messageBody
	 * @param ioSession
	 * @return
	 * Aug 18, 2011 
	 *
	 * @author<a href=zhanghuaup@126.com>�Ż�</a>
	 * @version 1.0
	 *
	 */
	public static byte[] doHeatBeat(byte[] messageBody ,IoSession ioSession){
//		System.out.println("��������Э��...................");
		byte[] posIdB = new byte[4];
		posIdB[0] = messageBody[2]; 
		posIdB[1] = messageBody[3];
		posIdB[2] = messageBody[4];
		posIdB[3] = messageBody[5]; 
		long  equNo = ByteUtil.lBytesToLong(posIdB); //�пػ�no
//		System.out.println("�пػ���no Ϊ��" + equNo);

		//�༭��Ӧ��Ϣ
		byte[] c = new byte[2];
		c[1] = ByteUtil.HEARTBEAT_BACK_CODE1; //Э����
		c[0] = ByteUtil.HEARTBEAT_BACK_CODE2; //Э����
		String s = "0";
		byte[] sB = s.getBytes(); //Ϊ�յ�ʱ�� ��ֵΪ0
		byte[] body = new byte[107];
		body[0] = messageBody[2]; 
		body[1] = messageBody[3];
		body[2] = messageBody[4];
		body[3] = messageBody[5]; //�п�no
		for(int i = 4;i<=98;i++){
			body[i] = 0x00;
		}
		
		Date now = new Date();  //��ȡ������ʱ��
		String nowYear = String.valueOf(now.getYear()+1900);
		String nowYearA = nowYear.substring(0,2);
		String nowYearB = nowYear.substring(2,4);
		String nowMonth = String.valueOf(now.getMonth()+1);
		if(1 == nowMonth.length()){
			nowMonth = "0" + nowMonth;
		}
		String nowDate = String.valueOf(now.getDate());
		if(1 == nowDate.length()){
			nowDate = "0" + nowDate;
		}
		String nowHour = String.valueOf(now.getHours());
		if(1 == nowHour.length()){
			nowHour = "0" + nowHour;
		}
		String nowMinutes = String.valueOf(now.getMinutes());
		if(1 == nowMinutes.length()){
			nowMinutes = "0" + nowMinutes;
		}
		String nowSeconds = String.valueOf(now.getSeconds());
		if(1 == nowSeconds.length()){
			nowSeconds = "0" + nowSeconds;
		}
		
		String nowS = nowYearA + nowYearB + nowMonth + nowDate + nowHour + nowMinutes + nowSeconds;
//		System.out.println(nowS);
		byte[] nowBcdB = ByteUtil.str2Bcd(nowS);
		System.out.println(nowS.length());
		body[4] = nowBcdB[0];
		body[5] = nowBcdB[1];
		body[6] = nowBcdB[2];
		body[7] = nowBcdB[3];
		body[8] = nowBcdB[4];
		body[9] = nowBcdB[5];
		body[10] = nowBcdB[6];
		
		Map<String, byte[]> fileVersionhd = Constats.fileVersionHashtable;
		if(null != fileVersionhd){
			byte[] gjkV = fileVersionhd.get("0");
			if(null != gjkV){
//				String gjkVS = new String(gjkV); 
//				System.out.println("���������Ͳ����ļ��汾"+gjkVS);
				//���������Ͳ����ļ��汾
				for(int i = 0;i<gjkV.length;i++){
					body[11+i] = gjkV[i];
				}
			}else{
//				System.out.println("���������Ͳ����ļ��汾 null");
				body[11] = sB[0];
			}
			byte[] gjzxcV = fileVersionhd.get("1");
			if(null != gjzxcV){
//				String gjzxcVS = new String(gjzxcV); 
//				System.out.println("�������г����ʲ����汾"+gjzxcVS);
				//�������г����ʲ����汾
				for(int i = 0;i<gjzxcV.length;i++){
					body[19+i] = gjzxcV[i];
				}
			}else{
//				System.out.println("�������г����ʲ����汾�� null");
				body[19] = sB[0];
			}
			byte[] hglV = fileVersionhd.get("2");
			if(null != hglV){
//				String hglVS = new String(hglV); 
//				System.out.println("�ڹ���Ա���ļ��汾"+hglVS);
				//�ڹ���Ա���ļ��汾
				for(int i = 0;i<hglV.length;i++){
					body[27+i] = hglV[i];
				}
			}else{
//				System.out.println("�ڹ���Ա���ļ��汾 null");
				body[27] = sB[0];
			}
			byte[] hcV = fileVersionhd.get("3");
			if(null != hcV){
//				String hcVS = new String(hcV); 
//				System.out.println("/�ڳ��ļ��汾"+hcVS);
				//�ڳ��ļ��汾
				for(int i = 0;i<hcV.length;i++){
					body[35+i] = hcV[i];
				}
			}else{
//				System.out.println("�ڳ��ļ��汾 null");
				body[35] = sB[0];
			}
			byte[] hyhV = fileVersionhd.get("4");
			
			if(null != hyhV){
//				String hyhVS = new String(hyhV); 
//				System.out.println("���û��汾"+hyhVS);
				//���û��汾
				for(int i = 0;i<hyhV.length;i++){
					body[43+i] = hyhV[i];
				}
			}else{
//				System.out.println("���û��汾 null");
				body[43] = sB[0];
			}
			byte[] bglV = fileVersionhd.get("5");
			
			if(null != bglV){
//				String bglVS = new String(bglV); 
//				System.out.println("�׹���Ա���ļ��汾"+bglVS);
				//�׹���Ա���ļ��汾
				for(int i = 0;i<bglV.length;i++){
					body[51+i] = bglV[i];
//					System.out.println(bglV[i]+"����Ա���汾ร�");
				}
			}else{
//				System.out.println("�׹���Ա���ļ��汾 null");
				body[51] = sB[0];
			}
			
			byte[] dtlV = fileVersionhd.get("6");
			if (null != dtlV) {
//				String bglVS = new String(bglV);
				// System.out.println("��ͷ���汾"+bglVS);
				// �׹���Ա���ļ��汾
				for (int i = 0; i < dtlV.length; i++) {
					body[59 + i] = dtlV[i];
					// System.out.println(bglV[i]+"����Ա���汾ร�");
				}
			} else {
				// System.out.println("�׹���Ա���ļ��汾 null");
				body[59] = sB[0];
			}
		}else{
			body[11] = sB[0];
//			body[12] = sB[1];
//			body[13] = sB[2];//���������Ͳ����ļ��汾
			
			body[19] = sB[0];
//			body[20] = sB[1];
//			body[21] = sB[2];//�������г����ʲ����汾
			
			
			
			body[27] = sB[0];
//			body[28] = sB[1];
//			body[29] = sB[2];//�ڹ���Ա���ļ��汾
			
			body[35] = sB[0];
//			body[36] = sB[1];
//			body[37] = sB[2];//�ڳ��ļ��汾
			
			body[43] = sB[0];
//			body[44] = sB[1];
//			body[45] = sB[2];//���û��汾
			
			body[51] = sB[0];
//			body[52] = sB[1];
//			body[53] = sB[2];//�׹���Ա���ļ��汾
			
			body[59] = sB[0];//�������汾 
			
		}
		
		
		body[67] = messageBody[14];
		body[68] = messageBody[15];
		body[69] = messageBody[16];
		body[70] = messageBody[17];
		body[71] = messageBody[18];
		body[72] = messageBody[19];
		body[73] = messageBody[20];
		body[74] = messageBody[21];
		body[75] = messageBody[22];
		body[76] = messageBody[23];
		body[77] = messageBody[24];
		body[78] = messageBody[25];
		body[79] = messageBody[26];
		body[80] = messageBody[27];
		body[81] = messageBody[28];
		body[82] = messageBody[29];//������
		
		body[83] = sB[0];
//		body[84]= sB[1];
//	    body[85]= sB[2]; //�пذ汾
		Map<String, String[]> timeHd = Constats.timeHashtable;
		String[] timeS = timeHd.get(String.valueOf(equNo));
		if(null != timeS){
			String rentalBeginTimeS = timeS[0]; //�⳵��ʼ
			String rentalEndTimeS = timeS[1]; //�⳵����
			String returnBeginTimeS = timeS[2]; //������ʼ
			String returnEndTimeS = timeS[3]; //��������
			String allTime = rentalBeginTimeS + rentalEndTimeS + returnBeginTimeS + returnEndTimeS;
			byte[] allTimeB = allTime.getBytes();
			for(int i = 0 ; i < 16 ; i++){
				body[91+i] = allTimeB[i];
			}
		}
		return BuildMessage(c, body);
	}
	
	/**
	 * 
	 * ��������:doTakeBike
	 * ��������:�⳵Э�� 
	 * @param messageBody
	 * @param ioSession
	 * @return
	 * Aug 18, 2011 
	 *
	 * @author<a href=zhanghuaup@126.com>�Ż�</a>
	 * @version 1.0
	 *
	 */
	public static byte[] doTakeBike(byte[] messageBody ,IoSession ioSession){
//		System.out.println("�����⳵Э�顣������");
		
		StringBuffer sb = new StringBuffer();
		
		byte[] EquipmentNoB = new byte[4];
		EquipmentNoB[0] = messageBody[2]; 
		EquipmentNoB[1] = messageBody[3];
		EquipmentNoB[2] = messageBody[4];
		EquipmentNoB[3] = messageBody[5]; 
		long  equipmentNo = ByteUtil.lBytesToLong(EquipmentNoB); //�пػ�No
//		System.out.println("�пػ���No Ϊ��" + EquipmentNo);
	
	
//		
		byte[] serialNumberB = new byte[4];
		serialNumberB[0] = messageBody[6]; 
		serialNumberB[1] = messageBody[7];
		serialNumberB[2] = messageBody[8];
		serialNumberB[3] = messageBody[9]; 
		long  serialNumber = ByteUtil.lBytesToLong(serialNumberB); //ͨ����ˮ��
//		System.out.println("ͨ����ˮ�� Ϊ��" + serialNumber);
		sb.append("1,");
		sb.append(serialNumber);
		sb.append(",");
		
		int zdType = messageBody[10]; //�ն�����
		if(1 == zdType){
//			System.out.println("���Ǻ���");
		}else if(2 == zdType){
//			System.out.println("���ǳ�׮");
		}else if(3 == zdType){
//			System.out.println("����բ��");
		}
		sb.append(zdType);
		sb.append(",");
		
		
		String zCDateYear = Integer.toHexString((int)messageBody[11]).toString() + Integer.toHexString((int)messageBody[12]).toString();
		String zCDateMonth = Integer.toHexString((int)messageBody[13]).toString();
		String zCDateDate = Integer.toHexString((int)messageBody[14]).toString();
		String zCDateHour = Integer.toHexString((int)messageBody[15]).toString();
		String zCDateMinute = Integer.toHexString((int)messageBody[16]).toString();
		String zCDateSecond = Integer.toHexString((int)messageBody[17]).toString();
		String zCDate = zCDateYear + "-" + zCDateMonth + "-" + zCDateDate + " " + zCDateHour + ":" + zCDateMinute + ":" + zCDateSecond; //�пػ�����
//		System.out.println("�⳵ʱ����:"+zCDate);
		sb.append(zCDate);
		sb.append(",");
		
	
		byte[] beforeTradeMoneyB = new byte[4];
		beforeTradeMoneyB[0] = messageBody[18];
		beforeTradeMoneyB[1] = messageBody[19];
		beforeTradeMoneyB[2] = messageBody[20];
		beforeTradeMoneyB[3] = messageBody[21];
		long beforeTradeMoney = ByteUtil.lBytesToLong(beforeTradeMoneyB);	//����ǰ���
//		System.out.println("����ǰ��"+beforeTradeMoney);
		sb.append(beforeTradeMoney);
		sb.append(",");

		byte[] tradeMoneyB = new byte[4];
		tradeMoneyB[0] = messageBody[22];
		tradeMoneyB[1] = messageBody[23];
		tradeMoneyB[2] = messageBody[24];
		tradeMoneyB[3] = messageBody[25];
		long tradeMoney = ByteUtil.lBytesToLong(tradeMoneyB);	//			���ѽ��
//		System.out.println("���ѽ�"+tradeMoney);
		sb.append(tradeMoney);
		sb.append(",");
	
		byte[] samCardNoB = new byte[4];
		samCardNoB[0] = messageBody[26];
		samCardNoB[1] = messageBody[27];
		samCardNoB[2] = messageBody[28];
		samCardNoB[3] = messageBody[29];
		long samCardNo = ByteUtil.lBytesToLong(samCardNoB);	//				SAM����
//		System.out.println("SAM���ţ�"+samCardNo);
		sb.append(samCardNo);
		sb.append(",");

		int icCardtype = messageBody[30];//	IC��������
//		System.out.println("�ҵĿ������ǣ�"+icCardtype);
		sb.append(icCardtype);
		sb.append(",");
		
		byte[] icCardInNoB = new byte[4];
		icCardInNoB[0] = messageBody[31];
		icCardInNoB[1] = messageBody[32];
		icCardInNoB[2] = messageBody[33];
		icCardInNoB[3] = messageBody[34];
		long icCardInNo = ByteUtil.lBytesToLong(icCardInNoB);	//	IC���ڲ�����
//		System.out.println("ic���ڲ����ţ�"+icCardInNo);
		sb.append(icCardInNo);
		sb.append(",");
	
		byte[] icCardIssueB = new byte[4];
		icCardIssueB[0] = messageBody[35];
		icCardIssueB[1] = messageBody[36];
		icCardIssueB[2] = messageBody[37];
		icCardIssueB[3] = messageBody[38];
		long icCardIssue = ByteUtil.lBytesToLong(icCardIssueB);	//		IC��������ˮ��
//		System.out.println("IC��������ˮ�ţ�"+icCardIssue);
		sb.append(icCardIssue);
		sb.append(",");
		
		String sKDateYear = Integer.toHexString((int)messageBody[39]).toString() + Integer.toHexString((int)messageBody[40]).toString();
		String sKDateMonth = Integer.toHexString((int)messageBody[41]).toString();
		String sKDateDate = Integer.toHexString((int)messageBody[42]).toString();
		String sKDate = sKDateYear + "-" + sKDateMonth + "-" + sKDateDate ; //�ۿ�����
//		System.out.println("�ۿ�������:"+sKDate);
		sb.append(sKDate);
		sb.append(",");
		
		String qbCzDateYear = "20" + Integer.toHexString((int)messageBody[43]).toString();
		String qbCzDateMonth = Integer.toHexString((int)messageBody[44]).toString();
		String qbCzDateDate = Integer.toHexString((int)messageBody[45]).toString();
		String qbCzDateHour = Integer.toHexString((int)messageBody[46]).toString();
		String qbCzDate = qbCzDateYear + "-" + qbCzDateMonth + "-" + qbCzDateDate + " " + qbCzDateHour + ":00:00"; //Ǯ����ֵ����
//		System.out.println("Ǯ����ֵ������:"+qbCzDate);
		sb.append(qbCzDate);
		sb.append(",");
		
		byte[] tradeNoB = new byte[4];
		tradeNoB[0] = messageBody[47];
		tradeNoB[1] = messageBody[48];
		tradeNoB[2] = messageBody[49];
		tradeNoB[3] = messageBody[50];
		long tradeNo = ByteUtil.lBytesToLong(tradeNoB);	//	�������к� (Ǯ�����Ѻ�)
//		System.out.println("�������кţ�"+tradeNo);
		sb.append(tradeNo);
		sb.append(",");
		
		String hYCode1 = Integer.toHexString((int)messageBody[51]).toString();
		String hYCode2 = Integer.toHexString((int)messageBody[52]).toString();
		String hYCode = hYCode1 + hYCode2; //��ҵ����
//		System.out.println("��ҵ���룺"+hYCode);
		sb.append(hYCode);
		sb.append(",");
		
		String cSCode1 = Integer.toHexString((int)messageBody[53]).toString();
		String cSCode2 = Integer.toHexString((int)messageBody[54]).toString();
		String cSCode = cSCode1 + cSCode2; //��Ƭ���д���
//		System.out.println("��Ƭ���д��룺"+cSCode);
		sb.append(cSCode);
		sb.append(",");
		
		byte[] tradeTACNoB = new byte[4];
		tradeTACNoB[0] = messageBody[55];
		tradeTACNoB[1] = messageBody[56];
		tradeTACNoB[2] = messageBody[57];
		tradeTACNoB[3] = messageBody[58];
		long tradeTACNo = ByteUtil.lBytesToLong(tradeTACNoB);	//����TAC�� (Ǯ�����Ѻ�)
//		System.out.println("����TAC�� (Ǯ�����Ѻ�)��"+tradeTACNo);
		sb.append(tradeTACNo);
		sb.append(",");
		
		String pileNo1 = Integer.toHexString((int)messageBody[59]).toString();
		String pileNo2 = Integer.toHexString((int)messageBody[60]).toString();
		String pileNo = pileNo1 + pileNo2; //ͣ��λ��
		if("0".equals(pileNo1)){
			pileNo = pileNo2;
		}
//		System.out.println("ͣ��λ�ţ�"+pileNo);
		sb.append(pileNo);
		sb.append(",");
		
		byte[] bikeRfidNoB = new byte[4];
		bikeRfidNoB[0] = messageBody[61];
		bikeRfidNoB[1] = messageBody[62];
		bikeRfidNoB[2] = messageBody[63];
		bikeRfidNoB[3] = messageBody[64];
		long bikeRfidNo = ByteUtil.lBytesToLong(bikeRfidNoB);	//���г�rfid����
//		System.out.println("���г�rfid���ţ�"+bikeRfidNo);
		sb.append(bikeRfidNo);
		sb.append(",");
		
		int takeType = messageBody[65]; //�⳵����
		if(0 == takeType){
//			System.out.println("�⳵����");
		}else if(1 == takeType){
//			System.out.println("�⳵�ѻ�");
		}
		sb.append(takeType);
		sb.append(",");

		int hcType =  messageBody[66]; //		��������
		if(0 == hcType){
//			System.out.println("����");
		}else if(1 == hcType){
//			System.out.println("����");
		}
		sb.append(hcType);
		sb.append(",");

		byte[] freeTimeB = new byte[4];
		freeTimeB[0] = messageBody[67];
		freeTimeB[1] = messageBody[68];
		int freeTime = ByteUtil.lBytes2ToInt(freeTimeB);//���ʱ�䣨��Ϊ��λ��
//		System.out.println("���ʱ�䣨��Ϊ��λ��:"+freeTime);
		sb.append(freeTime);
		sb.append(",");
		
		byte[] posWebsiteNoB = new byte[16];
		for(int i = 0 ; i < 16 ; i++){
			posWebsiteNoB[i] = messageBody[69+i];
		}
		byte[] posWebsiteNoN = ByteUtil.byteCut(posWebsiteNoB);
		String posWebsiteNo = new String(posWebsiteNoN); //������
//		System.out.println("�пػ������ţ�" + posWebsiteNo);
		sb.append(posWebsiteNo);
		
		String[] s = new String[4]; // 0ҵ������ 1�п�no 2���� 3 ���к�
		s[0] = "3"; //ҵ������
		s[1] = String.valueOf(equipmentNo); //�п�no
		s[2] = sb.toString(); // ����
		s[3] = String.valueOf(serialNumber); //���к�
		Constats.v.add(s);
		int size = 	Constats.v.size();
		if(size > Constats.HAND_TOP_SIZE ){ //VectorԪ�ظ����ﵽHAND_TOP_SIZE��ʱ�ַ�
			inserDB(Constats.v);
		}
	/*	
		//�洢���ݿ�
		if (null != sb.toString() || !"".equals(sb.toString())) {
			Communcation communcation = new Communcation();
			communcation.setCommType("3");// 3Ϊ�ϴ�ҵ������
			communcation.setEquipmentId(equipmentId);
			communcation.setCommContent(sb.toString());
			communcation.setSerialNumber(String.valueOf(serialNumber));
			CommuncationDao communcationDao = new CommuncationDaoImpl();
			communcationDao.setDBConnection(dbc);
			communcationDao.addCommuncation(communcation);
		}*/
		
		//�༭��Ӧ��Ϣ
		byte[] c = new byte[2];
		c[1] = ByteUtil.TAKEBIKE_BACK_CODE1;
		c[0] = ByteUtil.TAKEBIKE_BACK_CODE2;
	
		byte[] body = new byte[10];
		body[0] = messageBody[2]; 
		body[1] = messageBody[3];
		body[2] = messageBody[4];
		body[3] = messageBody[5]; //�п�no
		body[4] = messageBody[6]; 
		body[5] = messageBody[7]; 
		body[6] = messageBody[8]; 
		body[7] = messageBody[9]; //ͨ����ˮ��
		body[8] = 1;       //0:ʧ�� 1:�ɹ�
		body[9] = 0;       //�����Ϊ0,Ϊ�Ժ�Ԥ��
		return BuildMessage(c, body);
		
	}
	
	
	/**
	 * 
	 * ��������:doBackBike
	 * ��������:����Э�� 
	 * @param messageBody
	 * @param ioSession
	 * @return
	 * Aug 18, 2011 
	 *
	 * @author<a href=zhanghuaup@126.com>�Ż�</a>
	 * @version 1.0
	 *
	 */
	public static byte[] doBackBike(byte[] messageBody ,IoSession ioSession){
//		System.out.println("���뻹��Э�顣������");
		byte[] posIdB = new byte[4];
		posIdB[0] = messageBody[2]; 
		posIdB[1] = messageBody[3];
		posIdB[2] = messageBody[4];
		posIdB[3] = messageBody[5]; 
		long  equipmentNo = ByteUtil.lBytesToLong(posIdB); //�пػ�no
//		System.out.println("�пػ���id Ϊ��" + EquipmentNo);
//		EquipmentDao equipmentDao = new EquipmentDaoImpl();
//		equipmentDao.setDBConnection(dbc);
//		String equipmentId = equipmentDao.getEquipmentIdByNo(String.valueOf(EquipmentNo)); //�п�ID
		
		StringBuffer sb = new StringBuffer();
		byte[] serialNumberB = new byte[4];
		serialNumberB[0] = messageBody[6]; 
		serialNumberB[1] = messageBody[7];
		serialNumberB[2] = messageBody[8];
		serialNumberB[3] = messageBody[9]; 
		long  serialNumber = ByteUtil.lBytesToLong(serialNumberB); //ͨ����ˮ��
//		System.out.println("ͨ����ˮ�� Ϊ��" + serialNumber);
		sb.append("2,");
		sb.append(serialNumber);
		sb.append(",");
		
		int type = messageBody[10]; //�ն�����
		if(1 == type){
//			System.out.println("���Ǻ���");
		}else if(2 == type){
//			System.out.println("���ǳ�׮");
		}else if(3 == type){
//			System.out.println("����բ��");
		}
		sb.append(type);
		sb.append(",");
		
		
		String hCDateYear = Integer.toHexString((int)messageBody[11]).toString() + Integer.toHexString((int)messageBody[12]).toString();
		String hCDateMonth = Integer.toHexString((int)messageBody[13]).toString();
		String hCDateDate = Integer.toHexString((int)messageBody[14]).toString();
		String hCDateHour = Integer.toHexString((int)messageBody[15]).toString();
		String hCDateMinute = Integer.toHexString((int)messageBody[16]).toString();
		String hCDateSecond = Integer.toHexString((int)messageBody[17]).toString();
		String hCDate = hCDateYear + "-" + hCDateMonth + "-" + hCDateDate + " " + hCDateHour + ":" + hCDateMinute + ":" + hCDateSecond; //����shijan
//		System.out.println("����ʱ����:"+hCDate);
		sb.append(hCDate);
		sb.append(",");
		
		byte[] depositMoneyB = new byte[4];
		depositMoneyB[0] = messageBody[18]; 
		depositMoneyB[1] = messageBody[19];
		depositMoneyB[2] = messageBody[20];
		depositMoneyB[3] = messageBody[21]; 
		long  depositMoney = ByteUtil.lBytesToLong(depositMoneyB); //Ѻ����
//		System.out.println("Ѻ���� Ϊ��" + depositMoney);
		sb.append(depositMoney);
		sb.append(",");
		
		
		byte[] beforeTradeMoneyB = new byte[4];
		beforeTradeMoneyB[0] = messageBody[22];
		beforeTradeMoneyB[1] = messageBody[23];
		beforeTradeMoneyB[2] = messageBody[24];
		beforeTradeMoneyB[3] = messageBody[25];
		long beforeTradeMoney = ByteUtil.lBytesToLong(beforeTradeMoneyB);	//����ǰ���
//		System.out.println("����ǰ��"+beforeTradeMoney);
		sb.append(beforeTradeMoney);
		sb.append(",");

		byte[] tradeMoneyB = new byte[4];
		tradeMoneyB[0] = messageBody[26];
		tradeMoneyB[1] = messageBody[27];
		tradeMoneyB[2] = messageBody[28];
		tradeMoneyB[3] = messageBody[29];
		long tradeMoney = ByteUtil.lBytesToLong(tradeMoneyB);	//			���ѽ��
//		System.out.println("���ѽ�"+tradeMoney);
		sb.append(tradeMoney);
		sb.append(",");
	
		byte[] samCardNoB = new byte[4];
		samCardNoB[0] = messageBody[30];
		samCardNoB[1] = messageBody[31];
		samCardNoB[2] = messageBody[32];
		samCardNoB[3] = messageBody[33];
		long samCardNo = ByteUtil.lBytesToLong(samCardNoB);	//				SAM����
//		System.out.println("	SAM���ţ�"+samCardNo);
		sb.append(samCardNo);
		sb.append(",");

		int icCardtype = messageBody[34];//	IC��������
//		System.out.println("�ҵĿ������ǣ�"+icCardtype);
		sb.append(icCardtype);
		sb.append(",");

		
		byte[] icCardInNoB = new byte[4];
		icCardInNoB[0] = messageBody[35];
		icCardInNoB[1] = messageBody[36];
		icCardInNoB[2] = messageBody[37];
		icCardInNoB[3] = messageBody[38];
		long icCardInNo = ByteUtil.lBytesToLong(icCardInNoB);	//	IC���ڲ�����
//		System.out.println("	ic���ڲ����ţ�"+icCardInNo);
		sb.append(icCardInNo);
		sb.append(",");
	
		byte[] icCardIssueB = new byte[4];
		icCardIssueB[0] = messageBody[39];
		icCardIssueB[1] = messageBody[40];
		icCardIssueB[2] = messageBody[41];
		icCardIssueB[3] = messageBody[41];
		long icCardIssue = ByteUtil.lBytesToLong(icCardIssueB);	//		IC��������ˮ��
//		System.out.println("	IC��������ˮ�ţ�"+icCardIssue);
		sb.append(icCardIssue);
		sb.append(",");
		
		String sKDateYear = Integer.toHexString((int)messageBody[43]).toString() + Integer.toHexString((int)messageBody[44]).toString();
		String sKDateMonth = Integer.toHexString((int)messageBody[45]).toString();
		String sKDateDate = Integer.toHexString((int)messageBody[46]).toString();
		String sKDate = sKDateYear + "-" + sKDateMonth + "-" + sKDateDate ; //�ۿ�����
//		System.out.println("�ۿ�������:"+sKDate);
		sb.append(sKDate);
		sb.append(",");
		
		String yXDateYear = Integer.toHexString((int)messageBody[47]).toString() + Integer.toHexString((int)messageBody[48]).toString();
		String yXDateMonth = Integer.toHexString((int)messageBody[49]).toString();
		String yXDateDate = Integer.toHexString((int)messageBody[50]).toString();
		String yXDate = yXDateYear + "-" + yXDateMonth + "-" + yXDateDate ; //��Ч����
//		System.out.println("��Ч������:"+yXDate);
		sb.append(yXDate);
		sb.append(",");
		
		String qYDateYear = Integer.toHexString((int)messageBody[51]).toString() + Integer.toHexString((int)messageBody[52]).toString();
		String qYDateMonth = Integer.toHexString((int)messageBody[53]).toString();
		String qYDateDate = Integer.toHexString((int)messageBody[54]).toString();
		String qYDate = qYDateYear + "-" + qYDateMonth + "-" + qYDateDate ; //��������
//		System.out.println("����������:"+qYDate);
		sb.append(qYDate);
		sb.append(",");
		
		
		byte[] tradeNoB = new byte[4];
		tradeNoB[0] = messageBody[55];
		tradeNoB[1] = messageBody[56];
		tradeNoB[2] = messageBody[57];
		tradeNoB[3] = messageBody[58];
		long tradeNo = ByteUtil.lBytesToLong(tradeNoB);	//	�������к� (Ǯ�����Ѻ�)
//		System.out.println("�������кţ�"+tradeNo);
		sb.append(tradeNo);
		sb.append(",");
		
		String hYCode1 = Integer.toHexString((int)messageBody[59]).toString();
		String hYCode2 = Integer.toHexString((int)messageBody[60]).toString();
		String hYCode = hYCode1 + hYCode2; //��ҵ����
//		System.out.println("��ҵ���룺"+hYCode);
		sb.append(hYCode);
		sb.append(",");
		
		String cSCode1 = Integer.toHexString((int)messageBody[61]).toString();
		String cSCode2 = Integer.toHexString((int)messageBody[62]).toString();
		String cSCode = cSCode1 + cSCode2; //��Ƭ���д���
//		System.out.println("��Ƭ���д��룺"+cSCode);
		sb.append(cSCode);
		sb.append(",");
		
		byte[] tradeTACNoB = new byte[4];
		tradeTACNoB[0] = messageBody[63];
		tradeTACNoB[1] = messageBody[64];
		tradeTACNoB[2] = messageBody[65];
		tradeTACNoB[3] = messageBody[66];
		long tradeTACNo = ByteUtil.lBytesToLong(tradeTACNoB);	//����TAC�� (Ǯ�����Ѻ�)
//		System.out.println("����TAC�� (Ǯ�����Ѻ�)��"+tradeTACNo);
		sb.append(tradeTACNo);
		sb.append(",");
		
//		String zCDateYear = Integer.toHexString((int)messageBody[67]).toString() + Integer.toHexString((int)messageBody[68]).toString();
		String zCDateYear = "20" + Integer.toHexString((int)messageBody[68]).toString();
		String zCDateMonth = Integer.toHexString((int)messageBody[69]).toString();
		String zCDateDate = Integer.toHexString((int)messageBody[70]).toString();
		String zCDateHour = Integer.toHexString((int)messageBody[71]).toString();
		String zCDateMinute = Integer.toHexString((int)messageBody[72]).toString();
		String zCDateSecond = Integer.toHexString((int)messageBody[73]).toString();
		String zCDate = zCDateYear + "-" + zCDateMonth + "-" + zCDateDate + " " + zCDateHour + ":" + zCDateMinute + ":" + zCDateSecond; //�пػ�����
//		System.out.println("�⳵ʱ����:"+zCDate);
		sb.append(zCDate);
		sb.append(",");
		
		String pileNo1 = Integer.toHexString((int)messageBody[74]).toString();
		String pileNo2 = Integer.toHexString((int)messageBody[75]).toString();
		String pileNo = pileNo1 + pileNo2; //ͣ��λ��
		if("0".equals(pileNo1)){
			pileNo = pileNo2;
		}
//		System.out.println("ͣ��λ�ţ�"+pileNo);
		sb.append(pileNo);
		sb.append(",");
		
		byte[] bikeRfidNoB = new byte[4];
		bikeRfidNoB[0] = messageBody[76];
		bikeRfidNoB[1] = messageBody[77];
		bikeRfidNoB[2] = messageBody[78];
		bikeRfidNoB[3] = messageBody[79];
		long bikeRfidNo = ByteUtil.lBytesToLong(bikeRfidNoB);	//���г�rfid����
//		System.out.println("���г�rfid���ţ�"+bikeRfidNo);
		sb.append(bikeRfidNo);
		sb.append(",");
		
		int takeType = messageBody[80]; //�⳵����
		if(0 == takeType){
//			System.out.println("��������");
		}else if(1 == takeType){
//			System.out.println("�����ѻ�");
		}
		sb.append(takeType);
		sb.append(",");

		int hcType =  messageBody[81]; //		��������
		if(0 == hcType){
//			System.out.println("����");
		}else if(1 == hcType){
//			System.out.println("����");
		}
		sb.append(hcType);
		sb.append(",");
		
		byte[] posNoB = new byte[16];
		for(int i = 0 ; i < 16 ; i++){
			posNoB[i] = messageBody[82+i];
		}
		byte[] posNoN = ByteUtil.byteCut(posNoB);
		String posNo = new String(posNoN); //������
//		System.out.println("�пػ������ţ�" + posNo);
		sb.append(posNo);
		sb.append(",");
		
		byte[] freeTimeB = new byte[4];
		freeTimeB[0] = messageBody[97];
		freeTimeB[1] = messageBody[98];
		int freeTime = ByteUtil.lBytes2ToInt(freeTimeB);//���ʱ�䣨��Ϊ��λ��
	//	System.out.println("���ʱ�䣨��Ϊ��λ��:"+freeTime);
		sb.append(freeTime);
		
		String[] s = new String[4]; // 0ҵ������ 1�п�no 2���� 3 ���к�
		s[0] = "3"; //ҵ������
		s[1] = String.valueOf(equipmentNo); //�п�no
		s[2] = sb.toString(); // ����
		s[3] = String.valueOf(serialNumber); //���к�
		Constats.v.add(s);
		int size = 	Constats.v.size();
		if(size > Constats.HAND_TOP_SIZE ){ 
			inserDB(Constats.v);
		}
		
	/*	//�洢���ݿ�
		if (null != sb.toString() || !"".equals(sb.toString())) {
			Communcation communcation = new Communcation();
			communcation.setCommType("3");// 3Ϊ�ϴ�ҵ������
			communcation.setEquipmentId(equipmentId);
			communcation.setCommContent(sb.toString());
			communcation.setSerialNumber(String.valueOf(serialNumber));
			CommuncationDao communcationDao = new CommuncationDaoImpl();
			communcationDao.setDBConnection(dbc);
			communcationDao.addCommuncation(communcation);
		}*/
		
		//�༭��Ӧ��Ϣ
		byte[] c = new byte[2];
		c[1] = ByteUtil.BACKBIKE_BACK_CODE1;
		c[0] = ByteUtil.BACKBIKE_BACK_CODE2;
	
		byte[] body = new byte[10];
		body[0] = messageBody[2]; 
		body[1] = messageBody[3];
		body[2] = messageBody[4];
		body[3] = messageBody[5]; //�п�no
		body[4] = messageBody[6]; 
		body[5] = messageBody[7]; 
		body[6] = messageBody[8]; 
		body[7] = messageBody[9]; //ͨ����ˮ��
		body[8] = 1;       //0:ʧ�� 1:�ɹ�
		body[9] = 0;       //�����Ϊ0,Ϊ�Ժ�Ԥ��
		return BuildMessage(c, body);
	}
	
	/**
	 * 
	 * ��������:doYcTakeBike
	 * ��������: �쳣�⳵
	 * @param messageBody
	 * @param ioSession
	 * @return
	 * Aug 18, 2011 
	 *
	 * @author<a href=zhanghuaup@126.com>�Ż�</a>
	 * @version 1.0
	 *
	 */
	public static byte[] doYcTakeBike(byte[] messageBody ,IoSession ioSession){
//		System.out.println("�����⳵�쳣Э��" + messageBody.length);
		byte[] posIdB = new byte[4];
		posIdB[0] = messageBody[2]; 
		posIdB[1] = messageBody[3];
		posIdB[2] = messageBody[4];
		posIdB[3] = messageBody[5]; 
		long  equipmentNo = ByteUtil.lBytesToLong(posIdB); //�пػ�no
//		System.out.println("�пػ���No Ϊ��" + EquipmentNo);
//		EquipmentDao equipmentDao = new EquipmentDaoImpl();
//		equipmentDao.setDBConnection(dbc);
//		String equipmentId = equipmentDao.getEquipmentIdByNo(String.valueOf(EquipmentNo)); //�п�ID
		
		
		StringBuffer sb = new StringBuffer();
		byte[] serialNumberB = new byte[4];
		serialNumberB[0] = messageBody[6]; 
		serialNumberB[1] = messageBody[7];
		serialNumberB[2] = messageBody[8];
		serialNumberB[3] = messageBody[9]; 
		long  serialNumber = ByteUtil.lBytesToLong(serialNumberB); //ͨ����ˮ��
//		System.out.println("ͨ����ˮ�� Ϊ��" + serialNumber);
		sb.append("3,");
		sb.append(serialNumber);
		sb.append(",");
		
		int type = messageBody[10]; //�ն�����
		if(1 == type){
//			System.out.println("���Ǻ���");
		}else if(2 == type){
//			System.out.println("���ǳ�׮");
		}else if(3 == type){
//			System.out.println("����բ��");
		}
		sb.append(type);
		sb.append(",");
		
		
		String zCDateYear = Integer.toHexString((int)messageBody[11]).toString() + Integer.toHexString((int)messageBody[12]).toString();
		String zCDateMonth = Integer.toHexString((int)messageBody[13]).toString();
		String zCDateDate = Integer.toHexString((int)messageBody[14]).toString();
		String zCDateHour = Integer.toHexString((int)messageBody[15]).toString();
		String zCDateMinute = Integer.toHexString((int)messageBody[16]).toString();
		String zCDateSecond = Integer.toHexString((int)messageBody[17]).toString();
		String zCDate = zCDateYear + "-" + zCDateMonth + "-" + zCDateDate + " " + zCDateHour + ":" + zCDateMinute + ":" + zCDateSecond; //�пػ�����
//		System.out.println("�⳵ʱ����:"+zCDate);
		sb.append(zCDate);
		sb.append(",");
	
		byte[] beforeTradeMoneyB = new byte[4];
		beforeTradeMoneyB[0] = messageBody[18];
		beforeTradeMoneyB[1] = messageBody[19];
		beforeTradeMoneyB[2] = messageBody[20];
		beforeTradeMoneyB[3] = messageBody[21];
		long beforeTradeMoney = ByteUtil.lBytesToLong(beforeTradeMoneyB);	//����ǰ���
//		System.out.println("����ǰ��"+beforeTradeMoney);
		sb.append(beforeTradeMoney);
		sb.append(",");

		byte[] tradeMoneyB = new byte[4];
		tradeMoneyB[0] = messageBody[22];
		tradeMoneyB[1] = messageBody[23];
		tradeMoneyB[2] = messageBody[24];
		tradeMoneyB[3] = messageBody[25];
		long tradeMoney = ByteUtil.lBytesToLong(tradeMoneyB);	//			���ѽ��
//		System.out.println("���ѽ�"+tradeMoney);
		sb.append(tradeMoney);
		sb.append(",");
	
		byte[] samCardNoB = new byte[4];
		samCardNoB[0] = messageBody[26];
		samCardNoB[1] = messageBody[27];
		samCardNoB[2] = messageBody[28];
		samCardNoB[3] = messageBody[29];
		long samCardNo = ByteUtil.lBytesToLong(samCardNoB);	//				SAM����
//		System.out.println("	SAM���ţ�"+samCardNo);
		sb.append(samCardNo);
		sb.append(",");

		int icCardtype = messageBody[30];//	IC��������
//		System.out.println("�ҵĿ������ǣ�"+icCardtype);
		sb.append(icCardtype);
		sb.append(",");
		
		byte[] icCardInNoB = new byte[4];
		icCardInNoB[0] = messageBody[31];
		icCardInNoB[1] = messageBody[32];
		icCardInNoB[2] = messageBody[33];
		icCardInNoB[3] = messageBody[34];
		long icCardInNo = ByteUtil.lBytesToLong(icCardInNoB);	//	IC���ڲ�����
//		System.out.println("	ic���ڲ����ţ�"+icCardInNo);
		sb.append(icCardInNo);
		sb.append(",");
	
		byte[] icCardIssueB = new byte[4];
		icCardIssueB[0] = messageBody[35];
		icCardIssueB[1] = messageBody[36];
		icCardIssueB[2] = messageBody[37];
		icCardIssueB[3] = messageBody[38];
		long icCardIssue = ByteUtil.lBytesToLong(icCardIssueB);	//		IC��������ˮ��
//		System.out.println("	IC��������ˮ�ţ�"+icCardIssue);
		sb.append(icCardIssue);
		sb.append(",");
		
		String sKDateYear = Integer.toHexString((int)messageBody[39]).toString() + Integer.toHexString((int)messageBody[40]).toString();
		String sKDateMonth = Integer.toHexString((int)messageBody[41]).toString();
		String sKDateDate = Integer.toHexString((int)messageBody[42]).toString();
		String sKDate = sKDateYear + "-" + sKDateMonth + "-" + sKDateDate ; //�ۿ�����
//		System.out.println("�ۿ�������:"+sKDate);
		sb.append(sKDate);
		sb.append(",");
		
		String qbCzDateYear = "20" + Integer.toHexString((int)messageBody[43]).toString();
		String qbCzDateMonth = Integer.toHexString((int)messageBody[44]).toString();
		String qbCzDateDate = Integer.toHexString((int)messageBody[45]).toString();
		String qbCzDateHour = Integer.toHexString((int)messageBody[46]).toString();
		String qbCzDate = qbCzDateYear + "-" + qbCzDateMonth + "-" + qbCzDateDate + " " + qbCzDateHour + ":00:00"; //Ǯ����ֵ����
	//	System.out.println("Ǯ����ֵ������:"+qbCzDate);
		sb.append(qbCzDate);
		sb.append(",");
		
		byte[] tradeNoB = new byte[4];
		tradeNoB[0] = messageBody[47];
		tradeNoB[1] = messageBody[48];
		tradeNoB[2] = messageBody[49];
		tradeNoB[3] = messageBody[50];
		long tradeNo = ByteUtil.lBytesToLong(tradeNoB);	//	�������к� (Ǯ�����Ѻ�)
//		System.out.println("�������кţ�"+tradeNo);
		sb.append(tradeNo);
		sb.append(",");
		
		String hYCode1 = Integer.toHexString((int)messageBody[51]).toString();
		String hYCode2 = Integer.toHexString((int)messageBody[52]).toString();
		String hYCode = hYCode1 + hYCode2; //��ҵ����
//		System.out.println("��ҵ���룺"+hYCode);
		sb.append(hYCode);
		sb.append(",");
		
		String cSCode1 = Integer.toHexString((int)messageBody[53]).toString();
		String cSCode2 = Integer.toHexString((int)messageBody[54]).toString();
		String cSCode = cSCode1 + cSCode2; //��Ƭ���д���
//		System.out.println("��Ƭ���д��룺"+cSCode);
		sb.append(cSCode);
		sb.append(",");
		
		byte[] tradeTACNoB = new byte[4];
		tradeTACNoB[0] = messageBody[55];
		tradeTACNoB[1] = messageBody[56];
		tradeTACNoB[2] = messageBody[57];
		tradeTACNoB[3] = messageBody[58];
		long tradeTACNo = ByteUtil.lBytesToLong(tradeTACNoB);	//����TAC�� (Ǯ�����Ѻ�)
//		System.out.println("����TAC�� (Ǯ�����Ѻ�)��"+tradeTACNo);
		sb.append(tradeTACNo);
		sb.append(",");
		
		String pileNo1 = Integer.toHexString((int)messageBody[59]).toString();
		String pileNo2 = Integer.toHexString((int)messageBody[60]).toString();
		
		String pileNo = pileNo1 + pileNo2; //ͣ��λ��
		if("0".equals(pileNo1)){
			pileNo =  pileNo2;
		}
//		System.out.println("ͣ��λ�ţ�"+pileNo);
		
		sb.append(pileNo);
		sb.append(",");
		
		byte[] bikeRfidNoB = new byte[4];
		bikeRfidNoB[0] = messageBody[61];
		bikeRfidNoB[1] = messageBody[62];
		bikeRfidNoB[2] = messageBody[63];
		bikeRfidNoB[3] = messageBody[64];
		long bikeRfidNo = ByteUtil.lBytesToLong(bikeRfidNoB);	//���г�rfid����
//		System.out.println("���г�rfid���ţ�"+bikeRfidNo);
		sb.append(bikeRfidNo);
		sb.append(",");
		
		int takeType = messageBody[65]; //�⳵����
		if(0 == takeType){
//			System.out.println("�⳵����");
		}else if(1 == takeType){
//			System.out.println("�⳵�ѻ�");
		}
		sb.append(takeType);
		sb.append(",");
		
		int hcType =  messageBody[66]; //		��������
		if(0 == hcType){
//			System.out.println("����");
		}else if(1 == hcType){
//			System.out.println("����");
		}
		sb.append(hcType);
		sb.append(",");
		
		int takeBikeType = messageBody[67]; //	�⳵״̬
		if(0 == takeBikeType){
	//		System.out.println("�⳵����");
		}else if(1 == takeBikeType){
//			System.out.println("���⣬��δȡ������");
		}
		sb.append(takeBikeType);
		sb.append(",");

		byte[] freeTimeB = new byte[4];
		freeTimeB[0] = messageBody[68];
		freeTimeB[1] = messageBody[69];
		int freeTime = ByteUtil.lBytes2ToInt(freeTimeB);//���ʱ�䣨��Ϊ��λ��
//		System.out.println("���ʱ�䣨��Ϊ��λ��:"+freeTime);
		sb.append(freeTime);
		sb.append(",");
		
		byte[] posNoB = new byte[16];
		for(int i = 0 ; i < 16 ; i++){
			posNoB[i] = messageBody[70+i];
		}
		byte[] posNoN = ByteUtil.byteCut(posNoB);
		String posNo = new String(posNoN); //������
//		System.out.println("�пػ������ţ�" + posNo);
		sb.append(posNo);
		
		

		String[] s = new String[4]; // 0ҵ������ 1�п�no 2���� 3 ���к�
		s[0] = "3"; //ҵ������
		s[1] = String.valueOf(equipmentNo); //�п�no
		s[2] = sb.toString(); // ����
		s[3] = String.valueOf(serialNumber); //���к�
		Constats.v.add(s);
		int size = 	Constats.v.size();
		if(size > Constats.HAND_TOP_SIZE ){
			inserDB(Constats.v);
		}
		
		/*//�洢���ݿ�
		if (null != sb.toString() || !"".equals(sb.toString())) {
			Communcation communcation = new Communcation();
			communcation.setCommType("3");// 3Ϊ�ϴ�ҵ������
			communcation.setEquipmentId(equipmentId);
			communcation.setCommContent(sb.toString());
			communcation.setSerialNumber(String.valueOf(serialNumber));
			CommuncationDao communcationDao = new CommuncationDaoImpl();
			communcationDao.setDBConnection(dbc);
			communcationDao.addCommuncation(communcation);
		}*/
		
		
		//�༭��Ӧ��Ϣ
		byte[] c = new byte[2];
		c[1] = ByteUtil.TAKEBIKE_BACK_CODE1;
		c[0] = ByteUtil.TAKEBIKE_BACK_CODE2;
	
		byte[] body = new byte[10];
		body[0] = messageBody[2]; 
		body[1] = messageBody[3];
		body[2] = messageBody[4];
		body[3] = messageBody[5]; //�п�no
		body[4] = messageBody[6]; 
		body[5] = messageBody[7]; 
		body[6] = messageBody[8]; 
		body[7] = messageBody[9]; //ͨ����ˮ��
		body[8] = 1;       //0:ʧ�� 1:�ɹ�
		body[9] = 0;       //�����Ϊ0,Ϊ�Ժ�Ԥ��
		return BuildMessage(c, body);
	}
	/**
	 * 
	 * ��������:doYcBackBike
	 * ��������: �쳣����
	 * @param messageBody
	 * @param ioSession
	 * @return
	 * Aug 18, 2011 
	 *
	 * @author<a href=zhanghuaup@126.com>�Ż�</a>
	 * @version 1.0
	 *
	 */
	public static byte[] doYcBackBike(byte[] messageBody ,IoSession ioSession){
//		System.out.println("���뻹���쳣Э�顣������");
		byte[] posIdB = new byte[4];
		posIdB[0] = messageBody[2]; 
		posIdB[1] = messageBody[3];
		posIdB[2] = messageBody[4];
		posIdB[3] = messageBody[5]; 
		long  equipmentNo = ByteUtil.lBytesToLong(posIdB); //�пػ�no
//		System.out.println("�пػ���No Ϊ��" + EquipmentNo);
//		EquipmentDao equipmentDao = new EquipmentDaoImpl();
//		equipmentDao.setDBConnection(dbc);
//		String equipmentId = equipmentDao.getEquipmentIdByNo(String.valueOf(EquipmentNo)); //�п�ID
		
		StringBuffer sb = new StringBuffer();
		byte[] serialNumberB = new byte[4];
		serialNumberB[0] = messageBody[6]; 
		serialNumberB[1] = messageBody[7];
		serialNumberB[2] = messageBody[8];
		serialNumberB[3] = messageBody[9]; 
		long  serialNumber = ByteUtil.lBytesToLong(serialNumberB); //ͨ����ˮ��
//		System.out.println("ͨ����ˮ�� Ϊ��" + serialNumber);
		sb.append("4,");
		sb.append(serialNumber);
		sb.append(",");
		
		int type = messageBody[10]; //�ն�����
		if(1 == type){
//			System.out.println("���Ǻ���");
		}else if(2 == type){
//			System.out.println("���ǳ�׮");
		}else if(3 == type){
//			System.out.println("����բ��");
		}
		sb.append(type);
		sb.append(",");
		
		String hCDateYear = Integer.toHexString((int)messageBody[11]).toString() + Integer.toHexString((int)messageBody[12]).toString();
		String hCDateMonth = Integer.toHexString((int)messageBody[13]).toString();
		String hCDateDate = Integer.toHexString((int)messageBody[14]).toString();
		String hCDateHour = Integer.toHexString((int)messageBody[15]).toString();
		String hCDateMinute = Integer.toHexString((int)messageBody[16]).toString();
		String hCDateSecond = Integer.toHexString((int)messageBody[17]).toString();
		String hCDate = hCDateYear + "-" + hCDateMonth + "-" + hCDateDate + " " + hCDateHour + ":" + hCDateMinute + ":" + hCDateSecond; //����shijan
//		System.out.println("����ʱ����:"+hCDate);
		sb.append(hCDate);
		sb.append(",");
		
		byte[] depositMoneyB = new byte[4];
		depositMoneyB[0] = messageBody[18]; 
		depositMoneyB[1] = messageBody[19];
		depositMoneyB[2] = messageBody[20];
		depositMoneyB[3] = messageBody[21]; 
		long  depositMoney = ByteUtil.lBytesToLong(depositMoneyB); //Ѻ����
//		System.out.println("Ѻ���� Ϊ��" + depositMoney);
		sb.append(depositMoney);
		sb.append(",");
		
		byte[] beforeTradeMoneyB = new byte[4];
		beforeTradeMoneyB[0] = messageBody[22];
		beforeTradeMoneyB[1] = messageBody[23];
		beforeTradeMoneyB[2] = messageBody[24];
		beforeTradeMoneyB[3] = messageBody[25];
		long beforeTradeMoney = ByteUtil.lBytesToLong(beforeTradeMoneyB);	//����ǰ���
	//	System.out.println("����ǰ��"+beforeTradeMoney);
		sb.append(beforeTradeMoney);
		sb.append(",");
		

		byte[] tradeMoneyB = new byte[4];
		tradeMoneyB[0] = messageBody[26];
		tradeMoneyB[1] = messageBody[27];
		tradeMoneyB[2] = messageBody[28];
		tradeMoneyB[3] = messageBody[29];
		long tradeMoney = ByteUtil.lBytesToLong(tradeMoneyB);	//			���ѽ��
	//	System.out.println("���ѽ�"+tradeMoney);
		sb.append(tradeMoney);
		sb.append(",");
	
		byte[] samCardNoB = new byte[4];
		samCardNoB[0] = messageBody[30];
		samCardNoB[1] = messageBody[31];
		samCardNoB[2] = messageBody[32];
		samCardNoB[3] = messageBody[33];
		long samCardNo = ByteUtil.lBytesToLong(samCardNoB);	//				SAM����
	//	System.out.println("	SAM���ţ�"+samCardNo);
		sb.append(samCardNo);
		sb.append(",");

		int icCardtype = messageBody[34];//	IC��������
//		System.out.println("�ҵĿ������ǣ�"+icCardtype);
		sb.append(icCardtype);
		sb.append(",");
		
		byte[] icCardInNoB = new byte[4];
		icCardInNoB[0] = messageBody[35];
		icCardInNoB[1] = messageBody[36];
		icCardInNoB[2] = messageBody[37];
		icCardInNoB[3] = messageBody[38];
		long icCardInNo = ByteUtil.lBytesToLong(icCardInNoB);	//	IC���ڲ�����
	//	System.out.println("	ic���ڲ����ţ�"+icCardInNo);
		sb.append(icCardInNo);
		sb.append(",");
	
		byte[] icCardIssueB = new byte[4];
		icCardIssueB[0] = messageBody[39];
		icCardIssueB[1] = messageBody[40];
		icCardIssueB[2] = messageBody[41];
		icCardIssueB[3] = messageBody[42];
		long icCardIssue = ByteUtil.lBytesToLong(icCardIssueB);	//		IC��������ˮ��
//		System.out.println("	IC��������ˮ�ţ�"+icCardIssue);
		sb.append(icCardIssue);
		sb.append(",");
		
		String sKDateYear = Integer.toHexString((int)messageBody[43]).toString() + Integer.toHexString((int)messageBody[44]).toString();
		String sKDateMonth = Integer.toHexString((int)messageBody[45]).toString();
		String sKDateDate = Integer.toHexString((int)messageBody[46]).toString();
		String sKDate = sKDateYear + "-" + sKDateMonth + "-" + sKDateDate ; //�ۿ�����
	//	System.out.println("�ۿ�������:"+sKDate);
		sb.append(sKDate);
		sb.append(",");
		
		String yXDateYear = Integer.toHexString((int)messageBody[47]).toString() + Integer.toHexString((int)messageBody[48]).toString();
		String yXDateMonth = Integer.toHexString((int)messageBody[49]).toString();
		String yXDateDate = Integer.toHexString((int)messageBody[50]).toString();
		String yXDate = yXDateYear + "-" + yXDateMonth + "-" + yXDateDate ; //��Ч����
//		System.out.println("��Ч������:"+yXDate);
		sb.append(yXDate);
		sb.append(",");
		
		String qYDateYear = Integer.toHexString((int)messageBody[51]).toString() + Integer.toHexString((int)messageBody[52]).toString();
		String qYDateMonth = Integer.toHexString((int)messageBody[53]).toString();
		String qYDateDate = Integer.toHexString((int)messageBody[54]).toString();
		String qYDate = qYDateYear + "-" + qYDateMonth + "-" + qYDateDate ; //��������
	//	System.out.println("����������:"+qYDate);
		sb.append(qYDate);
		sb.append(",");
		
		byte[] tradeNoB = new byte[4];
		tradeNoB[0] = messageBody[55];
		tradeNoB[1] = messageBody[56];
		tradeNoB[2] = messageBody[57];
		tradeNoB[3] = messageBody[58];
		long tradeNo = ByteUtil.lBytesToLong(tradeNoB);	//	�������к� (Ǯ�����Ѻ�)
//		System.out.println("�������кţ�"+tradeNo);
		sb.append(tradeNo);
		sb.append(",");
		
		String hYCode1 = Integer.toHexString((int)messageBody[58]).toString();
		String hYCode2 = Integer.toHexString((int)messageBody[60]).toString();
		String hYCode = hYCode1 + hYCode2; //��ҵ����
//		System.out.println("��ҵ���룺"+hYCode);
		sb.append(hYCode);
		sb.append(",");
		
		String cSCode1 = Integer.toHexString((int)messageBody[61]).toString();
		String cSCode2 = Integer.toHexString((int)messageBody[62]).toString();
		String cSCode = cSCode1 + cSCode2; //��Ƭ���д���
//		System.out.println("��Ƭ���д��룺"+cSCode);
		sb.append(cSCode);
		sb.append(",");
		
		byte[] tradeTACNoB = new byte[4];
		tradeTACNoB[0] = messageBody[63];
		tradeTACNoB[1] = messageBody[64];
		tradeTACNoB[2] = messageBody[65];
		tradeTACNoB[3] = messageBody[66];
		long tradeTACNo = ByteUtil.lBytesToLong(tradeTACNoB);	//����TAC�� (Ǯ�����Ѻ�)
//		System.out.println("����TAC�� (Ǯ�����Ѻ�)��"+tradeTACNo);
		sb.append(tradeTACNo);
		sb.append(",");
//		Integer.toHexString((int)messageBody[67]).toString()
		String zCDateYear =  "20"+ Integer.toHexString((int)messageBody[68]).toString();
		if(Integer.toHexString((int)messageBody[68]).toString().length() == 1){
			zCDateYear =  "200"+ Integer.toHexString((int)messageBody[68]).toString();
		}
		String zCDateMonth = Integer.toHexString((int)messageBody[69]).toString();
		String zCDateDate = Integer.toHexString((int)messageBody[70]).toString();
		String zCDateHour = Integer.toHexString((int)messageBody[71]).toString();
		String zCDateMinute = Integer.toHexString((int)messageBody[72]).toString();
		String zCDateSecond = Integer.toHexString((int)messageBody[73]).toString();
		String zCDate = zCDateYear + "-" + zCDateMonth + "-" + zCDateDate + " " + zCDateHour + ":" + zCDateMinute + ":" + zCDateSecond; //�пػ�����
//		System.out.println("�⳵ʱ����:"+zCDate);
		
		sb.append(zCDate);
		sb.append(",");
		
		String pileNo1 = Integer.toHexString((int)messageBody[74]).toString();
		String pileNo2 = Integer.toHexString((int)messageBody[75]).toString();
		String pileNo = pileNo1 + pileNo2; //ͣ��λ��
		if("0".equals(pileNo1)){
			pileNo = pileNo2;
		}
//		System.out.println("ͣ��λ�ţ�"+pileNo);
		sb.append(pileNo);
		sb.append(",");
		
		
		byte[] bikeRfidNoB = new byte[4];
		bikeRfidNoB[0] = messageBody[76];
		bikeRfidNoB[1] = messageBody[77];
		bikeRfidNoB[2] = messageBody[78];
		bikeRfidNoB[3] = messageBody[79];
		long bikeRfidNo = ByteUtil.lBytesToLong(bikeRfidNoB);	//���г�rfid����
//		System.out.println("���г�rfid���ţ�"+bikeRfidNo);
		sb.append(bikeRfidNo);
		sb.append(",");
		
		int takeType = messageBody[80]; //�⳵����
		if(0 == takeType){
	//		System.out.println("�⳵����");
		}else if(1 == takeType){
//			System.out.println("�⳵�ѻ�");
		}
		sb.append(takeType);
		sb.append(",");

		int hcType =  messageBody[81]; //		��������
		if(0 == hcType){
	//		System.out.println("����");
		}else if(1 == hcType){
//			System.out.println("����");
		}
		sb.append(hcType);
		sb.append(",");
		
		int backState = messageBody[82]; //����״̬
		if(0 == backState ){
//			System.out.println("����״̬�� ����");
		}else if(1 == backState){
//			System.out.println("����������������δ�ۿ����ȡ��");
		}
		sb.append(backState);
		sb.append(",");

		
		byte[] posNoB = new byte[16];
		for(int i = 0 ; i < 16 ; i++){
			posNoB[i] = messageBody[83+i];
		}
		byte[] posNoN = ByteUtil.byteCut(posNoB);
		String posNo = new String(posNoN); //������
//		System.out.println("�пػ������ţ�" + posNo);
		sb.append(posNo);
		sb.append(",");

		byte[] freeTimeB = new byte[2];
		freeTimeB[0] = messageBody[99];
		freeTimeB[1] = messageBody[100];
		int freeTime = ByteUtil.lBytes2ToInt(freeTimeB);//���ʱ�䣨��Ϊ��λ��
//		System.out.println("���ʱ�䣨��Ϊ��λ��:"+freeTime);
		sb.append(freeTime);
		
		String[] s = new String[4]; // 0ҵ������ 1�п�no 2���� 3 ���к�
		s[0] = "3"; //ҵ������
		s[1] = String.valueOf(equipmentNo); //�п�no
		s[2] = sb.toString(); // ����
		s[3] = String.valueOf(serialNumber); //���к�
		Constats.v.add(s);
		int size = 	Constats.v.size();
		if(size > Constats.HAND_TOP_SIZE ){
			inserDB(Constats.v);
		}
	/*	
		//�洢���ݿ�
		if (null != sb.toString() || !"".equals(sb.toString())) {
			Communcation communcation = new Communcation();
			communcation.setCommType("3");// 3Ϊ�ϴ�ҵ������
			communcation.setEquipmentId(equipmentId);
			communcation.setCommContent(sb.toString());
			communcation.setSerialNumber(String.valueOf(serialNumber));
			CommuncationDao communcationDao = new CommuncationDaoImpl();
			communcationDao.setDBConnection(dbc);
			communcationDao.addCommuncation(communcation);
		}*/

		//�༭��Ӧ��Ϣ
		byte[] c = new byte[2];
		c[1] = ByteUtil.BACKBIKE_BACK_CODE1;
		c[0] = ByteUtil.BACKBIKE_BACK_CODE2;
	
		byte[] body = new byte[10];
		body[0] = messageBody[2]; 
		body[1] = messageBody[3];
		body[2] = messageBody[4];
		body[3] = messageBody[5]; //�п�no
		body[4] = messageBody[6]; 
		body[5] = messageBody[7]; 
		body[6] = messageBody[8]; 
		body[7] = messageBody[9]; //ͨ����ˮ��
		body[8] = 1;       //0:ʧ�� 1:�ɹ�
		body[9] = 0;       //�����Ϊ0,Ϊ�Ժ�Ԥ��
		return BuildMessage(c, body);
	}
	/**
	 * 
	 * ��������:doTakeBikeRecover
	 * ��������: �ָ��⳵Э��
	 * @param messageBody
	 * @param ioSession
	 * @return
	 * Aug 18, 2011 
	 *
	 * @author<a href=zhanghuaup@126.com>�Ż�</a>
	 * @version 1.0
	 *
	 */
	public static byte[] doTakeBikeRecover(byte[] messageBody ,IoSession ioSession){
//		System.out.println("�����⳵�ָ�Э�顣������");
		byte[] posIdB = new byte[4];
		posIdB[0] = messageBody[2]; 
		posIdB[1] = messageBody[3];
		posIdB[2] = messageBody[4];
		posIdB[3] = messageBody[5]; 
		long  equipmentNo = ByteUtil.lBytesToLong(posIdB); //�пػ�no
//		System.out.println("�пػ���id Ϊ��" + EquipmentNo);
//		EquipmentDao equipmentDao = new EquipmentDaoImpl();
//		equipmentDao.setDBConnection(dbc);
//		String equipmentId = equipmentDao.getEquipmentIdByNo(String.valueOf(EquipmentNo)); //�п�ID
		
		StringBuffer sb = new StringBuffer();
		byte[] serialNumberB = new byte[4];
		serialNumberB[0] = messageBody[6]; 
		serialNumberB[1] = messageBody[7];
		serialNumberB[2] = messageBody[8];
		serialNumberB[3] = messageBody[9]; 
		long  serialNumber = ByteUtil.lBytesToLong(serialNumberB); //ͨ����ˮ��
//		System.out.println("ͨ����ˮ�� Ϊ��" + serialNumber);
		sb.append("5,");
		sb.append(serialNumber);
		sb.append(",");
		
		int type = messageBody[10]; //�ն�����
		if(1 == type){
//			System.out.println("���Ǻ���");
		}else if(2 == type){
//			System.out.println("���ǳ�׮");
		}else if(3 == type){
//			System.out.println("����բ��");
		}
		sb.append(type);
		sb.append(",");
		
		String zCDateYear = Integer.toHexString((int)messageBody[11]).toString() + Integer.toHexString((int)messageBody[12]).toString();
		String zCDateMonth = Integer.toHexString((int)messageBody[13]).toString();
		String zCDateDate = Integer.toHexString((int)messageBody[14]).toString();
		String zCDateHour = Integer.toHexString((int)messageBody[15]).toString();
		String zCDateMinute = Integer.toHexString((int)messageBody[16]).toString();
		String zCDateSecond = Integer.toHexString((int)messageBody[17]).toString();
		String zCDate = zCDateYear + "-" + zCDateMonth + "-" + zCDateDate + " " + zCDateHour + ":" + zCDateMinute + ":" + zCDateSecond; //�пػ�����
//		System.out.println("�⳵ʱ����:"+zCDate);
		sb.append(zCDate);
		sb.append(",");
	
		byte[] beforeTradeMoneyB = new byte[4];
		beforeTradeMoneyB[0] = messageBody[18];
		beforeTradeMoneyB[1] = messageBody[19];
		beforeTradeMoneyB[2] = messageBody[20];
		beforeTradeMoneyB[3] = messageBody[21];
		long beforeTradeMoney = ByteUtil.lBytesToLong(beforeTradeMoneyB);	//����ǰ���
//		System.out.println("����ǰ��"+beforeTradeMoney);
		sb.append(beforeTradeMoney);
		sb.append(",");

		byte[] tradeMoneyB = new byte[4];
		tradeMoneyB[0] = messageBody[22];
		tradeMoneyB[1] = messageBody[23];
		tradeMoneyB[2] = messageBody[24];
		tradeMoneyB[3] = messageBody[25];
		long tradeMoney = ByteUtil.lBytesToLong(tradeMoneyB);	//			���ѽ��
//		System.out.println("���ѽ�"+tradeMoney);
		sb.append(tradeMoney);
		sb.append(",");
	
		byte[] samCardNoB = new byte[4];
		samCardNoB[0] = messageBody[26];
		samCardNoB[1] = messageBody[27];
		samCardNoB[2] = messageBody[28];
		samCardNoB[3] = messageBody[29];
		long samCardNo = ByteUtil.lBytesToLong(samCardNoB);	//				SAM����
//		System.out.println("	SAM���ţ�"+samCardNo);
		sb.append(samCardNo);
		sb.append(",");

		int icCardtype = messageBody[30];//	IC��������
//		System.out.println("�ҵĿ������ǣ�"+icCardtype);
		sb.append(icCardtype);
		sb.append(",");

		
		byte[] icCardInNoB = new byte[4];
		icCardInNoB[0] = messageBody[31];
		icCardInNoB[1] = messageBody[32];
		icCardInNoB[2] = messageBody[33];
		icCardInNoB[3] = messageBody[34];
		long icCardInNo = ByteUtil.lBytesToLong(icCardInNoB);	//	IC���ڲ�����
//		System.out.println("	ic���ڲ����ţ�"+icCardInNo);
		sb.append(icCardInNo);
		sb.append(",");
	
		byte[] icCardIssueB = new byte[4];
		icCardIssueB[0] = messageBody[35];
		icCardIssueB[1] = messageBody[36];
		icCardIssueB[2] = messageBody[37];
		icCardIssueB[3] = messageBody[38];
		long icCardIssue = ByteUtil.lBytesToLong(icCardIssueB);	//		IC��������ˮ��
//		System.out.println("	IC��������ˮ�ţ�"+icCardIssue);
		sb.append(icCardIssue);
		sb.append(",");
		
		String sKDateYear = Integer.toHexString((int)messageBody[39]).toString() + Integer.toHexString((int)messageBody[40]).toString();
		String sKDateMonth = Integer.toHexString((int)messageBody[41]).toString();
		String sKDateDate = Integer.toHexString((int)messageBody[42]).toString();
		String sKDate = sKDateYear + "-" + sKDateMonth + "-" + sKDateDate ; //�ۿ�����
	//	System.out.println("�ۿ�������:"+sKDate);
		sb.append(sKDate);
		sb.append(",");
		
		String qbCzDateYear = "20" + Integer.toHexString((int)messageBody[43]).toString();
		String qbCzDateMonth = Integer.toHexString((int)messageBody[44]).toString();
		String qbCzDateDate = Integer.toHexString((int)messageBody[45]).toString();
		String qbCzDateHour = Integer.toHexString((int)messageBody[46]).toString();
		String qbCzDate = qbCzDateYear + "-" + qbCzDateMonth + "-" + qbCzDateDate + " " + qbCzDateHour + ":00:00"; //Ǯ����ֵ����
//		System.out.println("Ǯ����ֵ������:"+qbCzDate);
		sb.append(qbCzDate);
		sb.append(",");
		
		byte[] tradeNoB = new byte[4];
		tradeNoB[0] = messageBody[47];
		tradeNoB[1] = messageBody[48];
		tradeNoB[2] = messageBody[49];
		tradeNoB[3] = messageBody[50];
		long tradeNo = ByteUtil.lBytesToLong(tradeNoB);	//	�������к� (Ǯ�����Ѻ�)
//		System.out.println("�������кţ�"+tradeNo);
		sb.append(tradeNo);
		sb.append(",");
		
		String hYCode1 = Integer.toHexString((int)messageBody[51]).toString();
		String hYCode2 = Integer.toHexString((int)messageBody[52]).toString();
		String hYCode = hYCode1 + hYCode2; //��ҵ����
//		System.out.println("��ҵ���룺"+hYCode);
		sb.append(hYCode);
		sb.append(",");
		
		String cSCode1 = Integer.toHexString((int)messageBody[53]).toString();
		String cSCode2 = Integer.toHexString((int)messageBody[54]).toString();
		String cSCode = cSCode1 + cSCode2; //��Ƭ���д���
//		System.out.println("��Ƭ���д��룺"+cSCode);
		sb.append(cSCode);
		sb.append(",");
		
		byte[] tradeTACNoB = new byte[4];
		tradeTACNoB[0] = messageBody[55];
		tradeTACNoB[1] = messageBody[56];
		tradeTACNoB[2] = messageBody[57];
		tradeTACNoB[3] = messageBody[58];
		long tradeTACNo = ByteUtil.lBytesToLong(tradeTACNoB);	//����TAC�� (Ǯ�����Ѻ�)
//		System.out.println("����TAC�� (Ǯ�����Ѻ�)��"+tradeTACNo);
		sb.append(tradeTACNo);
		sb.append(",");
		
		String pileNo1 = Integer.toHexString((int)messageBody[59]).toString();
		String pileNo2 = Integer.toHexString((int)messageBody[60]).toString();
		String pileNo = pileNo1 + pileNo2; //ͣ��λ��
//		System.out.println("ͣ��λ�ţ�"+pileNo);
		if("0".equals(pileNo1)){
			pileNo = pileNo2;
		}
//		System.out.println("ͣ��λ�ţ�"+pileNo);
		sb.append(pileNo);
		sb.append(",");
		
		byte[] bikeRfidNoB = new byte[4];
		bikeRfidNoB[0] = messageBody[61];
		bikeRfidNoB[1] = messageBody[62];
		bikeRfidNoB[2] = messageBody[63];
		bikeRfidNoB[3] = messageBody[64];
		long bikeRfidNo = ByteUtil.lBytesToLong(bikeRfidNoB);	//���г�rfid����
//		System.out.println("���г�rfid���ţ�"+bikeRfidNo);
		sb.append(bikeRfidNo);
		sb.append(",");
		
		int takeType = messageBody[65]; //�⳵����
		if(0 == takeType){
//			System.out.println("�⳵����");
		}else if(1 == takeType){
//			System.out.println("�⳵�ѻ�");
		}
		sb.append(takeType);
		sb.append(",");

		int hcType =  messageBody[66]; //		��������
		if(0 == hcType){
//			System.out.println("����");
		}else if(1 == hcType){
//			System.out.println("����");
		}
		sb.append(hcType);
		sb.append(",");

		byte[] freeTimeB = new byte[4];
		freeTimeB[0] = messageBody[67];
		freeTimeB[1] = messageBody[68];
		int freeTime = ByteUtil.lBytes2ToInt(freeTimeB);//���ʱ�䣨��Ϊ��λ��
	//	System.out.println("���ʱ�䣨��Ϊ��λ��:"+freeTime);
		sb.append(freeTime);
		sb.append(",");
		
		byte[] posNoB = new byte[16];
		for(int i = 0 ; i < 16 ; i++){
			posNoB[i] = messageBody[69+i];
		}
		byte[] posNoN = ByteUtil.byteCut(posNoB);
		String posNo = new String(posNoN); //������
	//	System.out.println("�пػ������ţ�" + posNo);
		sb.append(posNo);
		
		String[] s = new String[4]; // 0ҵ������ 1�п�no 2���� 3 ���к�
		s[0] = "3"; //ҵ������
		s[1] = String.valueOf(equipmentNo); //�п�no
		s[2] = sb.toString(); // ����
		s[3] = String.valueOf(serialNumber); //���к�
		Constats.v.add(s);
		int size = 	Constats.v.size();
		if(size > Constats.HAND_TOP_SIZE ){
			inserDB(Constats.v);
		}
		
		
	/*	//�洢���ݿ�
		if (null != sb.toString() || !"".equals(sb.toString())) {
			Communcation communcation = new Communcation();
			communcation.setCommType("3");// 3Ϊ�ϴ�ҵ������
			communcation.setEquipmentId(equipmentId);
			communcation.setCommContent(sb.toString());
			communcation.setSerialNumber(String.valueOf(serialNumber));
			CommuncationDao communcationDao = new CommuncationDaoImpl();
			communcationDao.setDBConnection(dbc);
			communcationDao.addCommuncation(communcation);
		}*/
		
		//�༭��Ӧ��Ϣ
		byte[] c = new byte[2];
		c[1] = ByteUtil.TAKEBIKE_RECOVER_BACK_CODE1;
		c[0] = ByteUtil.TAKEBIKE_RECOVER_BACK_CODE2;
	
		byte[] body = new byte[10];
		body[0] = messageBody[2]; 
		body[1] = messageBody[3];
		body[2] = messageBody[4];
		body[3] = messageBody[5]; //�п�no
		body[4] = messageBody[6]; 
		body[5] = messageBody[7]; 
		body[6] = messageBody[8]; 
		body[7] = messageBody[9]; //ͨ����ˮ��
		body[8] = 1;       //0:ʧ�� 1:�ɹ�
		body[9] = 0;       //�����Ϊ0,Ϊ�Ժ�Ԥ��
		return BuildMessage(c, body);
		
	}
	/**
	 * 
	 * ��������:doBackBikeRecover
	 * ��������:�ָ�����Э��
	 * @param messageBody
	 * @param ioSession
	 * @return
	 * Aug 18, 2011 
	 *
	 * @author<a href=zhanghuaup@126.com>�Ż�</a>
	 * @version 1.0
	 *
	 */
	public static byte[] doBackBikeRecover(byte[] messageBody ,IoSession ioSession){
//		System.out.println("���뻹���ָ�Э�顣������");
		byte[] posIdB = new byte[4];
		posIdB[0] = messageBody[2]; 
		posIdB[1] = messageBody[3];
		posIdB[2] = messageBody[4];
		posIdB[3] = messageBody[5]; 
		long  equipmentNo = ByteUtil.lBytesToLong(posIdB); //�пػ�id
//		System.out.println("�пػ���id Ϊ��" + EquipmentNo);
//		EquipmentDao equipmentDao = new EquipmentDaoImpl();
//		equipmentDao.setDBConnection(dbc);
//		String equipmentId = equipmentDao.getEquipmentIdByNo(String.valueOf(EquipmentNo)); //�п�ID
		
		StringBuffer sb = new StringBuffer();
		byte[] serialNumberB = new byte[4];
		serialNumberB[0] = messageBody[6]; 
		serialNumberB[1] = messageBody[7];
		serialNumberB[2] = messageBody[8];
		serialNumberB[3] = messageBody[9]; 
		long  serialNumber = ByteUtil.lBytesToLong(serialNumberB); //ͨ����ˮ��
//		System.out.println("ͨ����ˮ�� Ϊ��" + serialNumber);
		sb.append("6,");
		sb.append(serialNumber);
		sb.append(",");
		
		int type = messageBody[10]; //�ն�����
		if(1 == type){
//			System.out.println("���Ǻ���");
		}else if(2 == type){
//			System.out.println("���ǳ�׮");
		}else if(3 == type){
//			System.out.println("����բ��");
		}
		sb.append(type);
		sb.append(",");
		
		
		String hCDateYear = Integer.toHexString((int)messageBody[11]).toString() + Integer.toHexString((int)messageBody[12]).toString();
		String hCDateMonth = Integer.toHexString((int)messageBody[13]).toString();
		String hCDateDate = Integer.toHexString((int)messageBody[14]).toString();
		String hCDateHour = Integer.toHexString((int)messageBody[15]).toString();
		String hCDateMinute = Integer.toHexString((int)messageBody[16]).toString();
		String hCDateSecond = Integer.toHexString((int)messageBody[17]).toString();
		String hCDate = hCDateYear + "-" + hCDateMonth + "-" + hCDateDate + " " + hCDateHour + ":" + hCDateMinute + ":" + hCDateSecond; //����shijan
//		System.out.println("����ʱ����:"+hCDate);
		sb.append(hCDate);
		sb.append(",");
		
		byte[] depositMoneyB = new byte[4];
		depositMoneyB[0] = messageBody[18]; 
		depositMoneyB[1] = messageBody[19];
		depositMoneyB[2] = messageBody[20];
		depositMoneyB[3] = messageBody[21]; 
		long  depositMoney = ByteUtil.lBytesToLong(depositMoneyB); //Ѻ����
//		System.out.println("Ѻ���� Ϊ��" + depositMoney);
		sb.append(depositMoney);
		sb.append(",");
		
		byte[] beforeTradeMoneyB = new byte[4];
		beforeTradeMoneyB[0] = messageBody[22];
		beforeTradeMoneyB[1] = messageBody[23];
		beforeTradeMoneyB[2] = messageBody[24];
		beforeTradeMoneyB[3] = messageBody[25];
		long beforeTradeMoney = ByteUtil.lBytesToLong(beforeTradeMoneyB);	//����ǰ���
//		System.out.println("����ǰ��"+beforeTradeMoney);
		sb.append(beforeTradeMoney);
		sb.append(",");

		byte[] tradeMoneyB = new byte[4];
		tradeMoneyB[0] = messageBody[26];
		tradeMoneyB[1] = messageBody[27];
		tradeMoneyB[2] = messageBody[28];
		tradeMoneyB[3] = messageBody[29];
		long tradeMoney = ByteUtil.lBytesToLong(tradeMoneyB);	//			���ѽ��
//		System.out.println("���ѽ�"+tradeMoney);
		sb.append(tradeMoney);
		sb.append(",");
	
		byte[] samCardNoB = new byte[4];
		samCardNoB[0] = messageBody[30];
		samCardNoB[1] = messageBody[31];
		samCardNoB[2] = messageBody[32];
		samCardNoB[3] = messageBody[33];
		long samCardNo = ByteUtil.lBytesToLong(samCardNoB);	//				SAM����
//		System.out.println("	SAM���ţ�"+samCardNo);
		sb.append(samCardNo);
		sb.append(",");

		int icCardtype = messageBody[34];//	IC��������
//		System.out.println("�ҵĿ������ǣ�"+icCardtype);
		sb.append(icCardtype);
		sb.append(",");
		
		byte[] icCardInNoB = new byte[4];
		icCardInNoB[0] = messageBody[35];
		icCardInNoB[1] = messageBody[36];
		icCardInNoB[2] = messageBody[37];
		icCardInNoB[3] = messageBody[38];
		long icCardInNo = ByteUtil.lBytesToLong(icCardInNoB);	//	IC���ڲ�����
//		System.out.println("	ic���ڲ����ţ�"+icCardInNo);
		sb.append(icCardInNo);
		sb.append(",");
	
		byte[] icCardIssueB = new byte[4];
		icCardIssueB[0] = messageBody[39];
		icCardIssueB[1] = messageBody[40];
		icCardIssueB[2] = messageBody[41];
		icCardIssueB[3] = messageBody[42];
		long icCardIssue = ByteUtil.lBytesToLong(icCardIssueB);	//		IC��������ˮ��
//		System.out.println("	IC��������ˮ�ţ�"+icCardIssue);
		sb.append(icCardIssue);
		sb.append(",");
		
		String sKDateYear = Integer.toHexString((int)messageBody[43]).toString() + Integer.toHexString((int)messageBody[44]).toString();
		String sKDateMonth = Integer.toHexString((int)messageBody[45]).toString();
		String sKDateDate = Integer.toHexString((int)messageBody[46]).toString();
		String sKDate = sKDateYear + "-" + sKDateMonth + "-" + sKDateDate ; //�ۿ�����
//		System.out.println("�ۿ�������:"+sKDate);
		sb.append(sKDate);
		sb.append(",");
		
		String yXDateYear = Integer.toHexString((int)messageBody[47]).toString() + Integer.toHexString((int)messageBody[48]).toString();
		String yXDateMonth = Integer.toHexString((int)messageBody[49]).toString();
		String yXDateDate = Integer.toHexString((int)messageBody[50]).toString();
		String yXDate = yXDateYear + "-" + yXDateMonth + "-" + yXDateDate ; //��Ч����
//		System.out.println("��Ч������:"+yXDate);
		sb.append(yXDate);
		sb.append(",");
		
		
		String qYDateYear = Integer.toHexString((int)messageBody[51]).toString() + Integer.toHexString((int)messageBody[52]).toString();
		String qYDateMonth = Integer.toHexString((int)messageBody[53]).toString();
		String qYDateDate = Integer.toHexString((int)messageBody[54]).toString();
		String qYDate = qYDateYear + "-" + qYDateMonth + "-" + qYDateDate ; //��������
//		System.out.println("����������:"+qYDate);
		sb.append(qYDate);
		sb.append(",");
		
		byte[] tradeNoB = new byte[4];
		tradeNoB[0] = messageBody[55];
		tradeNoB[1] = messageBody[56];
		tradeNoB[2] = messageBody[57];
		tradeNoB[3] = messageBody[58];
		long tradeNo = ByteUtil.lBytesToLong(tradeNoB);	//	�������к� (Ǯ�����Ѻ�)
//		System.out.println("�������кţ�"+tradeNo);
		sb.append(tradeNo);
		sb.append(",");
		
		String hYCode1 = Integer.toHexString((int)messageBody[59]).toString();
		String hYCode2 = Integer.toHexString((int)messageBody[59]).toString();
		String hYCode = hYCode1 + hYCode2; //��ҵ����
	//	System.out.println("��ҵ���룺"+hYCode);
		sb.append(hYCode);
		sb.append(",");
		
		String cSCode1 = Integer.toHexString((int)messageBody[61]).toString();
		String cSCode2 = Integer.toHexString((int)messageBody[62]).toString();
		String cSCode = cSCode1 + cSCode2; //��Ƭ���д���
	//	System.out.println("��Ƭ���д��룺"+cSCode);
		sb.append(cSCode);
		sb.append(",");
		
		
		byte[] tradeTACNoB = new byte[4];
		tradeTACNoB[0] = messageBody[63];
		tradeTACNoB[1] = messageBody[64];
		tradeTACNoB[2] = messageBody[65];
		tradeTACNoB[3] = messageBody[66];
		long tradeTACNo = ByteUtil.lBytesToLong(tradeTACNoB);	//����TAC�� (Ǯ�����Ѻ�)
//		System.out.println("����TAC�� (Ǯ�����Ѻ�)��"+tradeTACNo);
		sb.append(tradeTACNo);
		sb.append(",");
		
		String zCDateYear = "20" + Integer.toHexString((int)messageBody[68]).toString();
		String zCDateMonth = Integer.toHexString((int)messageBody[69]).toString();
		String zCDateDate = Integer.toHexString((int)messageBody[70]).toString();
		String zCDateHour = Integer.toHexString((int)messageBody[71]).toString();
		String zCDateMinute = Integer.toHexString((int)messageBody[72]).toString();
		String zCDateSecond = Integer.toHexString((int)messageBody[73]).toString();
		String zCDate = zCDateYear + "-" + zCDateMonth + "-" + zCDateDate + " " + zCDateHour + ":" + zCDateMinute + ":" + zCDateSecond; //�пػ�����
	//	System.out.println("�⳵ʱ����:"+zCDate);
		sb.append(zCDate);
		sb.append(",");
		
		String pileNo1 = Integer.toHexString((int)messageBody[74]).toString();
		String pileNo2 = Integer.toHexString((int)messageBody[75]).toString();
		String pileNo = pileNo1 + pileNo2; //ͣ��λ��
//		System.out.println("ͣ��λ�ţ�"+pileNo);
		if("0".equals(pileNo1)){
			pileNo = pileNo2;
		}
		sb.append(pileNo);
		sb.append(",");
		
		
		byte[] bikeRfidNoB = new byte[4];
		bikeRfidNoB[0] = messageBody[76];
		bikeRfidNoB[1] = messageBody[77];
		bikeRfidNoB[2] = messageBody[78];
		bikeRfidNoB[3] = messageBody[79];
		long bikeRfidNo = ByteUtil.lBytesToLong(bikeRfidNoB);	//���г�rfid����
//		System.out.println("���г�rfid���ţ�"+bikeRfidNo);
		sb.append(bikeRfidNo);
		sb.append(",");
		
		int takeType = messageBody[80]; //�⳵����
		if(0 == takeType){
//			System.out.println("�⳵����");
		}else if(1 == takeType){
//			System.out.println("�⳵�ѻ�");
		}
		sb.append(takeType);
		sb.append(",");

		int hcType =  messageBody[81]; //		��������
		if(0 == hcType){
//			System.out.println("����");
		}else if(1 == hcType){
	//		System.out.println("����");
		}
		sb.append(hcType);
		sb.append(",");
		
		byte[] posNoB = new byte[16];
		for(int i = 0 ; i < 16 ; i++){
			posNoB[i] = messageBody[82+i];
		}
		
		byte[] posNoN = ByteUtil.byteCut(posNoB);
		String posNo = new String(posNoN); //������
//		System.out.println("�пػ������ţ�" + posNo);
		sb.append(posNo);
		sb.append(",");
		
		byte[] freeTimeB = new byte[4];
		freeTimeB[0] = messageBody[98];
		freeTimeB[1] = messageBody[99];
		int freeTime = ByteUtil.lBytes2ToInt(freeTimeB);//���ʱ�䣨��Ϊ��λ��
//		System.out.println("���ʱ�䣨��Ϊ��λ��:"+freeTime);
		sb.append(freeTime);
		
		String[] s = new String[4]; // 0ҵ������ 1�п�no 2���� 3 ���к�
		s[0] = "3"; //ҵ������
		s[1] = String.valueOf(equipmentNo); //�п�no
		s[2] = sb.toString(); // ����
		s[3] = String.valueOf(serialNumber); //���к�
		Constats.v.add(s);
		int size = 	Constats.v.size();
		if(size > Constats.HAND_TOP_SIZE ){
			inserDB(Constats.v);
		}
		
	/*	
		//�洢���ݿ�
		if (null != sb.toString() || !"".equals(sb.toString())) {
			Communcation communcation = new Communcation();
			communcation.setCommType("3");// 3Ϊ�ϴ�ҵ������
			communcation.setEquipmentId(equipmentId);
			communcation.setCommContent(sb.toString());
			communcation.setSerialNumber(String.valueOf(serialNumber));
			CommuncationDao communcationDao = new CommuncationDaoImpl();
			communcationDao.setDBConnection(dbc);
			communcationDao.addCommuncation(communcation);
		}*/
		
		
		
		
		//�༭��Ӧ��Ϣ
		byte[] c = new byte[2];
		c[1] = ByteUtil.BACKBIKE_RECOVER_BACK_CODE1;
		c[0] = ByteUtil.BACKBIKE_RECOVER_BACK_CODE2;
	
		byte[] body = new byte[10];
		body[0] = messageBody[2]; 
		body[1] = messageBody[3];
		body[2] = messageBody[4];
		body[3] = messageBody[5]; //�п�no
		body[4] = messageBody[6]; 
		body[5] = messageBody[7]; 
		body[6] = messageBody[8]; 
		body[7] = messageBody[9]; //ͨ����ˮ��
		body[8] = 1;       //0:ʧ�� 1:�ɹ�
		body[9] = 0;       //�����Ϊ0,Ϊ�Ժ�Ԥ��
		return BuildMessage(c, body);
	}
	/**
	 * 
	 * ��������:doBikePileStateInfomation
	 * ��������: ׮λʵʱ��Ϣ
	 * @param messageBody
	 * @param ioSession
	 * @return
	 * Aug 18, 2011 
	 *
	 * @author<a href=zhanghuaup@126.com>�Ż�</a>
	 * @version 1.0
	 *
	 */
	public static byte[] doBikePileStateInfomation(byte[] messageBody ,IoSession ioSession){
//		System.out.println("�����ϴ�׮λʱʵ��ϢЭ�顣����");
		byte[] posIdB = new byte[4];
		posIdB[0] = messageBody[2]; 
		posIdB[1] = messageBody[3];
		posIdB[2] = messageBody[4];
		posIdB[3] = messageBody[5]; 
		long  equipmentNo = ByteUtil.lBytesToLong(posIdB); //�пػ�No
//		System.out.println("�пػ���id Ϊ��" + EquipmentNo);
//		EquipmentDao equipmentDao = new EquipmentDaoImpl();
//		equipmentDao.setDBConnection(dbc);
//		String equipmentId = equipmentDao.getEquipmentIdByNo(String.valueOf(EquipmentNo)); //�п�ID
		
	/*
	 * ��ʱ�ò���
	 * 	byte[] serialNumberB = new byte[4];
		serialNumberB[0] = messageBody[6]; 
		serialNumberB[1] = messageBody[7];
		serialNumberB[2] = messageBody[8];
		serialNumberB[3] = messageBody[9]; 
		long  serialNumber = ByteUtil.lBytesToLong(serialNumberB); //ͨ����ˮ��
*///		System.out.println("ͨ����ˮ�� Ϊ��" + serialNumber);
		
		/*
		 * ��ʱ�ò���
		 * byte[] pileNum = new byte[2];  //��λ���� 
		pileNum[0] =  messageBody[10]; 
		pileNum[1] =  messageBody[11]; 
		int pileNumI = ByteUtil.lBytes2ToInt(pileNum);
//		System.out.println("��λ�����ǣ�"+pileNumI);
*/		
		int pileNumberG = (messageBody.length -12)/ 9;
		
		StringBuffer content = new StringBuffer();
		for (int q = 0; q < pileNumberG; q++) {
			byte date[] = new byte[9];
			for (int i = 0; i < 9; i++) {
				date[i] = messageBody[q * 9 + 12+ i];
			}
			int pileNo = date[0];// ��λ���
			byte rfIDb[] = new byte[4];
			rfIDb[0] = date[1];
			rfIDb[1] = date[2];
			rfIDb[2] = date[3];
			rfIDb[3] = date[4];
			long rfid = ByteUtil.lBytesToLong(rfIDb); //���г�rfid��
			int type = date[5]; //׮λ����
			int pileState = date[6]; //׮λ״̬  0=�޳�1=�г�
			int blackBikeFlag = date[7]; //�ڳ���־ 1=�ڳ� 0=����
			int isOnLine = date[8]; //�Ƿ����� 1=���� 0=������ 
			StringBuffer message = new StringBuffer();
			message.append(String.valueOf(pileNo)).append(",").append(
					String.valueOf(rfid)).append(",").append(
					String.valueOf(type)).append(",").append(
					String.valueOf(pileState)).append(",").append(
					String.valueOf(blackBikeFlag)).append(",").append(isOnLine);
			if (pileNumberG != (q + 1)) {
				message.append("#");
			}
			content.append(message);
		}
		
		
		
		String[] s = new String[3]; // 0ҵ������ 1�п�no 2���� 3 ���к�
		s[0] = "4"; //ҵ������
		s[1] = String.valueOf(equipmentNo); //�п�no
		s[2] = content.toString(); // ����
//		s[3] = String.valueOf(serialNumber); //���к�
		Constats.v.add(s);
		int size = 	Constats.v.size();
		if(size > Constats.HAND_TOP_SIZE ){
			inserDB(Constats.v);
		}
		
	/*	if (null != content.toString() || !"".equals(content.toString())) {
			Communcation com = new Communcation();
			com.setCommType("4");// 4Ϊ�ϴ�׮λʵʱ��Ϣ����
			com.setCommContent(content.toString());
			com.setEquipmentId(equipmentId);
//			System.out.println("�ҵ�վ��no�ǡ��������������뿪������������������������"+posNo);
			CommuncationDao communcationDao = new CommuncationDaoImpl();
			communcationDao.setDBConnection(dbc);
			try {
				if (!communcationDao.isMessExit(equipmentId)) {
					communcationDao.addPileStateMess(com);
				} else {
					communcationDao.updateByCommTypeAndPosNo(com);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}*/
		
		//�༭��Ӧ��Ϣ
		byte[] c = new byte[2];
		c[1] = ByteUtil.BIKEPILE_STATE_INFOMATION_BACK_CODE1;
		c[0] = ByteUtil.BIKEPILE_STATE_INFOMATION_BACK_CODE2;
	
		byte[] body = new byte[9];
		body[0] = messageBody[2]; 
		body[1] = messageBody[3];
		body[2] = messageBody[4];
		body[3] = messageBody[5]; //�п�no
		body[4] = messageBody[6]; 
		body[5] = messageBody[7]; 
		body[6] = messageBody[8]; 
		body[7] = messageBody[9]; //ͨ����ˮ��
		body[8] = 1;       //0:ʧ�� 1:�ɹ�
//		System.out.println("�ѷ����˰�����~~~~~~~");
		return BuildMessage(c, body);
	}
	/**
	 * 
	 * ��������:doIPCErrorInfomation
	 * ��������:���ػ�������Ϣ 
	 * @param messageBody
	 * @param ioSession
	 * @return
	 * Aug 19, 2011 
	 *
	 * @author<a href=zhanghuaup@126.com>�Ż�</a>
	 * @version 1.0
	 *
	 */
	public static byte[] doIPCErrorInfomation(byte[] messageBody ,IoSession ioSession){
		System.out.println("���빤�ػ�������ϢЭ�顣����");
		byte[] posIdB = new byte[4];
		posIdB[0] = messageBody[2]; 
		posIdB[1] = messageBody[3];
		posIdB[2] = messageBody[4];
		posIdB[3] = messageBody[5]; 
		long  posId = ByteUtil.lBytesToLong(posIdB); //�пػ�id
		System.out.println("�пػ���id Ϊ��" + posId);
		
		byte[] serialNumberB = new byte[4];
		serialNumberB[0] = messageBody[6]; 
		serialNumberB[1] = messageBody[7];
		serialNumberB[2] = messageBody[8];
		serialNumberB[3] = messageBody[9]; 
		long  serialNumber = ByteUtil.lBytesToLong(serialNumberB); //ͨ����ˮ��
		System.out.println("ͨ����ˮ�� Ϊ��" + serialNumber);
		
		String hCDateYear = Integer.toHexString((int)messageBody[10]).toString() + Integer.toHexString((int)messageBody[11]).toString();
		String hCDateMonth = Integer.toHexString((int)messageBody[12]).toString();
		String hCDateDate = Integer.toHexString((int)messageBody[13]).toString();
		String hCDateHour = Integer.toHexString((int)messageBody[14]).toString();
		String hCDateMinute = Integer.toHexString((int)messageBody[15]).toString();
		String hCDateSecond = Integer.toHexString((int)messageBody[16]).toString();
		String hCDate = hCDateYear + "-" + hCDateMonth + "-" + hCDateDate + " " + hCDateHour + ":" + hCDateMinute + ":" + hCDateSecond; //����shijan
		System.out.println("�ϴ�ʱ����:"+hCDate);
		
		byte[] samCardNoB = new byte[4];
		samCardNoB[0] = messageBody[17]; 
		samCardNoB[1] = messageBody[18];
		samCardNoB[2] = messageBody[19];
		samCardNoB[3] = messageBody[20]; 
		long  samCardNo = ByteUtil.lBytesToLong(samCardNoB); //ͨ����ˮ��
		System.out.println("SAM���� Ϊ��" + samCardNo);
		
		
		byte[] pileNoB = new byte[2];
		pileNoB[0] = messageBody[21]; 
		pileNoB[1] = messageBody[22];
		int pileNo = ByteUtil.lBytes2ToInt(pileNoB); // ͣ��λ��	
		System.out.println("ͣ��λ���ǣ� " +pileNo);
		
		int WrongType = messageBody[23];
		if(1 == WrongType){
			System.out.println("�����쳣");
			
		}else if(2 == WrongType){
			System.out.println("��������");
			
		}else if(3 == WrongType){
			System.out.println("��������");
			
		}else if(4 == WrongType){
			System.out.println("�������쳣");
			
		}else if(5 == WrongType){
			System.out.println("��Կ�쳣");
			
		}else if(6 == WrongType){
			System.out.println("CANģ���쳣");
			
		}else if(0 == WrongType){
			System.out.println("����");
			
		}
	
		byte[] websiteNoB = new byte[16];
		for(int i = 0 ; i < 16 ; i++){
			websiteNoB[i] = messageBody[24+i];
		}
		byte[] websiteNoN = ByteUtil.byteCut(websiteNoB);
		String websiteNo = new String(websiteNoN); //������
		System.out.println("�пػ������ţ�" + websiteNo);
			
		//�༭��Ӧ��Ϣ
		byte[] c = new byte[2];
		c[0] = ByteUtil.IPC_ERROR_INFOMATION_BACK_CODE1;
		c[1] = ByteUtil.IPC_ERROR_INFOMATION_BACK_CODE2;
	
		byte[] body = new byte[9];
		body[0] = messageBody[2]; 
		body[1] = messageBody[3];
		body[2] = messageBody[4];
		body[3] = messageBody[5]; //�п�id
		body[4] = messageBody[6]; 
		body[5] = messageBody[7]; 
		body[6] = messageBody[8]; 
		body[7] = messageBody[9]; //ͨ����ˮ��
		body[8] = 1;       //0:ʧ�� 1:�ɹ�
		return BuildMessage(c, body);
		
	}
	/**
	 * 
	 * ��������:doBikeUpDownShelf
	 * ��������: �������¼�
	 * @param messageBody
	 * @param ioSession
	 * @return
	 * Aug 19, 2011 
	 *
	 * @author<a href=zhanghuaup@126.com>�Ż�</a>
	 * @version 1.0
	 *
	 */
	public static byte[] doBikeUpDownShelf(byte[] messageBody ,IoSession ioSession){
//		System.out.println("���복�����¼�Э�顣������");
		byte[] posIdB = new byte[4];
		posIdB[0] = messageBody[2]; 
		posIdB[1] = messageBody[3];
		posIdB[2] = messageBody[4];
		posIdB[3] = messageBody[5]; 
		long  equipmentNo = ByteUtil.lBytesToLong(posIdB); //�пػ�no
//		System.out.println("�пػ���id Ϊ��" + EquipmentNo);
//		EquipmentDao equipmentDao = new EquipmentDaoImpl();
//		equipmentDao.setDBConnection(dbc);
//		String equipmentId = equipmentDao.getEquipmentIdByNo(String.valueOf(EquipmentNo)); //�п�ID
		
		StringBuffer sb = new StringBuffer();
		byte[] serialNumberB = new byte[4];
		serialNumberB[0] = messageBody[6]; 
		serialNumberB[1] = messageBody[7];
		serialNumberB[2] = messageBody[8];
		serialNumberB[3] = messageBody[9]; 
		long  serialNumber = ByteUtil.lBytesToLong(serialNumberB); //ͨ����ˮ��
	//	System.out.println("ͨ����ˮ�� Ϊ��" + serialNumber);
		sb.append("7,");
		sb.append(serialNumber);
		sb.append(",");
		
		byte[] CardInNoB = new byte[4];
		CardInNoB[0] = messageBody[10]; 
		CardInNoB[1] = messageBody[11];
		CardInNoB[2] = messageBody[12];
		CardInNoB[3] = messageBody[13]; 
		long  CardInNo = ByteUtil.lBytesToLong(CardInNoB); //����Ա�ڿ���
//		System.out.println("����Ա�ڿ���Ϊ��" + CardInNo);
		sb.append(CardInNo);
		sb.append(",");
		
		int type = 2;
		if(0x06 == messageBody[14]){
//			System.out.println("�¼�");
			type = 0;
		}else if(0x08 == messageBody[14]){
//			System.out.println("�ϼ�");
			type = 1;
		}
		sb.append(type);
		sb.append(",");
		
		byte[] serialNoB = new byte[4];
		serialNoB[0] = messageBody[15]; 
		serialNoB[1] = messageBody[16];
		serialNoB[2] = messageBody[17];
		serialNoB[3] = messageBody[18]; 
		long  serialNo = ByteUtil.lBytesToLong(serialNoB); //����Ա������ˮ��
//		System.out.println("����Ա������ˮ�ţ�" + serialNo);
		sb.append(serialNo);
		sb.append(",");
		
		String pileNo1 = Integer.toHexString((int)messageBody[19]).toString();
		String pileNo2 = Integer.toHexString((int)messageBody[20]).toString();
		String pileNo = pileNo1 + pileNo2; //ͣ��λ��
		if("0".equals(pileNo1)){
			pileNo = pileNo2;
		}
		sb.append(pileNo);
		sb.append(",");
		
		byte[] rfidB = new byte[4];
		rfidB[0] = messageBody[21]; 
		rfidB[1] = messageBody[22];
		rfidB[2] = messageBody[23];
		rfidB[3] = messageBody[24]; 
		long  rfid = ByteUtil.lBytesToLong(rfidB); //rfid��
//		System.out.println("rfid�ţ�" + rfid);
		sb.append(rfid);
		sb.append(",");
		
		String hCDateYear = Integer.toHexString((int)messageBody[25]).toString() + Integer.toHexString((int)messageBody[26]).toString();
		String hCDateMonth = Integer.toHexString((int)messageBody[27]).toString();
		String hCDateDate = Integer.toHexString((int)messageBody[28]).toString();
		String hCDateHour = Integer.toHexString((int)messageBody[29]).toString();
		String hCDateMinute = Integer.toHexString((int)messageBody[30]).toString();
		String hCDateSecond = Integer.toHexString((int)messageBody[31]).toString();
		String hCDate = hCDateYear + "-" + hCDateMonth + "-" + hCDateDate + " " + hCDateHour + ":" + hCDateMinute + ":" + hCDateSecond; //����shijan
//		System.out.println("����ʱ����:"+hCDate);
		sb.append(hCDate);
		sb.append(",");
		
		byte[] websiteNoB = new byte[16];
		for(int i = 0 ; i < 16 ; i++){
			websiteNoB[i] = messageBody[32+i];
		}
		byte[] websiteNoN = ByteUtil.byteCut(websiteNoB);
		String websiteNo = new String(websiteNoN); //������
//		System.out.println("�пػ������ţ�" + websiteNo);
		sb.append(websiteNo);
		
		String[] s = new String[4]; // 0ҵ������ 1�п�no 2���� 3 ���к�
		s[0] = "3"; //ҵ������
		s[1] = String.valueOf(equipmentNo); //�п�no
		s[2] = sb.toString(); // ����
		s[3] = String.valueOf(serialNumber); //���к�
		Constats.v.add(s);
		int size = 	Constats.v.size();
		if(size > Constats.HAND_TOP_SIZE ){
			inserDB(Constats.v);
		}
	/*	
		//�洢���ݿ�
		if (null != sb.toString() || !"".equals(sb.toString())) {
			Communcation communcation = new Communcation();
			communcation.setCommType("3");// 3Ϊ�ϴ�ҵ������
			communcation.setEquipmentId(equipmentId);
			communcation.setCommContent(sb.toString());
			communcation.setSerialNumber(String.valueOf(serialNumber));
			CommuncationDao communcationDao = new CommuncationDaoImpl();
			communcationDao.setDBConnection(dbc);
			communcationDao.addCommuncation(communcation);
		}*/
		
		
		//�༭��Ӧ��Ϣ
		byte[] c = new byte[2];
		c[1] = ByteUtil.BIKE_UP_DOWN_SHELF_BACK_CODE1;
		c[0] = ByteUtil.BIKE_UP_DOWN_SHELF_BACK_CODE2;
	
		byte[] body = new byte[9];
		body[0] = messageBody[2]; 
		body[1] = messageBody[3];
		body[2] = messageBody[4];
		body[3] = messageBody[5]; //�п�id
		body[4] = messageBody[6]; 
		body[5] = messageBody[7]; 
		body[6] = messageBody[8]; 
		body[7] = messageBody[9]; //ͨ����ˮ��
		body[8] = 1;       //0:ʧ�� 1:�ɹ�
		return BuildMessage(c, body);
	}
	
	
	
	
	/**
	 * 
	 * ��������:getTimeDifference
	 * ��������: 2��ʱ��֮��
	 * @param s1
	 * @param s2
	 * @return
	 * Dec 19, 2011 
	 *
	 * @author<a href=zhanghuaup@126.com>�Ż�</a>
	 * @version 1.0
	 *
	 */
	public static long getTimeDifference(String s1,String s2){
		  SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		  ParsePosition pos = new ParsePosition(0);
		  ParsePosition pos1 = new ParsePosition(0);
		  Date dt1=formatter.parse(s1,pos);
		  Date dt2=formatter.parse(s2,pos1);
		  long l = dt2.getTime() - dt1.getTime();
		  long minute = l/1000;
		  return minute;
	}
	
	/**
	 * 
	 * ��������:BuildMessage
	 * ��������: ���Ӧ����
	 * 
	 * @param c
	 * @param body
	 * @return
	 * Aug 19, 2011 
	 *
	 * @author<a href=zhanghuaup@126.com>�Ż�</a>
	 * @version 1.0
	 *
	 */
	public static byte[] BuildMessage(byte[] c,byte[] body){

		
		byte[] cbody = ByteUtil.plus2Bytes(c, body);
		//У���������
		int mes = CLibrary.INSTANCE.CRC16_Table(cbody, (short)cbody.length);
		byte[] bcB = ByteUtil.writeInt(mes);

		int length = body.length + 2;
//		System.out.println(length);
		byte[] lengthB = ByteUtil.writeInt(length);
//		for(int i = 0 ;i<4  ; i++){
//
//			        System.out.println(lengthB[i]);
//		}
//		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream os = new DataOutputStream(baos);
		try {
			os.writeByte(ByteUtil.START_CODE1);
			os.writeByte(ByteUtil.START_CODE2);
			os.writeByte(ByteUtil.START_CODE3);
			os.writeByte(ByteUtil.START_CODE4);
			os.writeByte(lengthB[0]);
			os.writeByte(lengthB[1]);
			os.write(c);
			os.write(body);
			os.writeByte(bcB[0]);
			os.writeByte(bcB[1]);
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
	
	/**
	 * 
	 * ��������:inserDB
	 * ��������:ͬ�������ַ�Vector 
	 * @param v
	 * Dec 19, 2011 
	 *
	 * @author<a href=zhanghuaup@126.com>�Ż�</a>
	 * @version 1.0
	 *
	 */
	public synchronized static void inserDB(Vector<String[]> v){
//		Constats.flag = 1;
		int vLength = v.size();
		for(int i = 0 ; i < vLength ; i++){ 
			String[] s = Constats.v.get(0);
			v.remove(0);
			if(null != s){
				if (null != s[2] || !"".equals(s[2])) {// 0ҵ������ 1�п�no 2���� 3 ���к�
					if("3".endsWith(s[0])){
						
					} else if ("4".endsWith(s[0])) { // 0ҵ������ 1�п�no 2����
						
					}
				}
			}
		}

	}

}
