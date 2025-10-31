package nic.dms.pojo.reqestresponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.sun.istack.internal.Nullable;

import nic.dms.pojo.TmEsign;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DmsRequest {
	private String dmsServiceUrl;
	private String stateCd;
	private String applNo;
	private String regnNo;
	private String purCd;
	private String ownerName;
	private Integer officeCd;
	private String serviceRgn = "0";
	private Integer vhClassType;
	private String jKey;
	private String jSecurityKey;
	private String docOperationType;

	private boolean esignFlag;
	private Boolean mergeflag = false;
	private Boolean esignUserOptFlag;

	private Boolean dse = false;
	private Boolean dss = false;
	private Map<Integer, List<String>> purDoc = new HashMap<Integer, List<String>>();
	private Map<String, Boolean> docDoubleSignStatus = new HashMap<String, Boolean>();

	private String docType;
	private TmEsign tmEsign = new TmEsign();
	private String clientReturnUrl;
	private String esignDetailUpdateApi;

	private String saveDoublesignPendUrl;
	private String moveDoublesignPendUrl;
	private Boolean popUpWin = false;
	private String buyerMobileNo;
	private String sellerMobileNo;
	private String pmtType;
	private String transType;
	private String portalCd;
	// @JsonIgnore
	private String authType;
	private String regn_type;
	private int appl_purcd;
	@JsonIgnore
	private Boolean autoUploadView = false;
	// @JsonIgnore
	private Boolean videoUpload = false;

	public Map<Integer, List<String>> getPurDoc() {
		return purDoc;
	}

	public void setPurDoc(Map<Integer, List<String>> purDoc) {
		this.purDoc = purDoc;
	}

	public String getDmsServiceUrl() {
		return dmsServiceUrl;
	}

	public void setDmsServiceUrl(String dmsServiceUrl) {
		this.dmsServiceUrl = dmsServiceUrl;
	}

	public String getStateCd() {
		return stateCd;
	}

	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}

	public String getApplNo() {
		return applNo;
	}

	public void setApplNo(String applNo) {
		this.applNo = applNo;
	}

	public String getRegnNo() {
		return regnNo;
	}

	public void setRegnNo(String regnNo) {
		this.regnNo = regnNo;
	}

	public String getPurCd() {
		return purCd;
	}

	public void setPurCd(String purCd) {
		this.purCd = purCd;
	}

	public String getServiceRgn() {
		return serviceRgn;
	}

	public void setServiceRgn(String serviceRgn) {
		this.serviceRgn = serviceRgn;
	}

	public Integer getVhClassType() {
		return vhClassType;
	}

	public void setVhClassType(Integer vhClassType) {
		this.vhClassType = vhClassType;
	}

	public String getjKey() {
		return jKey;
	}

	public void setjKey(String jKey) {
		this.jKey = jKey;
	}

	public String getjSecurityKey() {
		return jSecurityKey;
	}

	public void setjSecurityKey(String jSecurityKey) {
		this.jSecurityKey = jSecurityKey;
	}

	public String getDocOperationType() {
		return docOperationType;
	}

	public void setDocOperationType(String docOperationType) {
		this.docOperationType = docOperationType;
	}

	public boolean getEsignFlag() {
		return esignFlag;
	}

	public void setEsignFlag(boolean esignFlag) {
		this.esignFlag = esignFlag;
	}

	public Boolean getMergeflag() {
		return mergeflag;
	}

	public void setMergeflag(Boolean mergeflag) {
		this.mergeflag = mergeflag;
	}

	public Boolean getEsignUserOptFlag() {
		return esignUserOptFlag;
	}

	public void setEsignUserOptFlag(Boolean esignUserOptFlag) {
		this.esignUserOptFlag = esignUserOptFlag;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public TmEsign getTmEsign() {
		return tmEsign;
	}

	public void setTmEsign(TmEsign tmEsign) {
		this.tmEsign = tmEsign;
	}

	public String getClientReturnUrl() {
		return clientReturnUrl;
	}

	public void setClientReturnUrl(String clientReturnUrl) {
		this.clientReturnUrl = clientReturnUrl;
	}

	public String getEsignDetailUpdateApi() {
		return esignDetailUpdateApi;
	}

	public void setEsignDetailUpdateApi(String esignDetailUpdateApi) {
		this.esignDetailUpdateApi = esignDetailUpdateApi;
	}

	public Integer getOfficeCd() {
		return officeCd;
	}

	public void setOfficeCd(Integer officeCd) {
		this.officeCd = officeCd;
	}

	public Boolean getPopUpWin() {
		return popUpWin;
	}

	public void setPopUpWin(Boolean popUpWin) {
		this.popUpWin = popUpWin;
	}

	public Boolean getDse() {
		return dse;
	}

	public void setDse(Boolean dse) {
		this.dse = dse;
	}

	public Boolean getDss() {
		return dss;
	}

	public void setDss(Boolean dss) {
		this.dss = dss;
	}

	public Map<String, Boolean> getDocDoubleSignStatus() {
		return docDoubleSignStatus;
	}

	public void setDocDoubleSignStatus(Map<String, Boolean> docDoubleSignStatus) {
		this.docDoubleSignStatus = docDoubleSignStatus;
	}

	public String getSaveDoublesignPendUrl() {
		return saveDoublesignPendUrl;
	}

	public void setSaveDoublesignPendUrl(String saveDoublesignPendUrl) {
		this.saveDoublesignPendUrl = saveDoublesignPendUrl;
	}

	public String getMoveDoublesignPendUrl() {
		return moveDoublesignPendUrl;
	}

	public void setMoveDoublesignPendUrl(String moveDoublesignPendUrl) {
		this.moveDoublesignPendUrl = moveDoublesignPendUrl;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getBuyerMobileNo() {
		return buyerMobileNo;
	}

	public void setBuyerMobileNo(String buyerMobileNo) {
		this.buyerMobileNo = buyerMobileNo;
	}

	public String getSellerMobileNo() {
		return sellerMobileNo;
	}

	public void setSellerMobileNo(String sellerMobileNo) {
		this.sellerMobileNo = sellerMobileNo;
	}

	public String getPmtType() {
		return pmtType;
	}

	public void setPmtType(String pmtType) {
		this.pmtType = pmtType;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getPortalCd() {
		return portalCd;
	}

	public void setPortalCd(String portalCd) {
		this.portalCd = portalCd;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getRegn_type() {
		return regn_type;
	}

	public void setRegn_type(String regn_type) {
		this.regn_type = regn_type;
	}

	public int getAppl_purcd() {
		return appl_purcd;
	}

	public void setAppl_purcd(int appl_purcd) {
		this.appl_purcd = appl_purcd;
	}

	public Boolean getAutoUploadView() {
		return autoUploadView;
	}

	public void setAutoUploadView(Boolean autoUploadView) {
		this.autoUploadView = autoUploadView;
	}

	public Boolean getVideoUpload() {
		return videoUpload;
	}

	public void setVideoUpload(Boolean videoUpload) {
		this.videoUpload = videoUpload;
	}

}
