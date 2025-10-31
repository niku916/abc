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
@XmlType(name="Pi")
public class Pi  implements Serializable{
    private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "ms")
	private String ms;
	
	@XmlAttribute(name = "mv")
	private String mv;
	
	@XmlAttribute(name = "name")
	private String name;
	
	@XmlAttribute(name = "lname")
	private String lname;
	
	@XmlAttribute(name = "lmv")
	private String lmv;
	
	@XmlAttribute(name = "gender")
	private String gender;
	
	@XmlAttribute(name = "dob")
	private String dob;
	
	@XmlAttribute(name = "dobt")
	private String dobt;
	
	@XmlAttribute(name = "age")
	private String age;
	
	@XmlAttribute(name = "phone")
	private String phone;
	
	@XmlAttribute(name = "email")
	private String email;

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }

    public String getMv() {
        return mv;
    }

    public void setMv(String mv) {
        this.mv = mv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getLmv() {
        return lmv;
    }

    public void setLmv(String lmv) {
        this.lmv = lmv;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDobt() {
        return dobt;
    }

    public void setDobt(String dobt) {
        this.dobt = dobt;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
	
        
}
