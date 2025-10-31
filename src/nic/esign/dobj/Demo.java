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
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author nuicsi
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Demo")
public class Demo implements Serializable {
   
	private static final long serialVersionUID = 1L;
	@XmlAttribute(name="lang")
	private String lang;
	@XmlElement(name = "Pi")
	private Pi pi;
	@XmlElement(name = "Pa")
	private Pa pa;
	@XmlElement(name = "Pfa")
	private Pfa pfa; 

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Pi getPi() {
        return pi;
    }

    public void setPi(Pi pi) {
        this.pi = pi;
    }

    public Pa getPa() {
        return pa;
    }

    public void setPa(Pa pa) {
        this.pa = pa;
    }

    public Pfa getPfa() {
        return pfa;
    }

    public void setPfa(Pfa pfa) {
        this.pfa = pfa;
    }
        
}
