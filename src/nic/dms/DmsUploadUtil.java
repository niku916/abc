package nic.dms;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.faces.application.FacesMessage;
import javax.sql.RowSet;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.np.dms.client.DmsRestClient;
import com.np.dms.db.pojos.DmsFileDetails;
import com.np.dms.db.pojos.VtDocuments;
import com.np.dms.dto.CategoryMasterDataDto;
import com.np.dms.dto.RegnRequestDto;
import com.np.dms.dto.RequestDataDto;
import com.np.dms.dto.ResponseDto;

import nic.dms.common.constant.CommonConstant;
import nic.dms.common.utils.CommonUtils;
import nic.dms.common.utils.JSFUtils;
import nic.dms.pojo.DmsDocument;
import nic.dms.pojo.DmsDocumentUpload;
import nic.dms.pojo.DmsFileDetail;
import nic.dms.pojo.DmsFileUploadDetail;
import nic.dms.pojo.DocumentDetail;
import nic.dms.pojo.SubcategoryMasterData;
import nic.dms.pojo.SubcategoryMasterDataList;
import nic.dms.pojo.reqestresponse.DmsEsignDetailsUpdateRequest;
import nic.dms.pojo.reqestresponse.DmsRequest;
import nic.dms.pojo.reqestresponse.DoublesignPendingRecords;
import nic.dms.pojo.reqestresponse.ServiceResponse;
import nic.org.apache.log4j.Logger;
import nic.vahan.db.connection.TransactionManager;
import resources.ApplicationConfig;

public class DmsUploadUtil {
	private static final Logger LOGGER = Logger.getLogger(DmsUploadUtil.class);

	// upload documents==========================
	// upload file in case of merge == iterate loop to upload the same file
	// corresponding to each dms file
	public void uploadMergeDocumentToDms(DmsDocumentUpload dmsDocumentUpload, Integer index, String uploadedFrom) {
		try {
			String fileName = dmsDocumentUpload.getMergefileName();
			List<DocumentDetail> documents = dmsDocumentUpload.getDmsDocument().getDocumentList();
			int cnt = 0;
			for (DocumentDetail document : documents) {
				if (document.getDmsUpload().getFileSelect()) {
					uploadDocument(dmsDocumentUpload, cnt, uploadedFrom, fileName);
					document.getDmsUpload().setFileSelect(false);
					document.getDmsUpload().setEsigned(true);
					document.setSelectOrder(2);
				}
				cnt++;
			}
		} catch (Exception e) {
			LOGGER.error("uploadMergeDocumentToDms " + e.getMessage());
		}
	}

	// upload without merge file ================================
	public boolean uploadDocumentToDms(DmsDocumentUpload dmsDocumentUpload, Integer index, String uploadedFrom) {
		boolean status = false;
		try {
			String fileName = dmsDocumentUpload.getDmsDocument().getDocumentList().get(index).getDmsUpload()
					.getFileName();
			status = uploadDocument(dmsDocumentUpload, index, uploadedFrom, fileName);
			return status;
		} catch (Exception e) {
			LOGGER.error("uploadDocumentToDms " + e.getMessage());
		}
		return status;
	}

	public boolean uploadDocument(DmsDocumentUpload dmsDocumentUpload, Integer index, String uploadedFrom,
			String fileName) {
		boolean status = false;
		try {
			DmsFileUploadDetail dmsFileUploadDetail = new DmsFileUploadDetail();
			DmsFileDetail dmsFileDetail = new DmsFileDetail();
			DocumentDetail document = dmsDocumentUpload.getDmsDocument().getDocumentList().get(index);

			dmsFileDetail.setFileName(fileName);

			File file = new File(CommonConstant.FILE_PATH + fileName);
			byte[] fileContent = Files.readAllBytes(file.toPath());
			dmsFileDetail.setApiFile(fileContent);
			dmsFileDetail.setDocCatgId(document.getCatId());
			dmsFileDetail.setUploaded(false);
			dmsFileDetail.setDocId(document.getSubcategoryMasterData().getSubCatId());

			List<DmsFileDetail> dmsFileDetails = new ArrayList<DmsFileDetail>();
			dmsFileDetails.add(dmsFileDetail);
			dmsFileUploadDetail.setDmsFileDetails(dmsFileDetails);

			dmsFileUploadDetail.setJKey(CommonConstant.J_KEY);
			dmsFileUploadDetail.setJSecurityKey(CommonConstant.J_SECURITY_KEY);
			dmsFileUploadDetail.setState(dmsDocumentUpload.getDmsDocument().getState());
			dmsFileUploadDetail.setApplno(dmsDocumentUpload.getDmsDocument().getApplno());
			boolean uploadStatus = false;
			String key = getKey(dmsFileUploadDetail, document,
					dmsDocumentUpload.getDmsRequest().getDmsServiceUrl() + CommonConstant.DOC_KEY + "/");

			ServiceResponse sres = uploadDmsDocument(dmsFileUploadDetail, dmsDocumentUpload, document, key,
					uploadedFrom);

			if (sres != null) {
				if (CommonConstant.DMS_UPLOAD_SUCCESS.equalsIgnoreCase(sres.getStatusValue())) {
					document.getDmsUpload().setDmsUploaded(true);
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
							JSFUtils.getRelevantLangMsg("lebelname.uploadedSuccess"));
					RequestContext.getCurrentInstance().showMessageInDialog(message);
					// delete the local file
					String filePath = CommonConstant.FILE_PATH;
					Path path = new File(filePath, document.getDmsUpload().getFileName()).toPath();
					Files.deleteIfExists(path);
					status = true;
					// saveUpdateDmsUpdateDetail(dmsDocumentUpload, index,
					// uploadedFrom, fileName);
					moveFromVTtoVHDocTable(dmsDocumentUpload, index, uploadedFrom, fileName);
				} else {
					document.getDmsUpload().setDmsUploaded(false);
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", sres.getMessage());
					RequestContext.getCurrentInstance().showMessageInDialog(message);
				}
			} else {
				document.getDmsUpload().setDmsUploaded(false);
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
						JSFUtils.getRelevantLangMsg("lebelname.esign.dmsNotConnect"));
				RequestContext.getCurrentInstance().showMessageInDialog(message);
			}

