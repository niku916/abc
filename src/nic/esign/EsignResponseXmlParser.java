/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.esign;

import java.io.Serializable;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import nic.org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author nuicsi
 */
public class EsignResponseXmlParser implements Serializable {

    private static String exceptionStr = "EsignResponseXmlParser";
    private static final Logger LOGGER = Logger.getLogger(EsignResponseXmlParser.class);

    public static Element eSignXmlParser(String inputSource) throws Exception {

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = null;
            Document status = null;
            Element element = null;
            db = dbf.newDocumentBuilder();
            InputSource source = new InputSource();
            source.setCharacterStream(new StringReader(inputSource));
            status = db.parse(source);
            dbf.setValidating(true);
            dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
            dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
              
            NodeList nodes = status.getElementsByTagName("EsignResp");
            for (int i = 0; i < nodes.getLength(); i++) {
                element = (Element) nodes.item(i);
            }
            return element;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            exceptionStr = exceptionStr = "|" + e.toString();
            throw new Exception(exceptionStr, e);
        }
    }

    public static String getAadhaarResponse(String input)
            throws Exception,
            XPathExpressionException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            Document eSignResponseDoc = null;
            XPath xpath = null;
            StringBuilder aadhaarResp = new StringBuilder();
            builder = factory.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(input));
            eSignResponseDoc = builder.parse(is);
            factory.setValidating(true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            xpath = XPathFactory.newInstance().newXPath();
            String expression = "/EsignResp/AadhaarResp";
            aadhaarResp
                    .append(xpath.compile(expression).evaluate(eSignResponseDoc));

            return aadhaarResp.toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            exceptionStr = exceptionStr = "|" + e.toString();
            throw new Exception(exceptionStr, e);
        }
    }

    public static String getPKCS7(String input) throws Exception, XPathExpressionException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            Document eSignResponseDoc = null;
            XPath xpath = null;
            String pkcs7 = null;
            builder = factory.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(input));
            eSignResponseDoc = builder.parse(is);
            factory.setValidating(true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            xpath = XPathFactory.newInstance().newXPath();
            String expression = "/EsignResp/DocSignature";
            pkcs7 = (xpath.compile(expression).evaluate(eSignResponseDoc));
            return pkcs7;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            exceptionStr = exceptionStr = "|" + e.toString();
            throw new Exception(exceptionStr, e);
        }
    }

}
