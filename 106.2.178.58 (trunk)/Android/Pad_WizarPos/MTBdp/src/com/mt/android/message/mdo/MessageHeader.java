package com.mt.android.message.mdo;

public class MessageHeader {
	String id = "60";// TPDU ID
	String destion = "0004";// TPDU Ŀ�ĵ�ַ
	String source = "0000";// TPDU Դ��ַ
	String apptype = "60";// Ӧ�����
	String version = "10";// ����汾��
	String terminalStat = "1";// �ն�״̬
	String requirement = "1";// ����Ҫ��
	String obligate = "000000";// �����ֶ�

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDestion() {
		return destion;
	}

	public void setDestion(String destion) {
		this.destion = destion;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getApptype() {
		return apptype;
	}

	public void setApptype(String apptype) {
		this.apptype = apptype;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTerminalStat() {
		return terminalStat;
	}

	public void setTerminalStat(String terminalStat) {
		this.terminalStat = terminalStat;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getObligate() {
		return obligate;
	}

	public void setObligate(String obligate) {
		this.obligate = obligate;
	}
}
