package nic.dms.pojo;

import nic.dms.pojo.reqestresponse.DmsRequest;

//import nic.vahan.form.dobj.UploadDocumentDobj;

public class DmsDocumentUpload {
	private DmsDocument dmsDocument = new DmsDocument();
	private String docopertaionType;
//	private UploadDocumentDobj dobj = new UploadDocumentDobj();	
	private Integer index;
	private String uploadModBtnLbl;
	private int fileSelectCount = 1;
	private String mergefileName;
	private DmsEsignFlags dmsEsignFlags = new DmsEsignFlags();
	private DmsRequest dmsRequest = new DmsRequest();
	private boolean docNotConfigure = false;

	private boolean docUploaded = false;
	private boolean mandateFileSelect = true;

	public DmsDocument getDmsDocument() {
		return dmsDocument;
	}

	public void setDmsDocument(DmsDocument dmsDocument) {
		this.dmsDocument = dmsDocument;
	}

	public String getDocopertaionType() {
		return docopertaionType;
	}

	public void setDocopertaionType(String docopertaionType) {
		this.docopertaionType = docopertaionType;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getUploadModBtnLbl() {
		return uploadModBtnLbl;
	}

	public void setUploadModBtnLbl(String uploadModBtnLbl) {
		this.uploadModBtnLbl = uploadModBtnLbl;
	}

	public int getFileSelectCount() {
		return fileSelectCount;
	}

	public void setFileSelectCount(int fileSelectCount) {
		this.fileSelectCount = fileSelectCount;
	}

	public String getMergefileName() {
		return mergefileName;
	}

	public void setMergefileName(String mergefileName) {
		this.mergefileName = mergefileName;
	}

	public DmsEsignFlags getDmsEsignFlags() {
		return dmsEsignFlags;
	}

	public void setDmsEsignFlags(DmsEsignFlags dmsEsignFlags) {
		this.dmsEsignFlags = dmsEsignFlags;
	}

	public DmsRequest getDmsRequest() {
		return dmsRequest;
	}

	public void setDmsRequest(DmsRequest dmsRequest) {
		this.dmsRequest = dmsRequest;
	}

	public boolean isDocNotConfigure() {
		return docNotConfigure;
	}

	public void setDocNotConfigure(boolean docNotConfigure) {
		this.docNotConfigure = docNotConfigure;
	}

	public boolean isMandateFileSelect() {
		return mandateFileSelect;
	}

	public void setMandateFileSelect(boolean mandateFileSelect) {
		this.mandateFileSelect = mandateFileSelect;
	}

	public boolean isDocUploaded() {
		return docUploaded;
	}

	public void setDocUploaded(boolean docUploaded) {
		this.docUploaded = docUploaded;
	}

}
