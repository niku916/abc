package com.dms.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class SubcategoryMasterDataListDto {
	
	@JsonProperty("cat_id")
	private String catId;
	@JsonProperty("sub_cat_id")
	private String subCatId;
	@JsonProperty("sub_cat_name")
	private String subCatName;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}
