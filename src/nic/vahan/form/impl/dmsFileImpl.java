package nic.vahan.form.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.codec.binary.Base64;

import nic.dms.pojo.DmsDocumentUpload;
import nic.esign.dobj.ESignResponseDobj;
import nic.esign.dobj.EsignEkycDobj;
import nic.esign.dobj.EsignRequestDobj;
import nic.java.util.CommonUtils;
import nic.org.apache.log4j.Logger;
import nic.vahan.db.connection.TransactionManager;

public class dmsFileImpl {
	private static final Logger LOGGER = Logger.getLogger(dmsFileImpl.class);

	public static void insertEsignRequestResponse(EsignRequestDobj reqDobj, ESignResponseDobj resDobj, String certStr)
			throws Exception {
		PreparedStatement ps = null;
		String sql = null;
		TransactionManager tmgr = null;
		EsignEkycDobj esignEkycDobj = null;
		int i = 1;
		int j = 0;
		CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
		String certTrimed = certStr.trim();
		byte[] certDecr = Base64.decodeBase64(certTrimed);
		InputStream certStream = new ByteArrayInputStream(certDecr);
		X509Certificate cert = (X509Certificate) certFactory.generateCertificate(certStream);
		String subjectdn = cert.getSubjectDN().getName();
		Map<String, String> map = getMapDataCert(subjectdn);
		if (map != null) {
			esignEkycDobj = new EsignEkycDobj();
			if (!CommonUtils.isNullOrBlank(map.get("OID.2.5.4.16"))) {
				esignEkycDobj.setPostaladdress(map.get("OID.2.5.4.16").toString().replace("\"", ""));
			}
			if (!CommonUtils.isNullOrBlank(map.get("CN"))) {
				esignEkycDobj.setCommonname(map.get("CN"));
			}
			if (!CommonUtils.isNullOrBlank(map.get("L"))) {
				esignEkycDobj.setLocalityname(map.get("L"));
			}
			if (!CommonUtils.isNullOrBlank(map.get("ST"))) {
				esignEkycDobj.setStateorprovincename(map.get("ST"));
			}
			if (!CommonUtils.isNullOrBlank(map.get("STREET"))) {
				esignEkycDobj.setStreetaddress(map.get("STREET"));
			}
			if (!CommonUtils.isNullOrBlank(map.get("OID.2.5.4.17"))) {
				esignEkycDobj.setPostalcode(map.get("OID.2.5.4.17"));
			}
			if (!CommonUtils.isNullOrBlank(map.get("OID.2.5.4.45"))) {
				esignEkycDobj.setUniqueidentifier(map.get("OID.2.5.4.45"));
			}
			if (!CommonUtils.isNullOrBlank(map.get("OID.2.5.4.65"))) {
				esignEkycDobj.setPseudonym(map.get("OID.2.5.4.65"));
			}
			esignEkycDobj.setTxn(resDobj.getTxn());
			esignEkycDobj.setAppl_no(resDobj.getAppl_no());
			esignEkycDobj.setState_cd(resDobj.getState_cd());
			esignEkycDobj.setReg_no(resDobj.getReg_no());
			esignEkycDobj.setOff_cd(resDobj.getOff_cd());
			esignEkycDobj.setPurpose(resDobj.getPurpose());
		}
		// System.out.println(map.get("OID.2.5.4.16").toString().replace("\"",
		// "")); // postal
		// address
		// System.out.println(map.get("CN")); // common name
		// System.out.println(map.get("L")); // Localityname
		// System.out.println(map.get("ST")); // Stateorprovincename
		// System.out.println(map.get("STREET"));
		// System.out.println(map.get("OID.2.5.4.17")); // postal code
		// System.out.println(map.get("OID.2.5.4.45")); // uniqueIdentifier
		// System.out.println(map.get("OID.2.5.4.65")); // pseudonym
		try {
			tmgr = new TransactionManager("insertEsignRequestResponse");
			sql = " INSERT INTO onlineschema.vt_esign_request_log("
					+ " ver, sc, ts, txn, ekycidtype, ekycid, aspid, authmode, responsesigtype,"
					+ " responseurl, hashalgorithm, docinfo, signature, hash, appl_no,"
					+ " reg_no, state_cd, off_cd, purpose, op_dt, signatory)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?,?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";
			ps = tmgr.prepareStatement(sql);
			ps.setString(i++, reqDobj.getVer());
			ps.setString(i++, reqDobj.getSc());
			ps.setDate(i++, new java.sql.Date(convertStringToDate(reqDobj.getTs()).getTime()));
			ps.setString(i++, reqDobj.getTxn());
			ps.setString(i++, reqDobj.getEkycidtype());
			ps.setString(i++, reqDobj.getEkycid());
			ps.setString(i++, reqDobj.getAspid());
			ps.setString(i++, reqDobj.getAuthmode());
			ps.setString(i++, reqDobj.getResponsesigtype());
			ps.setString(i++, reqDobj.getResponseurl());
			ps.setString(i++, reqDobj.getHashalgorithm());
			ps.setString(i++, reqDobj.getDocinfo());
			ps.setString(i++, reqDobj.getSignature());
			ps.setString(i++, reqDobj.getHash());
			ps.setString(i++, reqDobj.getAppl_no());
			ps.setString(i++, reqDobj.getReg_no());
			ps.setString(i++, reqDobj.getState_cd());
			ps.setInt(i++, reqDobj.getOff_cd());
			ps.setString(i++, reqDobj.getPurpose());
			ps.setString(i++, reqDobj.getSignatory());
			j = ps.executeUpdate();
			if (j > 0) {
				j = 0;
				sql = "";
				ps.close();
				sql = " INSERT INTO onlineschema.vt_esign_response_log("
						+ " txn, status, ts, rescode, errcode, errmsg, userx509certificate,"
						+ " signatures, signature, appl_no, reg_no, state_cd, off_cd, purpose,op_dt, signatory)"
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";
				ps = tmgr.prepareStatement(sql);
				i = 1;
				ps.setString(i++, resDobj.getTxn());
				ps.setString(i++, resDobj.getStatus());
				ps.setDate(i++, new java.sql.Date(convertStringToDate(resDobj.getTs()).getTime()));
				ps.setString(i++, resDobj.getRescode());
				ps.setString(i++, resDobj.getErrcode());
				ps.setString(i++, resDobj.getErrmsg());
				ps.setString(i++, "");
				ps.setString(i++, resDobj.getSignatures());
				ps.setString(i++, resDobj.getSignature());
				ps.setString(i++, resDobj.getAppl_no());
				ps.setString(i++, resDobj.getReg_no());
				ps.setString(i++, resDobj.getState_cd());
				ps.setInt(i++, resDobj.getOff_cd());
				ps.setString(i++, resDobj.getPurpose());
				ps.setString(i++, resDobj.getSignatory());
				j = ps.executeUpdate();
			}
			if (j > 0 && esignEkycDobj != null) {
				j = 0;
				sql = "";
				ps.close();
				sql = " INSERT INTO onlineschema.vt_esign_ekyc_log("
						+ " txn, commonname, uniqueidentifier, pseudonym, localityname, stateorprovincename,"
						+ " streetaddress, postaladdress, postalcode, appl_no, reg_no, state_cd,"
						+ " off_cd, purpose, op_dt)"
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
				ps = tmgr.prepareStatement(sql);
				i = 1;
				ps.setString(i++, esignEkycDobj.getTxn());
				ps.setString(i++, esignEkycDobj.getCommonname());
				ps.setString(i++, esignEkycDobj.getUniqueidentifier());
				ps.setString(i++, esignEkycDobj.getPseudonym());
				ps.setString(i++, esignEkycDobj.getLocalityname());
				ps.setString(i++, esignEkycDobj.getStateorprovincename());
				ps.setString(i++, esignEkycDobj.getStreetaddress());
				ps.setString(i++, esignEkycDobj.getPostaladdress());
				ps.setString(i++, esignEkycDobj.getPostalcode());
				ps.setString(i++, esignEkycDobj.getAppl_no());
				ps.setString(i++, esignEkycDobj.getReg_no());
				ps.setString(i++, esignEkycDobj.getState_cd());
				ps.setInt(i++, esignEkycDobj.getOff_cd());
				ps.setString(i++, esignEkycDobj.getPurpose());
				j = ps.executeUpdate();
			}
			if (j > 0) {
				tmgr.commit();
			}
		} catch (Exception ex) {
			LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
		} finally {
			try {
				if (tmgr != null) {
					tmgr.release();
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}

			} catch (Exception ex) {
				LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
			}
		}
	}

