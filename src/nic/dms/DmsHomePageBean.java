package nic.dms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.util.URIUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.np.dms.db.pojos.VtDocuments;

import nic.dms.common.constant.CommonConstant;
import nic.dms.common.utils.CommonUtils;
import nic.dms.common.utils.JSFUtils;
import nic.dms.pojo.DmsDocument;
import nic.dms.pojo.DmsDocumentUpload;
import nic.dms.pojo.DmsFileUploadDetail;
import nic.dms.pojo.DmsFlag;
import nic.dms.pojo.DmsUpload;
import nic.dms.pojo.DocumentDetail;
import nic.dms.pojo.SubcategoryMasterData;
import nic.dms.pojo.TmEsign;
import nic.dms.pojo.reqestresponse.DmsRequest;
import nic.dms.pojo.reqestresponse.DoublesignPendingRecords;
import nic.esign.EsignRequest;
import nic.org.apache.log4j.Logger;
import nic.vahansms.VahanSMS;
import resources.ApplicationConfig;

@ManagedBean(name = "dmsHomePageBean")
@ViewScoped
public class DmsHomePageBean {
	private DmsUploadUtil dmsUploadUtil = new DmsUploadUtil();
	private DmsDocumentUpload dmsDocumentUpload;
	private String listOfDoc;
	// private boolean renderTable;
	private boolean docMerger;

	private Integer index;
	private Integer subCatId;

	private static final Logger LOGGER = Logger.getLogger(DmsHomePageBean.class);
	private String result = null;
	EsignRequest esignObj = new EsignRequest();

