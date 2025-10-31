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
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author nuicsi
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Bio")
public class Bio implements Serializable  {
   private static final long serialVersionUID = 1L;
	@XmlValue
	private byte[] value;
	@XmlAttribute(name="type")
	private String type;
	@XmlAttribute(name="posh")
	private String posh; 

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPosh() {
        return posh;
    }

    public void setPosh(String posh) {
        this.posh = posh;
    }
        
        
}
