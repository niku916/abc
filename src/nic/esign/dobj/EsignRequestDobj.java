package nic.esign.dobj;

import java.util.Date;

import com.sun.jmx.snmp.Timestamp;

public class EsignRequestDobj {

	String ver;
	String sc;
	String ts;
	String txn;
	String ekycidtype;
	String ekycid;
	String aspid;
	String authmode;
	String responsesigtype;
	String responseurl;
	String hashalgorithm;
	String docinfo;
	String signature;
	String hash;
	String appl_no;
	String reg_no;
	String state_cd;
	String purpose;
	String signatory;
	Integer off_cd;

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getSc() {
		return sc;
	}

	public void setSc(String sc) {
		this.sc = sc;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getTxn() {
		return txn;
	}

	public void setTxn(String txn) {
		this.txn = txn;
	}

	public String getEkycidtype() {
		return ekycidtype;
	}

	public void setEkycidtype(String ekycidtype) {
		this.ekycidtype = ekycidtype;
	}

	public String getEkycid() {
		return ekycid;
	}

	public void setEkycid(String ekycid) {
		this.ekycid = ekycid;
	}

	public String getAspid() {
		return aspid;
	}

	public void setAspid(String aspid) {
		this.aspid = aspid;
	}

	public String getAuthmode() {
		return authmode;
	}

	public void setAuthmode(String authmode) {
		this.authmode = authmode;
	}

	public String getResponsesigtype() {
		return responsesigtype;
	}

	public void setResponsesigtype(String responsesigtype) {
		this.responsesigtype = responsesigtype;
	}

	public String getResponseurl() {
		return responseurl;
	}

	public void setResponseurl(String responseurl) {
		this.responseurl = responseurl;
	}

	public String getHashalgorithm() {
		return hashalgorithm;
	}

	public void setHashalgorithm(String hashalgorithm) {
		this.hashalgorithm = hashalgorithm;
	}

	public String getDocinfo() {
		return docinfo;
	}

	public void setDocinfo(String docinfo) {
		this.docinfo = docinfo;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getAppl_no() {
		return appl_no;
	}

	public void setAppl_no(String appl_no) {
		this.appl_no = appl_no;
	}

	public String getReg_no() {
		return reg_no;
	}

	public void setReg_no(String reg_no) {
		this.reg_no = reg_no;
	}

	public String getState_cd() {
		return state_cd;
	}

	public void setState_cd(String state_cd) {
		this.state_cd = state_cd;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getSignatory() {
		return signatory;
	}

	public void setSignatory(String signatory) {
		this.signatory = signatory;
	}

	public Integer getOff_cd() {
		return off_cd;
	}

	public void setOff_cd(Integer off_cd) {
		this.off_cd = off_cd;
	}

}
