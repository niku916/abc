package nic.dms.pojo.reqestresponse;

public class DmsEsignDetailsUpdateRequest {
	private String stateCd;
	private Integer offCode;
	private String purCd;
	private String applNo;
	private String uploadedFrom;
	private String catId;
	private String catName;
	private String signerName;
	
	public String getStateCd() {
		return stateCd;
	}
	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}
	public Integer getOffCode() {
		return offCode;
	}
	public void setOffCode(Integer offCode) {
		this.offCode = offCode;
	}
	public String getPurCd() {
		return purCd;
	}
	public void setPurCd(String purCd) {
		this.purCd = purCd;
	}
	public String getApplNo() {
		return applNo;
	}
	public void setApplNo(String applNo) {
		this.applNo = applNo;
	}
	public String getUploadedFrom() {
		return uploadedFrom;
	}
	public void setUploadedFrom(String uploadedFrom) {
		this.uploadedFrom = uploadedFrom;
	}
	public String getCatId() {
		return catId;
	}
	public void setCatId(String catId) {
		this.catId = catId;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getSignerName() {
		return signerName;
	}
	public void setSignerName(String signerName) {
		this.signerName = signerName;
	}
}
