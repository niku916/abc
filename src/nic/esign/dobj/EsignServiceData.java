/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.esign.dobj;

import java.io.Serializable;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author nuicsi
 */
public class EsignServiceData implements Serializable {
    
    private static final long serialVersionUID = 1L;
	private String aspId;
	private String authTxn;
	private String esignTxn;
	private String udc;
	private String fdc;
	private String idc;
	private String pip;
	private String lov;
	private String tid;
	private String authVer;
	private String ci;
	private String esignClass;
	private String esignVer;
	private String preferredCa;
	private String requestIp;
	private String serviceIp;
	private String signedEsignXML;
	private String esignResponseXML;
	private byte[] sessionKey;
	private byte[] encryptedSessionKey;
	private XMLGregorianCalendar ts;

    public String getAspId() {
        return aspId;
    }

    public void setAspId(String aspId) {
        this.aspId = aspId;
    }

    public String getAuthTxn() {
        return authTxn;
    }

    public void setAuthTxn(String authTxn) {
        this.authTxn = authTxn;
    }

    public String getEsignTxn() {
        return esignTxn;
    }

    public void setEsignTxn(String esignTxn) {
        this.esignTxn = esignTxn;
    }

    public String getUdc() {
        return udc;
    }

    public void setUdc(String udc) {
        this.udc = udc;
    }

    public String getFdc() {
        return fdc;
    }

    public void setFdc(String fdc) {
        this.fdc = fdc;
    }

    public String getIdc() {
        return idc;
    }

    public void setIdc(String idc) {
        this.idc = idc;
    }

    public String getPip() {
        return pip;
    }

    public void setPip(String pip) {
        this.pip = pip;
    }

    public String getLov() {
        return lov;
    }

    public void setLov(String lov) {
        this.lov = lov;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getAuthVer() {
        return authVer;
    }

    public void setAuthVer(String authVer) {
        this.authVer = authVer;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getEsignClass() {
        return esignClass;
    }

    public void setEsignClass(String esignClass) {
        this.esignClass = esignClass;
    }

    public String getEsignVer() {
        return esignVer;
    }

    public void setEsignVer(String esignVer) {
        this.esignVer = esignVer;
    }

    public String getPreferredCa() {
        return preferredCa;
    }

    public void setPreferredCa(String preferredCa) {
        this.preferredCa = preferredCa;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getServiceIp() {
        return serviceIp;
    }

    public void setServiceIp(String serviceIp) {
        this.serviceIp = serviceIp;
    }

    public String getSignedEsignXML() {
        return signedEsignXML;
    }

    public void setSignedEsignXML(String signedEsignXML) {
        this.signedEsignXML = signedEsignXML;
    }

    public String getEsignResponseXML() {
        return esignResponseXML;
    }

    public void setEsignResponseXML(String esignResponseXML) {
        this.esignResponseXML = esignResponseXML;
    }

    public byte[] getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(byte[] sessionKey) {
        this.sessionKey = sessionKey;
    }

    public byte[] getEncryptedSessionKey() {
        return encryptedSessionKey;
    }

    public void setEncryptedSessionKey(byte[] encryptedSessionKey) {
        this.encryptedSessionKey = encryptedSessionKey;
    }

    public XMLGregorianCalendar getTs() {
        return ts;
    }

    public void setTs(XMLGregorianCalendar ts) {
        this.ts = ts;
    }
        
        
    
}
