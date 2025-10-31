/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.esign.dobj;

import java.util.Date;

/**
 *
 * @author nuicsi
 */
public class ESignResponseDobj {
	private String status;
	String txn;
	String ts;
	String rescode;
	String errcode;
	String errmsg;
	String userx509certificate;
	String signatures;
	String signature;
	String appl_no;
	String reg_no;
	String state_cd;
	String purpose;
	String signatory;
	Integer off_cd;

	public String getTxn() {
		return txn;
	}

	public void setTxn(String txn) {
		this.txn = txn;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getRescode() {
		return rescode;
	}

	public void setRescode(String rescode) {
		this.rescode = rescode;
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getUserx509certificate() {
		return userx509certificate;
	}

	public void setUserx509certificate(String userx509certificate) {
		this.userx509certificate = userx509certificate;
	}

	public String getSignatures() {
		return signatures;
	}

	public void setSignatures(String signatures) {
		this.signatures = signatures;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
