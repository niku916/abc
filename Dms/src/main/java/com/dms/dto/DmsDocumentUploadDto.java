package com.dms.dto;

import lombok.Data;

@Data
public class DmsDocumentUploadDto {
	private DmsDocumentDto dmsDocument = null;
	private String docopertaionType;
//	private UploadDocumentDobj dobj = new UploadDocumentDobj();	
	private Integer index;
	private String uploadModBtnLbl;
	private int fileSelectCount = 1;
	private String mergefileName;
	private DmsEsignFlagsDto dmsEsignFlags = null;
	private DmsDocumentDto dmsRequest = null;
	private boolean docNotConfigure = false;

}
