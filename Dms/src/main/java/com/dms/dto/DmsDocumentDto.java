package com.dms.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DmsDocumentDto {

	private List<DocumentDetailDto> documentList;
	@JsonProperty("uploadedList")
	private List<DocumentDetailDto> uploadedList;
	@JsonProperty("nonUploadedList")
	private List<DocumentDetailDto> nonUploadedList;
	@JsonProperty("mandatoryList")
	private List<DocumentDetailDto> mandatoryList;
	private List<DocumentDetailDto> allDocumentList;
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

}
