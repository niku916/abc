package com.dms.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DmsFileDetailDto {

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
}
