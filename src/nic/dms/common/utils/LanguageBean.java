package nic.dms.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 * Locale Bean used for provide the language selection functionality and set the
 * locale in run time environment.
 * 
 * @author Vinay
 *
 */
@ManagedBean(name = "lanBean")
@SessionScoped
public class LanguageBean {
	
	private List<SelectItem> languageList=new ArrayList<>();
	


	private Locale locale = Locale.ENGLISH;
	String language = null;
	
	public LanguageBean() {
		languageList.add(new SelectItem("en","English"));
		languageList.add(new SelectItem("hi","Hindi"));    
	}

	public Locale getLocale() {
		return locale;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void onLanguageChange() {
		// locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		if (!getLanguage().equalsIgnoreCase("en")) {
			String l = getLanguage();
			setLanguage(l);
			locale = new Locale(l);
			FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
			setLanguage(l);
		} else {
			locale = new Locale("en");
			FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.ENGLISH);
		}
	}

	public List<SelectItem> getLanguageList() {
		return languageList;
	}

	public void setLanguageList(List<SelectItem> languageList) {
		this.languageList = languageList;
	}
	
	

}
