/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.esign.dobj;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author nuicsi
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Auth")
@XmlRootElement(name = "Auth")
public class Auth implements Serializable{
    @XmlAttribute(name = "uid")
	private String uid;
	@XmlAttribute(name = "tid")
	private String tid;
	@XmlAttribute(name = "ver")
	private String ver;
	@XmlAttribute(name = "txn")
	private String txn;
        @XmlAttribute(name = "ac")
	private String ac;
        @XmlAttribute(name = "sa")
	private String sa;
	@XmlAttribute(name = "xmlns")
	private String xmlns;
	@XmlElement(name = "Meta")
	private Meta meta;
	@XmlElement(name = "Skey")
	private Skey skey;
	@XmlElement(name = "Data")
	private Data data;
	@XmlElement(name = "Hmac")
	private byte[] hmac;

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public String getSa() {
        return sa;
    }

    public void setSa(String sa) {
        this.sa = sa;
    }

        
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getTxn() {
        return txn;
    }

    public void setTxn(String txn) {
        this.txn = txn;
    }

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Skey getSkey() {
        return skey;
    }

    public void setSkey(Skey skey) {
        this.skey = skey;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public byte[] getHmac() {
        return hmac;
    }

    public void setHmac(byte[] hmac) {
        this.hmac = hmac;
    }
        
        

}
