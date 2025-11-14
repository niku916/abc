package com.dms.dto;

import lombok.Data;

@Data
public class TmEsignDto {
	
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

}
