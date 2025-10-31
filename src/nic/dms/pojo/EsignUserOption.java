package nic.dms.pojo;

public class EsignUserOption {
	private String stateCode;
	private String purCode;
	private String applNo;
	private Integer offCode;
	private boolean esignStatus;
	
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getPurCode() {
		return purCode;
	}
	public void setPurCode(String purCode) {
		this.purCode = purCode;
	}
	public String getApplNo() {
		return applNo;
	}
	public void setApplNo(String applNo) {
		this.applNo = applNo;
	}
	public Integer getOffCode() {
		return offCode;
	}
	public void setOffCode(Integer offCode) {
		this.offCode = offCode;
	}
	public boolean isEsignStatus() {
		return esignStatus;
	}
	public void setEsignStatus(boolean esignStatus) {
		this.esignStatus = esignStatus;
	}
}