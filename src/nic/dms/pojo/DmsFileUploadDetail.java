package nic.dms.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class DmsFileUploadDetail {

	@JsonProperty("j_key")
	private String jKey;
	@JsonProperty("j_securityKey")
	private String jSecurityKey;
	@JsonProperty("state")
	private String state;
	@JsonProperty("applno")
	private String applno;
	@JsonProperty("prj_name")
	private String prj_name;
	@JsonProperty("key")
	private String key;
	@JsonProperty("dmsFileDetails")
	private List<DmsFileDetail> dmsFileDetails = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

	@JsonProperty("state")
	public String getState() {
		return state;
	}

	@JsonProperty("state")
	public void setState(String state) {
		this.state = state;
	}

	@JsonProperty("applno")
	public String getApplno() {
		return applno;
	}

	@JsonProperty("applno")
	public void setApplno(String applno) {
		this.applno = applno;
	}
	
	@JsonProperty("prj_name")
	public String getPrj_name() {
		return prj_name;
	}
	@JsonProperty("prj_name")
	public void setPrj_name(String prj_name) {
		this.prj_name = prj_name;
	}

	@JsonProperty("key")
	public String getKey() {
		return key;
	}

	@JsonProperty("key")
	public void setKey(String key) {
		this.key = key;
	}

	@JsonProperty("dmsFileDetails")
	public List<DmsFileDetail> getDmsFileDetails() {
		return dmsFileDetails;
	}

	@JsonProperty("dmsFileDetails")
	public void setDmsFileDetails(List<DmsFileDetail> dmsFileDetails) {
		this.dmsFileDetails = dmsFileDetails;
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