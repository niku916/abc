//package TestMain;
//
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.commons.io.IOUtils;
//import org.primefaces.model.DefaultStreamedContent;
//import org.primefaces.model.StreamedContent;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.google.gson.Gson;
//import com.np.dms.client.DmsRestClient;
//import com.np.dms.constant.ModuleNameEnum;
//import com.np.dms.db.pojos.DmsFileDetails;
//import com.np.dms.db.pojos.VtDocuments;
//import com.np.dms.dto.CategoryMasterDataDto;
//import com.np.dms.dto.RequestDataDto;
//import com.np.dms.dto.ResponseDto;
//import com.np.dms.dto.UploadDataDto;
//
//public class TestMain {
//	private static final Logger LOGGER = LoggerFactory.getLogger(TestMain.class);
//	private static File f = new File("C:\\Users\\AnandPandey\\Pictures\\photo99.jpg");
//	private static File f1 = new File("C:\\Users\\AnandPandey\\Pictures\\istockphoto.jpg");
//	private static final String URI = "https://staging.parivahan.gov.in/dms-app/common-app";
//	// private static final String URI =
//	// "http://localhost:8080/dms-app/common-app";
//	private static final DmsRestClient restClient = new DmsRestClient(URI, "common-app", "VtDocuments");
//	private static final Gson GSON = new Gson();
//
//	public static void main(String args[]) throws IOException {
//		LOGGER.info("Exiting.............");
//		// ResponseDto response =
//		// restClient.invokeUploadDocAPI(uploadDocument());
//
//		// restClient.invokeUploadDocAPI( uploadDocument() );
//		ResponseDto response = null;
//		response = restClient.invokDeleteDocument(deleteDocument());
//		//response = restClient.invokeGetVideoListAPI(arg0);
//
//		// getvideolist
//		System.out.println("Response :: " + GSON.toJson(response));
//		// List<VtDocuments> vtList = new ArrayList<VtDocuments>();
//		// vtList = response.getVtDocuments();
//		// response = restClient.invokeUploadDocAPI(uploadDocument());
//		// response = restClient.invokViewDocument(viewDocument(vtList));
//		// response = restClient.invokeUploadImageVideoAPI(
//		// uploadMultiDocuments() );
//		// StreamedContent viewSignFile = new DefaultStreamedContent(new
//		// ByteArrayInputStream(response.getFileContent()));
//		// String encoded = new
//		// String(java.util.Base64.getEncoder().encode(response.getFileContent()));
//		// System.out.println("encoded :: " + encoded);
//		// System.out.println("Response :: " + GSON.toJson(response));
//	}
//
//	// response=restClient.invokeUploadDocAPI(
//
//	// uploadDocument() ); // Vahan4
//	// DMS upload for any one
//	// response = restClient.invokeUpdateDocAPI( updateDocument() );
//	// response = restClient.invokViewDocument( viewDocument() );
//	// response = restClient.invokCitizenConfDocument( vahanCitizenConf() );
//	// response=restClient.invokDeleteDocument(
//
//	// deleteDocument() );
//	// response = restClient.invokDocUploadConf( docUploadConf() );
//	// response = restClient.invokPermitCatDocConf( permitCatDocConf() );
//	// response = restClient.invokDocUploadAPI( docUploadApi() ); // m-vahan
//	// team for dealer side
//	// LOGGER.info( "Response :: " + GSON.toJson( response ) );
//	private static RequestDataDto getVtDocListAPI() {
//		RequestDataDto req = new RequestDataDto();
//		try {
//			req.setAppNo("GJ230717M0938792");// MH231219V0942378
//		} catch (Exception ex) {
//			LOGGER.error("" + ex.getCause(), ex);
//		}
//		return req;
//	}
//
//	private static RequestDataDto viewDocument(List<VtDocuments> vtList) {
//		RequestDataDto req = new RequestDataDto();
//		try {
//			// {doc_object_id}
//			String doc_object_id = vtList.get(0).getDoc_object_id();
//			String docObjectId[] = doc_object_id.split("/");
//			String buketName = docObjectId[0];
//			String applNo = docObjectId[1];
//			String fileName = docObjectId[2];
//			req.setBucketName(buketName);
//			req.setAppNo(applNo);
//			req.setFileName(fileName);
//		} catch (Exception ex) {
//			LOGGER.error("" + ex.getCause(), ex);
//		}
//		return req;
//	}
//
//	private static RequestDataDto uploadDocument() {
//		RequestDataDto req = new RequestDataDto();
//		DmsFileDetails fileDetails = new DmsFileDetails();
//		List<DmsFileDetails> deList = new ArrayList<>();
//		try {
//			req.setAppNo("MP221001695");
//			req.setState("MP");
//			fileDetails.setApiFile(IOUtils.toByteArray(new FileInputStream(f)));
//			fileDetails.setFileName(f.getName());
//			fileDetails.setDocCatgId("F20");
//			fileDetails.setDocId(1621);
//			deList.add(fileDetails);
//			req.setDmsFileDetails(deList);
//		} catch (FileNotFoundException ex) {
//			ex.printStackTrace();
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
//		return req;
//	}
//
//	private static RequestDataDto updateDocument() {
//		RequestDataDto req = new RequestDataDto();
//		try {
//			req.setAppNo("DL108X987755");
//			req.setState("DL");
//			req.setDocKey("dev-vahan/DL108X987755/DL108X987755-DSFC-987755-DL108X9877551-1686559039810");
//			req.setFileToUpload(IOUtils.toByteArray(new FileInputStream(f1)));
//			req.setPrj_name(ModuleNameEnum.VAHAN_SERVICE.getCode());
//			req.setFileName(f.getName());
//		} catch (FileNotFoundException ex) {
//			LOGGER.error("" + ex.getCause(), ex);
//		} catch (IOException ex) {
//			LOGGER.error("" + ex.getCause(), ex);
//		}
//		return req;
//	}
//
//	private static RequestDataDto vahanCitizenConf() {
//		RequestDataDto req = new RequestDataDto();
//		try {
//			req.setAppNo("GJ230711T115");
//			req.setState("GJ");
//			req.setPurposeCode("5");
//			// req.setPermit( "0" );
//			// req.setTransType( "P" );
//			// req.setAuthMode( "B" );
//			// req.setPermitCat( "42" );
//			// req.setVhClassType( "2" );
//		} catch (Exception ex) {
//			LOGGER.error("" + ex.getCause(), ex);
//		}
//		return req;
//	}
//
//	private static RequestDataDto docUploadConf() {
//		RequestDataDto req = new RequestDataDto();
//		try {
//			req.setAppNo("GJ230717M0938735");
//			req.setState("GJ");
//			req.setPurposeCode("14");
//		} catch (Exception ex) {
//			LOGGER.error("" + ex.getCause(), ex);
//		}
//		return req;
//	}
//
//	private static RequestDataDto permitCatDocConf() {
//		RequestDataDto req = new RequestDataDto();
//		try {
//			req.setAppNo("DL208X70012563");
//			req.setState("DL");
//			req.setPurposeCode("26");
//			req.setPermit("101");
//			req.setPermitCat("101");
//		} catch (Exception ex) {
//			LOGGER.error("" + ex.getCause(), ex);
//		}
//		return req;
//	}
//
//	private static RequestDataDto docUploadApi() {
//		RequestDataDto requestData = null;
//		UploadDataDto uploadDataDto = null;
//		ResponseDto response = null;
//		try {
//			requestData = docUploadConf();
//			response = restClient.invokDocUploadConf(requestData);
//			System.err.println(response.getUploadDataDto());
//			if (null != response) {
//				uploadDataDto = response.getUploadDataDto();
//				if (null != uploadDataDto && null != uploadDataDto.getNonUploadedList()
//						&& !uploadDataDto.getNonUploadedList().isEmpty()) {
//					int x = 0;
//					System.out.println("Non Upload Data::::::::::::::::::::::::::::::::"
//							+ uploadDataDto.getNonUploadedList().size());
//					for (CategoryMasterDataDto catMast : uploadDataDto.getNonUploadedList()) {
//						uploadDataDto.getNonUploadedList().get(uploadDataDto.getNonUploadedList().indexOf(catMast))
//								.setFileName(f.getName());
//						uploadDataDto.getNonUploadedList().get(uploadDataDto.getNonUploadedList().indexOf(catMast))
//								.setApiFile(IOUtils.toByteArray(new FileInputStream(f)));
//						uploadDataDto.getNonUploadedList().get(uploadDataDto.getNonUploadedList().indexOf(catMast))
//								.setSubcategoryMasterData(catMast.getSubcategoryMasterDataList().get(0));
//						uploadDataDto.getNonUploadedList().get(uploadDataDto.getNonUploadedList().indexOf(catMast))
//								.setUniqueRefNo(++x + "");
//					}
//				}
//				if (null != uploadDataDto && null != uploadDataDto.getUploadedList()
//						&& !uploadDataDto.getUploadedList().isEmpty()) {
//					System.out.println(
//							"Upload Data::::::::::::::::::::::::::::::::" + uploadDataDto.getUploadedList().size());
//					for (CategoryMasterDataDto catMast : uploadDataDto.getUploadedList()) {
//						uploadDataDto.getUploadedList().get(uploadDataDto.getUploadedList().indexOf(catMast))
//								.setFileName(f1.getName());
//						uploadDataDto.getUploadedList().get(uploadDataDto.getUploadedList().indexOf(catMast))
//								.setApiFile(IOUtils.toByteArray(new FileInputStream(f1)));
//					}
//				}
//			}
//			requestData.setNonUploadedList(uploadDataDto.getNonUploadedList());
//			requestData.setMandatoryList(uploadDataDto.getMandatoryList());
//			requestData.setUploadedList(uploadDataDto.getUploadedList());
//		} catch (Exception ex) {
//			LOGGER.error("" + ex.getCause(), ex);
//		}
//		return requestData;
//	}
//
//	public static RequestDataDto deleteDocument() {
//		RequestDataDto req = new RequestDataDto();
//		//
//		// uat-vahan/DL241120V0247922/DL241120V0247922-RETVD-1771-DL241120V02479221-1732082741807
//		try {
//			req.setDocKey("uat-vahan/DL241120V0247922/DL241120V0247922-RETVD-1771-DL241120V02479221-1732600568977");
//			req.setPrj_name("VCTZNS");
//		} catch (Exception ex) {
//			LOGGER.error("" + ex.getCause(), ex);
//		}
//		return req;
//	}
//}