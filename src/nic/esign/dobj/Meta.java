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
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author nuicsi
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Meta")
public class Meta implements Serializable {
    private static final long serialVersionUID = 1L;
	@XmlAttribute(name="udc")
	private String udc;
	@XmlAttribute(name="fdc")
	private String fdc;
	@XmlAttribute(name="idc")
	private String idc;
	@XmlAttribute(name="pip")
	private String pip;
	@XmlAttribute(name="lot")
	private String lot;
	@XmlAttribute(name="lov")
	private String lov;

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

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getLov() {
        return lov;
    }

    public void setLov(String lov) {
        this.lov = lov;
    }
        
	
}
