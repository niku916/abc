package com.dms.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DocumentDetailDto implements Comparable<DocumentDetailDto> {

	private MultipartFile uploadedfile;
	@JsonProperty("catId")
	private String catId;
	@JsonProperty("catName")
	private String catName;
	@JsonProperty("mandatory")
	private String mandatory;
	@JsonProperty("docUploaded")
	private Boolean docUploaded;
	@JsonProperty("fileName")
	private String fileName;
	@JsonProperty("uniqueRefNo")
	private String uniqueRefNo;
	@JsonProperty("docVerified")
	private Boolean docVerified;
	@JsonProperty("docApproved")
	private Boolean docApproved;
	@JsonProperty("docRecieved")
	private Boolean docRecieved;
	@JsonProperty("tempDocApproved")
	private Boolean tempDocApproved;
	@JsonProperty("subcategoryMasterData")
	private SubcategoryMasterDataDto subcategoryMasterData;
	@JsonProperty("subcategoryMasterDataList")
	private List<SubcategoryMasterDataListDto> subcategoryMasterDataList;
	@JsonProperty("objectId")
	private String objectId;
	@JsonProperty("file")
	private Object file;
	@JsonProperty("apiFile")
	private Object apiFile;
	@JsonProperty("message")
	private String message;
	@JsonProperty("docUploadedDate")
	private String docUploadedDate;
	@JsonProperty("docUrl")
	private String docUrl;
	private DmsUploadDto dmsUpload = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	private Integer selectOrder = 0;

	@Override
	public int compareTo(DocumentDetailDto uploadedList) {
		int order = uploadedList.getSelectOrder();

		// desc order
		return order - this.selectOrder;
	}

}
