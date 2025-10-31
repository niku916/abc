package nic.dms.serveice;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.util.Base64;
//import org.apache.commons.codec.binary.Base64;

import nic.org.apache.log4j.Logger;
import nic.vahan.form.impl.dmsFileImpl;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.itextpdf.text.pdf.PdfSignatureAppearance;

import nic.dms.common.constant.CommonConstant;
import nic.esign.EsignResponseXmlParser;
import nic.esign.dobj.ESignResponseDobj;
import nic.esign.dobj.EsignRequestDobj;
import resources.ApplicationConfig;

@Path("/esignstatus")
public class EsignResponse {

	String result = null;
	@Context
	private HttpServletResponse response;
	@Context
	private HttpServletRequest request;
	private static final Logger LOGGER = Logger.getLogger(EsignResponse.class);

	@Context
	UriInfo uriInfo;

	@POST
	@Path("/checkESignStatus")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void ReadEspResponse(@FormParam("eSignResponse") String resp) {
		String rtn = null;
		String status = "-1";

		try {
			if (resp != null) {
				//System.out.println(resp);
				Element responseXmlElement;
				responseXmlElement = EsignResponseXmlParser.eSignXmlParser(resp);
				status = responseXmlElement.getAttribute("status");
				String errCode = responseXmlElement.getAttribute("errCode");
				String errMsg = responseXmlElement.getAttribute("errMsg");
				String txn = responseXmlElement.getAttribute("txn");
				String resCode = responseXmlElement.getAttribute("resCode");
				if (status.equalsIgnoreCase("1")) {
					NodeList doc = responseXmlElement.getElementsByTagName("DocSignature");
					String docSign = doc.item(0).getTextContent();
					NodeList certNd = responseXmlElement.getElementsByTagName("UserX509Certificate");
					String certStr = certNd.item(0).getTextContent();
					NodeList docSignVal = responseXmlElement.getElementsByTagName("SignatureValue");
					String docSignValue = docSignVal.item(0).getTextContent();
					rtn = CommonConstant.getContextPath() + "dms/ui/uploadDoc/uploadDocument.xhtml?status=" + status
							+ ",data=" + docSign + ",cert=" + certStr + ",trans=" + txn;
					LOGGER.info("E-sign rtn Url " + rtn);
					HttpSession session = request.getSession();
					EsignRequestDobj reqDobj = (EsignRequestDobj) session.getAttribute("EsignRequestDobj");
					LOGGER.info(" EsignRequestDobj " + reqDobj);
					if (reqDobj != null) {
						ESignResponseDobj resDobj = new ESignResponseDobj();
						resDobj.setTxn(txn);
						resDobj.setStatus(status);
						resDobj.setTs(dmsFileImpl.getCurrentDateDDMMYYYHHMMSS());
						resDobj.setRescode(resCode);
						resDobj.setErrcode(errCode);
						resDobj.setErrmsg(errMsg);
						resDobj.setUserx509certificate(certStr);
						resDobj.setSignature(docSignValue);
						resDobj.setAppl_no(reqDobj.getAppl_no());
						resDobj.setReg_no(reqDobj.getReg_no());
						resDobj.setState_cd(reqDobj.getState_cd());
						resDobj.setOff_cd(reqDobj.getOff_cd());
						resDobj.setPurpose(reqDobj.getPurpose());
						resDobj.setSignatory(reqDobj.getSignatory());
						dmsFileImpl.insertEsignRequestResponse(reqDobj, resDobj, certStr);
					}
				}
				if (status.equalsIgnoreCase("0")) {
					rtn = CommonConstant.getContextPath() + "dms/ui/uploadDoc/uploadDocument.xhtml?status=" + status;
				}
			} else {
				rtn = CommonConstant.getContextPath() + "dms/ui/uploadDoc/uploadDocument.xhtml?status=" + status;
			}
			LOGGER.info("E-sign Redirection Url " + rtn);
			response.sendRedirect(rtn);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());

		}
	}

	// public static String getBase64DecodedStringXMLRespone(String XMLString) {
	// String decryptedXML = null;
	// decryptedXML = new
	// String(org.apache.commons.codec.binary.Base64.decode(XMLString.getBytes()));
	// return decryptedXML;
	// }

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/checkESignS")
	public void checkESignKAStatus(String xmlString) throws Exception {

		// String t1 = test[1].replaceAll("%2B", "\\+");
		// String t2 = t1.replaceAll("%3D", "=");
		// byte[] bys = Base64.getDecoder().decode(t2);
		// String responestr = new String(bys);

		// Element responseXmlElement;
		// responseXmlElement =
		// EsignResponseXmlParser.eSignXmlParser(responestr);
		// System.out.println("responseXmlElement : " + responestr);
		//
		// String status = responseXmlElement.getAttribute("status");
		// System.out.println("status=======================" + status);
		//
		// String errCode = responseXmlElement.getAttribute("errCode");
		// String errMsg = responseXmlElement.getAttribute("errMsg");
		// String txn = responseXmlElement.getAttribute("txn");
		//
		// NodeList doc =
		// responseXmlElement.getElementsByTagName("DocSignature");
		//
		// if (status.equalsIgnoreCase("1")) {
		//
		// String docSign = doc.item(0).getTextContent();
		// System.out.println("doc= " + doc.item(0).getTextContent());
		//
		// // System.out.println("docSign "+docSign);
		// tt = CommonConstant.getContextPath() +
		// "dms/ui/uploadDoc/uploadDocument.xhtml?msg=" + docSign;
		// // tt = CommonConstant.getContextPath() +
		// // "dms/ui/uploadDoc/uploadDocument.xhtml?status=" + status +
		// ",data="
		// // + docSign + ",cert=" + certStr + ",trans=" + txn;
		//// + docSign + ",cert=" + certStr + ",trans=" + txn;
		// // tt =
		// //
		// "http://164.100.78.110/vahanservice/vahan/ui/eSign/form_upload_signedPdf.xhtml?msg="
		// // + docSign;
		// } else {
		//
		// tt =
		// "http://164.100.78.110/vahanservice/vahan/ui/statevalidation/homepage.xhtml";
		// //
		// "http://10.25.97.148:8089/vahanservice/vahan/ui/eSign/form_upload_signedPdf.xhtml";
		// }

		// response.sendRedirect(tt);

		String rtn = null;
		// String status = "-1";
		String tt = null;

		//System.out.println("********* ================================== " + xmlString);

		String[] test = xmlString.split("msg=");

		// request.getAttribute("transId");
		String t1 = test[1].replaceAll("%2B", "\\+");
		String t2 = t1.replaceAll("%3D", "=");
		byte[] bys = Base64.getDecoder().decode(t2);
		String responestr = new String(bys);
		//System.out.println(responestr);
		// String t1 = test[1].replaceAll("%2B", "\\+");
		// String t2 = t1.replaceAll("%3D", "=");
		// byte[] bys = Base64.decodeBase64(t2);

		// String responestr = new String(bys);

		Element responseXmlElement;
		responseXmlElement = EsignResponseXmlParser.eSignXmlParser(responestr);
		String status = responseXmlElement.getAttribute("status");
		String errCode = responseXmlElement.getAttribute("errCode");
		String errMsg = responseXmlElement.getAttribute("errMsg");
		String txn = responseXmlElement.getAttribute("txn");
		if (status.equalsIgnoreCase("1")) {
			NodeList doc = responseXmlElement.getElementsByTagName("DocSignature");
			String docSign = doc.item(0).getTextContent().trim();
			// LOGGER.info("doc signtaure......" + docSign);
			NodeList certNd = responseXmlElement.getElementsByTagName("UserX509Certificate");
			String certStr = certNd.item(0).getTextContent().trim();
			certStr.replaceAll("%20", "");

			rtn = CommonConstant.getContextPath() + "dms/ui/uploadDoc/uploadDocument.xhtml?status=" + status + ",data="
					+ docSign + ",cert=" + certStr + ",trans=" + txn;
		}
		if (status.equalsIgnoreCase("0")) {
			rtn = CommonConstant.getContextPath() + "dms/ui/uploadDoc/uploadDocument.xhtml?status=" + status.trim();
		}
		LOGGER.info(" created url " + rtn);
		rtn = rtn.replaceAll("%20", "").trim();
		LOGGER.info(" created url after %20 remove " + rtn);
		//System.out.println(rtn);
		response.sendRedirect(rtn.trim());

		// String status = responseXmlElement.getAttribute("status");
		// LOGGER.info("status=======================" + status);
		//
		// String errCode = responseXmlElement.getAttribute("errCode");
		// String errMsg = responseXmlElement.getAttribute("errMsg");
		// String txn = responseXmlElement.getAttribute("txn");
		//
		// NodeList doc =
		// responseXmlElement.getElementsByTagName("DocSignature");
		//
		// if (status.equalsIgnoreCase("1")) {
		//
		// String docSign = doc.item(0).getTextContent();
		// LOGGER.info("doc= " + doc.item(0).getTextContent().length());
		//
		// tt =
		// CommonConstant.getContextPath()+"dms/ui/uploadDoc/uploadDocument.xhtml?msg="+docSign;
		//
		// } else {
		//
		// tt =
		// CommonConstant.getContextPath()+"dms/ui/uploadDoc/uploadDocument.xhtml";
		// }
		// response.sendRedirect(tt);

	}

	// public void checkESignKAStatus(String xmlString) throws Exception {
	// String rtn = null;
	// String status = "-1";
	// String resp = xmlString.split("msg=")[1];
	// try{
	// if(resp != null){
	// Element responseXmlElement;
	// responseXmlElement = EsignResponseXmlParser.eSignXmlParser(resp);
	// status = responseXmlElement.getAttribute("status");
	// String errCode = responseXmlElement.getAttribute("errCode");
	// String errMsg = responseXmlElement.getAttribute("errMsg");
	// String txn = responseXmlElement.getAttribute("txn");
	// if (status.equalsIgnoreCase("1")) {
	// NodeList doc = responseXmlElement.getElementsByTagName("DocSignature");
	// String docSign = doc.item(0).getTextContent();
	//// LOGGER.info("doc signtaure......" + docSign);
	// NodeList certNd =
	// responseXmlElement.getElementsByTagName("UserX509Certificate");
	// String certStr = certNd.item(0).getTextContent();
	//
	// rtn=
	// CommonConstant.getContextPath()+"dms/ui/uploadDoc/uploadDocument.xhtml?status="
	// + status + ",data=" + docSign+ ",cert=" + certStr+ ",trans=" + txn;
	// }
	// if (status.equalsIgnoreCase("0")) {
	// rtn =
	// CommonConstant.getContextPath()+"dms/ui/uploadDoc/uploadDocument.xhtml?status="
	// + status;
	// }
	// }
	// else {
	// rtn =
	// CommonConstant.getContextPath()+"dms/ui/uploadDoc/uploadDocument.xhtml?status="
	// + status;
	// }
	// response.sendRedirect(rtn);
	// }catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	//
	// }

	@GET
	@Path("/pdf")
	@Produces("application/pdf")
	public Response getFile(@QueryParam(value = "filename") String fileName) {
		String path = CommonConstant.FILE_PATH;
		File file = new File(path + fileName);
		if (file.exists()) {
			ResponseBuilder response = Response.ok((Object) file);
			response.header("Content-Disposition", "inline; filename=" + fileName);
			return response.build();
		} else {
			return null;
		}
	}

	// public static void main(String args[]) throws Exception {
	// System.out.println("Hello");
	// String xmlString =
	// "msg=PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz48RXNpZ25SZXNwIGVyckNvZGU9Ik5BIiBlcnJNc2c9Ik5BIiByZXNDb2RlPSJFNzkxQjAyNjlFNjY0RkQ2OEZENkE3MTQ3RTczMDNFNSIgc3RhdHVzPSIxIiB0cz0iMjAyMi0wMS0yNFQxMzoxODo1NiIgdHhuPSJlNmM3NTQ3Ny05YzAwLTRmMmEtOTgzNi04OGY1MzA2NDdkOWUiPjxVc2VyWDUwOUNlcnRpZmljYXRlPk1JSUd0ekNDQlorZ0F3SUJBZ0lFYVVkc0l6QU5CZ2txaGtpRzl3MEJBUXNGQURDQ0FSRXhDekFKQmdOVkJBWVRBa2xPTVRFd0x3WURWUVFLRXloT1UwUk1JR1V0UjI5MlpYSnVZVzVqWlNCSmJtWnlZWE4wY25WamRIVnlaU0JNYVcxcGRHVmtNUjB3R3dZRFZRUUxFeFJEWlhKMGFXWjVhVzVuSUVGMWRHaHZjbWwwZVRFUE1BMEdBMVVFRVJNR05EQXdNREV6TVJRd0VnWURWUVFJRXd0TllXaGhjbUZ6YUhSeVlURTBNRElHQTFVRUNSTXJVMlZ1WVhCaGRHa2dRbUZ3WVhRZ1RXRnlaeXhNYjNkbGNpQlFZWEpsYkNCWFpYTjBMRTExYldKaGFURTBNRElHQTFVRU14TXJNWE4wSUVac2IyOXlMRlJwYldWeklGUnZkMlZ5TEV0aGJXRnNZU0JOYVd4c2N5QkRiMjF3YjNWdVpERWRNQnNHQTFVRUF4TVVUbE5FVENCbExVZHZkaUJEUVNBeU1ERTBMVEl3SGhjTk1qSXdNVEkwTURjME9EVTFXaGNOTWpJd01USTBNRGd4T0RVMFdqQ0I1akVWTUJNR0ExVUVBeE1NUTJoaGFYUm9jbUVnVkNCSE1SRXdEd1lEVlFRS0V3aFFaWEp6YjI1aGJERVNNQkFHQTFVRUNCTUpTMkZ5Ym1GMFlXdGhNUXN3Q1FZRFZRUUdFd0pKVGpFUE1BMEdBMVVFRVJNR05UWXdNRGt4TVNrd0p3WURWUVJCRXlBMk5qTTVZVE01TXpBMk16VTBZMlV6T1dVd1lqRmhOalV6TVRVNE9UZGlaVEZPTUV3R0ExVUVMaE5GTVRrNU0wWXhOakpFTWpZM1FrSTFNamMxTXpnNU5qZ3pSRFpHTmpVd1JUZzVNVU0xTkVWR05rUTVOVGd6TkVJeE1rVTRNVGc1UlRneU4wUXdRMEV3UVRRMVFqaERNUTB3Q3dZRFZRUU1Fd1F5TmpReE1Ga3dFd1lIS29aSXpqMENBUVlJS29aSXpqMERBUWNEUWdBRXltZjBUSVhucXR2eTJwT0VxckJWTzVjQXpNNnVuVnFtaDdWd3h0WkttaHFMNHRmSlFiRGs3V3dSYk8vRmEyODZyRU5iRkxIQmFtMFFFSDlldG1aUENhT0NBd2d3Z2dNRU1Ba0dBMVVkRXdRQ01BQXdIUVlEVlIwT0JCWUVGQ0M4WU1GNlhKU3VMSW5ldWVJWklzdWtMWkVjTUJNR0ExVWRJd1FNTUFxQUNFWW8wOFFRL2g3Q01BNEdBMVVkRHdFQi93UUVBd0lHd0RDQ0FYc0dBMVVkSHdTQ0FYSXdnZ0Z1TUVlZ1JhQkRoa0ZvZEhSd09pOHZaV2R2ZGkxdWMyUnNMbU52TG1sdUwyVnphV2R1TDNKbGNHOXphWFJ2Y25rdlkzSnNMMDVUUkV4bExVZHZka05CTWpBeE5DMHlMbU55YkRDQ0FTR2dnZ0Vkb0lJQkdhU0NBUlV3Z2dFUk1Rc3dDUVlEVlFRR0V3SkpUakV4TUM4R0ExVUVDaE1vVGxORVRDQmxMVWR2ZG1WeWJtRnVZMlVnU1c1bWNtRnpkSEoxWTNSMWNtVWdUR2x0YVhSbFpERWRNQnNHQTFVRUN4TVVRMlZ5ZEdsbWVXbHVaeUJCZFhSb2IzSnBkSGt4RHpBTkJnTlZCQkVUQmpRd01EQXhNekVVTUJJR0ExVUVDQk1MVFdGb1lYSmhjMmgwY21FeE5EQXlCZ05WQkFrVEsxTmxibUZ3WVhScElFSmhjR0YwSUUxaGNtY3NURzkzWlhJZ1VHRnlaV3dnVjJWemRDeE5kVzFpWVdreE5EQXlCZ05WQkRNVEt6RnpkQ0JHYkc5dmNpeFVhVzFsY3lCVWIzZGxjaXhMWVcxaGJHRWdUV2xzYkhNZ1EyOXRjRzkxYm1ReEhUQWJCZ05WQkFNVEZFNVRSRXdnWlMxSGIzWWdRMEVnTWpBeE5DMHlNRm9HQ0NzR0FRVUZCd0VCQkU0d1REQktCZ2dyQmdFRkJRY3dBb1krYUhSMGNITTZMeTlsWjI5MkxXNXpaR3d1WTI4dWFXNHZaWE5wWjI0dmNtVndiM05wZEc5eWVTOU9VMFJNWlMxSGIzWkRRVEl3TVRRdE1pNWpaWEl3Z2NBR0ExVWRJQVNCdURDQnRUQ0JzZ1lIWUlKa1pBSUVBVENCcGpCYUJnZ3JCZ0VGQlFjQ0FSWk9hSFIwY0hNNkx5OTNkM2N1WldkdmRpMXVjMlJzTG1OdkxtbHVMMlZ6YVdkdUwzSmxjRzl6YVhSdmNua3ZZM0J6TDA1VFJFeGxMVWR2ZGkxRFFTMURVRk10ZG1WeU5DNHdMakV1Y0dSbU1FZ0dDQ3NHQVFVRkJ3SUNNRHdhT2tGaFpHaGhZWEl0WlV0WlF5MVBWRkFnUTJ4aGMzTWdRMlZ5ZEdsbWFXTmhkR1VnYVhOemRXVmtJR0o1SUU1VFJFd2daUzFIYjNZZ1EwRXdGUVlEVlIwbEJBNHdEQVlLS3dZQkJBR0NOd29ERERBTkJna3Foa2lHOXcwQkFRc0ZBQU9DQVFFQVJTc1lKQ0ZkMkQxcmxYQ1dEc0hSRzFVVDgrTkpVbzFXc2ZmYTBPQnRsR2dQWG4xbk5hajFJcVN2WWVCTXJLVkR1Vnd3OEEwZ25FSjYwekRzN1JJaktBZUR3Uk12MlpKcXp3b1Uxcms5UXRBa3RsTTkzUmpDeW92cUNKMU1jU2IwNFJwVWIxSUxKUGtUaFRLRWhscmdEdVFVSVZiZ1JQNkJJalJyUStRckE2THJFMTJpdHcweGg4NHpZalNIMmZEU2xlckcrcFhEU3FhSDhFYytDTkJjQzBXWSthL1J3TzlKZzJ6Qmd6UkVQek82dlNGTlZFTW8vTm0zSFBGU3lzbE5JNFZHbFY1cVFvUUh1M0hxR1RsbHlBVDF2OEZlQ0xUbks4Z0l1cmxEVjJHMDFOci9HWExEVWZUYzNzVFZERWphM1VxUnJMSzk1d3hQdXJnTFAwYmNpUT09PC9Vc2VyWDUwOUNlcnRpZmljYXRlPjxTaWduYXR1cmVzPjxEb2NTaWduYXR1cmUgZXJyb3I9IiIgaWQ9IjEiIHNpZ0hhc2hBbGdvcml0aG09IlNIQTI1NiI+TUlBR0NTcUdTSWIzRFFFSEFxQ0FNSUFDQVFFeER6QU5CZ2xnaGtnQlpRTUVBZ0VGQURDQUJna3Foa2lHOXcwQkJ3RUFBS0NBTUlJR3R6Q0NCWitnQXdJQkFnSUVhVWRzSXpBTkJna3Foa2lHOXcwQkFRc0ZBRENDQVJFeEN6QUpCZ05WQkFZVEFrbE9NVEV3THdZRFZRUUtFeWhPVTBSTUlHVXRSMjkyWlhKdVlXNWpaU0JKYm1aeVlYTjBjblZqZEhWeVpTQk1hVzFwZEdWa01SMHdHd1lEVlFRTEV4UkRaWEowYVdaNWFXNW5JRUYxZEdodmNtbDBlVEVQTUEwR0ExVUVFUk1HTkRBd01ERXpNUlF3RWdZRFZRUUlFd3ROWVdoaGNtRnphSFJ5WVRFME1ESUdBMVVFQ1JNclUyVnVZWEJoZEdrZ1FtRndZWFFnVFdGeVp5eE1iM2RsY2lCUVlYSmxiQ0JYWlhOMExFMTFiV0poYVRFME1ESUdBMVVFTXhNck1YTjBJRVpzYjI5eUxGUnBiV1Z6SUZSdmQyVnlMRXRoYldGc1lTQk5hV3hzY3lCRGIyMXdiM1Z1WkRFZE1Cc0dBMVVFQXhNVVRsTkVUQ0JsTFVkdmRpQkRRU0F5TURFMExUSXdIaGNOTWpJd01USTBNRGMwT0RVMVdoY05Nakl3TVRJME1EZ3hPRFUwV2pDQjVqRVZNQk1HQTFVRUF4TU1RMmhoYVhSb2NtRWdWQ0JITVJFd0R3WURWUVFLRXdoUVpYSnpiMjVoYkRFU01CQUdBMVVFQ0JNSlMyRnlibUYwWVd0aE1Rc3dDUVlEVlFRR0V3SkpUakVQTUEwR0ExVUVFUk1HTlRZd01Ea3hNU2t3SndZRFZRUkJFeUEyTmpNNVlUTTVNekEyTXpVMFkyVXpPV1V3WWpGaE5qVXpNVFU0T1RkaVpURk9NRXdHQTFVRUxoTkZNVGs1TTBZeE5qSkVNalkzUWtJMU1qYzFNemc1TmpnelJEWkdOalV3UlRnNU1VTTFORVZHTmtRNU5UZ3pORUl4TWtVNE1UZzVSVGd5TjBRd1EwRXdRVFExUWpoRE1RMHdDd1lEVlFRTUV3UXlOalF4TUZrd0V3WUhLb1pJemowQ0FRWUlLb1pJemowREFRY0RRZ0FFeW1mMFRJWG5xdHZ5MnBPRXFyQlZPNWNBek02dW5WcW1oN1Z3eHRaS21ocUw0dGZKUWJEazdXd1JiTy9GYTI4NnJFTmJGTEhCYW0wUUVIOWV0bVpQQ2FPQ0F3Z3dnZ01FTUFrR0ExVWRFd1FDTUFBd0hRWURWUjBPQkJZRUZDQzhZTUY2WEpTdUxJbmV1ZUlaSXN1a0xaRWNNQk1HQTFVZEl3UU1NQXFBQ0VZbzA4UVEvaDdDTUE0R0ExVWREd0VCL3dRRUF3SUd3RENDQVhzR0ExVWRId1NDQVhJd2dnRnVNRWVnUmFCRGhrRm9kSFJ3T2k4dlpXZHZkaTF1YzJSc0xtTnZMbWx1TDJWemFXZHVMM0psY0c5emFYUnZjbmt2WTNKc0wwNVRSRXhsTFVkdmRrTkJNakF4TkMweUxtTnliRENDQVNHZ2dnRWRvSUlCR2FTQ0FSVXdnZ0VSTVFzd0NRWURWUVFHRXdKSlRqRXhNQzhHQTFVRUNoTW9UbE5FVENCbExVZHZkbVZ5Ym1GdVkyVWdTVzVtY21GemRISjFZM1IxY21VZ1RHbHRhWFJsWkRFZE1Cc0dBMVVFQ3hNVVEyVnlkR2xtZVdsdVp5QkJkWFJvYjNKcGRIa3hEekFOQmdOVkJCRVRCalF3TURBeE16RVVNQklHQTFVRUNCTUxUV0ZvWVhKaGMyaDBjbUV4TkRBeUJnTlZCQWtUSzFObGJtRndZWFJwSUVKaGNHRjBJRTFoY21jc1RHOTNaWElnVUdGeVpXd2dWMlZ6ZEN4TmRXMWlZV2t4TkRBeUJnTlZCRE1US3pGemRDQkdiRzl2Y2l4VWFXMWxjeUJVYjNkbGNpeExZVzFoYkdFZ1RXbHNiSE1nUTI5dGNHOTFibVF4SFRBYkJnTlZCQU1URkU1VFJFd2daUzFIYjNZZ1EwRWdNakF4TkMweU1Gb0dDQ3NHQVFVRkJ3RUJCRTR3VERCS0JnZ3JCZ0VGQlFjd0FvWSthSFIwY0hNNkx5OWxaMjkyTFc1elpHd3VZMjh1YVc0dlpYTnBaMjR2Y21Wd2IzTnBkRzl5ZVM5T1UwUk1aUzFIYjNaRFFUSXdNVFF0TWk1alpYSXdnY0FHQTFVZElBU0J1RENCdFRDQnNnWUhZSUprWkFJRUFUQ0JwakJhQmdnckJnRUZCUWNDQVJaT2FIUjBjSE02THk5M2QzY3VaV2R2ZGkxdWMyUnNMbU52TG1sdUwyVnphV2R1TDNKbGNHOXphWFJ2Y25rdlkzQnpMMDVUUkV4bExVZHZkaTFEUVMxRFVGTXRkbVZ5TkM0d0xqRXVjR1JtTUVnR0NDc0dBUVVGQndJQ01Ed2FPa0ZoWkdoaFlYSXRaVXRaUXkxUFZGQWdRMnhoYzNNZ1EyVnlkR2xtYVdOaGRHVWdhWE56ZFdWa0lHSjVJRTVUUkV3Z1pTMUhiM1lnUTBFd0ZRWURWUjBsQkE0d0RBWUtLd1lCQkFHQ053b0REREFOQmdrcWhraUc5dzBCQVFzRkFBT0NBUUVBUlNzWUpDRmQyRDFybFhDV0RzSFJHMVVUOCtOSlVvMVdzZmZhME9CdGxHZ1BYbjFuTmFqMUlxU3ZZZUJNcktWRHVWd3c4QTBnbkVKNjB6RHM3UklqS0FlRHdSTXYyWkpxendvVTFyazlRdEFrdGxNOTNSakN5b3ZxQ0oxTWNTYjA0UnBVYjFJTEpQa1RoVEtFaGxyZ0R1UVVJVmJnUlA2QklqUnJRK1FyQTZMckUxMml0dzB4aDg0ellqU0gyZkRTbGVyRytwWERTcWFIOEVjK0NOQmNDMFdZK2EvUndPOUpnMnpCZ3pSRVB6TzZ2U0ZOVkVNby9ObTNIUEZTeXNsTkk0VkdsVjVxUW9RSHUzSHFHVGxseUFUMXY4RmVDTFRuSzhnSXVybERWMkcwMU5yL0dYTERVZlRjM3NUVkRFamEzVXFSckxLOTV3eFB1cmdMUDBiY2lUQ0NCUG93Z2dQaW9BTUNBUUlDQWlmVE1BMEdDU3FHU0liM0RRRUJDd1VBTURveEN6QUpCZ05WQkFZVEFrbE9NUkl3RUFZRFZRUUtFd2xKYm1ScFlTQlFTMGt4RnpBVkJnTlZCQU1URGtORFFTQkpibVJwWVNBeU1ERTBNQjRYRFRFNU1EY3lOakV4TVRVd01sb1hEVEkwTURNd05UQTJNekF3TUZvd2dnRVJNUXN3Q1FZRFZRUUdFd0pKVGpFeE1DOEdBMVVFQ2hNb1RsTkVUQ0JsTFVkdmRtVnlibUZ1WTJVZ1NXNW1jbUZ6ZEhKMVkzUjFjbVVnVEdsdGFYUmxaREVkTUJzR0ExVUVDeE1VUTJWeWRHbG1lV2x1WnlCQmRYUm9iM0pwZEhreER6QU5CZ05WQkJFVEJqUXdNREF4TXpFVU1CSUdBMVVFQ0JNTFRXRm9ZWEpoYzJoMGNtRXhOREF5QmdOVkJBa1RLMU5sYm1Gd1lYUnBJRUpoY0dGMElFMWhjbWNzVEc5M1pYSWdVR0Z5Wld3Z1YyVnpkQ3hOZFcxaVlXa3hOREF5QmdOVkJETVRLekZ6ZENCR2JHOXZjaXhVYVcxbGN5QlViM2RsY2l4TFlXMWhiR0VnVFdsc2JITWdRMjl0Y0c5MWJtUXhIVEFiQmdOVkJBTVRGRTVUUkV3Z1pTMUhiM1lnUTBFZ01qQXhOQzB5TUlJQklqQU5CZ2txaGtpRzl3MEJBUUVGQUFPQ0FROEFNSUlCQ2dLQ0FRRUEwOXlrTWlmeXgxcXhPRE1JVzE1L0YvRTRsV3l2VVpseHU0MWZhanBBUE5DVXkxamtPM1VwK3Rrcnpua0RhdHYrRkZQSUVkV25ySWZtRzJyVFFjUmpEM2FXdm9wNzJPTlZrSFI0NnlRRTJRdkNlaEhGUFMzQklCcnhUSXRJMnJ3STNZVnV5UDdQeGh5ejF3RFNaSEh2cTBCK3RGUC9LbXArdUNVMmZTK01iVitsTmxXRG1LWVF6MGNrN2hTRFR5QlVxWlNsejdWZnVYcUo3WEJFMm1PMkdDb1dnT2syL2h2NEU3MlNXUlZUYkhEemF4WFpwUElpUWZkUitOb0s4M0hMck9lRVFYMjdrSmxnQkwybkw5Z2tkNjk3QzRnNmI1VG1mTjV6amIvS3BMTzRxQUpMVG1JNi9leTR5TnRkeVlGRm1LaHZwUzNRV0Y3djh4c1NSNUl1QlFJREFRQUJvNElCTHpDQ0FTc3dFd1lEVlIwakJBd3dDb0FJUXJqRnoyMnpWK0V3Z1lBR0NDc0dBUVVGQndFQkJIUXdjakFlQmdnckJnRUZCUWN3QVlZU2FIUjBjRG92TDI5amRuTXVaMjkyTG1sdU1GQUdDQ3NHQVFVRkJ6QUNoa1JvZEhSd09pOHZkM2QzTG1OallTNW5iM1l1YVc0dlkyTmhMM05wZEdWekwyUmxabUYxYkhRdlptbHNaWE12Wm1sc1pYTXZRME5CU1c1a2FXRXlNREUwTG1ObGNqQVNCZ05WSFNBRUN6QUpNQWNHQldDQ1pHUUNNRVlHQTFVZEh3US9NRDB3TzZBNW9EZUdOV2gwZEhBNkx5OWpZMkV1WjI5MkxtbHVMM0ozTDNKbGMyOTFjbU5sY3k5RFEwRkpibVJwWVRJd01UUk1ZWFJsYzNRdVkzSnNNQkVHQTFVZERnUUtCQWhHS05QRUVQNGV3akFPQmdOVkhROEJBZjhFQkFNQ0FRWXdFZ1lEVlIwVEFRSC9CQWd3QmdFQi93SUJBREFOQmdrcWhraUc5dzBCQVFzRkFBT0NBUUVBbng4am5ZSWtIVW14VktxbG1jMnBMZ3Vwb0dsR2pIaVFJWE42S0h5TUxhMnp2Um9NaG5oSk1GSURrK2QzRHZLZHA5VjN4d054Qk1HeDZFdklqRmV6UnZSQ0xzSkNPL1lzbVdjUkRnS0hZUnRndGZFWXpQWFFUTVB2Wm5vZlNEKysvdWNIRGJqWnlsOXArc1UwVU9KT2VManBDRXlWc1FjSHJVMWVSbXR1Ymg0dDI4c05EcVgzVVZIRFBDVkdmaHZhbVBySFJGUUYzUVlET3FlUlljeHpKdGFidDEvOGFvS2Y4WHZSb0NhUnpza211UkdBRjZMVkYwbDBVS3htL3Noait2ZXhQdDFQL29SbVNkMllyVjExUGlCbnJOeWxVSnlJWHF1bW9HZm1KZlowbjJOQ3JvdXlJMi9TaVZmUFlYblFwcnhzbnd3blNiUXdsZGdMNktpbUNBQUFNWUlDSWpDQ0FoNENBUUV3Z2dFYk1JSUJFVEVMTUFrR0ExVUVCaE1DU1U0eE1UQXZCZ05WQkFvVEtFNVRSRXdnWlMxSGIzWmxjbTVoYm1ObElFbHVabkpoYzNSeWRXTjBkWEpsSUV4cGJXbDBaV1F4SFRBYkJnTlZCQXNURkVObGNuUnBabmxwYm1jZ1FYVjBhRzl5YVhSNU1ROHdEUVlEVlFRUkV3WTBNREF3TVRNeEZEQVNCZ05WQkFnVEMwMWhhR0Z5WVhOb2RISmhNVFF3TWdZRFZRUUpFeXRUWlc1aGNHRjBhU0JDWVhCaGRDQk5ZWEpuTEV4dmQyVnlJRkJoY21Wc0lGZGxjM1FzVFhWdFltRnBNVFF3TWdZRFZRUXpFeXN4YzNRZ1JteHZiM0lzVkdsdFpYTWdWRzkzWlhJc1MyRnRZV3hoSUUxcGJHeHpJRU52YlhCdmRXNWtNUjB3R3dZRFZRUURFeFJPVTBSTUlHVXRSMjkySUVOQklESXdNVFF0TWdJRWFVZHNJekFOQmdsZ2hrZ0JaUU1FQWdFRkFLQ0JsVEFZQmdrcWhraUc5dzBCQ1FNeEN3WUpLb1pJaHZjTkFRY0JNQndHQ1NxR1NJYjNEUUVKQlRFUEZ3MHlNakF4TWpRd056UTROVFphTUNvR0NTcUdTSWIzRFFFSk5ERWRNQnN3RFFZSllJWklBV1VEQkFJQkJRQ2hDZ1lJS29aSXpqMEVBd0l3THdZSktvWklodmNOQVFrRU1TSUVJS1h4Q3VNSHdLTlB1WEczZUh6VUJ3NHhvN3dZU0lxS0xIbTJDKy8raGlCQU1Bb0dDQ3FHU000OUJBTUNCRWN3UlFJZ2VDdml2bXdKeXA5dFBiS3hFNnpmV2IrditiWkNpZFpJUEdPU2c4dTZTdXdDSVFDRXFqbzl2OGlYWUtraHlyQ0ZmdlBBT0xzdHJ0djdLZXlHSm5aYk45ZnIyd0FBQUFBQUFBPT08L0RvY1NpZ25hdHVyZT48L1NpZ25hdHVyZXM+PFNpZ25hdHVyZSB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnIyI+PFNpZ25lZEluZm8+PENhbm9uaWNhbGl6YXRpb25NZXRob2QgQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy9UUi8yMDAxL1JFQy14bWwtYzE0bi0yMDAxMDMxNSI+PC9DYW5vbmljYWxpemF0aW9uTWV0aG9kPjxTaWduYXR1cmVNZXRob2QgQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzA0L3htbGRzaWctbW9yZSNyc2Etc2hhMjU2Ij48L1NpZ25hdHVyZU1ldGhvZD48UmVmZXJlbmNlIFVSST0iIj48VHJhbnNmb3Jtcz48VHJhbnNmb3JtIEFsZ29yaXRobT0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnI2VudmVsb3BlZC1zaWduYXR1cmUiPjwvVHJhbnNmb3JtPjwvVHJhbnNmb3Jtcz48RGlnZXN0TWV0aG9kIEFsZ29yaXRobT0iaHR0cDovL3d3dy53My5vcmcvMjAwMS8wNC94bWxlbmMjc2hhMjU2Ij48L0RpZ2VzdE1ldGhvZD48RGlnZXN0VmFsdWU+YTdSL0lhZDd2QWVoVktGOUxwM3JJc1FiWmJiQWRjQlo0R3Yzd1B2eFd5OD08L0RpZ2VzdFZhbHVlPjwvUmVmZXJlbmNlPjwvU2lnbmVkSW5mbz48U2lnbmF0dXJlVmFsdWU+alJCTytwV1A2NnAyRXpUb3FXTlN4WmgxOHVOUG90MHlnV0F5RzdVcHc0NFg3T3lTWDkwcnc3N3h0Wk1leDVGOThncUdpa3UvRm9TNg0KSStHYVg1emJCWkRRMndiWlNCREl4WTlZQXRBRVFKODhXeHlmZlF0ZHFkYWhXV01LSzBKbFNUL256U3RtQnhNSmNmVlhUSWpweU9RVg0KY0kxSFZaNUNSTnBsQTJiZFZOUEJsV1lJTno4MEd2ZUMwcXIrRjFvVHhaWGlwUTNsQmdNcXdPc0Nqa09QTnZPNjJUNFNQWndrbDJVRQ0Kb0Y2dHpGMk9iblZnempmVnBvUG40YjhOYnBvRW1sR2I3S3c2T1k5RTAwSnpLbDZxbVUzbm1aTXVBVWt3WG96T013ZDhtMVNIcHN5Mw0KNHFwQ2hYM3JiLzRMVncyaFYvbVl3SlF4Zy9jSVpnbS9zSjA1aEE9PTwvU2lnbmF0dXJlVmFsdWU+PEtleUluZm8+PEtleVZhbHVlPjxSU0FLZXlWYWx1ZT48TW9kdWx1cz5tbEpWeFdEK05OM1E5UVkxTy9vdW45M1pCTGplOXExT0U5cFZqbHBrSmcvT3pMVndnYnNkNUFtMmVaeWx1c09JTllmeU4xYnFUYTVEDQo0NkFXSFVwS0piQ003em5NNlYrRnRURFdzakVveWZLcGVUM05kQVFyZjlwNjYxV1JEbmxDdjNtQ080TVRBdUNuZkcyTEtaWnRlZW9rDQpZQVFLS21EaGpZUnZSd0d1dWFaWHErdkhyd2svdHVubGFWeVV1dWpSd20xQ0k3OFFrNjlVa3ltUTY5VlQzS1VvV3B5OHNRNGJWd1pBDQpkU1h0Q245d2JvTWNSb1lVV0pRTm4yWS90Y1diaUgwU3RZWnhCZ25Cbk1PeHVjMGZyOElRYTQyWlRqUkRHemNUTnVMcGhnVEl5UndhDQpPVmRJczFpYVgxcTRZRi9vZ2ZsWmFuMmdoMStFOFJjQnhuckg2UT09PC9Nb2R1bHVzPjxFeHBvbmVudD5BUUFCPC9FeHBvbmVudD48L1JTQUtleVZhbHVlPjwvS2V5VmFsdWU+PFg1MDlEYXRhPjxYNTA5U3ViamVjdE5hbWU+Q049RFMgTlNETCBFIEdPVkVSTkFOQ0UgSU5GUkFTVFJVQ1RVUkUgTElNSVRFRCAyLE89TlNETCBFIEdPVkVSTkFOQ0UgSU5GUkFTVFJVQ1RVUkUgTElNSVRFRCxPVT1WSUNFIFBSRVNJREVOVCwyLjUuNC41PSMxMzQwMzI2NjY2MzczNjYzMzM2NjYyMzYzMTYxNjEzNjYxNjYzMjM2NjYzMzMwMzUzODM4NjQ2NDM0MzQzNzY1Mzc2MzYxNjQzNDM0MzYzMjMwNjIzNzM4NjYzMTY1Mzg2NDMxNjE2NTY0MzA2MzY2NjEzMjMwMzAzNjY1Mzk2MjYyMzYsMi41LjQuNjU9IzEzMjAzMzYyMzg2MjM2MzkzMTYyMzMzNjM4MzI2MjY1NjYzMzMwNjEzMzM0MzgzMTY2MzM2NDM4NjQ2MzY0MzczMTMxLFNUUkVFVD0xIEZMUiBUSU1FUyBUT1dFUiBLQU1BTEEgTUlMTFMgQ09NUFVORCBTRU5BUEFUSSBCQVBBVCBNQVJHLDIuNS40LjE3PSMxMzA2MzQzMDMwMzAzMTMwLDIuNS40LjIwPSMxMzQwMzczOTM1MzQzOTY2NjEzMDM3MzAzODYzMzI2MzM0NjYzMzMyMzI2NTY2MzA2MjM3NjEzNDM4MzYzMTMwNjE2NDYxMzgzMDYxMzc2NDYzNjYzMzMzNjUzOTM4Mzc2MzMwMzMzNTY0MzgzNjMzNjEzOTYyNjE2NDM5MzkzNDMwMzAsU1Q9TWFoYXJhc2h0cmEsQz1JTjwvWDUwOVN1YmplY3ROYW1lPjxYNTA5Q2VydGlmaWNhdGU+TUlJSGFqQ0NCbEtnQXdJQkFnSUdZRDdoR04zZU1BMEdDU3FHU0liM0RRRUJDd1VBTUlIZE1Rc3dDUVlEVlFRR0V3SkpUakVtTUNRRw0KQTFVRUNoTWRWbVZ5WVhONWN5QlVaV05vYm05c2IyZHBaWE1nVUhaMElFeDBaQzR4SFRBYkJnTlZCQXNURkVObGNuUnBabmxwYm1jZw0KUVhWMGFHOXlhWFI1TVE4d0RRWURWUVFSRXdZME1EQXdNalV4RkRBU0JnTlZCQWdUQzAxaGFHRnlZWE5vZEhKaE1SSXdFQVlEVlFRSg0KRXdsV0xsTXVJRTFoY21jeE1qQXdCZ05WQkRNVEtVOW1abWxqWlNCT2J5NGdNakVzSURKdVpDQkdiRzl2Y2l3Z1FtaGhkbTVoSUVKMQ0KYVd4a2FXNW5NUmd3RmdZRFZRUURFdzlXWlhKaGMzbHpJRU5CSURJd01UUXdIaGNOTWpFd05URTNNVFV5T1RBMVdoY05Nak13TlRFMw0KTVRVeU9UQTFXakNDQWI0eEN6QUpCZ05WQkFZVEFrbE9NUlF3RWdZRFZRUUlFd3ROWVdoaGNtRnphSFJ5WVRGSk1FY0dBMVVFRkJOQQ0KTnprMU5EbG1ZVEEzTURoak1tTTBaak15TW1WbU1HSTNZVFE0TmpFd1lXUmhPREJoTjJSalpqTXpaVGs0TjJNd016VmtPRFl6WVRsaQ0KWVdRNU9UUXdNREVQTUEwR0ExVUVFUk1HTkRBd01ERXdNVU13UVFZRFZRUUpFem94SUVaTVVpQlVTVTFGVXlCVVQxZEZVaUJMUVUxQg0KVEVFZ1RVbE1URk1nUTA5TlVGVk9SQ0JUUlU1QlVFRlVTU0JDUVZCQlZDQk5RVkpITVNrd0p3WURWUVJCRXlBellqaGlOamt4WWpNMg0KT0RKaVpXWXpNR0V6TkRneFpqTmtPR1JqWkRjeE1URkpNRWNHQTFVRUJSTkFNbVptTnpaak0yWmlOakZoWVRaaFpqSTJaak13TlRnNA0KWkdRME5EZGxOMk5oWkRRME5qSXdZamM0WmpGbE9HUXhZV1ZrTUdObVlUSXdNRFpsT1dKaU5qRVhNQlVHQTFVRUN4TU9Wa2xEUlNCUQ0KVWtWVFNVUkZUbFF4TVRBdkJnTlZCQW9US0U1VFJFd2dSU0JIVDFaRlVrNUJUa05GSUVsT1JsSkJVMVJTVlVOVVZWSkZJRXhKVFVsVQ0KUlVReE5qQTBCZ05WQkFNVExVUlRJRTVUUkV3Z1JTQkhUMVpGVWs1QlRrTkZJRWxPUmxKQlUxUlNWVU5VVlZKRklFeEpUVWxVUlVRZw0KTWpDQ0FTSXdEUVlKS29aSWh2Y05BUUVCQlFBRGdnRVBBRENDQVFvQ2dnRUJBSnBTVmNWZy9qVGQwUFVHTlR2NkxwL2QyUVM0M3ZhdA0KVGhQYVZZNWFaQ1lQenN5MWNJRzdIZVFKdG5tY3BickRpRFdIOGpkVzZrMnVRK09nRmgxS1NpV3dqTzg1ek9sZmhiVXcxckl4S01ueQ0KcVhrOXpYUUVLMy9hZXV0VmtRNTVRcjk1Z2p1REV3TGdwM3h0aXltV2JYbnFKR0FFQ2lwZzRZMkViMGNCcnJtbVY2dnJ4NjhKUDdicA0KNVdsY2xMcm8wY0p0UWlPL0VKT3ZWSk1wa092VlU5eWxLRnFjdkxFT0cxY0dRSFVsN1FwL2NHNkRIRWFHRkZpVURaOW1QN1hGbTRoOQ0KRXJXR2NRWUp3WnpEc2JuTkg2L0NFR3VObVU0MFF4czNFemJpNllZRXlNa2NHamxYU0xOWW1sOWF1R0JmNklINVdXcDlvSWRmaFBFWA0KQWNaNngra0NBd0VBQWFPQ0Frb3dnZ0pHTUVBR0ExVWRKUVE1TURjR0Npc0dBUVFCZ2pjVUFnSUdDQ3NHQVFVRkJ3TUVCZ29yQmdFRQ0KQVlJM0NnTU1CZ2txaGtpRzl5OEJBUVVHQ0NzR0FRVUZCd01DTUJNR0ExVWRJd1FNTUFxQUNFcUdxS05VZFl2Q01Ha0dDQ3NHQVFVRg0KQndFQkJGMHdXekFnQmdnckJnRUZCUWN3QVlZVWFIUjBjRG92TDI5amMzQXVkbk5wWjI0dWFXNHdOd1lJS3dZQkJRVUhNQUtHSzJoMA0KZEhCek9pOHZkM2QzTG5aemFXZHVMbWx1TDNKbGNHOXphWFJ2Y25rdmRuTnBaMjVqWVM1alpYSXdnZ0VKQmdOVkhTQUVnZ0VBTUlIOQ0KTUZFR0JtQ0NaR1FDQWpCSE1FVUdDQ3NHQVFVRkJ3SUNNRGthTjA5eVoyRnVhWHBoZEdsdmJtRnNJRU5zWVhOeklESWdRMlZ5ZEdsbQ0KYVdOaGRHVWdTWE56ZFdWa0lHSjVJRlpsY21GemVYTWdRMEV3TlFZR1lJSmtaQW9CTUNzd0tRWUlLd1lCQlFVSEFnSXdIUm9iUkc5ag0KZFcxbGJuUWdVMmxuYm1WeUlFTmxjblJwWm1sallYUmxNRG9HQm1DQ1pHUUtBekF3TUM0R0NDc0dBUVVGQndJQ01DSWFJRUYxZEdodg0KY21sNlpXUWdVMmxuYm1GMGIzSjVJRU5sY25ScFptbGpZWFJsTURVR0FEQXhNQzhHQ0NzR0FRVUZCd0lCRmlOb2RIUndjem92TDNkMw0KZHk1MmMybG5iaTVwYmk5eVpYQnZjMmwwYjNKNUwyTndjekFvQmdOVkhSOEVJVEFmTUIyZ0c2QVpoaGRvZEhSd2N6b3ZMMk5oTG5aeg0KYVdkdUxtbHVMMk55YkRBUkJnTlZIUTRFQ2dRSVQwS1duZmRiNEZFd0RnWURWUjBQQVFIL0JBUURBZ2JBTUIwR0ExVWRFUVFXTUJTQg0KRW1GdWEzVnphR1JBYm5Oa2JDNWpieTVwYmpBSkJnTlZIUk1FQWpBQU1BMEdDU3FHU0liM0RRRUJDd1VBQTRJQkFRQjUxWmJUZ0FaSQ0KTFB1alVFdCtXczNHWTRSY0dxVWpxaFA5dHp4clBWdy9VRE9UeDBWN09hdEJEMkpBMVEycVpSZDFReElReWxId3F5aVNWT0pRdjRNaQ0KUEFPN0JoYXNTWCtPc0Yydkt4YVljaUhMamdzUmdIMnNiTGk2VDVTb3pjdUJ4Zi95UVZYWlJ4Skl1UjdzbFJHTXpCR2drYzE1elJmeQ0KRHMxY3d0MEIya3kza3JLT24zWjFZbFRJVUt0elJqRVVGUUxHU0Y3Yk1lbFEvenYrWHpQMUU2enRzTDhIcUNwbTh0anZBd1VrRTMzQQ0KZ1Urc3NmMFR2MFp2dHRnL3lYb1BNOFF2bG1KSUVRejV2S09DNVNzYlVUeEZ0Nmd1Q0V4MnJrZU1nNm54M1dNWkpVdjlMaFFXaSs2TQ0KOXBtcStyNW9INHFkeXkyYUVrb3p0aTBoTlNXYjwvWDUwOUNlcnRpZmljYXRlPjwvWDUwOURhdGE+PC9LZXlJbmZvPjwvU2lnbmF0dXJlPjwvRXNpZ25SZXNwPg==";
	// String[] test = xmlString.split("msg=");
	//
	// // request.getAttribute("transId");
	//
	// String t1 = test[1].replaceAll("%2B", "\\+");
	// String t2 = t1.replaceAll("%3D", "=");
	// byte[] bys = Base64.decodeBase64(t2);
	// String responestr = new String(bys);
	//
	// Element responseXmlElement;
	// responseXmlElement = EsignResponseXmlParser.eSignXmlParser(responestr);
	// System.out.println(responseXmlElement);
	// String status = responseXmlElement.getAttribute("status");
	// String errCode = responseXmlElement.getAttribute("errCode");
	// String errMsg = responseXmlElement.getAttribute("errMsg");
	// String txn = responseXmlElement.getAttribute("txn");
	// String rtn = null;
	// if (status.equalsIgnoreCase("1")) {
	// NodeList doc = responseXmlElement.getElementsByTagName("DocSignature");
	// String docSign = doc.item(0).getTextContent();
	//// LOGGER.info("doc signtaure......" + docSign);
	// NodeList certNd =
	// responseXmlElement.getElementsByTagName("UserX509Certificate");
	// String certStr = certNd.item(0).getTextContent();
	//
	// rtn = CommonConstant.getContextPath() +
	// "dms/ui/uploadDoc/uploadDocument.xhtml?status=" + status + ",data="
	// + docSign + ",cert=" + certStr + ",trans=" + txn;
	// System.out.println(rtn);
	// }
	// }

}