			return status;
		} catch (Exception e) {
			LOGGER.error("uploadDocument " + e.getMessage());
		}
		return status;
	}

	// update documents ============================================
	// update file in case of merge == iterate loop to upload the same file
	// corresponding to each dms file
	public void updateMergeDocumentToDms(DmsDocumentUpload dmsDocumentUpload, Integer index, String uploadedFrom) {
		try {
			String fileName = dmsDocumentUpload.getMergefileName();
			List<DocumentDetail> documents = dmsDocumentUpload.getDmsDocument().getDocumentList();
			int cnt = 0;
			for (DocumentDetail document : documents) {
				if (document.getDmsUpload().getFileSelect()) {
					updateDocument(dmsDocumentUpload, cnt, uploadedFrom, fileName);
					document.getDmsUpload().setFileSelect(false);
					document.getDmsUpload().setEsigned(true);
					document.setSelectOrder(2);
				}
				cnt++;
			}
		} catch (Exception e) {
			LOGGER.error("updateMergeDocumentToDms " + e.getMessage());
		}
	}

	// update file without merge
	public boolean updateDocumentToDms(DmsDocumentUpload dmsDocumentUpload, Integer index, String uploadedFrom) {
		boolean status = false;
		try {
			String fileName = dmsDocumentUpload.getDmsDocument().getDocumentList().get(index).getDmsUpload()
					.getFileName();
			status = updateDocument(dmsDocumentUpload, index, uploadedFrom, fileName);
			return status;
		} catch (Exception e) {
			LOGGER.error("updateDocumentToDms " + e.getMessage());
		}
		return status;
	}

	public boolean updateDocument(DmsDocumentUpload dmsDocumentUpload, Integer index, String uploadedFrom,
			String fileName) {
		boolean status = false;
		try {
			DmsFileUploadDetail dmsFileUploadDetail = new DmsFileUploadDetail();
			DmsFileDetail dmsFileDetail = new DmsFileDetail();
			DocumentDetail document = dmsDocumentUpload.getDmsDocument().getDocumentList().get(index);

			dmsFileDetail.setFileName(fileName);

			File file = new File(CommonConstant.FILE_PATH + fileName);
			byte[] fileContent = Files.readAllBytes(file.toPath());
			dmsFileDetail.setApiFile(fileContent);

			dmsFileDetail.setDocCatgId(document.getCatId());
			dmsFileDetail.setUploaded(false);
			dmsFileDetail.setDocId(document.getSubcategoryMasterData().getSubCatId());

			List<DmsFileDetail> dmsFileDetails = new ArrayList<DmsFileDetail>();
			dmsFileDetails.add(dmsFileDetail);
			dmsFileUploadDetail.setDmsFileDetails(dmsFileDetails);

			dmsFileUploadDetail.setJKey(CommonConstant.J_KEY);
			dmsFileUploadDetail.setJSecurityKey(CommonConstant.J_SECURITY_KEY);
			dmsFileUploadDetail.setState(dmsDocumentUpload.getDmsDocument().getState());
			dmsFileUploadDetail.setApplno(dmsDocumentUpload.getDmsDocument().getApplno());

			String key = getKeyForUploadedFile(dmsDocumentUpload, document);
			if (!key.equals("")) {
				ServiceResponse sres = updateDmsDocument(dmsFileUploadDetail, dmsDocumentUpload, document, key,
						uploadedFrom);

				if (sres != null) {
					if (CommonConstant.DMS_UPLOAD_SUCCESS.equalsIgnoreCase(sres.getStatusValue())) {
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
								JSFUtils.getRelevantLangMsg("lebelname.uploadedSuccess"));
						RequestContext.getCurrentInstance().showMessageInDialog(message);

						String filePath = CommonConstant.FILE_PATH;
						Path path = new File(filePath, document.getDmsUpload().getFileName()).toPath();
						// Files.deleteIfExists(path);
						document.getDmsUpload().setDmsUploaded(true);
						status = true;
					} else {
						document.getDmsUpload().setDmsUploaded(false);
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", sres.getMessage());
						RequestContext.getCurrentInstance().showMessageInDialog(message);
					}
				} else {
					document.getDmsUpload().setDmsUploaded(false);
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							JSFUtils.getRelevantLangMsg("lebelname.esign.dmsNotConnect"));
					RequestContext.getCurrentInstance().showMessageInDialog(message);
				}
			}
			return status;
		} catch (Exception e) {
			LOGGER.error("updateDocument " + e.getMessage());
		}
		return status;
	}

	public ServiceResponse uploadDmsDocument(DmsFileUploadDetail dmsFileUploadDetail,
			DmsDocumentUpload dmsDocumentUpload, DocumentDetail documentDetail, String key, String uploadedFrom) {
		ServiceResponse sres = null;
		DmsRequest dmsRequest = dmsDocumentUpload.getDmsRequest();
		if (!key.equals("")) {
			dmsFileUploadDetail.setKey(key);
			dmsFileUploadDetail.setPrj_name("VCTZNS");
			sres = updateDocumentApiCall(dmsFileUploadDetail, dmsDocumentUpload.getDmsRequest());
			if (CommonConstant.DMS_UPLOAD_SUCCESS.equalsIgnoreCase(sres.getStatusValue())) {
				documentDetail.getDmsUpload().setKey(key);
				String docUrl = dmsRequest.getDmsServiceUrl() + CommonConstant.DOC_VIEW + "/" + key;
				documentDetail.getDmsUpload().setDocUrl(docUrl);
				StreamedContent viewSignFile = ImageToInputStream(key);
				byte[] imageInBytes = ImageToByte(key);
				documentDetail.getDmsUpload().setImageInBytes(imageInBytes);
				documentDetail.getDmsUpload().setViewSignFile(viewSignFile);
			}
		} else {
			sres = uploadDocumentApiCall(dmsFileUploadDetail, dmsDocumentUpload.getDmsRequest());
			if (CommonConstant.DMS_UPLOAD_SUCCESS.equalsIgnoreCase(sres.getStatusValue())) {
				// key = getKeyForUploadedFile(dmsDocumentUpload,
				// documentDetail);
				// new request
				key = getKey(dmsFileUploadDetail, documentDetail,
						dmsDocumentUpload.getDmsRequest().getDmsServiceUrl() + CommonConstant.DOC_KEY + "/");
				// new request
				String docUrl = dmsRequest.getDmsServiceUrl() + CommonConstant.DOC_VIEW + "/" + key;
				documentDetail.getDmsUpload().setDocUrl(docUrl);
				documentDetail.getDmsUpload().setKey(key);
				byte[] imageInBytes = ImageToByte(key);
				StreamedContent viewSignFile = ImageToInputStream(key);
				documentDetail.getDmsUpload().setImageInBytes(imageInBytes);
				documentDetail.getDmsUpload().setViewSignFile(viewSignFile);
				if (dmsDocumentUpload.getDmsRequest().getEsignFlag()) {
					// service call from vahancitizen
					DmsEsignDetailsUpdateRequest updateRequest = prepareUpdateReqObj(dmsDocumentUpload, documentDetail,
							uploadedFrom);
					// TODO=============================
					String saveUrl = "";
					saveDmsEsignDetails(updateRequest, dmsDocumentUpload.getDmsRequest().getEsignDetailUpdateApi());
				}
			}
		}

		return sres;
	}

	// //update document
	public ServiceResponse updateDmsDocument(DmsFileUploadDetail dmsFileUploadDetail,
			DmsDocumentUpload dmsDocumentUpload, DocumentDetail documentDetail, String key, String uploadedFrom) {
		ServiceResponse sres = null;
		dmsFileUploadDetail.setKey(key);
		sres = updateDocumentApiCall(dmsFileUploadDetail, dmsDocumentUpload.getDmsRequest());
		if (CommonConstant.DMS_UPLOAD_SUCCESS.equalsIgnoreCase(sres.getStatusValue())) {
			documentDetail.getDmsUpload().setKey(key);
			String docUrl = dmsDocumentUpload.getDmsRequest().getDmsServiceUrl() + CommonConstant.DOC_VIEW + "/" + key;
			documentDetail.getDmsUpload().setDocUrl(docUrl);
			if (dmsDocumentUpload.getDmsRequest().getEsignFlag()) {
				// service call from vahancitizen
				DmsEsignDetailsUpdateRequest updateRequest = prepareUpdateReqObj(dmsDocumentUpload, documentDetail,
						uploadedFrom);
				// TODO=============================
				String saveUrl = "";
				saveDmsEsignDetails(updateRequest, dmsDocumentUpload.getDmsRequest().getEsignDetailUpdateApi());
			}

		}
		return sres;
	}

	// DMS Services++++++++++++++++++++++++++++++++++
	// ==================get list of document to be upload or already uploaded
	// ==============

	public DmsDocument getListofDocToUploadOrUploaded(DmsRequest dmsRequest) {
		Client client;
		WebTarget target;
		ResponseDto response = null;
		ObjectMapper mapper = new ObjectMapper();
		// System.out.println(dmsRequest.getApplNo() + " " +
		// dmsRequest.getStateCd() + " " + dmsRequest.getRegnNo()
		// + " " + dmsRequest.getPurCd() + " " + dmsRequest.getServiceRgn() + "
		// "
		// + dmsRequest.getVhClassType());
		String URL = "";
		boolean isTestingEnviroment = Boolean.valueOf(new ApplicationConfig(ApplicationConfig.PROP_PAYMENT_MODE)
				.getProperties(ApplicationConfig.PROP_KEY_MODE_ISTESTING));
		if (isTestingEnviroment) {
			URL = ApplicationConfig.STAGING_DMS_URL;
		} else {
			URL = ApplicationConfig.PROD_DMS_URL;
		}
		try {
			// old request

			// client = ClientBuilder.newClient();

			// if (!CommonUtils.isNullOrBlank(dmsRequest.getPortalCd())
			// && dmsRequest.getPortalCd().equalsIgnoreCase("OP")) {
			// target = client.target(dmsRequest.getDmsServiceUrl() +
			// CommonConstant.DOC_LIST)
			// .queryParam("applno", dmsRequest.getApplNo()).queryParam("state",
			// dmsRequest.getStateCd())
			// .queryParam("purpose",
			// dmsRequest.getPurCd()).queryParam("authMode",
			// dmsRequest.getAuthType())
			// .queryParam("transType", dmsRequest.getTransType())
			// .queryParam("permit",
			// dmsRequest.getPmtType()).queryParam("j_key",
			// dmsRequest.getjKey())
			// .queryParam("j_securityKey", dmsRequest.getjSecurityKey());

			// old request
			// } else {
			// old request

			// target = client.target(dmsRequest.getDmsServiceUrl() +
			// CommonConstant.DOC_LIST)
			// .queryParam("applno",
			// dmsRequest.getApplNo()).queryParam("state",
			// dmsRequest.getStateCd())
			// .queryParam("regNo",
			// dmsRequest.getRegnNo()).queryParam("purpose",
			// dmsRequest.getPurCd())
			// .queryParam("authMode", dmsRequest.getAuthType())
			// .queryParam("serviceRgn", dmsRequest.getServiceRgn())
			// .queryParam("vhClassType", dmsRequest.getVhClassType())
			// .queryParam("j_key", dmsRequest.getjKey())
			// .queryParam("j_securityKey", dmsRequest.getjSecurityKey());

			// old request

			// new request

			DmsRestClient restClient = new DmsRestClient(URL, "common-app", "VtDocuments");
			if ((dmsRequest.getAppl_purcd() == ApplicationConfig.PUR_NOCISUUED
					|| dmsRequest.getAppl_purcd() == ApplicationConfig.PUR_TRCISUUED)
					&& (!CommonUtils.isNullOrBlank(dmsRequest.getRegn_type()))) {
				String ReqURl = "";
				if (isTestingEnviroment) {
					ReqURl = ApplicationConfig.STAGING_DMS_URL_NEW_REGN;
				} else {
					ReqURl = ApplicationConfig.PROD_DMS_URL_NEW_REGN;
				}
				DmsRestClient restClientforTRC = new DmsRestClient(ReqURl, "new-regn-type", "VtDocuments");
				response = restClientforTRC.invokNewRegnDocConf(newRegnDocConf(dmsRequest));
			} else {
				response = restClient.invokCitizenConfDocument(vahanCitizenConf(dmsRequest));
			}
			// new request

			// }
			// new request
			if (response != null) {
				DmsDocument dmsDocument = fillaldetails(response);
				dmsDocument.setApplno(dmsRequest.getApplNo());
				return dmsDocument;
			}
			// new request

			// old request
			// String trgt = target.toString();
			// String jsonStr = target.request().get(String.class);
			// DmsDocument dmsDocument = mapper.readValue(jsonStr,
			// DmsDocument.class);
			// return dmsDocument;
			// old request
		} catch (Exception e) {
			LOGGER.error("getListofDocToUploadOrUploaded " + e.getMessage());
			return null;
		}
		return null;
	}

	public DmsDocument fillaldetails(ResponseDto response) {
		DmsDocument returndobj = new DmsDocument();
		try {
			List<DocumentDetail> mandatelist = new ArrayList<>();
			List<DocumentDetail> NonUploadedlist = new ArrayList<>();
			List<DocumentDetail> uploadedlist = new ArrayList<>();
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
			LOGGER.error("fillaldetails " + e.getMessage());
		}
		return returndobj;
	}

	public List<DocumentDetail> fillAllListOfDMS(List<CategoryMasterDataDto> list) {
		List<DocumentDetail> docList = new ArrayList<>();
		try {
			if (list != null && list.size() > 0) {
				for (CategoryMasterDataDto manDate : list) {
					DocumentDetail dobj = new DocumentDetail();
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
						dobj.setSubcategoryMasterData(new SubcategoryMasterData());
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
							SubcategoryMasterDataList tt = new SubcategoryMasterDataList();
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
			LOGGER.error("fillAllListOfDMS " + e.getMessage());
		}
		return docList;
	}

	// ===================upload documents=============================
	public ServiceResponse uploadDocumentApiCall(DmsFileUploadDetail dmsFileUploadDetail, DmsRequest request) {
		ServiceResponse serviceResponse = null;
		try {
			String URL = "";
			boolean isTestingEnviroment = Boolean.valueOf(new ApplicationConfig(ApplicationConfig.PROP_PAYMENT_MODE)
					.getProperties(ApplicationConfig.PROP_KEY_MODE_ISTESTING));
			if (isTestingEnviroment) {
				URL = ApplicationConfig.STAGING_DMS_URL;
			} else {
				URL = ApplicationConfig.PROD_DMS_URL;
			}
			// old request

			// ObjectMapper mapper = new ObjectMapper();
			// String jsonInString =
			// mapper.writeValueAsString(dmsFileUploadDetail);
			// Client clientUpload = ClientBuilder.newClient();
			// WebTarget targetUpload =
			// clientUpload.target(request.getDmsServiceUrl() +
			// CommonConstant.DOC_UPLOAD);
			// String responseUpload =
			// targetUpload.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
			// .post(Entity.json(jsonInString), String.class);
			//
			// JSONObject jsonObject = new JSONObject(responseUpload);

			// old request
			ResponseDto response = null;
			DmsRestClient restClient = new DmsRestClient(URL, "common-app", "VtDocuments");
			if (request.getVideoUpload()
					&& dmsFileUploadDetail.getDmsFileDetails().get(0).getFileName().contains("Video")) {
				response = restClient.invokeUploadImageVideoAPI(uploadSingleDocument(dmsFileUploadDetail, request));
			} else {
				response = restClient.invokeUploadDocAPI(uploadDocument(dmsFileUploadDetail, request));
			}
			serviceResponse = new ServiceResponse();
			// serviceResponse.setMessage(jsonObject.get("message").toString());
			// serviceResponse.setStatusValue(jsonObject.get("statusCode").toString());
			serviceResponse.setMessage(response.getStatusDesc());
			serviceResponse.setStatusValue(response.getStatusCode());
			return serviceResponse;
		} catch (Exception e) {
			LOGGER.error("uploadDocumentApiCall for ApplNo - " + request.getApplNo() + " " + e.getMessage());
		}
		return serviceResponse;
	}

	private static RequestDataDto uploadDocument(DmsFileUploadDetail dmsFileUploadDetail, DmsRequest request)
			throws FileNotFoundException {
		RequestDataDto req = new RequestDataDto();
		DmsFileDetails fileDetails = new DmsFileDetails();
		List<DmsFileDetails> deList = new ArrayList<>();
		try {
			if (dmsFileUploadDetail != null && (dmsFileUploadDetail.getDmsFileDetails() != null
					&& dmsFileUploadDetail.getDmsFileDetails().size() > 0)) {
				req.setAppNo(request.getApplNo());
				req.setState(request.getStateCd());
				fileDetails.setApiFile(dmsFileUploadDetail.getDmsFileDetails().get(0).getApiFile());
				fileDetails.setFileName(dmsFileUploadDetail.getDmsFileDetails().get(0).getFileName());
				fileDetails.setDocCatgId(dmsFileUploadDetail.getDmsFileDetails().get(0).getDocCatgId());
				fileDetails.setDocId(dmsFileUploadDetail.getDmsFileDetails().get(0).getDocId());
				deList.add(fileDetails);
				req.setDmsFileDetails(deList);
			}

		} catch (Exception e) {
			LOGGER.error("uploadDocument " + e.getMessage());
		}
		return req;
	}

	// ===========update documents=======================================

	public ServiceResponse updateDocumentApiCall(DmsFileUploadDetail dmsFileUploadDetail, DmsRequest request) {
		ServiceResponse serviceResponse = null;
		try {
			String URL = "";
			boolean isTestingEnviroment = Boolean.valueOf(new ApplicationConfig(ApplicationConfig.PROP_PAYMENT_MODE)
					.getProperties(ApplicationConfig.PROP_KEY_MODE_ISTESTING));
			if (isTestingEnviroment) {
				URL = ApplicationConfig.STAGING_DMS_URL;
			} else {
				URL = ApplicationConfig.PROD_DMS_URL;
			}
			// old request

			// ObjectMapper mapper = new ObjectMapper();
			// String jsonInString =
			// mapper.writeValueAsString(dmsFileUploadDetail);
			// Client clientUpload = ClientBuilder.newClient();
			// WebTarget targetUpload =
			// clientUpload.target(request.getDmsServiceUrl() +
			// CommonConstant.DOC_UPDATE);
			// String responseUpload =
			// targetUpload.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
			// .post(Entity.json(jsonInString), String.class);
			//
			// JSONObject jsonObject = new JSONObject(responseUpload);
			//
			// serviceResponse = new ServiceResponse();
			// serviceResponse.setMessage(jsonObject.get("message").toString());
			// serviceResponse.setStatusValue(jsonObject.get("statusCode").toString());

			// old request

			ResponseDto response = null;
			DmsRestClient restClient = new DmsRestClient(URL, "common-app", "VtDocuments");
			if (request.getVideoUpload()
					&& dmsFileUploadDetail.getDmsFileDetails().get(0).getFileName().contains("Video")) {
				response = restClient.invokeUploadImageVideoAPI(uploadSingleDocument(dmsFileUploadDetail, request));
			} else {
				response = restClient.invokeUpdateDocAPI(updateDocumentNewApi(dmsFileUploadDetail, request));
			}
			serviceResponse = new ServiceResponse();
			serviceResponse.setMessage(response.getStatusDesc());
			serviceResponse.setStatusValue(response.getStatusCode());
		} catch (Exception e) {
			LOGGER.error("updateDocumentApiCall " + e.getMessage());
		}
		return serviceResponse;
	}

	private static RequestDataDto updateDocumentNewApi(DmsFileUploadDetail dmsFileUploadDetail, DmsRequest request)
			throws FileNotFoundException, IOException {
		RequestDataDto req = new RequestDataDto();
		try {
			req.setAppNo(request.getApplNo());
			req.setState(request.getStateCd());
			req.setDocKey(dmsFileUploadDetail.getKey());
			req.setFileToUpload(dmsFileUploadDetail.getDmsFileDetails().get(0).getApiFile());
			req.setPrj_name(dmsFileUploadDetail.getPrj_name());
			req.setFileName(dmsFileUploadDetail.getDmsFileDetails().get(0).getFileName());
		} catch (Exception e) {
			LOGGER.error("updateDocumentNewApi " + e.getMessage());
		}
		return req;
	}
	// ++++++++++++++++++++++++++

	public String getDmsUploadedFileUrl(String appNo, String catId, DmsRequest dmsRequest) {
		String docUrl = null;
		try {
			// getKeyForUploadedFile(dmsDocumentUpload, uploadedDocs);

			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(dmsRequest.getDmsServiceUrl() + CommonConstant.DOC_KEY + "/" + appNo);
			String response = target.request().get(String.class);
			JSONObject jsonObject = new JSONObject(response);
			JSONArray jsonArray = jsonObject.getJSONArray("vtDocuments");
			String key = "";
			for (int i = 0; i < jsonArray.length(); i++) {
				String docCatid = (String) ((JSONObject) jsonArray.get(i)).get("doc_catg_id");
				if (docCatid.equalsIgnoreCase(catId)) {
					key = (String) ((JSONObject) jsonArray.get(i)).get("doc_object_id");
					break;
				}
			}
			if (key != null) {
				docUrl = dmsRequest.getDmsServiceUrl() + CommonConstant.DOC_VIEW + "/" + key;
			}
			// LOGGER.info("getDmsUploadedFileUrl docUrl =========== " +
			// docUrl);
			return docUrl;
		} catch (Exception e) {
			LOGGER.error("getDmsUploadedFileUrl " + e.getMessage());
			return docUrl;
		}
	}

	// ========= convert image file to pdf=====================
	public String imageToPdf(String filePath, String inputFile) {
		Document document = new Document();

		String input = filePath + File.separator + inputFile;
		String[] fileSplt = inputFile.split("\\.");
		String outputFile = fileSplt[0] + ".pdf";
		String output = filePath + File.separator + outputFile;
		try {
			FileOutputStream fos = new FileOutputStream(output);
			PdfWriter writer = PdfWriter.getInstance(document, fos);
			writer.open();
			document.open();
			document.add(Image.getInstance(input));
			document.close();
			writer.close();
			fos.close();
			return outputFile;
		} catch (Exception e) {
			LOGGER.error("imageToPdf " + e.getMessage());
			return null;
		}
	}

	// match the purpose code
	public boolean getMatchPurposeCode(String purCode1, String purCode2) {
		if (purCode2 != null && purCode1 != null) {
			String[] purcodes2 = purCode2.split(",");
			String[] purcodes1 = purCode1.split(",");
			for (String pc2 : purcodes2) {
				for (String pc1 : purcodes1) {
					if (pc1.equalsIgnoreCase(pc2)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public String getFileType(String fileName) {
		String[] fileSplt = fileName.split("\\.");
		String fileExt = fileSplt[fileSplt.length - 1];

		if (fileExt.equalsIgnoreCase(CommonConstant.DOC_TYPE_PDF)) {
			return CommonConstant.DOC_TYPE_PDF;
		} else {
			return CommonConstant.DOC_TYPE_NONPDF;
		}
	}

	// ok
	public List<DocumentDetail> copyUploadedNonuploadedToDocumentList(DmsDocumentUpload dmsDocumentUpload) {
		List<DocumentDetail> documentList = new ArrayList<DocumentDetail>();
		List<DocumentDetail> uploadedList = dmsDocumentUpload.getDmsDocument().getUploadedList();
		List<DocumentDetail> nonUploadedList = dmsDocumentUpload.getDmsDocument().getNonUploadedList();
		DocumentDetail doc;
		for (DocumentDetail uploadDoc : uploadedList) {
			doc = uploadDoc;
			if (doc.getDocUploaded()) {
				String docUrl = getDmsUploadedFileUrl(dmsDocumentUpload.getDmsDocument().getApplno(), doc.getCatId(),
						dmsDocumentUpload.getDmsRequest());
				doc.getDmsUpload().setDocUrl(docUrl);
				doc.getDmsUpload().setDmsUploaded(true);
				doc.getDmsUpload().setDmsUploadedAlready(true);
				doc.setSelectOrder(2);
				if (doc.getSubcategoryMasterData() == null) {
					doc.setSubcategoryMasterData(new SubcategoryMasterData());
				}
				uploadDoc.setSelectOrder(2);
			}
			documentList.add(doc);
		}

		for (DocumentDetail nonUploadDoc : nonUploadedList) {
			doc = nonUploadDoc;
			if (doc.getSubcategoryMasterData() == null) {
				doc.setSubcategoryMasterData(new SubcategoryMasterData());
			}
			documentList.add(doc);
		}
		return documentList;
	}

	// ok
	public List<DocumentDetail> setFlag(DmsDocumentUpload dmsDocumentUpload) {
		List<DocumentDetail> uploadedList = dmsDocumentUpload.getDmsDocument().getUploadedList();
		for (DocumentDetail uploadDoc : uploadedList) {
			if (uploadDoc.getDocUploaded()) {
				String docUrl = getDmsUploadedFileUrl(dmsDocumentUpload.getDmsDocument().getApplno(),
						uploadDoc.getCatId(), dmsDocumentUpload.getDmsRequest());
				uploadDoc.getDmsUpload().setDocUrl(docUrl);
				uploadDoc.getDmsUpload().setDmsUploaded(true);
				uploadDoc.getDmsUpload().setDmsUploadedAlready(true);
				uploadDoc.setSelectOrder(2);
				if (uploadDoc.getSubcategoryMasterData() == null) {
					uploadDoc.setSubcategoryMasterData(new SubcategoryMasterData());
				}
				uploadDoc.setSelectOrder(2);
			}

		}
		return uploadedList;
	}

	public List<DocumentDetail> configureNonUploaded(DmsDocumentUpload dmsDocumentUpload) {
		List<DocumentDetail> nonUploadedList = dmsDocumentUpload.getDmsDocument().getNonUploadedList();
		for (DocumentDetail nonUploadDoc : nonUploadedList) {
			if (nonUploadDoc.getSubcategoryMasterData() == null) {
				nonUploadDoc.setSubcategoryMasterData(new SubcategoryMasterData());
			}

		}
		return nonUploadedList;
	}

	// public DocumentDetail copyDoc(DocumentDetail doc){
	//
	// DocumentDetail allDoc;
	// allDoc = new DocumentDetail();
	// allDoc.setCatId(doc.getCatId());
	// allDoc.setCatName(doc.getCatName());
	// allDoc.setMandatory(doc.getMandatory());
	// allDoc.setDocUploaded(doc.getDocUploaded());
	// allDoc.setFileName(doc.getFileName());
	// allDoc.setUniqueRefNo(doc.getUniqueRefNo());
	// allDoc.setDocVerified(doc.getDocVerified());
	// allDoc.setDocApproved(doc.getDocApproved());
	// allDoc.setDocRecieved(doc.getDocRecieved());
	// allDoc.setTempDocApproved(doc.getTempDocApproved());
	// allDoc.setSubcategoryMasterData(doc.getSubcategoryMasterData());
	// allDoc.setSubcategoryMasterDataList(doc.getSubcategoryMasterDataList());
	// allDoc.setObjectId(doc.getObjectId());
	// allDoc.setFile(doc.getFile());
	// allDoc.setApiFile(doc.getApiFile());
	// allDoc.setMessage(doc.getMessage());
	// allDoc.setDocUploadedDate(doc.getDocUploadedDate());
	// allDoc.setDocUrl(doc.getDocUrl());
	// allDoc.setDmsUpload(doc.getDmsUpload());
	// return allDoc;
	// }

	public boolean saveDmsEsignDetails(DmsEsignDetailsUpdateRequest updateRequest, String uploadDocUrl) {
		boolean status = false;
		try {
			ObjectMapper mapper = new ObjectMapper();

			String jsonInString = mapper.writeValueAsString(updateRequest);
			Client client = ClientBuilder.newClient();
			WebTarget targetUpload = client.target(uploadDocUrl);
			String responseUpload = targetUpload.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
					.post(Entity.json(jsonInString), String.class);

			if (CommonConstant.SUCCESS.equalsIgnoreCase(responseUpload)) {
				status = true;
				status = true;
			}
			return status;
		} catch (Exception e) {
			LOGGER.error("saveDmsEsignDetails " + e.getMessage());
		}
		return status;
	}

	private DmsEsignDetailsUpdateRequest prepareUpdateReqObj(DmsDocumentUpload dmsDocumentUpload,
			DocumentDetail documentDetail, String uploadedFrom) {
		DmsEsignDetailsUpdateRequest updateReq = new DmsEsignDetailsUpdateRequest();
		updateReq.setStateCd(dmsDocumentUpload.getDmsDocument().getState());
		updateReq.setOffCode(dmsDocumentUpload.getDmsRequest().getOfficeCd());
		updateReq.setPurCd(dmsDocumentUpload.getDmsDocument().getPurposeCode());
		updateReq.setApplNo(dmsDocumentUpload.getDmsDocument().getApplno());
		updateReq.setUploadedFrom(uploadedFrom);
		updateReq.setCatId(documentDetail.getCatId());
		updateReq.setCatName(documentDetail.getCatName());
		updateReq.setSignerName(documentDetail.getDmsUpload().getSignerName());

		// LOGGER.info("signer name == " +
		// documentDetail.getDmsUpload().getSignerName());
		return updateReq;

	}

	public String getKeyForUploadedFile(DmsDocumentUpload dmsDocumentUpload, DocumentDetail document) {
		String key = "";
		try {
			DmsRequest request = dmsDocumentUpload.getDmsRequest();
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(request.getDmsServiceUrl() + CommonConstant.DOC_KEY + "/"
					+ dmsDocumentUpload.getDmsDocument().getApplno());
			String response = target.request().get(String.class);
			JSONObject jsonObject = new JSONObject(response);
			JSONArray jsonArray = jsonObject.getJSONArray("vtDocuments");

			for (int i = 0; i < jsonArray.length(); i++) {
				String docCatid = (String) ((JSONObject) jsonArray.get(i)).get("doc_catg_id");
				if (docCatid.equalsIgnoreCase(document.getCatId())) {
					key = (String) ((JSONObject) jsonArray.get(i)).get("doc_object_id");
					break;
				}
			}
			return key;
		} catch (Exception e) {
			LOGGER.error("getKeyForUploadedFile " + e.getMessage());
		}

		return key;
	}

	public String getKey(DmsFileUploadDetail dmsFileUploadDetail, DocumentDetail document, String getKeyUrl) {
		String key = "";
		try {
			String URL = "";
			boolean isTestingEnviroment = Boolean.valueOf(new ApplicationConfig(ApplicationConfig.PROP_PAYMENT_MODE)
					.getProperties(ApplicationConfig.PROP_KEY_MODE_ISTESTING));
			if (isTestingEnviroment) {
				URL = ApplicationConfig.STAGING_DMS_URL;
			} else {
				URL = ApplicationConfig.PROD_DMS_URL;
			}
			// Old Request

			// Client client = ClientBuilder.newClient();
			// WebTarget target = client.target(getKeyUrl +
			// dmsFileUploadDetail.getApplno());
			// String response = target.request().get(String.class);
			// JSONObject jsonObject = new JSONObject(response);
			// JSONArray jsonArray = jsonObject.getJSONArray("vtDocuments");
			//
			// for (int i = 0; i < jsonArray.length(); i++) {
			// String docCatid = (String) ((JSONObject)
			// jsonArray.get(i)).get("doc_catg_id");
			// if (docCatid.equalsIgnoreCase(document.getCatId())) {
			// key = (String) ((JSONObject)
			// jsonArray.get(i)).get("doc_object_id");
			// break;
			// }
			// }

			// Old Request
			ResponseDto response = null;
			DmsRestClient restClient = new DmsRestClient(URL, "common-app", "VtDocuments");
			response = restClient.invokeGetVtDocAPI(getVtDocListAPI(dmsFileUploadDetail));
			List<VtDocuments> vtList = new ArrayList<VtDocuments>();
			vtList = response.getVtDocuments();
			for (int i = 0; i < vtList.size(); i++) {
				String docCatid = vtList.get(i).getDoc_catg_id();
				if (docCatid.equalsIgnoreCase(document.getCatId())) {
					key = vtList.get(i).getDoc_object_id();
					break;
				}
			}
			return key;
		} catch (Exception e) {
			LOGGER.error("getKey " + e.getMessage());
		}

		return key;
	}

	private static RequestDataDto getVtDocListAPI(DmsFileUploadDetail dmsFileUploadDetail) {
		RequestDataDto req = new RequestDataDto();
		try {
			req.setAppNo(dmsFileUploadDetail.getApplno());
		} catch (Exception ex) {
			LOGGER.error("" + ex.getCause(), ex);
		}
		return req;
	}

	public static byte[] getKeyBytes(String key) throws UnsupportedEncodingException {
		byte[] keyBytes = new byte[16];
		byte[] parameterKeyBytes = key.getBytes(CommonConstant.characterEncoding);
		System.arraycopy(parameterKeyBytes, 0, keyBytes, 0, Math.min(parameterKeyBytes.length, keyBytes.length));
		return keyBytes;
	}

	public static String decrypt(String encryptedText, String key) {

		try {
			byte[] cipheredBytes = Base64.decodeBase64(encryptedText);
			byte[] keyBytes = getKeyBytes(key);
			return new String(decrypt(cipheredBytes, keyBytes, keyBytes), CommonConstant.characterEncoding);
		} catch (Exception e) {
			//
			LOGGER.error(e.getMessage());
		}
		return null;

	}

	public static byte[] decrypt(byte[] cipherText, byte[] key, byte[] initialVector) {

		try {

			Cipher cipher = Cipher.getInstance(CommonConstant.cipherTransformation);
			SecretKeySpec secretKeySpecy = new SecretKeySpec(key, CommonConstant.aesEncryptionAlgorithm);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpecy, ivParameterSpec);
			cipherText = cipher.doFinal(cipherText);
			return cipherText;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		return null;

	}

	public boolean saveDoublesignPendingRecords(DoublesignPendingRecords pendingRecords, String saveUrl) {
		boolean status = false;
		try {
			ObjectMapper mapper = new ObjectMapper();

			String jsonInString = mapper.writeValueAsString(pendingRecords);
			LOGGER.info("saveDoublesignPendingRecords url === " + saveUrl);
			Client client = ClientBuilder.newClient();
			WebTarget targetUpload = client.target(saveUrl);
			String responseUpload = targetUpload.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
					.post(Entity.json(jsonInString), String.class);

			if (CommonConstant.SUCCESS.equalsIgnoreCase(responseUpload)) {
				status = true;
			}
			return status;
		} catch (Exception e) {
			LOGGER.error("saveDoublesignPendingRecords " + e.getMessage());
		}
		return status;
	}

	public boolean moveDoublesignPendingHistory(DoublesignPendingRecords pendingRecords, String moveUrl) {
		boolean status = false;
		try {
			ObjectMapper mapper = new ObjectMapper();

			String jsonInString = mapper.writeValueAsString(pendingRecords);
			// LOGGER.info("moveDoublesignPendingHistory url === " + moveUrl);
			Client client = ClientBuilder.newClient();
			WebTarget targetUpload = client.target(moveUrl);
			String responseUpload = targetUpload.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
					.post(Entity.json(jsonInString), String.class);

			if (CommonConstant.SUCCESS.equalsIgnoreCase(responseUpload)) {
				status = true;
			}
			return status;
		} catch (Exception e) {
			LOGGER.error("moveDoublesignPendingHistory " + e.getMessage());
		}
		return status;
	}

	public static void getFileFromDms(String sourceFileLoc, String dmsFileUrl) throws IOException {
		BufferedInputStream in = null;
		File file = null;
		FileOutputStream fileOutputStream = null;

		in = new BufferedInputStream(new URL(dmsFileUrl).openStream());
		file = new File(sourceFileLoc);
		file.createNewFile();
		fileOutputStream = new FileOutputStream(sourceFileLoc);
		byte dataBuffer[] = new byte[1024];
		int bytesRead;
		while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
			fileOutputStream.write(dataBuffer, 0, bytesRead);
		}
		in.close();
		fileOutputStream.close();

	}

	private static RequestDataDto vahanCitizenConf(DmsRequest dmsRequest) {
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
			LOGGER.error("vahanCitizenConf " + ex.getCause(), ex);
		}
		return req;
	}

	private static RegnRequestDto newRegnDocConf(DmsRequest dmsRequest) {
		RegnRequestDto req = new RegnRequestDto();
		try {
			req.setAppNo(dmsRequest.getApplNo());
			req.setState(dmsRequest.getStateCd());
			req.setPurposeCode("1");
			// req.setRegNo(dmsRequest.getRegnNo());
			req.setRegType(dmsRequest.getRegn_type());
		} catch (Exception ex) {
			LOGGER.error("newRegnDocConf " + ex.getCause(), ex);
		}
		return req;
	}

	public static byte[] ImageToByte(String keyAsDocObjId) {
		// StreamedContent viewSignFile = null;
		byte[] bytes = null;
		try {
			String URL = "";
			boolean isTestingEnviroment = Boolean.valueOf(new ApplicationConfig(ApplicationConfig.PROP_PAYMENT_MODE)
					.getProperties(ApplicationConfig.PROP_KEY_MODE_ISTESTING));
			if (isTestingEnviroment) {
				URL = ApplicationConfig.STAGING_DMS_URL;
			} else {
				URL = ApplicationConfig.PROD_DMS_URL;
			}
			ResponseDto response = null;
			DmsRestClient restClient = new DmsRestClient(URL, "common-app", "VtDocuments");
			response = restClient.invokViewDocument(viewDocument(keyAsDocObjId));
			bytes = response.getFileContent();
			// viewSignFile = new DefaultStreamedContent(new
			// ByteArrayInputStream(response.getFileContent()));
		} catch (Exception e) {
			LOGGER.error("ImageToByte " + e.getMessage());
		}
		return bytes;
	}

	private static RequestDataDto viewDocument(String docObjId) {
		RequestDataDto req = new RequestDataDto();
		try {
			// {doc_object_id}
			String doc_object_id = docObjId;
			String docObjectId[] = doc_object_id.split("/");
			String buketName = docObjectId[0];
			String applNo = docObjectId[1];
			String fileName = docObjectId[2];
			req.setBucketName(buketName);
			req.setAppNo(applNo);
			req.setFileName(fileName);
		} catch (Exception ex) {
			LOGGER.error("viewDocument " + ex.getCause(), ex);
		}
		return req;
	}

	public static StreamedContent ImageToInputStream(String keyAsDocObjId) {
		StreamedContent viewSignFile = null;
		try {
			String URL = "";
			boolean isTestingEnviroment = Boolean.valueOf(new ApplicationConfig(ApplicationConfig.PROP_PAYMENT_MODE)
					.getProperties(ApplicationConfig.PROP_KEY_MODE_ISTESTING));
			if (isTestingEnviroment) {
				URL = ApplicationConfig.STAGING_DMS_URL;
			} else {
				URL = ApplicationConfig.PROD_DMS_URL;
			}
			ResponseDto response = null;
			DmsRestClient restClient = new DmsRestClient(URL, "common-app", "VtDocuments");
			response = restClient.invokViewDocument(viewDocument(keyAsDocObjId));
			viewSignFile = new DefaultStreamedContent(new ByteArrayInputStream(response.getFileContent()));
		} catch (Exception e) {
			LOGGER.error("ImageToInputStream " + e.getMessage());
		}
		return viewSignFile;
	}

	public void saveUpdateDmsUpdateDetail(DmsDocumentUpload dmsDocumentUpload, int index, String uploadedFrom,
			String fileName) {
		PreparedStatement ps = null;
		TransactionManager tmgr = null;
		try {
			tmgr = new TransactionManager("saveUpdateDmsUpdateDetail");
			if (isDocIdAlrdyExist(dmsDocumentUpload, index)) {
				updateDocDetails(dmsDocumentUpload, index, fileName);
			} else {
				String sql = "INSERT INTO onlineschema.dms_doc_uploaded(state_cd, off_cd, pur_cd, appl_no, uploaded_from, doc_cat_id, doc_cat_name, uploaded_by,uploaded_file_name, op_dt) "
						+ "	VALUES (?, ?, ?, ?, ?, ?, ?, ?,?, CURRENT_TIMESTAMP) ";
				ps = tmgr.prepareStatement(sql);
				ps.setString(1, dmsDocumentUpload.getDmsRequest().getStateCd());
				ps.setInt(2, dmsDocumentUpload.getDmsRequest().getOfficeCd());
				ps.setString(3, dmsDocumentUpload.getDmsRequest().getPurCd());
				ps.setString(4, dmsDocumentUpload.getDmsRequest().getApplNo());
				ps.setString(5, "F");
				ps.setString(6, dmsDocumentUpload.getDmsDocument().getNonUploadedList().get(index).getCatId());
				ps.setString(7, dmsDocumentUpload.getDmsDocument().getNonUploadedList().get(index).getCatName());
				ps.setString(8, dmsDocumentUpload.getDmsRequest().getOwnerName());
				ps.setString(9, fileName);
				ps.executeUpdate();
				tmgr.commit();
			}

		} catch (SQLException ex) {
			LOGGER.error("uploaddocumentImpl.insertDmsDetail " + ex);

		} finally {
			try {
				if (tmgr != null) {
					tmgr.release();
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
			} catch (Exception e) {
				LOGGER.error("uploaddocumentImpl.insertDmsDetail" + e);
			}
		}
	}

	public void moveFromVTtoVHDocTable(DmsDocumentUpload dmsDocumentUpload, int index, String uploadedFrom,
			String fileName) {
		PreparedStatement pstmt = null;
		TransactionManager tmgr = null;
		try {
			tmgr = new TransactionManager("moveFromVTtoVHDocTable");
			String sqlInsert = "INSERT INTO onlineschema.dms_doc_uploaded_hist"
					+ " ( state_cd, off_cd, pur_cd, appl_no, uploaded_from, doc_cat_id, doc_cat_name, uploaded_by, op_dt,moved_on,moved_by,uploaded_file_name) "
					+ " SELECT state_cd,off_cd,pur_cd,appl_no,uploaded_from,doc_cat_id ,doc_cat_name, uploaded_by,op_dt,clock_timestamp(),?,uploaded_file_name from onlineschema.dms_doc_uploaded "
					+ " WHERE state_cd=? and appl_no=? and doc_cat_id=?";
			pstmt = tmgr.prepareStatement(sqlInsert);
			pstmt.setString(1, "DMS");
			pstmt.setString(2, dmsDocumentUpload.getDmsRequest().getStateCd());
			pstmt.setString(3, dmsDocumentUpload.getDmsRequest().getApplNo());
			pstmt.setString(4, dmsDocumentUpload.getDmsDocument().getNonUploadedList().get(index).getCatId());
			int rowsUpdated = pstmt.executeUpdate();
			pstmt.close();
			if (rowsUpdated > 0) {
				String sqlDel = "DELETE FROM  onlineschema.dms_doc_uploaded "
						+ " WHERE  state_cd=?  and appl_no=? and doc_cat_id=?";
				pstmt = tmgr.prepareStatement(sqlDel);
				pstmt.setString(1, dmsDocumentUpload.getDmsRequest().getStateCd());
				pstmt.setString(2, dmsDocumentUpload.getDmsRequest().getApplNo());
				pstmt.setString(3, dmsDocumentUpload.getDmsDocument().getNonUploadedList().get(index).getCatId());
				pstmt.executeUpdate();
				tmgr.commit();
			}
		} catch (SQLException se) {
			LOGGER.info(se);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
				if (tmgr != null) {
					tmgr.release();
				}
			} catch (Exception e) {
				LOGGER.info(e);
			}
		}
	}

	public static boolean isDocIdAlrdyExist(DmsDocumentUpload dmsDocumentUpload, int index) {
		String sql = "SELECT * FROM onlineschema.dms_doc_uploaded WHERE state_cd=?  and appl_no=? and doc_cat_id=?";
		PreparedStatement pstm = null;
		RowSet rs = null;
		TransactionManager iTmgr = null;
		LOGGER.info("isDocIdAlrdyExist parameter " + dmsDocumentUpload.getDmsRequest().getStateCd()
				+ dmsDocumentUpload.getDmsRequest().getApplNo()
				+ dmsDocumentUpload.getDmsDocument().getNonUploadedList().get(index).getCatId());
		try {
			iTmgr = new TransactionManager("isDocIdAlrdyExist");
			pstm = iTmgr.prepareStatement(sql);
			pstm.setString(1, dmsDocumentUpload.getDmsRequest().getStateCd());
			pstm.setString(2, dmsDocumentUpload.getDmsRequest().getApplNo());
			pstm.setString(3, dmsDocumentUpload.getDmsDocument().getNonUploadedList().get(index).getCatId());
			LOGGER.info("isDocIdAlrdyExist query " + pstm.toString());
			rs = iTmgr.fetchDetachedRowSet_No_release();
			if (rs.next()) {
				return true;
			}
		} catch (Exception se) {
			LOGGER.error("isDocIdAlrdyExist " + se);

		} finally {
			try {
				if (iTmgr != null) {
					iTmgr.release();
				}
				if (pstm != null) {
					pstm.close();
					pstm = null;
				}
			} catch (Exception e) {
				LOGGER.error(e);
			}
		}

		return false;
	}

	public static void updateDocDetails(DmsDocumentUpload dmsDocumentUpload, int index, String fileName)
			throws SQLException {
		PreparedStatement pstm = null;
		TransactionManager tmgr = null;
		String insCapl = " UPDATE onlineschema.dms_doc_uploaded "
				+ " SET doc_cat_id=?, doc_cat_name=?, uploaded_file_name=?, op_dt=clock_timestamp()"
				+ " WHERE state_cd=?  and appl_no=? and doc_cat_id=?";
		try {
			tmgr = new TransactionManager("updateDocDetails");
			pstm = tmgr.prepareStatement(insCapl);
			pstm.setString(1, dmsDocumentUpload.getDmsDocument().getNonUploadedList().get(index).getCatId());
			pstm.setString(2, dmsDocumentUpload.getDmsDocument().getNonUploadedList().get(index).getCatName());
			pstm.setString(3, fileName);
			pstm.setString(4, dmsDocumentUpload.getDmsRequest().getStateCd());
			pstm.setString(5, dmsDocumentUpload.getDmsRequest().getApplNo());
			pstm.setString(6, dmsDocumentUpload.getDmsDocument().getNonUploadedList().get(index).getCatId());
			pstm.executeUpdate();
			tmgr.commit();
		} catch (Exception se) {
			LOGGER.error(se);

		} finally {
			try {
				if (tmgr != null) {
					tmgr.release();
				}
				if (pstm != null) {
					pstm.close();
					pstm = null;
				}
			} catch (Exception e) {
				LOGGER.error(e);
			}
		}

	}

	public String getAutoUploadFormStatus(DmsRequest dmsRequest) throws Exception {
		String response = "";
		String autoFormUploadapiUrl = "";
		String status = "";
		String param = "applNo=" + dmsRequest.getApplNo() + "&regnNo=" + dmsRequest.getRegnNo() + "&vhClassType="
				+ dmsRequest.getVhClassType() + "&authMode=" + dmsRequest.getAuthType() + "&purcd="
				+ dmsRequest.getPurCd() + "&purposeReasonCode=0" + "&statecd=" + dmsRequest.getStateCd()
				+ "&clientid=dmsupload";
		DefaultHttpClient httpClient = new DefaultHttpClient();
		boolean isTestingEnvironment = Boolean.valueOf(new ApplicationConfig(ApplicationConfig.PROP_PAYMENT_MODE)
				.getProperties(ApplicationConfig.PROP_KEY_MODE_ISTESTING));
		// https://staging.parivahan.gov.in/vahanonlineform/pdf/autoupload?applNo=DL240904V0446790&regnNo=DL10C0999&vhClassType=2&authMode=M&purcd=6&purposeReasonCode=0&statecd=DL&clientid=dmsupload
		if (isTestingEnvironment) {
			autoFormUploadapiUrl = "https://staging.parivahan.gov.in/vahanonlineform/pdf/autoupload?" + param;
		} else {
			autoFormUploadapiUrl = "https://vahan.parivahan.gov.in/vahanonlineform/pdf/autoupload?" + param;
		}
		try {
			HttpGet getRequest = new HttpGet(autoFormUploadapiUrl);
			getRequest.addHeader("content-type", "application/json");
			HttpResponse httpresponse = httpClient.execute(getRequest);
			HttpEntity httpEntity = httpresponse.getEntity();
			response = EntityUtils.toString(httpEntity);
			if (!CommonUtils.isNullOrBlank(response)) {
				JSONObject jsonobj = new JSONObject(response);
				if (response.contains("statusCode")) {
					JSONObject jsonobjdata = (JSONObject) jsonobj.get("apiMessage");
					if (jsonobjdata != null) {
						int statusInt = (Integer) jsonobjdata.get("statusCode");
						status = String.valueOf(statusInt);
					}
				}
			}
		} catch (Exception jb) {
			LOGGER.error("getwalfarefundapi exception: " + jb);
		} finally {
			if (null != httpClient) {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return status;
	}

	public boolean isAutoUploadForm(DmsRequest dmsRequest) {
		String sql = "SELECT * FROM ve_esign.vm_dms_esign_file_upload_mapping WHERE state_cd=? and pur_cd in ("
				+ dmsRequest.getPurCd() + ") and purpose_reason_code=?";
		PreparedStatement pstm = null;
		RowSet rs = null;
		TransactionManager iTmgr = null;
		try {
			iTmgr = new TransactionManager("isAutoUploadForm");
			pstm = iTmgr.prepareStatement(sql);
			pstm.setString(1, dmsRequest.getStateCd());
			pstm.setString(2, dmsRequest.getServiceRgn());
			// pstm.setString(3, "0");
			rs = iTmgr.fetchDetachedRowSet_No_release();
			if (rs.next()) {
				return true;
			}
		} catch (Exception se) {
			LOGGER.error(se);

		} finally {
			try {
				if (iTmgr != null) {
					iTmgr.release();
				}
				if (pstm != null) {
					pstm.close();
					pstm = null;
				}
			} catch (Exception e) {
				LOGGER.error(e);
			}
		}
		return false;
	}

	public List<VtDocuments> getList(String applicationNo) {
		List<VtDocuments> vtList = new ArrayList<VtDocuments>();
		try {
			String URL = "";
			boolean isTestingEnviroment = Boolean.valueOf(new ApplicationConfig(ApplicationConfig.PROP_PAYMENT_MODE)
					.getProperties(ApplicationConfig.PROP_KEY_MODE_ISTESTING));
			if (isTestingEnviroment) {
				URL = ApplicationConfig.STAGING_DMS_URL;
			} else {
				URL = ApplicationConfig.PROD_DMS_URL;
			}

			ResponseDto response = null;
			DmsRestClient restClient = new DmsRestClient(URL, "common-app", "VtDocuments");
			response = restClient.invokeGetVtDocAPI(getVtDocListAPIForAutoApload(applicationNo));
			vtList = response.getVtDocuments();

		} catch (Exception e) {
			LOGGER.error("getList " + e.getMessage());
		}
		return vtList;

	}

	public List<VtDocuments> getListForVideo(String applicationNo) {
		List<VtDocuments> vtList = new ArrayList<VtDocuments>();
		try {
			String URL = "";
			boolean isTestingEnviroment = Boolean.valueOf(new ApplicationConfig(ApplicationConfig.PROP_PAYMENT_MODE)
					.getProperties(ApplicationConfig.PROP_KEY_MODE_ISTESTING));
			if (isTestingEnviroment) {
				URL = ApplicationConfig.STAGING_DMS_URL;
			} else {
				URL = ApplicationConfig.PROD_DMS_URL;
			}

			ResponseDto response = null;
			DmsRestClient restClient = new DmsRestClient(URL, "common-app", "VtDocuments");
			response = restClient.invokeGetVideoListAPI(getVtDocListAPIForAutoApload(applicationNo));
			vtList = response.getVtDocuments();

		} catch (Exception e) {
			LOGGER.error("getList " + e.getMessage());
		}
		return vtList;

	}

	public static RequestDataDto getVtDocListAPIForAutoApload(String applNo) {
		RequestDataDto req = new RequestDataDto();
		try {
			req.setAppNo(applNo);
		} catch (Exception ex) {
			LOGGER.error("" + ex.getCause(), ex);
		}
		return req;
	}

	public String getOnlineUploaddocViewUrl(String state) {
		PreparedStatement ps = null;
		TransactionManager tmgr = null;
		String sql = null;
		String docViewUrl = null;
		try {
			boolean isTestingEnviroment = Boolean.valueOf(new ApplicationConfig(ApplicationConfig.PROP_PAYMENT_MODE)
					.getProperties(ApplicationConfig.PROP_KEY_MODE_ISTESTING));
			if (!isTestingEnviroment) {
				tmgr = new TransactionManager("getOnlineUploaddocViewUrl()");

			} else {
				tmgr = new TransactionManager("getOnlineUploaddocViewUrl()");
			}
			sql = "select api_viewdoc from onlineschema.vm_online_uploaddoc where state_cd=?";
			ps = tmgr.prepareStatement(sql);
			ps.setString(1, state);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				docViewUrl = rs.getString("api_viewdoc");
			}
			return docViewUrl;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			try {
				if (tmgr != null) {
					tmgr.release();
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
		return docViewUrl;
	}

	public RequestDataDto uploadSingleDocument(DmsFileUploadDetail dmsFileUploadDetail, DmsRequest request) {
		RequestDataDto req = new RequestDataDto();
		DmsFileDetails fileDetails = new DmsFileDetails();
		try {
			req.setAppNo(request.getApplNo());
			req.setState(request.getStateCd());
			fileDetails.setApiFile(dmsFileUploadDetail.getDmsFileDetails().get(0).getApiFile());
			fileDetails.setFileName(dmsFileUploadDetail.getDmsFileDetails().get(0).getFileName());
			fileDetails.setDocCatgId(dmsFileUploadDetail.getDmsFileDetails().get(0).getDocCatgId());
			fileDetails.setDocId(dmsFileUploadDetail.getDmsFileDetails().get(0).getDocId());
			req.setFileDetails(fileDetails);
		} catch (Exception e) {
			LOGGER.error("uploadSingleDocument " + e.getMessage());
		}
		return req;
	}

	public List<String> getKeyForUploadModify(DmsFileUploadDetail dmsFileUploadDetail, DocumentDetail document,
			String getKeyUrl) {
		String key = "";
		List<String> pur_cdList = new ArrayList<>();
		try {
			String URL = "";
			boolean isTestingEnviroment = Boolean.valueOf(new ApplicationConfig(ApplicationConfig.PROP_PAYMENT_MODE)
					.getProperties(ApplicationConfig.PROP_KEY_MODE_ISTESTING));
			if (isTestingEnviroment) {
				URL = ApplicationConfig.STAGING_DMS_URL;
			} else {
				URL = ApplicationConfig.PROD_DMS_URL;
			}
			ResponseDto response = null;
			DmsRestClient restClient = new DmsRestClient(URL, "common-app", "VtDocuments");
			response = restClient.invokeGetVtDocAPI(getVtDocListAPI(dmsFileUploadDetail));
			List<VtDocuments> vtList = new ArrayList<VtDocuments>();
			vtList = response.getVtDocuments();
			for (int i = 0; i < vtList.size(); i++) {
				String docCatid = vtList.get(i).getDoc_catg_id();
				if (docCatid.equalsIgnoreCase(document.getCatId())) {
					key = vtList.get(i).getDoc_object_id();
					pur_cdList.add(key);
					String docUrl = vtList.get(i).getDoc_url();
					String[] fileSplt = docUrl.split("\\.");
					String fileExt = fileSplt[fileSplt.length - 1];
					pur_cdList.add(fileExt);
					break;
				}
			}
			return pur_cdList;
		} catch (Exception e) {
			LOGGER.error("getKey " + e.getMessage());
		}
		return pur_cdList;

	}
}
