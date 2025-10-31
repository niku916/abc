/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.esign.dobj;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author nuicsi
 */
@XmlRootElement(name = "Esign")
public class Esign implements Serializable {

    private static final long serialVersionUID = 1L;
    private String ver;
    private String sc;
    private XMLGregorianCalendar ts;
    private String txn;
    private String ekycMode;
    private String ekycId;
    private String ekycIdType;
    private String aspId;
    private String AuthMode;
    private String responseSigType;
    private String preVerified;
    private String responseUrl;
    //@XmlElement(name = "Docs")
    private Docs Docs;

  
    
    
//    @XmlElement(name = "OrgDetails")
//    private OrgDetails orgDetails;

    
    @XmlAttribute(name = "ver")
    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    @XmlAttribute(name = "sc")
    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    @XmlAttribute(name = "ts")
    public XMLGregorianCalendar getTs() {
        return ts;
    }

    public void setTs(XMLGregorianCalendar xmlGregorianCalendar) {
        this.ts = xmlGregorianCalendar;
    }

    @XmlAttribute(name = "txn")
    public String getTxn() {
        return txn;
    }

    public void setTxn(String txn) {
        this.txn = txn;
    }

    @XmlAttribute(name = "aspId")
    public String getAspId() {
        return aspId;
    }

    public void setAspId(String aspId) {
        this.aspId = aspId;
    }

    

    @XmlAttribute(name = "ekycMode")
    public String getEkycMode() {
        return ekycMode;
    }

    public void setEkycMode(String ekycMode) {
        this.ekycMode = ekycMode;
    }

    @XmlAttribute(name = "ekycId")
    public String getEkycId() {
        return ekycId;
    }

    public void setEkycId(String ekycId) {
        this.ekycId = ekycId;
    }

    @XmlAttribute(name = "ekycIdType")
    public String getEkycIdType() {
        return ekycIdType;
    }

    public void setEkycIdType(String ekycIdType) {
        this.ekycIdType = ekycIdType;
    }

    @XmlAttribute(name = "AuthMode")
    public String getAuthMode() {
        return AuthMode;
    }

    public void setAuthMode(String AuthMode) {
        this.AuthMode = AuthMode;
    }

    @XmlAttribute(name = "responseSigType")
    public String getResponseSigType() {
        return responseSigType;
    }

    public void setResponseSigType(String responseSigType) {
        this.responseSigType = responseSigType;
    }

    @XmlAttribute(name = "preVerified")
    public String getPreVerified() {
        return preVerified;
    }

    public void setPreVerified(String preVerified) {
        this.preVerified = preVerified;
    }

    @XmlAttribute(name = "responseUrl")
    public String getResponseUrl() {
        return responseUrl;
    }

    public void setResponseUrl(String responseUrl) {
        this.responseUrl = responseUrl;
    }

   

   

//    public OrgDetails getOrgDetails() {
//        return orgDetails;
//    }
//
//    public void setOrgDetails(OrgDetails orgDetails) {
//        this.orgDetails = orgDetails;
//    }

    public Docs getDocs() {
        return Docs;
    }

    public void setDocs(Docs Docs) {
        this.Docs = Docs;
    }

}
