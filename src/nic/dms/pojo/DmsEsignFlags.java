package nic.dms.pojo;

public class DmsEsignFlags {
	private boolean renderOwnerInfo=false;
//	private boolean renderUploadDoc=false;
	private boolean esignStatus = false;
	private boolean dmsEsignMendateStatus = false;
	private Boolean userSelectSignStatus = null;
	private boolean frzEsignOption= false;
	private boolean mandateFileSelect = true;
	private boolean mergeDocOption;
	private boolean esignFstatus = false;
	
	public boolean isRenderOwnerInfo() {
		return renderOwnerInfo;
	}
	public void setRenderOwnerInfo(boolean renderOwnerInfo) {
		this.renderOwnerInfo = renderOwnerInfo;
	}
//	public boolean isRenderUploadDoc() {
//		return renderUploadDoc;
//	}
//	public void setRenderUploadDoc(boolean renderUploadDoc) {
//		this.renderUploadDoc = renderUploadDoc;
//	}
	public boolean isEsignStatus() {
		return esignStatus;
	}
	public void setEsignStatus(boolean esignStatus) {
		this.esignStatus = esignStatus;
	}
	public boolean isDmsEsignMendateStatus() {
		return dmsEsignMendateStatus;
	}
	public void setDmsEsignMendateStatus(boolean dmsEsignMendateStatus) {
		this.dmsEsignMendateStatus = dmsEsignMendateStatus;
	}
	public Boolean getUserSelectSignStatus() {
		return userSelectSignStatus;
	}
	public void setUserSelectSignStatus(Boolean userSelectSignStatus) {
		this.userSelectSignStatus = userSelectSignStatus;
	}
	public boolean isFrzEsignOption() {
		return frzEsignOption;
	}
	public void setFrzEsignOption(boolean frzEsignOption) {
		this.frzEsignOption = frzEsignOption;
	}
	public boolean isMandateFileSelect() {
		return mandateFileSelect;
	}
	public void setMandateFileSelect(boolean mandateFileSelect) {
		this.mandateFileSelect = mandateFileSelect;
	}
	public boolean isMergeDocOption() {
		return mergeDocOption;
	}
	public void setMergeDocOption(boolean mergeDocOption) {
		this.mergeDocOption = mergeDocOption;
	}
	public boolean isEsignFstatus() {
		return esignFstatus;
	}
	public void setEsignFstatus(boolean esignFstatus) {
		this.esignFstatus = esignFstatus;
	}
	
}
