package com.dms.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DmsFileUploadDetailDto {
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
	private List<DmsFileDetailDto> dmsFileDetails = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}
