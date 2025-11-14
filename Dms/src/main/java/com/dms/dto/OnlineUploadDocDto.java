package com.dms.dto;

import lombok.Data;

@Data
public class OnlineUploadDocDto {
	
	private String stateCode;
	private String purCode;
	private boolean docMerge;
	private boolean esign;
	private boolean mandateDocsBfrRcpt;
	private boolean aadhaarEsignMandate;
	private String esignPurCode;
	private String apiUploadDocUrl;
	private String apiUpdateDocUrl;
	private String apiViewDocUrl;
	private String apiListDocsUrl;
	private String docKeyUrl;
	private String signedFileUrl;

}
