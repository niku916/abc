/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.esign.dobj;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Nidhi
 */
@XmlRootElement(name = "OTP")
public class Otp implements Serializable {

	private static final long serialVersionUID = 1L;
	private String ASPID;
	private String ver;
	private XMLGregorianCalendar ts;
	private String txn;
	private String uid;
	//private String sc;

	/*public String getSc() {
		return sc;
	}

	@XmlAttribute(name = "sc")
	public void setSc(String sc) {
		this.sc = sc;
	}*/

	public String getASPID() {
		return ASPID;
	}

	@XmlAttribute(name = "aspId")
	public void setASPID(String aSPID) {
		ASPID = aSPID;
	}

	public String getVer() {
		return ver;
	}

	@XmlAttribute(name = "ver")
	public void setVer(String ver) {
		this.ver = ver;
	}

	public XMLGregorianCalendar getTs() {
		return ts;
	}

	@XmlAttribute(name = "ts")
	public void setTs(XMLGregorianCalendar ts) {
		this.ts = ts;
	}

	public String getTxn() {
		return txn;
	}

	@XmlAttribute(name = "txn")
	public void setTxn(String txn) {
		this.txn = txn;
	}

	public String getUid() {
		return uid;
	}

	@XmlAttribute(name = "uid")
	public void setUid(String uid) {
		this.uid = uid;
	}

}
