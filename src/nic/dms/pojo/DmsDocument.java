package nic.dms.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class DmsDocument {
	private List<DocumentDetail> documentList = null;

	@JsonProperty("uploadedList")
	private List<DocumentDetail> uploadedList = null;
	@JsonProperty("nonUploadedList")
	private List<DocumentDetail> nonUploadedList = null;
	@JsonProperty("mandatoryList")
	private List<DocumentDetail> mandatoryList = null;
	// private List<DocumentDetail> allDocumentList = null;
	@JsonProperty("applno")
	private String applno;
	@JsonProperty("state")
	private String state;
	@JsonProperty("purposeCode")
	private String purposeCode;
	@JsonProperty("purposeName")
	private String purposeName;
	@JsonProperty("fuelType")
	private Object fuelType;
	@JsonProperty("regNo")
	private String regNo;
	@JsonProperty("officeName")
	private String officeName;
	@JsonProperty("j_key")
	private String jKey;
	@JsonProperty("j_securityKey")
	private String jSecurityKey;
	@JsonProperty("message")
	private String message;
	@JsonProperty("status")
	private Object status;
	@JsonProperty("docFrom")
	private Object docFrom;
	@JsonProperty("vhClassType")
	private Object vhClassType;
	@JsonProperty("serviceRgn")
	private Object serviceRgn;
	@JsonProperty("owCode")
	private Object owCode;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	private Integer offCode;
	private String statusCode;

	public List<DocumentDetail> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<DocumentDetail> documentList) {
		this.documentList = documentList;
	}

	@JsonProperty("uploadedList")
	public List<DocumentDetail> getUploadedList() {
		return uploadedList;
	}

	@JsonProperty("uploadedList")
	public void setUploadedList(List<DocumentDetail> uploadedList) {
		this.uploadedList = uploadedList;
	}

	@JsonProperty("nonUploadedList")
	public List<DocumentDetail> getNonUploadedList() {
		return nonUploadedList;
	}

	@JsonProperty("nonUploadedList")
	public void setNonUploadedList(List<DocumentDetail> nonUploadedList) {
		this.nonUploadedList = nonUploadedList;
	}

	@JsonProperty("mandatoryList")
	public List<DocumentDetail> getMandatoryList() {
		return mandatoryList;
	}

	@JsonProperty("mandatoryList")
	public void setMandatoryList(List<DocumentDetail> mandatoryList) {
		this.mandatoryList = mandatoryList;
	}

	@JsonProperty("applno")
	public String getApplno() {
		return applno;
	}

	@JsonProperty("applno")
	public void setApplno(String applno) {
		this.applno = applno;
	}

	@JsonProperty("state")
	public String getState() {
		return state;
	}

	@JsonProperty("state")
	public void setState(String state) {
		this.state = state;
	}

	@JsonProperty("purposeCode")
	public String getPurposeCode() {
		return purposeCode;
	}

	@JsonProperty("purposeCode")
	public void setPurposeCode(String purposeCode) {
		this.purposeCode = purposeCode;
	}

	@JsonProperty("purposeName")
	public String getPurposeName() {
		return purposeName;
	}

	@JsonProperty("purposeName")
	public void setPurposeName(String purposeName) {
		this.purposeName = purposeName;
	}

	@JsonProperty("fuelType")
	public Object getFuelType() {
		return fuelType;
	}

	@JsonProperty("fuelType")
	public void setFuelType(Object fuelType) {
		this.fuelType = fuelType;
	}

	@JsonProperty("regNo")
	public String getRegNo() {
		return regNo;
	}

	@JsonProperty("regNo")
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	@JsonProperty("officeName")
	public String getOfficeName() {
		return officeName;
	}

	@JsonProperty("officeName")
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	@JsonProperty("j_key")
	public String getJKey() {
		return jKey;
	}

	@JsonProperty("j_key")
	public void setJKey(String jKey) {
		this.jKey = jKey;
	}

	@JsonProperty("j_securityKey")
	public String getJSecurityKey() {
		return jSecurityKey;
	}

	@JsonProperty("j_securityKey")
	public void setJSecurityKey(String jSecurityKey) {
		this.jSecurityKey = jSecurityKey;
	}

	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage(String message) {
		this.message = message;
	}

	@JsonProperty("status")
	public Object getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(Object status) {
		this.status = status;
	}

	@JsonProperty("docFrom")
	public Object getDocFrom() {
		return docFrom;
	}

	@JsonProperty("docFrom")
	public void setDocFrom(Object docFrom) {
		this.docFrom = docFrom;
	}

	@JsonProperty("vhClassType")
	public Object getVhClassType() {
		return vhClassType;
	}

	@JsonProperty("vhClassType")
	public void setVhClassType(Object vhClassType) {
		this.vhClassType = vhClassType;
	}

	@JsonProperty("serviceRgn")
	public Object getServiceRgn() {
		return serviceRgn;
	}

	@JsonProperty("serviceRgn")
	public void setServiceRgn(Object serviceRgn) {
		this.serviceRgn = serviceRgn;
	}

	@JsonProperty("owCode")
	public Object getOwCode() {
		return owCode;
	}

	@JsonProperty("owCode")
	public void setOwCode(Object owCode) {
		this.owCode = owCode;
	}

	// public List<DocumentDetail> getAllDocumentList() {
	// return allDocumentList;
	// }
	//
	// public void setAllDocumentList(List<DocumentDetail> allDocumentList) {
	// this.allDocumentList = allDocumentList;
	// }
	public Integer getOffCode() {
		return offCode;
	}

	public void setOffCode(Integer offCode) {
		this.offCode = offCode;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

}