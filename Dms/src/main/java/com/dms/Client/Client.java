package com.dms.Client;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.dms.dto.DmsDocumentDto;
import com.dms.dto.DocumentDetailDto;
import com.dms.dto.SubcategoryMasterDataDto;
import com.dms.dto.SubcategoryMasterDataListDto;
import com.dms.dto.request.DmsRequestForServiceDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.np.dms.db.pojos.SubcategoryMasterData;
import com.np.dms.dto.CategoryMasterDataDto;
import com.np.dms.dto.ResponseDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Client {

	private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

	private final RestTemplate restTemplate;
	private final ObjectMapper mapper;

	// Toggle to choose staging/prod (true -> testing/staging)
	@Value("${app.mode.testing:false}")
	private boolean isTestingEnvironment;

	// Common and new-regn endpoints (set these in application.properties /
	// application.yml)
	@Value("${dms.url.staging}")
	private String stagingDmsUrl;

	@Value("${dms.url.prod}")
	private String prodDmsUrl;

	@Value("${dms.url.staging.newRegn:}")
	private String stagingDmsUrlNewRegn;

	@Value("${dms.url.prod.newRegn:}")
	private String prodDmsUrlNewRegn;

	// If you need client-id/secret or headers, you can add them here as properties

//    public DmsUploadService(RestTemplate restTemplate, ObjectMapper mapper) {
//        this.restTemplate = restTemplate;
//        this.mapper = mapper;
//    }

	/**
	 * Main service method adapted from your original code.
	 */
	public DmsDocumentDto getListofDocToUploadOrUploaded(DmsRequestForServiceDto dmsRequest) {
		try {
			String baseUrl = isTestingEnvironment ? stagingDmsUrl : prodDmsUrl;
			ResponseDto response = null;

			// common flow
			String commonUrl = baseUrl;
			Object payload = buildVahanCitizenConfPayload(dmsRequest);
			response = postForResponseDto(commonUrl, payload);

			if (response != null) {
				DmsDocumentDto dmsDocument = fillAllDetails(response);
				dmsDocument.setApplno(dmsRequest.getApplNo());
				return dmsDocument;
			}
		} catch (Exception e) {
			LOGGER.error("getListofDocToUploadOrUploaded error: {}", e.getMessage(), e);
			return null;
		}
		return null;
	}

	private ResponseDto postForResponseDto(String endpoint, Object payload) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			// add headers if needed: headers.set("X-Client-Id","common-app");
			HttpEntity<Object> requestEntity = new HttpEntity<>(payload, headers);
			ResponseEntity<String> resp = restTemplate.exchange(endpoint, HttpMethod.POST, requestEntity, String.class);

			if (resp.getStatusCode().is2xxSuccessful() && resp.getBody() != null) {
				return mapper.readValue(resp.getBody(), ResponseDto.class);
			} else {
				LOGGER.warn("DMS call returned non-2xx status: {} for endpoint {}", resp.getStatusCode(), endpoint);
			}
		} catch (Exception e) {
			LOGGER.error("postForResponseDto error: {}", e.getMessage(), e);
		}
		return null;
	}

	private Object buildVahanCitizenConfPayload(DmsRequestForServiceDto dmsRequest) {
		var payload = new java.util.HashMap<String, Object>();
		payload.put("applNo", dmsRequest.getApplNo());
		payload.put("stateCd", dmsRequest.getStateCd());
		payload.put("regnNo", dmsRequest.getRegnNo());
		payload.put("purpose", dmsRequest.getPurCd());
		payload.put("authMode", dmsRequest.getAuthType());
		payload.put("serviceRgn", dmsRequest.getServiceRgn());
		payload.put("vhClassType", dmsRequest.getVhClassType());
		payload.put("j_key", dmsRequest.getJKey());
		payload.put("j_securityKey", dmsRequest.getJSecurityKey());
		return payload;
	}

	private Object buildNewRegnDocConfPayload(DmsRequestForServiceDto dmsRequest) {
		var payload = new java.util.HashMap<String, Object>();
		payload.put("applNo", dmsRequest.getApplNo());
		payload.put("stateCd", dmsRequest.getStateCd());
		payload.put("regnType", dmsRequest.getRegn_type());
		payload.put("purpose", dmsRequest.getAppl_purcd());
		payload.put("j_key", dmsRequest.getJKey());
		payload.put("j_securityKey", dmsRequest.getJSecurityKey());
		return payload;
	}

	private DmsDocumentDto fillAllDetails(ResponseDto response) {
		DmsDocumentDto returnObj = new DmsDocumentDto();
		try {
			List<DocumentDetailDto> mandatoryList = new ArrayList<>();
			List<DocumentDetailDto> nonUploadedList = new ArrayList<>();
			List<DocumentDetailDto> uploadedList = new ArrayList<>();

			if (response != null && response.getUploadDataDto() != null) {
				mandatoryList = fillAllListOfDMS(response.getUploadDataDto().getMandatoryList());
				nonUploadedList = fillAllListOfDMS(response.getUploadDataDto().getNonUploadedList());
				uploadedList = fillAllListOfDMS(response.getUploadDataDto().getUploadedList());

				returnObj.setMandatoryList(mandatoryList);
				returnObj.setNonUploadedList(nonUploadedList);
				returnObj.setUploadedList(uploadedList);
				returnObj.setDocFrom(response.getUploadDataDto().getDocFrom());
				returnObj.setPurposeName(response.getUploadDataDto().getPurposeName());
				returnObj.setStatusCode(response.getStatusCode());
			}
		} catch (Exception e) {
			LOGGER.error("fillAllDetails error: {}", e.getMessage(), e);
		}
		return returnObj;
	}

	/**
	 * Converts a list of CategoryMasterDataDto into List<DocumentDetail>
	 */
	private List<DocumentDetailDto> fillAllListOfDMS(List<CategoryMasterDataDto> list) {
		List<DocumentDetailDto> docList = new ArrayList<>();
		try {
			if (list != null && !list.isEmpty()) {
				for (CategoryMasterDataDto manDate : list) {
					DocumentDetailDto dobj = new DocumentDetailDto();
					dobj.setCatName(manDate.getCatName());
					dobj.setCatId(manDate.getCatId());
					dobj.setMandatory(manDate.getMandatory());
					dobj.setDocUploaded(manDate.isDocUploaded());
					dobj.setFileName(manDate.getFileName());
					dobj.setUniqueRefNo(manDate.getUniqueRefNo());
					dobj.setDocVerified(manDate.isDocVerified());
					dobj.setDocApproved(manDate.isDocApproved());
					dobj.setDocRecieved(manDate.isDocRecieved());
					dobj.setTempDocApproved(manDate.isTempDocApproved());

					if (dobj.getSubcategoryMasterData() == null) {
						dobj.setSubcategoryMasterData(new SubcategoryMasterDataDto());
					}
					if (dobj.getSubcategoryMasterDataList() == null || dobj.getSubcategoryMasterDataList().isEmpty()) {
						dobj.setSubcategoryMasterDataList(new ArrayList<>());
					}

					if (manDate.getSubcategoryMasterData() != null) {
						SubcategoryMasterData sc = manDate.getSubcategoryMasterData();
						dobj.getSubcategoryMasterData().setCatId(sc.getCat_id());
						try {
							dobj.getSubcategoryMasterData().setSubCatId(Integer.parseInt(sc.getSub_cat_id()));
						} catch (NumberFormatException nfe) {
							LOGGER.warn("Invalid sub_cat_id format: {}", sc.getSub_cat_id());
						}
						dobj.getSubcategoryMasterData().setSubCatName(sc.getSub_cat_name());
					}

					if (manDate.getSubcategoryMasterDataList() != null
							&& !manDate.getSubcategoryMasterDataList().isEmpty()) {
						for (com.np.dms.db.pojos.SubcategoryMasterData ff : manDate.getSubcategoryMasterDataList()) {
							SubcategoryMasterDataListDto tt = new SubcategoryMasterDataListDto();
							tt.setCatId(ff.getCat_id());
							tt.setSubCatId(ff.getSub_cat_id());
							tt.setSubCatName(ff.getSub_cat_name());
							dobj.getSubcategoryMasterDataList().add(tt);
						}
					}

					dobj.setObjectId(manDate.getObjectId());
					dobj.setFile(manDate.getFile());
					dobj.setApiFile(manDate.getApiFile());
					dobj.setMessage(manDate.getMessage());
					dobj.setDocUploadedDate(manDate.getDocUploadedDate());
					dobj.setDocUrl(manDate.getDocUrl());

					docList.add(dobj);
				}
			}
		} catch (Exception e) {
			LOGGER.error("fillAllListOfDMS error: {}", e.getMessage(), e);
		}
		return docList;
	}

}
