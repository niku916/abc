package nic.dms.common.constant;

import resources.ApplicationConfig;

public class CommonConstant {
	public static final String FILE_PATH = System.getProperty("catalina.base")
			+ new ApplicationConfig(ApplicationConfig.PROP_DMS_URL).getProperties(ApplicationConfig.UPLOADED_FILE_PATH);
	public static final String stagingContextPath = new ApplicationConfig(ApplicationConfig.PROP_VDS_URL)
			.getProperties(ApplicationConfig.STAGING_CONTEXT_PATH);
	public static final String serverContextPath = new ApplicationConfig(ApplicationConfig.PROP_VDS_URL)
			.getProperties(ApplicationConfig.PROD_CONTEXT_PATH);
	public static final String localContextPath = new ApplicationConfig(ApplicationConfig.PROP_VDS_URL)
			.getProperties(ApplicationConfig.LOCAL_CONTEXT_PATH);

	public static String getContextPath() {
		try {
			boolean isTestingEnviroment = Boolean.valueOf(new ApplicationConfig(ApplicationConfig.PROP_PAYMENT_MODE)
					.getProperties(ApplicationConfig.PROP_KEY_MODE_ISTESTING));
			boolean isLocalEnviroment = Boolean.valueOf(new ApplicationConfig(ApplicationConfig.PROP_PAYMENT_MODE)
					.getProperties(ApplicationConfig.PROP_KEY_MODE_ISLOCAL));
			if (!isTestingEnviroment) {
				// server

				return serverContextPath;
			} else {
				if (isLocalEnviroment) {
					// local
					return localContextPath;
				} else {
					// staging
					return stagingContextPath;
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	public static final String refererVds = "vahandocumentsystem";
	public static final String refererVcs = "vahanservice";
	public static final String apiPath = "https://vahan.parivahan.gov.in/dms-app/";
	public static final String Vahan_Context_Url_LIVE = "https://vahan.parivahan.gov.in/";
	public static final String Vahan_Context_Url_Staging = "https://staging.parivahan.gov.in/";

	public static String J_KEY = "vVl%2FAz1yGsjOAG18WDeScg%3D%3D";
	public static final String J_SECURITY_KEY = "6D6C069D681B40DBF95CAD7B3ED71BE1A46F0A7036BC711860B00BAAE50FE8A4%21TZFIMZbTiUtqXMfARJ1DGgyNicWFwYkwTA0ip%2FQ8Wns%3D";

	public static final String ADD_UPDATE_PAGE = "addUpdateDoc";
	public static final String REGN_RENEWAL = "regnRenewal";

	public static final String FRESH_UPLOAD = "F";
	public static final String PENDING_UPLOAD = "P";
	public static final String UPDATE_UPLOAD = "M";

	public static final String MERGE_FILE_NAME = "mergedfile";
	public static final String DMS_UPLOAD_SUCCESS = "V001";

	public static final String DOC_TYPE_PDF = "pdf";
	public static final String DOC_TYPE_NONPDF = "nonpdf";

	public static final String AUTH_LETTER = "authLetter";

	public static final String UPLOADED_DOC = "U";
	public static final String NONUPLOADED_DOC = "N";
	public static final String BOTH_DOC = "B";

	public static final String DOC_UPLOAD = "upload-dms-doc";
	public static final String DOC_UPDATE = "update-dms-doc";
	public static final String DOC_VIEW = "view-dms-doc";
	public static final String DOC_LIST = "dms-vahan-citizen-conf";
	public static final String DOC_KEY = "getVtDocList";
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";

	public static final String ESIGN_RESP_URL = "rest/esignstatus/checkESignStatus/";
	public static final String ESIGN_KA_RESP_URL = "rest/esignstatus/checkESignS/";
	public static final String signedFileUrl = "rest/esignstatus/pdf";
	public static final String errorFileUrl = "dms/ui/uploadDoc/directCallError.xhtml";

	public static final String characterEncoding = "UTF-8";
	public static final String cipherTransformation = "AES/CBC/PKCS5Padding";
	public static final String aesEncryptionAlgorithm = "AES";
	public static final String TOKEN = "VCSToken";
	public static final String BLANK_STRING = "";
	public static final String Esign_Context_Url_LIVE = "https://esignservice.cdac.in/";
}
