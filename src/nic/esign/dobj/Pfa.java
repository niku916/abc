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
@XmlType(name="Pfa")
public class Pfa  implements Serializable{
    private static final long serialVersionUID = 1L;
	@XmlAttribute(name="mv")
	protected int mv;
	@XmlAttribute(name="ms")
	protected String ms;
	@XmlAttribute(name="av")
	protected String av;
	@XmlAttribute(name="lmv")
	protected int lmv;
	@XmlAttribute(name="lav")
	protected String lav;

    public int getMv() {
        return mv;
    }

    public void setMv(int mv) {
        this.mv = mv;
    }

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }

    public String getAv() {
        return av;
    }

    public void setAv(String av) {
        this.av = av;
    }

    public int getLmv() {
        return lmv;
    }

    public void setLmv(int lmv) {
        this.lmv = lmv;
    }

    public String getLav() {
        return lav;
    }

    public void setLav(String lav) {
        this.lav = lav;
    }
        
        
}