	public static Date convertStringToDate(String dateString) {
		Date date = null;

		DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
		try {
			date = df.parse(dateString);

		} catch (Exception ex) {

		}
		return date;
	}

	public static String getCurrentDateDDMMYYYHHMMSS() {
		// get current date & time

		java.util.Calendar cal = java.util.Calendar.getInstance(TimeZone.getDefault());
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");

		/*
		 ** on some JDK, the default TimeZone is wrong we must set the TimeZone
		 * manually!!! sdf.setTimeZone(TimeZone.getTimeZone("EST"));
		 */
		sdf.setTimeZone(TimeZone.getDefault());
		return sdf.format(cal.getTime());
	}

	public static Map getMapDataCert(String data) {
		String strDatas[] = data.split(", ");
		Map<String, String> map = new HashMap<String, String>();
		for (String strData : strDatas) {
			String str[] = strData.split("=");
			map.put(str[0].trim(), str[1].trim());
		}
		return map;
	}

	public static void saveUpdateDmsUpdateDetail(DmsDocumentUpload dmsDocumentUpload, int index, String uploadedFrom,
			String fileName) {
		PreparedStatement ps = null;
		TransactionManager tmgr = null;
		int i = 0;
		try {
			tmgr = new TransactionManager("saveUpdateDmsUpdateDetail");
			String sql = "INSERT INTO onlineschema.dms_doc_uploaded(state_cd, off_cd, pur_cd, appl_no, uploaded_from, doc_cat_id, doc_cat_name, uploaded_by,uploaded_file_name, op_dt) "
					+ "	VALUES (?, ?, ?, ?, ?, ?, ?, ?,?, CURRENT_TIMESTAMP) ";
			ps = tmgr.prepareStatement(sql);
			ps.setString(1, dmsDocumentUpload.getDmsDocument().getState());
			ps.setInt(2, dmsDocumentUpload.getDmsRequest().getOfficeCd());
			ps.setString(3, dmsDocumentUpload.getDmsDocument().getPurposeCode());
			ps.setString(4, dmsDocumentUpload.getDmsDocument().getApplno());
			ps.setString(5, "F");
			ps.setString(6, dmsDocumentUpload.getDmsDocument().getNonUploadedList().get(index).getCatId());
			ps.setString(7, dmsDocumentUpload.getDmsDocument().getNonUploadedList().get(index).getCatName());
			ps.setString(8, dmsDocumentUpload.getDmsRequest().getOwnerName());
			ps.setString(9, fileName);
			i = ps.executeUpdate();
			tmgr.commit();
		} catch (SQLException ex) {
			LOGGER.error("uploaddocumentImpl.insertDmsDetail " + ex);

		} finally {
			try {
				if (tmgr != null) {
					tmgr.release();
				}
			} catch (Exception e) {
				LOGGER.error("uploaddocumentImpl.insertDmsDetail" + e);
			}
		}
	}
}
