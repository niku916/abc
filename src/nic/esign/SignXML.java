/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.esign;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
//import java.util.Base64;
import java.util.Collections;
import java.util.Enumeration;
import java.util.UUID;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import nic.dms.pojo.TmEsign;
import nic.org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
//import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author nuicsi
 */
public class SignXML implements Serializable {

	private static XMLSignatureFactory fac = null;
	private static final String JCE_PROVIDER = "BC";
	private static final String CERTIFICATE_TYPE = "X.509";
	Utility util = new Utility();
	private static final Logger LOGGER = Logger.getLogger(SignXML.class);

	public boolean validateEspResponseXml(String respXml) throws Exception {
		boolean isVerified = false;
		try {

			CertificateFactory certFactory = null;
			PublicKey publicKey = null;

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder builder = dbf.newDocumentBuilder();
			InputSource ins = new InputSource();
			ins.setCharacterStream(new StringReader(respXml));
			Document doc = builder.parse(ins);
			dbf.setValidating(true);
			dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
			dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);

			NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
			if (nl.getLength() == 0) {
				return isVerified;
			}

			// String encodedEspCert = (String) prop.get("espCert");
			InputStream is = SignXML.class.getClassLoader().getResourceAsStream("keystore.jks");

			certFactory = CertificateFactory.getInstance(CERTIFICATE_TYPE, JCE_PROVIDER);
			X509Certificate cert = (X509Certificate) certFactory.generateCertificate(is);
			publicKey = cert.getPublicKey();

			DOMValidateContext valContext = new DOMValidateContext(publicKey, nl.item(0));
			XMLSignatureFactory factory = XMLSignatureFactory.getInstance("DOM");
			XMLSignature signature = factory.unmarshalXMLSignature(valContext);

			boolean coreValidity = signature.validate(valContext);

			if (coreValidity) {
				isVerified = signature.getSignatureValue().validate(valContext);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return isVerified;

	}

	// public static void main(String[] args) {
	//
	// try {
	//
	// InputStream is = null;
	//
	// is = SignXML.class.getClassLoader().getResourceAsStream("keystore.jks");
	// KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
	// String password = "changeit";
	// keystore.load(is, password.toCharArray());
	// Enumeration enumeration = keystore.aliases();
	// while (enumeration.hasMoreElements()) {
	// String alias = (String) enumeration.nextElement();
	// Certificate certificate = keystore.getCertificate(alias);
	// PublicKey publicKey = keystore.getCertificate(alias).getPublicKey();
	// byte[] encodedCertKey = certificate.getEncoded();
	// byte[] encodedPublicKey = publicKey.getEncoded();
	// String b64PublicKey =
	// Base64.getMimeEncoder().encodeToString(encodedPublicKey);
	// String b64CertKey =
	// Base64.getMimeEncoder().encodeToString(encodedCertKey);
	// String publicKeyString = "-----BEGIN CERTIFICATE-----\n"
	// + b64PublicKey
	// + "\n-----END CERTIFICATE-----";
	//
	// String certKeyString = "-----BEGIN CERTIFICATE-----\n"
	// + b64CertKey
	// + "\n-----END CERTIFICATE-----";
	//
	//
	// }
	//
	// } catch (CertificateException | NoSuchAlgorithmException |
	// KeyStoreException | IOException e) {
	// LOGGER.error(e.getMessage());
	// }

	// try {
	//
	// InputStream is = null;
	//
	// is = SignXML.class.getClassLoader().getResourceAsStream("keystore.jks");
	// // new FileInputStream("/home/myuser/my-keystore/mykeystore.jks");
	// KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
	// String password = "changeit";
	// char[] passwd = password.toCharArray();
	// keystore.load(is, passwd);
	// String alias = "aspcertv2";
	// Key key = keystore.getKey(alias, passwd);
	// if (key instanceof PrivateKey) {
	// // Get certificate of public key
	// Certificate cert = keystore.getCertificate(alias);
	// // Get public key
	// PublicKey publicKey = cert.getPublicKey();
	//
	// String publicKeyString = new
	// String(Base64.encodeBase64(publicKey.getEncoded()));
	//
	//
	//
	//
	//
	//
	// String ttt1= signEspRequest("<?xml version=\"1.0\"
	// encoding=\"UTF-8\"?><Esign AuthMode=\"1\" aspId=\"CCFB-900\"
	// ekycId=\"845642122051\" ekycIdType=\"A\" ekycMode=\"U\" preVerified=\"n\"
	// responseSigType=\"pkcs7\" responseUrl=\"http://10.25.97.148:8084/eSign/\"
	// sc=\"Y\" ts=\"2018-04-12T11:58:53\"
	// txn=\"e05c3317-f878-497b-a262-7399818e5318\" ver=\"2.0\"><Docs><InputHash
	// docInfo=\"DOC\" hashAlgorithm=\"SHA256\"
	// id=\"1\">b2782d590ad56f02d0cb467955a1b013f58eeef5a01a2ad15cdca014326d746b</InputHash></Docs></Esign>");
	//
	//
	// }
	//
	// } catch (Exception e) {
	// LOGGER.error(e.getMessage());
	// }
	// }

	// I am unable to clk...open word / no
	public static String signEspRequest1(String xml, String keystore) throws Exception {
		// ESignUtil eSignUtil = new ESignUtil();
		InputStream is = null;
		try {

			fac = XMLSignatureFactory.getInstance("DOM");
			Reference ref = fac.newReference("", fac.newDigestMethod(DigestMethod.SHA1, null),
					Collections.singletonList(fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)),
					null, null);
			SignedInfo si = fac.newSignedInfo(
					fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
					fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));
			KeyStore ks = KeyStore.getInstance("JKS");

			is = SignXML.class.getClassLoader().getResourceAsStream(keystore);

			ks.load(is, "changeit".toCharArray());
			Enumeration<String> enumAlias = ks.aliases();
			String aliase = null;
			while (enumAlias.hasMoreElements()) {
				aliase = enumAlias.nextElement();

			}
			KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(aliase,
					new KeyStore.PasswordProtection("changeit".toCharArray()));
			X509Certificate cert = (X509Certificate) keyEntry.getCertificate();

			KeyInfoFactory kif = fac.getKeyInfoFactory();
			ArrayList x509Content = new ArrayList();
			x509Content.add(cert.getSubjectX500Principal().getName());
			x509Content.add(cert);
			X509Data xd = kif.newX509Data(x509Content);
			KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource ins = new InputSource();
			ins.setCharacterStream(new StringReader(xml));
			Document doc = db.parse(ins);
			dbf.setValidating(true);
			dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
			dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
			DOMSignContext dsc = new DOMSignContext(keyEntry.getPrivateKey(), doc.getDocumentElement());
			XMLSignature signature = fac.newXMLSignature(si, ki);
			signature.sign(dsc);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trans = tf.newTransformer();

			OutputStream os;

			// os = new FileOutputStream(System.getProperty("user.home") +
			// System.getProperty("file.separator") + "xmlOut.xml");
			os = new ByteArrayOutputStream();
			trans.transform(new DOMSource(doc), new StreamResult(os));
			String signedXML = convertDocumentToString(doc);

			// //////////////////////////////////////////////////////////////////////
			// CertificateFactory cf = CertificateFactory.getInstance("X.509");
			// List mylist = new ArrayList();
			// FileInputStream in = new FileInputStream(signedXML);
			// Certificate c = cf.generateCertificate(in);
			// mylist.add(c);
			//
			// CertPath cp = cf.generateCertPath(mylist);
			//
			// Certificate trust = cf.generateCertificate(in);
			// TrustAnchor anchor = new TrustAnchor((X509Certificate) trust,
			// null);
			// PKIXParameters params = new
			// PKIXParameters(Collections.singleton(anchor));
			// params.setRevocationEnabled(false);
			// CertPathValidator cpv = CertPathValidator.getInstance("PKIX");
			// PKIXCertPathValidatorResult result =
			// (PKIXCertPathValidatorResult) cpv.validate(cp, params);
			//
			// /////////////////////////////////////////////////////////////////////
			return signedXML;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getMessage());
			return null;
		} finally {

			if (is != null) {
				is.close();
			}
		}
	}

	public static String signEspRequest(String xml, String alias, TmEsign tmEsign) throws Exception {
		// ESignUtil eSignUtil = new ESignUtil();
		InputStream is = null;
		try {

			fac = XMLSignatureFactory.getInstance("DOM");
			Reference ref = fac.newReference("", fac.newDigestMethod(DigestMethod.SHA1, null),
					Collections.singletonList(fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)),
					null, null);
			SignedInfo si = fac.newSignedInfo(
					fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
					fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));
			KeyStore ks = KeyStore.getInstance("JKS");

			// is =
			// SignXML.class.getClassLoader().getResourceAsStream("keystore_cdac1.jks");
			// //keystore
			// is =
			// SignXML.class.getClassLoader().getResourceAsStream("clientcert.jks");
			is = SignXML.class.getClassLoader().getResourceAsStream(tmEsign.getKeystoreFile());
			ks.load(is, tmEsign.geteSignPass().toCharArray());
			Enumeration<String> enumAlias = ks.aliases();
			String aliase = null;
			while (enumAlias.hasMoreElements()) {
				String st = enumAlias.nextElement();
				if (st.equalsIgnoreCase(alias)) { // aspcertificate alias_ncode
					aliase = st;
				}
				aliase = st;

			}
			KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(aliase,
					new KeyStore.PasswordProtection(tmEsign.geteSignPass().toCharArray()));
			X509Certificate cert = (X509Certificate) keyEntry.getCertificate();

			KeyInfoFactory kif = fac.getKeyInfoFactory();
			ArrayList x509Content = new ArrayList();
			x509Content.add(cert.getSubjectX500Principal().getName());
			x509Content.add(cert);
			X509Data xd = kif.newX509Data(x509Content);
			KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource ins = new InputSource();
			ins.setCharacterStream(new StringReader(xml));
			Document doc = db.parse(ins);
			dbf.setValidating(true);
			dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
			dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
			DOMSignContext dsc = new DOMSignContext(keyEntry.getPrivateKey(), doc.getDocumentElement());
			XMLSignature signature = fac.newXMLSignature(si, ki);
			signature.sign(dsc);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trans = tf.newTransformer();

			OutputStream os;

			// os = new FileOutputStream(System.getProperty("user.home") +
			// System.getProperty("file.separator") + "xmlOut.xml");
			os = new ByteArrayOutputStream();
			trans.transform(new DOMSource(doc), new StreamResult(os));
			String signedXML = convertDocumentToString(doc);

			// //////////////////////////////////////////////////////////////////////
			// CertificateFactory cf = CertificateFactory.getInstance("X.509");
			// List mylist = new ArrayList();
			// FileInputStream in = new FileInputStream(signedXML);
			// Certificate c = cf.generateCertificate(in);
			// mylist.add(c);
			//
			// CertPath cp = cf.generateCertPath(mylist);
			//
			// Certificate trust = cf.generateCertificate(in);
			// TrustAnchor anchor = new TrustAnchor((X509Certificate) trust,
			// null);
			// PKIXParameters params = new
			// PKIXParameters(Collections.singleton(anchor));
			// params.setRevocationEnabled(false);
			// CertPathValidator cpv = CertPathValidator.getInstance("PKIX");
			// PKIXCertPathValidatorResult result =
			// (PKIXCertPathValidatorResult) cpv.validate(cp, params);
			//
			// /////////////////////////////////////////////////////////////////////
			return signedXML;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		} finally {

			if (is != null) {
				is.close();
			}
		}
	}

	private static String convertDocumentToString(Document doc) throws Exception {
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer;
			String output = null;
			transformer = tf.newTransformer();
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			output = writer.getBuffer().toString();
			return output;
		} catch (TransformerFactoryConfigurationError e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}

	// public static void main(String[] args) throws Exception {
	// createXmlTree("5555");
	// }
	public static String createXmlTree(String hash) throws Exception {
		String inputHashXml = null;
		try {

			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

			Document document = documentBuilder.newDocument();

			// root element
			// Element root = document.createElement("Docs");
			// document.appendChild(root);
			// employee element
			Element inputHash = document.createElement("InputHash");

			document.appendChild(inputHash);

			// set an attribute to staff element
			Attr attr = document.createAttribute("id");
			attr.setValue("1");
			inputHash.setAttributeNode(attr);

			Attr attr1 = document.createAttribute("hashAlgorithm");
			attr1.setValue("SHA256");

			inputHash.setAttributeNode(attr1);

			Attr attr2 = document.createAttribute("docInfo");
			attr2.setValue("DOC");

			inputHash.setAttributeNode(attr2);

			inputHash.appendChild(document.createTextNode(hash));

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

			DOMSource source = new DOMSource(document);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			transformer.transform(source, result);
			inputHashXml = writer.toString();

		} catch (TransformerFactoryConfigurationError e) {
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getMessage());

		}
		return inputHashXml;
	}

	public static String createEsignXmlTree() throws Exception {
		String inputHashXml = null;

		try {

			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

			Document document = documentBuilder.newDocument();

			// root element
			Element root = document.createElement("Esign");
			document.appendChild(root);

			Attr attr = document.createAttribute("ver");
			attr.setValue("2.0");
			root.setAttributeNode(attr);

			Attr attr1 = document.createAttribute("sc");
			attr1.setValue("y");
			root.setAttributeNode(attr1);

			// Attr attr2 = document.createAttribute("ts");
			// attr2.setValue(util.getTimeStamp());
			// root.setAttributeNode(attr2);
			Attr attr3 = document.createAttribute("txn");
			attr3.setValue(UUID.randomUUID().toString());
			root.setAttributeNode(attr3);

			Attr attr4 = document.createAttribute("ekycMode");
			attr4.setValue("U");
			root.setAttributeNode(attr4);

			Attr attr5 = document.createAttribute("ekycId");
			attr5.setValue("845642122051");
			root.setAttributeNode(attr5);

			Attr attr6 = document.createAttribute("ekycIdType");
			attr6.setValue("A");
			root.setAttributeNode(attr6);

			Attr attr7 = document.createAttribute("aspId");
			attr7.setValue("CCFB-900");
			root.setAttributeNode(attr7);

			Attr attr8 = document.createAttribute("AuthMode");
			attr8.setValue("1");
			root.setAttributeNode(attr8);

			Attr attr9 = document.createAttribute("responseSigType");
			attr9.setValue("1");
			root.setAttributeNode(attr9);

			Attr attr10 = document.createAttribute("preVerified");
			attr10.setValue("n");
			root.setAttributeNode(attr10);

			Attr attr11 = document.createAttribute("responseUrl");
			attr11.setValue("https://164.100.78.110/eSign/");
			root.setAttributeNode(attr11);

			String docXml = createXmlTree("9999999999");

			root.appendChild(document.createTextNode(docXml));
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

			DOMSource source = new DOMSource(document);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			transformer.transform(source, result);
			inputHashXml = writer.toString();

		} catch (TransformerFactoryConfigurationError e) {
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getMessage());

		}
		return inputHashXml;
	}

	public Document convertStringToDocument(String xmlStr) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
