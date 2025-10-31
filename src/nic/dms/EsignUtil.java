package nic.dms;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import nic.org.apache.log4j.Logger;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Font.FontStyle;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignature;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;

import nic.dms.common.constant.ESignProperty;
import nic.dms.pojo.TmEsign;
import nic.dms.pojo.reqestresponse.DmsRequest;
import resources.ApplicationConfig;

public class EsignUtil {
	private static final Logger LOGGER = Logger.getLogger(EsignUtil.class);
	HttpSession session = null;

	public static String getHashDocument(String fileLoc, boolean secondSign, HttpSession session, DmsRequest dmsRequest)
			throws DocumentException, IOException {
		ByteArrayOutputStream os;
		PdfSignatureAppearance appearance = null;
		PdfReader reader = null;
		String hashDocument = null;
		InputStream is = null;
		PdfStamper stamper = null;

		reader = new PdfReader(fileLoc);
		os = new ByteArrayOutputStream();
		stamper = PdfStamper.createSignature(reader, os, '\0', null, true);
		appearance = stamper.getSignatureAppearance();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss 'IST'");
		String datetime = LocalDateTime.now().format(formatter);
		appearance.setSignDate(Calendar.getInstance());

		appearance.setAcro6Layers(false);
		appearance.setLayer4Text(PdfSignatureAppearance.questionMark);
		Font layer2Font = new Font();
		appearance.setLayer2Font(layer2Font);
		appearance.setImage(null);
		appearance.setCertificationLevel(PdfSignatureAppearance.NOT_CERTIFIED);

		int pagenumber = reader.getNumberOfPages();
		PdfDictionary lastPg = reader.getPageN(pagenumber);
		Rectangle pgRect = reader.getPageSize(lastPg);

		float pgWidth = pgRect.getWidth();
		int contentEstimated = 8192;
		Rectangle rect;

		if (secondSign) {
			rect = new Rectangle(pgWidth - 50 - 100, 48, pgWidth - 50, 80);
			appearance.setReason(dmsRequest.getOwnerName() + "(Buyer)");
		} else {
			boolean isTo = false;
			String purCd[] = dmsRequest.getPurCd().split(",");
			for (int i = 0; i < purCd.length; i++) {
				if (purCd[i].equals(String.valueOf(ApplicationConfig.PUR_TO))) {
					isTo = true;
					break;
				}
			}
			if (isTo && (dmsRequest.getServiceRgn().equalsIgnoreCase(ApplicationConfig.tOAuction)
					|| (dmsRequest.getServiceRgn().equalsIgnoreCase(ApplicationConfig.tOSuccession)))) {
				rect = new Rectangle(pgWidth - 200 - 100, 48, pgWidth - 200, 80);
				appearance.setReason(dmsRequest.getOwnerName() + "(Buyer)");
				appearance.setVisibleSignature(rect, pagenumber, null);
			} else {
				rect = new Rectangle(pgWidth - 200 - 100, 48, pgWidth - 200, 80);
				appearance.setReason(dmsRequest.getOwnerName() + "(Seller)");
				appearance.setVisibleSignature(rect, pagenumber, null);

			}

		}

		HashMap<PdfName, Integer> exc = new HashMap<PdfName, Integer>();
		exc.put(PdfName.CONTENTS, new Integer(contentEstimated * 2 + 2));

		PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ADBE_PKCS7_DETACHED);
		dic.setReason(appearance.getReason());
		// dic.setLocation(appearance.getLocation());
		// dic.setContact(appearance.getContact());
		dic.setDate(new PdfDate(appearance.getSignDate()));
		appearance.setCryptoDictionary(dic);
		appearance.preClose(exc);
		is = appearance.getRangeStream();
		LOGGER.info("appearance before esign ...." + appearance);
		LOGGER.info("bytestream before esign ...." + os);
		session.setAttribute("appearance", appearance);
		session.setAttribute("bytestream", os);
		session.setAttribute("reader", reader);

