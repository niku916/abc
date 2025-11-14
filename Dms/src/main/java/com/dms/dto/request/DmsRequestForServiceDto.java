package com.dms.dto.request;

import java.util.List;
import java.util.Map;

import com.dms.dto.TmEsignDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DmsRequestForServiceDto {

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
	private Map<Integer, List<String>> purDoc;
	private Map<String, Boolean> docDoubleSignStatus;
	private String docType;
	private TmEsignDto tmEsign;
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

}
