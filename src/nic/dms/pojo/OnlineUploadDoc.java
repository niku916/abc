package nic.dms.pojo;

public class OnlineUploadDoc {
	private String stateCode;
	private String purCode;
	private boolean docMerge;
	private boolean esign;
	private boolean mandateDocsBfrRcpt;
	private boolean aadhaarEsignMandate;
	private String esignPurCode;
//	private String apiBaseUrl;
	private String apiUploadDocUrl;
	private String apiUpdateDocUrl;
	private String apiViewDocUrl;
	private String apiListDocsUrl;
	private String docKeyUrl;
	private String signedFileUrl;

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

	public boolean isDocMerge() {
		return docMerge;
	}

	public void setDocMerge(boolean docMerge) {
		this.docMerge = docMerge;
	}

	public boolean isEsign() {
		return esign;
	}

	public void setEsign(boolean esign) {
		this.esign = esign;
	}

	public boolean isMandateDocsBfrRcpt() {
		return mandateDocsBfrRcpt;
	}

	public void setMandateDocsBfrRcpt(boolean mandateDocsBfrRcpt) {
		this.mandateDocsBfrRcpt = mandateDocsBfrRcpt;
	}

	public boolean isAadhaarEsignMandate() {
		return aadhaarEsignMandate;
	}

	public void setAadhaarEsignMandate(boolean aadhaarEsignMandate) {
		this.aadhaarEsignMandate = aadhaarEsignMandate;
	}

	public String getEsignPurCode() {
		return esignPurCode;
	}

	public void setEsignPurCode(String esignPurCode) {
		this.esignPurCode = esignPurCode;
	}

//	public String getApiBaseUrl() {
//		return apiBaseUrl;
//	}
//	public void setApiBaseUrl(String apiBaseUrl) {
//		this.apiBaseUrl = apiBaseUrl;
//	}
	

	public String getSignedFileUrl() {
		return signedFileUrl;
	}

	public String getApiUploadDocUrl() {
		return apiUploadDocUrl;
	}

	public void setApiUploadDocUrl(String apiUploadDocUrl) {
		this.apiUploadDocUrl = apiUploadDocUrl;
	}

	public String getApiUpdateDocUrl() {
		return apiUpdateDocUrl;
	}

	public void setApiUpdateDocUrl(String apiUpdateDocUrl) {
		this.apiUpdateDocUrl = apiUpdateDocUrl;
	}

	public String getApiViewDocUrl() {
		return apiViewDocUrl;
	}

	public void setApiViewDocUrl(String apiViewDocUrl) {
		this.apiViewDocUrl = apiViewDocUrl;
	}

	public String getApiListDocsUrl() {
		return apiListDocsUrl;
	}

	public void setApiListDocsUrl(String apiListDocsUrl) {
		this.apiListDocsUrl = apiListDocsUrl;
	}

	public String getDocKeyUrl() {
		return docKeyUrl;
	}

	public void setDocKeyUrl(String docKeyUrl) {
		this.docKeyUrl = docKeyUrl;
	}

	public void setSignedFileUrl(String signedFileUrl) {
		this.signedFileUrl = signedFileUrl;
	}

}