		hashDocument = DigestUtils.sha256Hex(is);
		return hashDocument;

	}

	public static void signDoc(Map map, PdfSignatureAppearance appearance, ByteArrayOutputStream os, String signFileLoc)
			throws IOException, DocumentException, CertificateException {
		String docsignresp = map.get("data").toString();
		docsignresp = docsignresp.replaceAll("%20", "");
		String pkcsResponse = docsignresp.trim();
		byte[] sigbytes = Base64.decodeBase64(pkcsResponse);
		byte[] paddedSig = new byte[8192];

		X509Certificate cert = getCertificate(map);
		appearance.setCertificate(cert);

		System.arraycopy(sigbytes, 0, paddedSig, 0, sigbytes.length);
		PdfDictionary dic2 = new PdfDictionary();
		dic2.put(PdfName.CONTENTS, new PdfString(paddedSig).setHexWriting(true));
		if (appearance != null) {
			appearance.close(dic2);
		}

		OutputStream outputStream = new FileOutputStream(signFileLoc);
		if (os != null) {
			os.writeTo(outputStream);
		}
		os.close();
		outputStream.close();
		// index = dmsDocumentUpload.getIndex();
	}

	public static X509Certificate getCertificate(Map map) throws CertificateException {
		X509Certificate cert = null;

		String certificate = map.get("cert").toString();
		certificate = certificate.replaceAll("%20", "");
		String certTrimed = certificate.trim();
		CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
		byte[] certDecr = Base64.decodeBase64(certTrimed);
		InputStream certStream = new ByteArrayInputStream(certDecr);

		cert = (X509Certificate) certFactory.generateCertificate(certStream);
		return cert;

	}

	public static String getSignerName(Map map) throws CertificateException {
		String certificate = map.get("cert").toString();
		certificate = certificate.replaceAll("%20", "");
		String certTrimed = certificate.trim();
		CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
		byte[] certDecr = Base64.decodeBase64(certTrimed);
		InputStream certStream = new ByteArrayInputStream(certDecr);

		X509Certificate cert = (X509Certificate) certFactory.generateCertificate(certStream);
		String subjectdn = cert.getSubjectDN().getName();

		Map certMap = getMapData(subjectdn);
		return certMap.get("CN").toString();

	}

	public static Map getMapData(String data) {
		String strDatas[] = data.split(",");
		Map<String, String> map = new HashMap<String, String>();
		for (String strData : strDatas) {
			String str[] = strData.split("=");
			map.put(str[0].trim(), str[1].trim());
		}
		return map;
	}

	public String generateEsignReq(String returnHash, TmEsign tmEsign) {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			session = (HttpSession) ec.getSession(true);

			Random random = new Random();
			String transId = String.valueOf(random.nextInt(9999));

			String userName = ESignProperty.ESIGN_USERNAME;
			String password = ESignProperty.ESIGN_PASSWORD;
			String deptCode = ESignProperty.ESIGN_DEPTCODE;
			Integer serviceId = Integer.parseInt(ESignProperty.ESIGN_SERVICE_ID);
			String callBackUrl = tmEsign.getEsignRespUrl();
			String aesEncryptKey = ESignProperty.AES_KEY;
			String aesEncryptVector = ESignProperty.AES_VECTOR;
			String eTokenNo = "";
			String returnMsg = getESignHashXML(encrypt(aesEncryptKey, aesEncryptVector, userName),
					encrypt(aesEncryptKey, aesEncryptVector, password), deptCode, eTokenNo, serviceId, "transport",
					returnHash, callBackUrl, transId, ESignProperty.AUTH_MODE_OTP);
			return returnMsg;

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}

	public String getESignHashXML(String userId, String password, String departmentCode, String eTokenNo, int serviceId,
			String aadharHolderName, String hash, String responseURL, String transactionNo, String authMode) {

		String strXML = "<esigndata><departmentcode>T_DEPARTMENTCODE</departmentcode><serviceid>T_SERVICEID</serviceid><userid>T_USERID</userid><password>T_PASSWORD</password><hash>T_HASH</hash><etokenno>T_ETOKEN</etokenno><aadharholdername>T_AADHARHOLDERNAME</aadharholdername><responseurl>T_RESPONSEURL</responseurl><transactionno>T_TRANSACTIONNO</transactionno><authmode>T_AUTHMODE</authmode></esigndata>";
		strXML = strXML.replace("T_DEPARTMENTCODE", departmentCode);
		strXML = strXML.replace("T_USERID", userId);
		strXML = strXML.replace("T_PASSWORD", password);
		if (eTokenNo != "")
			strXML = strXML.replace("T_ETOKEN", eTokenNo);
		else
			strXML = strXML.replace("T_ETOKEN", "");

		strXML = strXML.replace("T_TRANSACTIONNO", transactionNo);
		strXML = strXML.replace("T_SERVICEID", String.valueOf(serviceId));

		strXML = strXML.replace("T_AADHARHOLDERNAME", aadharHolderName);
		strXML = strXML.replace("T_HASH", hash);
		// strXML = strXML.replace("T_RESPONSEURL", responseURL + "/" +
		// transactionNo);
		strXML = strXML.replace("T_RESPONSEURL", responseURL);
		strXML = strXML.replace("T_AUTHMODE", authMode);
		LOGGER.info("strXML===" + strXML);
		byte[] bytesEncoded = Base64.encodeBase64(strXML.getBytes());

		String encodedString = new String(bytesEncoded);
		LOGGER.info("Encoded String" + encodedString);
		return encodedString;
	}

	public static String encrypt(String encryptkey, String encryptvector, String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(encryptvector.getBytes("UTF-8"));

			SecretKeySpec skeySpec = new SecretKeySpec(encryptkey.getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(value.getBytes());
			LOGGER.info("encrypted string:" + Base64.encodeBase64String(encrypted));
			return Base64.encodeBase64String(encrypted);
		} catch (Exception ex) {

		}
		return null;
	}
}
