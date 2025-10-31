package nic.dms.pojo;

public class TmEsign {
	private String stateCode;
	private String aspId;
	private String aspVendor;
	private String esignUrl;
	private String certAliase;
	private String reqEsignParamName;
	private String keystoreFile;
	private String esignRespUrl;
	private String eSignPass;
	private String redirectUrl;

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getAspId() {
		return aspId;
	}

	public void setAspId(String aspId) {
		this.aspId = aspId;
	}

	public String getAspVendor() {
		return aspVendor;
	}

	public void setAspVendor(String aspVendor) {
		this.aspVendor = aspVendor;
	}

	public String getCertAliase() {
		return certAliase;
	}

	public void setCertAliase(String certAliase) {
		this.certAliase = certAliase;
	}

	public String getReqEsignParamName() {
		return reqEsignParamName;
	}

	public void setReqEsignParamName(String reqEsignParamName) {
		this.reqEsignParamName = reqEsignParamName;
	}

	public String getKeystoreFile() {
		return keystoreFile;
	}

	public void setKeystoreFile(String keystoreFile) {
		this.keystoreFile = keystoreFile;
	}

	public String getEsignUrl() {
		return esignUrl;
	}

	public void setEsignUrl(String esignUrl) {
		this.esignUrl = esignUrl;
	}

	public String getEsignRespUrl() {
		return esignRespUrl;
	}

	public void setEsignRespUrl(String esignRespUrl) {
		this.esignRespUrl = esignRespUrl;
	}

	public String geteSignPass() {
		return eSignPass;
	}

	public void seteSignPass(String eSignPass) {
		this.eSignPass = eSignPass;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

}
