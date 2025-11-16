package com.dms.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dms.dto.DmsDocumentDto;
import com.dms.dto.DocumentDetailDto;
import com.dms.dto.SubcategoryMasterDataDto;
import com.dms.dto.SubcategoryMasterDataListDto;
import com.dms.dto.request.DmsRequestForServiceDto;
import com.np.dms.client.DmsRestClient;
import com.np.dms.dto.CategoryMasterDataDto;
import com.np.dms.dto.RequestDataDto;
import com.np.dms.dto.ResponseDto;

@Service
public class ExternalService {

	private static final Logger logger = LogManager.getLogger(ExternalService.class);

	@Value("${dms.url.staging}")
	private String dmsStagingServicesUrl;
	@Value("${dms.url.prod}")
	private String dmsProdServicesUrl;
	

	public DmsDocumentDto getListofDocToUploadOrUploadeds(DmsRequestForServiceDto dmsRequest) {
		ResponseDto response = null;
		
		try {
			DmsRestClient restClient = new DmsRestClient(dmsStagingServicesUrl, "common-app", "VtDocuments");
			response = restClient.invokCitizenConfDocument(vahanCitizenConf(dmsRequest));
			if (response != null) {
				DmsDocumentDto dmsDocument = fillaldetails(response);
				dmsDocument.setApplno(dmsRequest.getApplNo());
				return dmsDocument;
			}

		} catch (Exception e) {
			logger.error("getListofDocToUploadOrUploaded " + e.getMessage());
			return null;
		}
		return null;
	}
	
	private static RequestDataDto vahanCitizenConf(DmsRequestForServiceDto dmsRequest) {
		RequestDataDto req = new RequestDataDto();
		try {
			req.setAppNo(dmsRequest.getApplNo());
			req.setState(dmsRequest.getStateCd());
			req.setPurposeCode(dmsRequest.getPurCd());
			req.setRegNo(dmsRequest.getRegnNo());
			req.setAuthMode(dmsRequest.getAuthType());
			req.setServiceRgn(dmsRequest.getServiceRgn());
			req.setVhClassType(String.valueOf(dmsRequest.getVhClassType()));
			// System.out.println(req.toString());
		} catch (Exception ex) {
			logger.error("vahanCitizenConf " + ex.getCause(), ex);
		}
		return req;
	}
	
	public DmsDocumentDto fillaldetails(ResponseDto response) {
		DmsDocumentDto returndobj = new DmsDocumentDto();
		try {
			List<DocumentDetailDto> mandatelist = new ArrayList<>();
			List<DocumentDetailDto> NonUploadedlist = new ArrayList<>();
			List<DocumentDetailDto> uploadedlist = new ArrayList<>();
			mandatelist = fillAllListOfDMS(response.getUploadDataDto().getMandatoryList());
			NonUploadedlist = fillAllListOfDMS(response.getUploadDataDto().getNonUploadedList());
			uploadedlist = fillAllListOfDMS(response.getUploadDataDto().getUploadedList());
			returndobj.setMandatoryList(mandatelist);
			returndobj.setNonUploadedList(NonUploadedlist);
			returndobj.setUploadedList(uploadedlist);
			returndobj.setDocFrom(response.getUploadDataDto().getDocFrom());
			returndobj.setPurposeName(response.getUploadDataDto().getPurposeName());
			returndobj.setStatusCode(response.getStatusCode());
		} catch (Exception e) {
			logger.error("fillaldetails " + e.getMessage());
		}
		return returndobj;
	}
	
	public List<DocumentDetailDto> fillAllListOfDMS(List<CategoryMasterDataDto> list) {
		List<DocumentDetailDto> docList = new ArrayList<>();
		try {
			if (list != null && list.size() > 0) {
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
						dobj.getSubcategoryMasterData().setCatId(manDate.getSubcategoryMasterData().getCat_id());
						dobj.getSubcategoryMasterData()
								.setSubCatId(Integer.parseInt(manDate.getSubcategoryMasterData().getSub_cat_id()));
						dobj.getSubcategoryMasterData()
								.setSubCatName(manDate.getSubcategoryMasterData().getSub_cat_name());
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
			logger.error("fillAllListOfDMS " + e.getMessage());
		}
		return docList;
	}
	
}
