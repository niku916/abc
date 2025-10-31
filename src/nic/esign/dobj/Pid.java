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
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author nuicsi
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pid")
@XmlRootElement(name = "Pid")
public class Pid implements Serializable {
    private static final long serialVersionUID = 1L;
	@XmlAttribute(name = "ts")
	private XMLGregorianCalendar ts;
	@XmlAttribute(name = "ver")
	private String ver;
	@XmlAttribute(name = "xmlns")
	private String xmlns;
	@XmlElement(name = "Demo")
	private Demo demo;
	@XmlElement(name = "Bios")
	private Bios bios;
	@XmlElement(name = "Pv")
	private Pv pv;

    public XMLGregorianCalendar getTs() {
        return ts;
    }

    public void setTs(XMLGregorianCalendar ts) {
        this.ts = ts;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public Demo getDemo() {
        return demo;
    }

    public void setDemo(Demo demo) {
        this.demo = demo;
    }

    public Bios getBios() {
        return bios;
    }

    public void setBios(Bios bios) {
        this.bios = bios;
    }

    public Pv getPv() {
        return pv;
    }

    public void setPv(Pv pv) {
        this.pv = pv;
    }
        
        
    
}
