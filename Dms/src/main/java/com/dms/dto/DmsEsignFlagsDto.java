package com.dms.dto;

import lombok.Data;

@Data
public class DmsEsignFlagsDto {
	
	private boolean renderOwnerInfo=false;
//	private boolean renderUploadDoc=false;
	private boolean esignStatus = false;
	private boolean dmsEsignMendateStatus = false;
	private Boolean userSelectSignStatus = null;
	private boolean frzEsignOption= false;
	private boolean mandateFileSelect = true;
	private boolean mergeDocOption;
	private boolean esignFstatus = false;

}
