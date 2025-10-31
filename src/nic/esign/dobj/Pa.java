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
@XmlType(name="Pa")
public class Pa implements Serializable {
    
    private static final long serialVersionUID = 1L;
	@XmlAttribute(name="ms")
	protected String ms;
	@XmlAttribute(name="co")
	protected String co;
	@XmlAttribute(name="house")
	protected String house;
	@XmlAttribute(name="street")
	protected String street;
	@XmlAttribute(name="lm")
	protected String lm;
	@XmlAttribute(name="loc")
	protected String loc;
	@XmlAttribute(name="vtc")
	protected String vtc;
	@XmlAttribute(name="po")
	protected String po;
	@XmlAttribute(name="subdist")
	protected String subdist;
	@XmlAttribute(name="dist")
	protected String dist;
	@XmlAttribute(name="state")
	protected String state;
	@XmlAttribute(name="pc")
	protected String pc;

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLm() {
        return lm;
    }

    public void setLm(String lm) {
        this.lm = lm;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getVtc() {
        return vtc;
    }

    public void setVtc(String vtc) {
        this.vtc = vtc;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public String getSubdist() {
        return subdist;
    }

    public void setSubdist(String subdist) {
        this.subdist = subdist;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }
        
        
    
}
