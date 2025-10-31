/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.vahansms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import nic.org.apache.log4j.Logger;

/**
 *
 * @author vinay
 */
public class VahanSMS {

	private static final Logger LOGGER = Logger.getLogger(VahanSMS.class);
	static BufferedReader br = null;
//
//	private static final String SMS_URL = "http://10.246.40.176:7081/SMS/proactivesmsNew?encdata=";
//	private static final String OTP_URL = "http://10.246.40.176:7080/OTP/proactivesmsNew?encdata=";

	public VahanSMS() {
	}

	public static String vahanEncryption(String strVal) {
		StringBuffer encVal = new StringBuffer();
		char ch[] = strVal.toCharArray();
		for (char c : ch) {
			encVal.append(Integer.toHexString((byte) c));
		}
		return encVal.toString();
	}

	public void sendVahanSms(String strMobileNo, String strMessage, String stateCD, String templeteId) {
		int respcd;
		try {
			String readLine = "";
			strMobileNo = strMobileNo.replaceAll("[^0-9]", "");
			if (!strMobileNo.equalsIgnoreCase("")) {
				Boolean blnSendSms = Long.parseLong(strMobileNo) > 999999999 ? true : false;
				if (blnSendSms) {
					strMessage = strMessage.replaceAll(" ", "%20");
					String smsString = "mobileno=" + strMobileNo + "|" + "message=" + strMessage + "|" + "prjname="
							+ "VAHANSERVICES" + "|" + "statecd=" + stateCD + "|" + "tmpid=" + templeteId;
					smsString = vahanEncryption(smsString);
					HttpClient client = new HttpClient();
					PostMethod method = null;
					client.getParams().setParameter("http.useragent", "Test Client");
					method = new PostMethod("http://10.248.93.34/SMS/proactivesmsNew");
					method.addParameter("encdata", smsString);
					method.addParameter("return_url", "XXXX");
					respcd = client.executeMethod(method);
					if (respcd == HttpStatus.SC_NOT_IMPLEMENTED) {
						method.getResponseBodyAsString();
					} else {
						br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
						String temp;
						while (((temp = br.readLine()) != null)) {
							readLine = readLine + temp;
						}
					}

				}
			}

		} catch (MalformedURLException e) {
		} catch (IOException e) {

		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ex) {
				}
			}
		}
	}

	public void sendOTPSms(String strMobileNo, String strMessage, String stateCD, String templeteId) {
		int respcd;
		try {
			strMobileNo = strMobileNo.replaceAll("[^0-9]", "");
			if (!strMobileNo.equalsIgnoreCase("")) {
				Boolean blnSendSms = Long.parseLong(strMobileNo) > 999999999 ? true : false;
				if (blnSendSms) {
					strMessage = strMessage.replaceAll(" ", "%20");
					String smsString = "mobileno=" + strMobileNo + "|" + "message=" + strMessage + "|" + "prjname="
							+ "VAHANSERVICES" + "|" + "statecd=" + stateCD + "|" + "tmpid=" + templeteId;
					// String smsString = "mobileno=" + strMobileNo + "|" +
					// "message=" + strMessage+ "|" + "prjname=" +
					// "VAHANSERVICES" ;
					smsString = vahanEncryption(smsString);
					// URL httpUrl = new
					// URL("http://10.248.93.4:7080/OTP/proactivesms?encdata=" +
					// smsString);
					URL httpUrl = new URL("http://10.248.93.34/OTP/proactivesmsNew?encdata=" + smsString);
					URLConnection con = httpUrl.openConnection();
					con.connect();
					HttpURLConnection httpConnection = (HttpURLConnection) con;
					httpConnection.setConnectTimeout(5000);

					respcd = httpConnection.getResponseCode();
				}
			}
		} catch (Exception ex) {
			 
		}
	}
}
