package nic.dms.common.utils;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public class JSFUtils {
	// filePath
	public static boolean isFilePathValid(String compValue) {
		if (compValue != null && !compValue.equalsIgnoreCase("")) {
			return compValue.matches("[a-zA-Z0-9/.:;,+&@#\\\\-_| \\\\[\\\\]()]*");
		}
		return false;
	}

	public static String getRelevantLangMsg(String proerty_key) {

		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

		if (locale != null) {
			ResourceBundle labels = ResourceBundle.getBundle("resources.Language", locale);
			if (labels != null) {

				return labels.getString(proerty_key);
			}
		}
		return proerty_key;
	}

	public static boolean isAlphaNumericWithDot(String compValue) {

		if (compValue != null && !compValue.equalsIgnoreCase("")) {
			return compValue.matches("[a-zA-Z0-9._-]*");
		}
		return false;
	}

	public static boolean isExtensionValid(String compValue) {

		if (compValue != null && !compValue.equalsIgnoreCase("")) {
			String[] content_type = { "application/pdf", "image/jpeg", "image/jpg", "image/png" };
			return Arrays.asList(content_type).contains(compValue);
		}
		return false;
	}

	public static boolean isExtensionValidforVideo(String compValue) {

		if (compValue != null && !compValue.equalsIgnoreCase("")) {
			String[] content_type = { "video/mp4", "video/x-flv", "video/x-mpegURL", "video/MP2T", "video/3gpp",
					"video/webm", "video/quicktime", "video/x-msvideo", "video/x-ms-wmv" };
			return Arrays.asList(content_type).contains(compValue);
		}
		return false;
	}
}
