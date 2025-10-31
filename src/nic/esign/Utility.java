/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.esign;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.xml.bind.JAXBContext;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Nidhi
 */
public class Utility {

    private String exceptionStr = "Utility";

    public XMLGregorianCalendar getTimeStamp() {
        Calendar calendar = GregorianCalendar.getInstance();
        XMLGregorianCalendar xmlGregorianCalendar = XMLGregorianCalendarImpl.createDateTime(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
        return xmlGregorianCalendar;
    }

    public String marshallObj(Class clazz, Object obj) throws Exception {
        try {
            StringWriter xml = new StringWriter();
            JAXBContext.newInstance(clazz).createMarshaller().marshal(obj, xml);
         
            return xml.toString();
        } catch (Exception e) {
            exceptionStr = exceptionStr = "|" + e.toString();
            throw new Exception(exceptionStr, e);
        }
    }
}
