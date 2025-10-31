package nic.esign;

import java.util.UUID;
import javax.faces.application.FacesMessage;
import javax.servlet.http.HttpSession;

import nic.org.apache.log4j.Logger;
import nic.vahan.form.impl.dmsFileImpl;
import resources.ApplicationConfig;

import org.primefaces.context.RequestContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import nic.dms.common.constant.CommonConstant;
import nic.dms.pojo.TmEsign;
import nic.dms.pojo.reqestresponse.DmsRequest;
import nic.esign.dobj.Auth;
import nic.esign.dobj.Docs;
import nic.esign.dobj.Esign;
import nic.esign.dobj.EsignRequestDobj;
import nic.esign.dobj.EsignServiceData;
import nic.esign.dobj.InputHash;

/**
 *
 * @author nuicsi
 */
public class EsignRequest {
	Utility util = new Utility();
	SignXML signXML = new SignXML();
	private static final Logger LOGGER = Logger.getLogger(EsignRequest.class);

	public String eSignRequest(String hash, HttpSession session, TmEsign tmEsign, DmsRequest dmsRequest)
			throws Exception {
		String result = null;
		Esign esign = new Esign();
		InputHash inputHash = new InputHash();
		Docs Docs = new Docs();
		try {
			String inputHashXML = signXML.createXmlTree(hash);
			Docs.setValue(inputHashXML);
			esign.setAspId(tmEsign.getAspId());
			esign.setSc("Y");
			esign.setVer("2.1"); // 2.1
			esign.setTs(util.getTimeStamp());
			esign.setTxn(UUID.randomUUID().toString().replaceAll("-", ""));
			session.setAttribute("aspTxnID", esign.getTxn());
			esign.setEkycIdType("A");
			esign.setEkycId("");
			esign.setAuthMode("1");
			esign.setResponseSigType("pkcs7");
			esign.setResponseUrl(tmEsign.getEsignRespUrl());
			boolean isLocalEnviroment = Boolean.valueOf(new ApplicationConfig(ApplicationConfig.PROP_PAYMENT_MODE)
					.getProperties(ApplicationConfig.PROP_KEY_MODE_ISLOCAL));
			if (isLocalEnviroment) {
				esign.setResponseUrl("http://localhost:8080/vahandocumentsystem/rest/esignstatus/checkESignStatus/");
			}
			esign.setDocs(Docs);

			// logger.log("---------------------- E-Sign Request End
			// ----------------------", eSignPropMap);
			SignXML signXML = new SignXML();
			String eSignXml = util.marshallObj(Esign.class, esign);

			eSignXml = eSignXml.replace("&lt;", "<");
			eSignXml = eSignXml.replace("&gt;", ">");
			eSignXml = eSignXml.replace("docs", "Docs");
			if (tmEsign.getStateCode().equalsIgnoreCase("OR")) {
				tmEsign.seteSignPass(ApplicationConfig.OR_ESIGN_PASS);
			}
			if (tmEsign.getStateCode().equalsIgnoreCase("DL")) {
				tmEsign.seteSignPass(ApplicationConfig.DL_ESIGN_PASS);
			}
			if (tmEsign.getStateCode().equalsIgnoreCase("JK")) {
				tmEsign.seteSignPass(ApplicationConfig.JK_ESIGN_PASS);
			}
			String signedEsignXML = signXML.signEspRequest(eSignXml, tmEsign.getCertAliase(), tmEsign);
			Document doc = signXML.convertStringToDocument(signedEsignXML);
			Element root = doc.getDocumentElement();
			NodeList nl = root.getElementsByTagName("SignatureValue");
			Element InputHashElement = (Element) root.getElementsByTagName("InputHash").item(0);
			String hashAlgorithm = InputHashElement.getAttribute("hashAlgorithm");
			String docInfo = InputHashElement.getAttribute("docInfo");

			LOGGER.info("signedEsignXML: " + signedEsignXML);
			if (signedEsignXML != null) {
				EsignRequestDobj reqDobj = new EsignRequestDobj();
				reqDobj.setAppl_no(dmsRequest.getApplNo());
				reqDobj.setAspid(esign.getAspId());
				reqDobj.setAuthmode(esign.getAuthMode());
				reqDobj.setDocinfo(docInfo);
				reqDobj.setEkycid(esign.getEkycId());
				reqDobj.setEkycidtype(esign.getEkycIdType());
				reqDobj.setHash(hash);
				reqDobj.setHashalgorithm(hashAlgorithm);
				reqDobj.setOff_cd(dmsRequest.getOfficeCd());
				reqDobj.setPurpose(dmsRequest.getPurCd());
				reqDobj.setReg_no(dmsRequest.getRegnNo());
				reqDobj.setResponsesigtype(esign.getResponseSigType());
				reqDobj.setResponseurl(esign.getResponseUrl());
				reqDobj.setSc(esign.getSc());
				String purCd = dmsRequest.getPurCd();
				purCd = "," + purCd + ",";
				if (purCd.contains(",5,")) {
					reqDobj.setSignatory("B");
				} else {
					reqDobj.setSignatory("NA");
				}
				reqDobj.setState_cd(dmsRequest.getStateCd());
				reqDobj.setTs(dmsFileImpl.getCurrentDateDDMMYYYHHMMSS());
				reqDobj.setTxn(esign.getTxn());
				reqDobj.setVer(esign.getVer());
				session.setAttribute("EsignRequestDobj", reqDobj);
			}
			result = signedEsignXML;

		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			// exceptionStr = exceptionStr = "|" + e.toString();
			// throw new Exception(exceptionStr, e);
		}
		return result;
	}

	public static void showMessage(String msgContent) {
		RequestContext.getCurrentInstance()
				.showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", msgContent));
	}

}
