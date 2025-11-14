package com.dms.dto;

import lombok.Data;

@Data
public class DmsFlagDto {

	private Boolean signDoc;
	private Boolean signDocCol;
	private Boolean disableSignButton;
	private Boolean viewSignDocBtn;
	private Boolean uploadDocBtn;
	private Boolean disableUploadBtn = false;
	private Boolean viewDocBtn;
	private Boolean browseOption;

}
