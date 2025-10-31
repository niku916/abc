package nic.dms.pojo;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;


public class DmsFileDetail {
	
	@JsonProperty("fileName")
	private String fileName;
	@JsonProperty("docCatgId")
	private String docCatgId;
	@JsonProperty("apiFile")
	private byte[] apiFile;
	@JsonProperty("regNo")
	private String regNo;
	@JsonProperty("docId")
	private Integer docId;
	@JsonProperty("uploaded")
	private Boolean uploaded;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	@JsonProperty("fileName")
	public String getFileName() {
	return fileName;
	}
	
	@JsonProperty("fileName")
	public void setFileName(String fileName) {
	this.fileName = fileName;
	}
	
	@JsonProperty("docCatgId")
	public String getDocCatgId() {
	return docCatgId;
	}
	
	@JsonProperty("docCatgId")
	public void setDocCatgId(String docCatgId) {
	this.docCatgId = docCatgId;
	}	
	
	@JsonProperty("apiFile")
	public byte[] getApiFile() {
		return apiFile;
	}
	
	@JsonProperty("apiFile")
	public void setApiFile(byte[] apiFile) {
		this.apiFile = apiFile;
	}

	@JsonProperty("regNo")
	public String getRegNo() {
	return regNo;
	}
	
	@JsonProperty("regNo")
	public void setRegNo(String regNo) {
	this.regNo = regNo;
	}
	
	@JsonProperty("docId")
	public Integer getDocId() {
	return docId;
	}
	
	@JsonProperty("docId")
	public void setDocId(Integer docId) {
	this.docId = docId;
	}
	
	@JsonProperty("uploaded")
	public Boolean getUploaded() {
	return uploaded;
	}
	
	@JsonProperty("uploaded")
	public void setUploaded(Boolean uploaded) {
	this.uploaded = uploaded;
	}
	
	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}
	
	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}

}