	public StreamedContent getStreamedContent() {
		return streamedContent;
	}

	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}

	private String esignDoc;
	private DmsRequest dmsRequest;
	private Boolean queryParam = true;
	private EsignUtil esignUtil = new EsignUtil();
	private Boolean enableConf = false;
	private String imageBase64String;
	private StreamedContent viewSignStreamedContent;
	private StreamedContent streamedContent;
	private String docTypeForView = "pdf";
	List<VtDocuments> docsListapplwise = null;
	String ViewdocUrl = "";
	private Boolean viewAutoUploadDocs = false;

	public DmsHomePageBean() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		boolean isTestingEnviroment = Boolean.valueOf(new ApplicationConfig(ApplicationConfig.PROP_PAYMENT_MODE)
				.getProperties(ApplicationConfig.PROP_KEY_MODE_ISTESTING));
		// LOGGER.info("req.getRequestURI()..." + req.getRequestURI());
		// LOGGER.info("req.getRequestURL()..." + req.getRequestURL());
		// LOGGER.info("req.getHeaderReferer..." + req.getHeader("Referer"));
		if (!isTestingEnviroment) {
			if (req.getHeader("Referer") != null) {
				boolean restrictservice = false;
				if (req.getHeader("Referer").contains("https://www.google.com/")) {
					restrictservice = true;
					// LOGGER.info("restrictservice in google ..." +
					// restrictservice
					// + " with refer..."
					// + req.getHeader("Referer"));
				} else if (!restrictservice && !isTestingEnviroment
						&& !(req.getHeader("Referer").contains(CommonConstant.Vahan_Context_Url_LIVE)
								|| req.getHeader("Referer").contains(CommonConstant.Esign_Context_Url_LIVE))) {
					restrictservice = true;
				} else if (!restrictservice && isTestingEnviroment
						&& !(req.getHeader("Referer").contains(CommonConstant.Vahan_Context_Url_Staging)
								|| req.getHeader("Referer").contains(CommonConstant.Esign_Context_Url_LIVE))) {
					restrictservice = true;
				}

				if (restrictservice) {
					ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
					try {
						ec.redirect("/" + CommonConstant.refererVds + "/dms/ui/uploadDoc/access-denied_new.xhtml");
						return;
					} catch (IOException e) {
					}
				}
			}
		}
		if (req.getHeader("Referer") == null || req.getHeader("Referer").contains(CommonConstant.refererVds)
				|| req.getHeader("Referer").contains(CommonConstant.refererVcs) && !isTestingEnviroment) {
			if (req.getSession().getAttribute("dmsdocupload") != null) {
				// req.getSession().removeAttribute("dmsdocupload");
			}
			if (req.getHeader("Referer") == null && !isTestingEnviroment) {
				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
				try {
					ec.redirect("/" + CommonConstant.refererVds + "/dms/ui/uploadDoc/access-denied.xhtml");
					return;
				} catch (IOException e) {
				}
			}
			if (req.getHeader("Referer") != null && req.getHeader("Referer").contains("https://www.google.com/")) {
				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
				try {
					ec.redirect("/" + CommonConstant.refererVds + "/dms/ui/uploadDoc/access-denied_new.xhtml");
					return;
				} catch (IOException e) {
				}
			}

			try {
				ByteArrayOutputStream os = (ByteArrayOutputStream) req.getSession().getAttribute("bytestream");
				if (os != null) {
					os.close();
				}

				PdfReader reader = (PdfReader) req.getSession().getAttribute("reader");
				if (reader != null) {
					reader.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.info(e.getMessage());
			}

		} else {
			if (req.getSession().getAttribute("dmsdocupload") != null) {
				dmsDocumentUpload = (DmsDocumentUpload) req.getSession().getAttribute("dmsdocupload");
			}
			if (req.getParameter("status") == null || req.getParameter("status").equalsIgnoreCase("0")) {
				try {
					ByteArrayOutputStream os = (ByteArrayOutputStream) req.getSession().getAttribute("bytestream");
					if (os != null) {
						os.close();
					}
					PdfReader reader = (PdfReader) req.getSession().getAttribute("reader");
					if (reader != null) {
						reader.close();
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
				}
			}

		}
	}

	public void backToClient() {

		try {

			String encUrl = URIUtil.encodeQuery(dmsDocumentUpload.getDmsRequest().getClientReturnUrl());
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			javax.servlet.http.HttpServletResponse response = ((javax.servlet.http.HttpServletResponse) ec
					.getResponse());
			response.addHeader("Referer", "/vahandocumentsystem/dms/ui/uploadDoc/uploadDocument.xhtml");
			ec.redirect(encUrl);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

	}

	public String getListOfDoc() {

		ObjectMapper mapper = new ObjectMapper();
		try {

			if (dmsDocumentUpload == null) {
				HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
						.getRequest();

				// if(req.getHeader("Referer") == null){
				// ExternalContext ec =
				// FacesContext.getCurrentInstance().getExternalContext();
				// ec.redirect(CommonConstant.getContextPath()+CommonConstant.errorFileUrl);
				// return null;
				// }
				String jsonData = null;
				if (req != null && req.getParameter("dmsRequest") != null) {
					dmsDocumentUpload = new DmsDocumentUpload();
					String data = req.getParameter("dmsRequest").trim();
					data = data.replace("%2b", "+");

					jsonData = DmsUploadUtil.decrypt(data, CommonConstant.TOKEN);
					if (jsonData != null) {
						ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();

						queryParam = true;
						dmsRequest = mapper.readValue(jsonData, DmsRequest.class);
						// set the path of return esign response

						if (dmsRequest.getTmEsign() != null) {
							if (dmsRequest.getStateCd().equalsIgnoreCase("KA")) {
								// dmsRequest.getTmEsign().setEsignRespUrl(("http://localhost:8088/vahandocumentsystem/"
								// + CommonConstant.ESIGN_KA_RESP_URL).trim());
								dmsRequest.getTmEsign().setEsignRespUrl(
										(CommonConstant.getContextPath() + CommonConstant.ESIGN_KA_RESP_URL).trim());
							} else {
								dmsRequest.getTmEsign().setEsignRespUrl(
										(CommonConstant.getContextPath() + CommonConstant.ESIGN_RESP_URL).trim());
							}
							// LOGGER.info("Esign not null " +
							// dmsRequest.getApplNo());
						}

						//
						if (!CommonUtils.isNullOrBlank(dmsRequest.getPortalCd())
								&& dmsRequest.getPortalCd().equalsIgnoreCase("OP")) {
							String encryptedString = CommonUtils.encrypt("applNo=" + dmsRequest.getApplNo(),
									"bbd8409829c6c214");
							dmsRequest.setClientReturnUrl(dmsRequest.getClientReturnUrl() + "?" + encryptedString);
						} else {
							dmsRequest.setClientReturnUrl(dmsRequest.getClientReturnUrl() + "?src=dms");
						}
						dmsDocumentUpload.setDmsRequest(dmsRequest);
						if (CommonUtils.isNullOrBlank(dmsRequest.getAuthType())) {
							dmsRequest.setAuthType("M");
						}
						// if (dmsRequest.getStateCd().equalsIgnoreCase("DL")) {
						// dmsRequest.setVideoUpload(true);
						// dmsRequest.setAutoUploadView(true);
						// }
						// auto aupload func
						if (dmsRequest.getAutoUploadView()) {
							ViewdocUrl = dmsUploadUtil.getOnlineUploaddocViewUrl(dmsRequest.getStateCd());
							if (dmsUploadUtil.isAutoUploadForm(dmsRequest)) {
								String status = dmsUploadUtil.getAutoUploadFormStatus(dmsRequest);
								if (!CommonUtils.isNullOrBlank(status)) {
									setViewAutoUploadDocs(true);
								}
							}
						}
						// get list of doc to upload from DMS
						DmsDocument dmsDocument = dmsUploadUtil.getListofDocToUploadOrUploaded(dmsRequest);
						dmsDocumentUpload.setDmsDocument(dmsDocument);

						// already uploaded list of documents
						if (CommonConstant.UPLOADED_DOC.equalsIgnoreCase(dmsRequest.getDocType())) {
							if (dmsDocumentUpload.getDmsDocument().getUploadedList() == null) {
								dmsDocumentUpload.setDocNotConfigure(true);
							} else {
								// set flag for uploaded docs and set it into
								// documentList
								dmsDocumentUpload.getDmsDocument()
										.setDocumentList(dmsUploadUtil.setFlag(dmsDocumentUpload));
							}
						}
						// Non-uploaded list of documents
						if (CommonConstant.NONUPLOADED_DOC.equalsIgnoreCase(dmsRequest.getDocType())) {
							if (dmsDocumentUpload.getDmsDocument().getNonUploadedList() == null
									|| dmsDocumentUpload.getDmsDocument().getStatusCode().equalsIgnoreCase("VA039")) {
								dmsDocumentUpload.setDocNotConfigure(true);
							} else if (dmsDocumentUpload.getDmsDocument().getNonUploadedList().size() == 0) {
								dmsDocumentUpload.setDocUploaded(true);
							} else {
								dmsDocumentUpload.getDmsDocument()
										.setDocumentList(dmsUploadUtil.configureNonUploaded(dmsDocumentUpload));
							}
						}
						// both uploaded and non-uploaded list of documents
						if (CommonConstant.BOTH_DOC.equalsIgnoreCase(dmsRequest.getDocType())) {
							if (dmsDocumentUpload.getDmsDocument().getUploadedList() == null
									&& dmsDocumentUpload.getDmsDocument().getNonUploadedList() == null) {
								dmsDocumentUpload.setDocNotConfigure(true);
							} else {
								// copy both types of documents into
								// documentList, set flag and set it into
								// documentlist
								dmsDocumentUpload.getDmsDocument().setDocumentList(
										dmsUploadUtil.copyUploadedNonuploadedToDocumentList(dmsDocumentUpload));
							}
						}
						// second esign for already uploaded docs
						if (dmsRequest.getDse() && dmsRequest.getDss()) {
							List<DocumentDetail> dsDocumentList = new ArrayList<DocumentDetail>();
							for (DocumentDetail uploadDoc : dmsDocumentUpload.getDmsDocument().getDocumentList()) {
								if (dmsRequest.getDocDoubleSignStatus().get(uploadDoc.getCatId()) != null) {
									uploadDoc.getDmsUpload().setFileDoubleSign(true);
									uploadDoc.getDmsUpload()
											.setSignBtn(JSFUtils.getRelevantLangMsg("lebelname.esignBuyer"));
									dsDocumentList.add(uploadDoc);
								}
							}
							dmsDocumentUpload.getDmsDocument().setDocumentList(dsDocumentList);
						}
						for (DocumentDetail uploadDoc : dmsDocumentUpload.getDmsDocument().getDocumentList()) {
							uploadDoc.getDmsUpload().setDmsFlag(prepareFlag(uploadDoc.getDmsUpload()));
						}
					}
				} else {
					queryParam = false;
				}
			}

			return listOfDoc;
		} catch (Exception e) {
			// e.printStackTrace();
			LOGGER.error("getListOfDoc " + e.getMessage());
			return listOfDoc;
		}
	}

	public boolean matchPurCd(String purCode, String purCodeComaSeperated) {
		if (purCodeComaSeperated != null && purCode != null) {
			String[] purcodes2 = purCodeComaSeperated.split(",");
			for (String pc2 : purcodes2) {
				if (pc2.equalsIgnoreCase(purCode)) {
					return true;
				}
			}
		}
		return false;
	}

	public void selectSubDoc(ValueChangeEvent e) {
		subCatId = (Integer) e.getNewValue();
	}

	public void fileUploadListener(FileUploadEvent e) {
		index = (Integer) e.getComponent().getAttributes().get("indx");
		String filePath = CommonConstant.FILE_PATH;
		// LOGGER.info("File path for DMS " + filePath);
		try {
			// LOGGER.info("List Size " +
			// dmsDocumentUpload.getDmsDocument().getDocumentList().size());
			DocumentDetail document = dmsDocumentUpload.getDmsDocument().getDocumentList().get(index);
			// LOGGER.info("document List Size " + document);
			int countmultipleextension = 0;
			String catNameForVideo = document.getCatName();
			for (int i = 0; i < e.getFile().getFileName().length(); i++) {
				if (e.getFile().getFileName().charAt(i) == '.') {
					countmultipleextension++;
				}
			}
			if (countmultipleextension > 1 || (!JSFUtils.isAlphaNumericWithDot(e.getFile().getFileName()))) {
				showMessage(JSFUtils.getRelevantLangMsg("lebelname.invalidFileName"));
				return;
			}
			if (catNameForVideo.contains("Video")) {
				if (!JSFUtils.isExtensionValidforVideo(e.getFile().getContentType())) {
					showMessage(JSFUtils.getRelevantLangMsg("lebelname.allowedFileType.video"));
					return;
				}
			} else {
				if (!JSFUtils.isExtensionValid(e.getFile().getContentType())) {
					showMessage(JSFUtils.getRelevantLangMsg("lebelname.allowedFileType"));
					return;
				}
			}
			// LOGGER.info("Logger 1 ");
			UploadedFile file = e.getFile();
			// LOGGER.info("Logger 2 ");
			InputStream input = file.getInputstream();
			// LOGGER.info("Logger 3 ");
			InputStream inputSign = file.getInputstream();
			// LOGGER.info("Logger 4 ");
			String appNo = dmsDocumentUpload.getDmsDocument().getApplno();
			// LOGGER.info("Logger 5 ");
			SubcategoryMasterData subcategoryMasterData = document.getSubcategoryMasterData();
			// LOGGER.info("Logger 6 ");
			if (subcategoryMasterData != null) {
				document.getSubcategoryMasterData().setSubCatId(subCatId);
			} else {
				// LOGGER.info("Logger 7 ");
				subcategoryMasterData = new SubcategoryMasterData();
				// LOGGER.info("Logger 8 ");
				subcategoryMasterData.setSubCatId(subCatId);
				// LOGGER.info("Logger 9 ");
				document.setSubcategoryMasterData(subcategoryMasterData);
				// LOGGER.info("Logger 10 ");
			}
			if (catNameForVideo.contains("Video")) {
				Integer maxFileSize = 10240000;// 10 MB
				if (0 == file.getSize() || file.getSize() > maxFileSize) {
					// Integer maxFileSizeInKB = (maxFileSize) / 1024000;
					showMessage(" Please select a file size Upto 10 MB.");
					return;

				}
			}
			// Integer maxFileSize=10240000;
			// final List<String> allwdcontentTypes =
			// Arrays.asList("application/pdf", "image/jpeg", "image/png");
			// Integer maxFileSize=512000;
			// if (0 == file.getSize() || file.getSize() > maxFileSize) {
			// Integer maxFileSizeInKB = (maxFileSize) / 1024;
			// showMessage("\"Please select a file size less than " +
			// maxFileSizeInKB + " KB.");
			// return;
			//
			// }
			// LOGGER.info("Logger 11 ");
			String catName = document.getCatName();
			// LOGGER.info("Logger 12 ");
			String[] fileSplt = (file.getFileName()).split("\\.");
			// LOGGER.info("Logger 13 ");
			String fileExt = fileSplt[fileSplt.length - 1];
			// LOGGER.info("Logger 14 ");
			String fileName = (catName + appNo + "." + fileExt).replaceAll("\\s+", "").replaceAll("/", "_");
			// LOGGER.info("Logger 15 ");
			DmsUpload dmsUpload = document.getDmsUpload();
			// LOGGER.info("Logger 16 ");
			dmsUpload.setFileName(fileName);
			// LOGGER.info("Logger 17 ");
			dmsUpload.setSubCatId(subCatId);
			// LOGGER.info("Logger 18 ");
			if (fileExt.equalsIgnoreCase(CommonConstant.DOC_TYPE_PDF)) {
				dmsUpload.setDocType(CommonConstant.DOC_TYPE_PDF);
			} else {
				dmsUpload.setDocType(CommonConstant.DOC_TYPE_NONPDF);
			}
			// LOGGER.info("Logger 19 ");
			dmsUpload.setSelectedFileName(file.getFileName());
			// LOGGER.info("Logger 20 ");
			dmsUpload.setIndex(index);
			// LOGGER.info("Logger 21 ");
			File directory = new File(filePath);
			if (!directory.exists()) {
				Files.createDirectories(Paths.get(filePath));
			}
			Path path = new File(filePath, fileName).toPath();
			Files.deleteIfExists(path);
			Files.copy(input, path);

			dmsUpload.setFileSelect(true);
			// dmsUpload.setSelectOrder(1);
			// LOGGER.info("Logger 22 ");
			dmsDocumentUpload.getDmsDocument().getDocumentList().get(index).setDmsUpload(dmsUpload);
			// LOGGER.info("Logger 23 ");
			dmsDocumentUpload.getDmsDocument().getDocumentList().get(index).setSelectOrder(1);
			// LOGGER.info("Logger 24 ");

			DmsUpload dmsUpld = dmsDocumentUpload.getDmsDocument().getDocumentList().get(index).getDmsUpload();
			// LOGGER.info("Logger 25 ");
			dmsDocumentUpload.getDmsDocument().getDocumentList().get(index).getDmsUpload()
					.setDmsFlag(prepareFlag(dmsUpld));
			// LOGGER.info("Logger 26 ");
			dmsUploadUtil.saveUpdateDmsUpdateDetail(dmsDocumentUpload, index, "F", fileName);
			// LOGGER.info("Logger 27 ");

		} catch (Exception ex) {
			LOGGER.error("fileUploadListener " + ex.getMessage());
		}
		return;
	}

	public void prepareEsign() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		PdfSignatureAppearance appearance;
		PdfReader reader = null;
		String hashDocument = null;
		TmEsign tmEsign = null;

		String filePath = CommonConstant.FILE_PATH;
		String sourceFileLoc = null;
		String signFileLoc = null;
		String signFileName = null;

		DocumentDetail document = null;
		boolean doubleSign = false;

		try {
			document = dmsDocumentUpload.getDmsDocument().getDocumentList().get(index);
			doubleSign = document.getDmsUpload().getFileDoubleSign();
			if (doubleSign) {
				String fileUrl = document.getDmsUpload().getDocUrl();
				String sourceFileName = (document.getCatName() + dmsDocumentUpload.getDmsDocument().getApplno()
						+ ".pdf").replaceAll("\\s+", "");
				sourceFileLoc = filePath + sourceFileName;

				signFileName = (document.getCatName() + dmsDocumentUpload.getDmsDocument().getApplno()
						+ "_buyer_signed.pdf").replaceAll("\\s+", "");
				signFileLoc = filePath + signFileName;
				dmsDocumentUpload.getDmsDocument().getDocumentList().get(index).getDmsUpload()
						.setFileName(signFileName);
				// LOGGER.info("signFileName===1===" + signFileName);
				DmsUploadUtil.getFileFromDms(sourceFileLoc, fileUrl);

			} else {
				if (!document.getDmsUpload().getDocType().equalsIgnoreCase(CommonConstant.DOC_TYPE_PDF)) {
					document.getDmsUpload()
							.setFileName(dmsUploadUtil.imageToPdf(filePath, document.getDmsUpload().getFileName()));
				}
				String sourceFileName = document.getDmsUpload().getFileName();
				sourceFileLoc = filePath + sourceFileName;

				signFileName = (document.getCatName() + dmsDocumentUpload.getDmsDocument().getApplno() + "_signed.pdf")
						.replaceAll("\\s+", "");
				signFileLoc = filePath + signFileName;
				dmsDocumentUpload.getDmsDocument().getDocumentList().get(index).getDmsUpload()
						.setFileName(signFileName);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		try {
			hashDocument = EsignUtil.getHashDocument(sourceFileLoc, doubleSign, session,
					dmsDocumentUpload.getDmsRequest());
			tmEsign = dmsDocumentUpload.getDmsRequest().getTmEsign();
			if (dmsDocumentUpload.getDmsRequest().getStateCd().equalsIgnoreCase("KA")) {
				result = esignUtil.generateEsignReq(hashDocument, tmEsign);

			} else {

				result = esignObj.eSignRequest(hashDocument, session, tmEsign, dmsDocumentUpload.getDmsRequest());
			}
			dmsDocumentUpload.setIndex(index);
			session.setAttribute("sourcefileloc", sourceFileLoc);
			session.setAttribute("signfileloc", signFileLoc);
			session.setAttribute("signfilename", signFileName);
			session.setAttribute("eSignRequest", result);
			session.setAttribute("dmsdocupload", dmsDocumentUpload);
			if (null != result && tmEsign != null) {
				session.setAttribute("esignUrl", tmEsign.getEsignUrl());
				session.setAttribute("reqparam", tmEsign.getReqEsignParamName());
				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
				if (dmsDocumentUpload.getDmsRequest().getStateCd().equalsIgnoreCase("KA")) {
					ec.redirect("/" + CommonConstant.refererVds + "/dms/ui/uploadDoc/eSignKAResponse.jsp");
				} else {
					ec.redirect("/" + CommonConstant.refererVds + "/dms/ui/uploadDoc/eSignReguest.jsp");
				}

			} else {
				LOGGER.info("Prepare esign Technical Error Contact to System Administrator");
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			LOGGER.info("error in ...." + e);
		}
	}

	public String getEsignDoc() {
		PdfSignatureAppearance appearance = null;
		ByteArrayOutputStream os = null;
		String sesTransId = "";
		String signFileLoc = null;
		Map map = null;
		try {

			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			if (req != null) {
				// if (req != null && req.getHeader("Referer") != null
				// &&
				// !req.getHeader("Referer").contains(CommonConstant.refererVds)
				// &&
				// !req.getHeader("Referer").contains(CommonConstant.refererVcs))
				// {

				String encString = req.getQueryString();
				if (null != encString) {
					map = EsignUtil.getMapData(encString);
					if (map.get("status") != null) {
						if (!map.get("status").toString().equals("-1") && !map.get("status").toString().equals("0")) {
							HttpSession session = req.getSession(false);
							if (session != null) {
								// LOGGER.info("aspTxnID ...." +
								// session.getAttribute("aspTxnID"));
								// LOGGER.info("dmsdocupload ...." +
								// session.getAttribute("dmsdocupload"));
								// LOGGER.info("appearance ...." +
								// session.getAttribute("appearance"));
								// LOGGER.info("bytestream ...." +
								// session.getAttribute("bytestream"));
								LOGGER.info("signfileloc ...." + session.getAttribute("signfileloc").toString());
								if (session.getAttribute("aspTxnID") != null) {
									sesTransId = session.getAttribute("aspTxnID").toString();
								}
								if (session.getAttribute("dmsdocupload") != null) {
									dmsDocumentUpload = (DmsDocumentUpload) session.getAttribute("dmsdocupload");
								}
								if (session.getAttribute("appearance") != null) {
									appearance = (PdfSignatureAppearance) session.getAttribute("appearance");
								}
								if (session.getAttribute("bytestream") != null) {
									os = (ByteArrayOutputStream) session.getAttribute("bytestream");
								}
								if (session.getAttribute("signfileloc") != null) {
									signFileLoc = session.getAttribute("signfileloc").toString();
								}

								if (map.get("data") != null) {
									// esign doc
									EsignUtil.signDoc(map, appearance, os, signFileLoc);

									index = dmsDocumentUpload.getIndex();
									String fileName = (String) session.getAttribute("signfilename");
									String signedFileUrl = CommonConstant.getContextPath()
											+ CommonConstant.signedFileUrl + "?filename=" + fileName;

									DmsUpload dmsUpload = dmsDocumentUpload.getDmsDocument().getDocumentList()
											.get(index).getDmsUpload();
									dmsUpload.setEsigned(true);
									dmsUpload.setSignedDocUrl(signedFileUrl);
									dmsUpload.setSignerName(EsignUtil.getSignerName(map));
									dmsUpload.setDmsFlag(prepareFlag(dmsUpload));
									dmsDocumentUpload.getDmsDocument().getDocumentList().get(index)
											.setDmsUpload(dmsUpload);

								}
							}
						} else {
							FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
									JSFUtils.getRelevantLangMsg("lebelname.response.statusZero"));
							RequestContext.getCurrentInstance().showMessageInDialog(message);
						}
					}
				}
			}
			return esignDoc;
		} catch (Exception e) {
			LOGGER.error("getEsignDoc " + e.getMessage());
		}
		return esignDoc;
	}

	public void uploadToDms() {

		if (!enableConf) {
			RequestContext.getCurrentInstance().update("userconf");
			RequestContext.getCurrentInstance().execute("PF('userconf').show()");
			return;
		}
		boolean status = dmsUploadUtil.uploadDocumentToDms(dmsDocumentUpload, index, CommonConstant.PENDING_UPLOAD);
		// prepareLocalFlag();
		if (status) {
			manageDoubleSignRec();
			dmsDocumentUpload.getDmsDocument().getDocumentList().get(index).getDmsUpload().setDisableUploadBtn(true);
			DmsFlag dmsFlag = prepareFlag(
					dmsDocumentUpload.getDmsDocument().getDocumentList().get(index).getDmsUpload());
			dmsDocumentUpload.getDmsDocument().getDocumentList().get(index).getDmsUpload().setDmsFlag(dmsFlag);
			setEnableConf(false);
			RequestContext.getCurrentInstance().execute("PF('userconf').hide()");
		}

	}

	public void updateToDms() {

		boolean status = dmsUploadUtil.updateDocumentToDms(dmsDocumentUpload, index, CommonConstant.UPDATE_UPLOAD);
		// prepareLocalFlag();
		if (status) {
			manageDoubleSignRec();
			dmsDocumentUpload.getDmsDocument().getDocumentList().get(index).getDmsUpload().setDisableUploadBtn(true);
			DmsFlag dmsFlag = prepareFlag(
					dmsDocumentUpload.getDmsDocument().getDocumentList().get(index).getDmsUpload());

			dmsDocumentUpload.getDmsDocument().getDocumentList().get(index).getDmsUpload().setDmsFlag(dmsFlag);
		}
	}

	private void manageDoubleSignRec() {
		dmsRequest = dmsDocumentUpload.getDmsRequest();
		DocumentDetail document = dmsDocumentUpload.getDmsDocument().getDocumentList().get(index);
		if (document.getDocUploaded() && dmsRequest.getDse() && dmsRequest.getDss()) {
			Map<Integer, List<String>> purDoc = dmsRequest.getPurDoc();
			for (String purCode : dmsRequest.getPurCd().split(",")) {
				List<String> docLst = purDoc.get(Integer.parseInt(purCode));
				if (docLst != null) {
					for (String doCcatId : docLst) {
						Boolean docDoubleSignStatus = dmsRequest.getDocDoubleSignStatus().get(doCcatId);
						if (document.getCatId().equalsIgnoreCase(doCcatId) && docDoubleSignStatus != null
								&& dmsRequest.getDocDoubleSignStatus().get(doCcatId)) {
							// TODO --Move data from t1 to t2

							DoublesignPendingRecords pendingRecords = new DoublesignPendingRecords();
							pendingRecords.setStateCd(dmsRequest.getStateCd());
							pendingRecords.setApplNo(dmsRequest.getApplNo());
							pendingRecords.setRegnNo(dmsRequest.getRegnNo());
							pendingRecords.setPurCd(purCode);
							pendingRecords.setDocCatid(document.getCatId());
							boolean status = dmsUploadUtil.moveDoublesignPendingHistory(pendingRecords,
									dmsRequest.getMoveDoublesignPendUrl());
							if (status) {
								boolean isTestingEnviroment = Boolean
										.valueOf(new ApplicationConfig(ApplicationConfig.PROP_PAYMENT_MODE)
												.getProperties(ApplicationConfig.PROP_KEY_MODE_ISTESTING));
								if (!isTestingEnviroment) {
									if (!CommonUtils.isNullOrBlank(dmsRequest.getSellerMobileNo())) {
										String smsString = "Buyer has signed the document. Please do final submit from - Reprint receipt option under Status menu of Vahan Citizen Portal. MoRTH";
										VahanSMS sms = new VahanSMS();
										String templeteId = ApplicationConfig.SMS_TEMPLATE_AFTER_BUYER_ESIGN;
										sms.sendOTPSms(dmsRequest.getSellerMobileNo(), smsString,
												dmsRequest.getStateCd(), templeteId);
									}
								}
							}

						}
					}
				}
			}
		} else if (dmsRequest.getDse()) {
			Map<Integer, List<String>> purDoc = dmsRequest.getPurDoc();
			boolean isTo = false;
			boolean doubleEign = true;
			String purCd[] = dmsRequest.getPurCd().split(",");
			for (int i = 0; i < purCd.length; i++) {
				if (purCd[i].equals(String.valueOf(ApplicationConfig.PUR_TO))) {
					isTo = true;
					break;
				}
			}
			if (isTo && (dmsRequest.getServiceRgn().equalsIgnoreCase(ApplicationConfig.tOAuction)
					|| (dmsRequest.getServiceRgn().equalsIgnoreCase(ApplicationConfig.tOSuccession)))) {
				doubleEign = false;
			}
			if (doubleEign) {
				for (String purCode : dmsRequest.getPurCd().split(",")) {
					List<String> docLst = purDoc.get(Integer.parseInt(purCode));
					if (docLst != null) {
						for (String doCcatId : docLst) {
							if (document.getCatId().equalsIgnoreCase(doCcatId)) {
								// TODO --Insert data into t1 through api
								DoublesignPendingRecords pendingRecords = new DoublesignPendingRecords();
								pendingRecords.setStateCd(dmsRequest.getStateCd());
								pendingRecords.setApplNo(dmsRequest.getApplNo());
								pendingRecords.setRegnNo(dmsRequest.getRegnNo());
								pendingRecords.setPurCd(purCode);
								pendingRecords.setDocCatid(document.getCatId());

								boolean status = dmsUploadUtil.saveDoublesignPendingRecords(pendingRecords,
										dmsRequest.getSaveDoublesignPendUrl());
								if (status) {
									boolean isTestingEnviroment = Boolean
											.valueOf(new ApplicationConfig(ApplicationConfig.PROP_PAYMENT_MODE)
													.getProperties(ApplicationConfig.PROP_KEY_MODE_ISTESTING));
									if (!isTestingEnviroment) {
										if (!CommonUtils.isNullOrBlank(dmsRequest.getBuyerMobileNo())) {
											String smsString = "Please eSign the document using the option - eSign by Buyer under Status menu of Vahan Citizen Portal. MoRTH";
											VahanSMS sms = new VahanSMS();
											String templeteId = ApplicationConfig.SMS_TEMPLATE_BUYER_ESIGN;
											sms.sendOTPSms(dmsRequest.getBuyerMobileNo(), smsString,
													dmsRequest.getStateCd(), templeteId);
										}
									}

								}

							}
						}
					}
				}
			}

		}
	}

	public DmsFlag prepareFlag(DmsUpload dmsUpload) {
		try {
			DmsFlag dmsFlag = new DmsFlag();

			boolean browseOption = dmsUpload.getFileDoubleSign() == false
					&& ((dmsUpload.getDmsUploadedAlready() == false
							&& dmsDocumentUpload.getDmsRequest().getDocType().equalsIgnoreCase("B"))
							|| dmsDocumentUpload.getDmsRequest().getDocType().equalsIgnoreCase("U")
							|| dmsDocumentUpload.getDmsRequest().getDocType().equalsIgnoreCase("N"));
			dmsFlag.setBrowseOption(browseOption);

			dmsFlag.setSignDocCol(dmsDocumentUpload.getDmsRequest().getEsignFlag());
			dmsFlag.setSignDoc((dmsDocumentUpload.getDmsRequest().getEsignFlag() && dmsUpload.getFileSelect())
					|| dmsUpload.getFileDoubleSign());
			dmsFlag.setDisableSignButton(dmsUpload.getEsigned());
			dmsFlag.setViewSignDocBtn(dmsUpload.getEsigned() && !dmsUpload.getDisableUploadBtn());
			dmsFlag.setUploadDocBtn(dmsUpload.getEsigned()
					|| (dmsUpload.getFileSelect() && !dmsDocumentUpload.getDmsRequest().getEsignFlag()));
			// if (dmsFlag.getUploadDocBtn()) {
			// dmsFlag.setViewDocBtn(dmsFlag.getUploadDocBtn());
			// }
			dmsFlag.setDisableUploadBtn(dmsUpload.getDisableUploadBtn());
			dmsFlag.setViewDocBtn(dmsUpload.getDmsUploaded());
			// dmsFlag.setUploadDocBtn(true); // by ashish
			return dmsFlag;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}

	}

	public static void showMessage(String msgContent) {
		RequestContext.getCurrentInstance()
				.showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN, "Alert", msgContent));
	}

	public String generateRandomIdForNotCaching() {
		return java.util.UUID.randomUUID().toString();
	}

	public void getViewDocs(DocumentDetail documentDetail) {
		try {
			byte[] imageInBytes;
			StreamedContent viewSignFile;
			DmsFileUploadDetail dmsFileUploadDetail = new DmsFileUploadDetail();
			dmsFileUploadDetail.setApplno(dmsDocumentUpload.getDmsDocument().getApplno());
			DmsUpload dmsUpload = documentDetail.getDmsUpload();
			if (!CommonUtils.isNullOrBlank(dmsUpload.getDocType()) && dmsUpload.getDocType().equalsIgnoreCase("pdf")) {
				docTypeForView = "application/pdf";
			} else if (!CommonUtils.isNullOrBlank(dmsUpload.getDocType())
					&& !dmsUpload.getDocType().equalsIgnoreCase("pdf") && dmsUpload.getFileName().contains("Video")) {
				docTypeForView = "video/mp4";
			} else {
				docTypeForView = "image/png";
			}
			if (dmsUpload.getImageInBytes() == null) {
				List<String> listKeyExtn = dmsUploadUtil.getKeyForUploadModify(dmsFileUploadDetail, documentDetail,
						dmsDocumentUpload.getDmsRequest().getDmsServiceUrl() + CommonConstant.DOC_KEY + "/");
				String key = listKeyExtn.get(0);
				String fileExt = listKeyExtn.get(1);
				if (fileExt.equalsIgnoreCase("pdf")) {
					docTypeForView = "application/pdf";
				} else {
					docTypeForView = "image/png";
				}

				imageInBytes = dmsUploadUtil.ImageToByte(key);
				viewSignFile = dmsUploadUtil.ImageToInputStream(key);
				if (documentDetail.getCatName().contains("Video")) {
					docTypeForView = "video/mp4";
				}

			} else {
				imageInBytes = dmsUpload.getImageInBytes();
				viewSignFile = dmsUpload.getViewSignFile();
			}
			if (docTypeForView.equalsIgnoreCase("video/mp4")) {
				viewDocumentUploadedOnlyVideo();
			} else {
				InputStream is = new ByteArrayInputStream(imageInBytes);
				StreamedContent image = new DefaultStreamedContent(is);
				streamedContent = image;
				String encoded = new String(java.util.Base64.getEncoder().encode(imageInBytes));
				// LOGGER.info("View File before set parameter" + encoded);
				setImageBase64String(encoded);
				// LOGGER.info("View File after set parameter" +
				// imageBase64String);
				setViewSignStreamedContent(viewSignFile);
				RequestContext.getCurrentInstance().execute("PF('viewDocs').show()");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private StreamedContent createStream(String fileName) {
		streamedContent = new DefaultStreamedContent(getData(fileName), "application/pdf", "downloaded_" + fileName);
		return streamedContent;
	}

	private InputStream getData(String fileName) {
		File file = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\" + fileName);

		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return is;

	}

	public void viewDocumentUploaded() throws IOException {
		try {
			docsListapplwise = dmsUploadUtil.getList(dmsRequest.getApplNo());
			if (docsListapplwise.size() > 0) {
				RequestContext.getCurrentInstance().execute("PF('viewdocappldlg').show()");
			}
		} catch (Exception e) {
			LOGGER.error("ViewUploadedDocumentBean:viewDocumentUploaded:  " + e.getMessage());

		}

	}

	public void viewDocumentUploadedOnlyVideo() throws IOException {
		try {
			docsListapplwise = dmsUploadUtil.getListForVideo(dmsRequest.getApplNo());
			if (docsListapplwise.size() > 0) {
				RequestContext.getCurrentInstance().execute("PF('viewdocappldlg').show()");
			}
		} catch (Exception e) {
			LOGGER.error("ViewUploadedDocumentBean:viewDocumentUploaded:  " + e.getMessage());

		}

	}

	public void setListOfDoc(String listOfDoc) {
		this.listOfDoc = listOfDoc;
	}

	public DmsDocumentUpload getDmsDocumentUpload() {
		return dmsDocumentUpload;
	}

	public void setDmsDocumentUpload(DmsDocumentUpload dmsDocumentUpload) {
		this.dmsDocumentUpload = dmsDocumentUpload;
	}

	public boolean isDocMerger() {
		return docMerger;
	}

	public void setDocMerger(boolean docMerger) {
		this.docMerger = docMerger;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Integer getSubCatId() {
		return subCatId;
	}

	public void setSubCatId(Integer subCatId) {
		this.subCatId = subCatId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setEsignDoc(String esignDoc) {
		this.esignDoc = esignDoc;
	}

	public DmsRequest getDmsRequest() {
		return dmsRequest;
	}

	public void setDmsRequest(DmsRequest dmsRequest) {
		this.dmsRequest = dmsRequest;
	}

	public Boolean getQueryParam() {
		return queryParam;
	}

	public Boolean getEnableConf() {
		return enableConf;
	}

	public void setEnableConf(Boolean enableConf) {
		this.enableConf = enableConf;
	}

	public void setQueryParam(Boolean queryParam) {
		this.queryParam = queryParam;
	}

	public String getImageBase64String() {
		return imageBase64String;
	}

	public void setImageBase64String(String imageBase64String) {
		this.imageBase64String = imageBase64String;
	}

	public StreamedContent getViewSignStreamedContent() {
		return viewSignStreamedContent;
	}

	public void setViewSignStreamedContent(StreamedContent viewSignStreamedContent) {
		this.viewSignStreamedContent = viewSignStreamedContent;
	}

	public String getDocTypeForView() {
		return docTypeForView;
	}

	public void setDocTypeForView(String docTypeForView) {
		this.docTypeForView = docTypeForView;
	}

	public List<VtDocuments> getDocsListapplwise() {
		return docsListapplwise;
	}

	public void setDocsListapplwise(List<VtDocuments> docsListapplwise) {
		this.docsListapplwise = docsListapplwise;
	}

	public String getViewdocUrl() {
		return ViewdocUrl;
	}

	public void setViewdocUrl(String viewdocUrl) {
		ViewdocUrl = viewdocUrl;
	}

	public Boolean getViewAutoUploadDocs() {
		return viewAutoUploadDocs;
	}

	public void setViewAutoUploadDocs(Boolean viewAutoUploadDocs) {
		this.viewAutoUploadDocs = viewAutoUploadDocs;
	}

}
