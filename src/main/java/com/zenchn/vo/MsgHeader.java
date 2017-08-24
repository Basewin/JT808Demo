package com.zenchn.vo;

public class MsgHeader {
	/// <summary>
    /// ��ϢID
    /// </summary>
    public int msgId;

    /// <summary>
    /// ��Ϣ������  byte[2-3]
    /// </summary>
    public int msgBodyPropsField;

    /// <summary>
    /// ��Ϣ�峤��
    /// </summary>
    public int msgBodyLength;

    /// <summary>
    /// ���ݼ��ܷ�ʽ
    /// </summary>
    public int encryptionType;

    /// <summary>
    /// �Ƿ�ְ�,true==>����Ϣ����װ��
    /// </summary>
    public boolean hasSubPackage;

    /// <summary>
    /// ����λ[14-15]
    /// </summary>
    public String reservedBit;

    /// <summary>
    /// �ն��ֻ���
    /// </summary>
    public String terminalPhone;

    /// <summary>
    /// ��ˮ��
    /// </summary>
    public int flowId;

    /// <summary>
    /// ��Ϣ����װ�� byte[12-15]
    /// </summary>
    public int packageInfoField;

    /// <summary>
    /// ��Ϣ������(word(16))
    /// </summary>
    public long totalSubPackage;

    /// <summary>
    /// �����(word(16))��η��͵������Ϣ���Ƿְ��еĵڼ�����Ϣ��, �� 1 ��ʼ
    /// </summary>
    public long subPackageSeq;

    /**��ϢID**/
    public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public int getMsgBodyPropsField() {
		return msgBodyPropsField;
	}

	public void setMsgBodyPropsField(int msgBodyPropsField) {
		this.msgBodyPropsField = msgBodyPropsField;
	}

	public int getMsgBodyLength() {
		return msgBodyLength;
	}

	public void setMsgBodyLength(int msgBodyLength) {
		this.msgBodyLength = msgBodyLength;
	}

	public int getEncryptionType() {
		return encryptionType;
	}

	public void setEncryptionType(int encryptionType) {
		this.encryptionType = encryptionType;
	}

	public boolean isHasSubPackage() {
		return hasSubPackage;
	}

	public void setHasSubPackage(boolean hasSubPackage) {
		this.hasSubPackage = hasSubPackage;
	}

	public String getReservedBit() {
		return reservedBit;
	}

	public void setReservedBit(String reservedBit) {
		this.reservedBit = reservedBit;
	}

	/**�ն��ֻ���**/
	public String getTerminalPhone() {
		return terminalPhone;
	}

	public void setTerminalPhone(String terminalPhone) {
		this.terminalPhone = terminalPhone;
	}

	/**��ˮ��**/
	public int getFlowId() {
		return flowId;
	}

	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}

	public int getPackageInfoField() {
		return packageInfoField;
	}

	public void setPackageInfoField(int packageInfoField) {
		this.packageInfoField = packageInfoField;
	}

	public long getTotalSubPackage() {
		return totalSubPackage;
	}

	public void setTotalSubPackage(long totalSubPackage) {
		this.totalSubPackage = totalSubPackage;
	}

	public long getSubPackageSeq() {
		return subPackageSeq;
	}

	public void setSubPackageSeq(long subPackageSeq) {
		this.subPackageSeq = subPackageSeq;
	}

	public String toString()
    {
        return "MsgHeader [msgId=" + msgId + ", msgBodyPropsField=" + msgBodyPropsField + ", msgBodyLength="
                + msgBodyLength + ", encryptionType=" + encryptionType + ", hasSubPackage=" + hasSubPackage
                + ", reservedBit=" + reservedBit + ", terminalPhone=" + terminalPhone + ", flowId=" + flowId
                + ", packageInfoField=" + packageInfoField + ", totalSubPackage=" + totalSubPackage
                + ", subPackageSeq=" + subPackageSeq + "]";
    }
}
