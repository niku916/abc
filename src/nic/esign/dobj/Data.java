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
@XmlType(name="Data", propOrder={"value"})
public class Data implements Serializable {
    private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name="type")
	private String type;
	@XmlValue
	private byte[] value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }
        
    
}
