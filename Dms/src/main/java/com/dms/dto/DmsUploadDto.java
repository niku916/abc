package com.dms.dto;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import lombok.Data;

@Data
public class DmsUploadDto {
	private MultipartFile  file;
	private String fileName;
	private String selectedFileName;
	private String key;
	private String docUrl;
	private String signedDocUrl;
	private int index;
	private Integer selectOrder = 0;
	private Integer subCatId;
	private String docType;
	private DmsFlagDto dmsFlag;
	private InputStream inputStream;
	private String signBtn = "eSign";

	private Boolean fileSelect = false;
	private Boolean dispalyCol = true;
	private Boolean esigned = false;
	private Boolean dmsUploaded = false;

	private Boolean dmsUploadedAlready = false;
	private Boolean fileDoubleSign = false;
	private Boolean disableUploadBtn = false;
	private StreamingResponseBody viewSignFile = null;
	private byte[] imageInBytes;

}
