package nic.dms.pojo;

import java.io.InputStream;

import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

public class DmsUpload {
	private UploadedFile file;
	private String fileName;
	private String selectedFileName;
	private String key;
	private String docUrl;
	private String signedDocUrl;
	private int index;
	private Integer selectOrder = 0;
	private Integer subCatId;
	private String docType;
	private DmsFlag dmsFlag;
	private InputStream inputStream;
	private String signBtn = "eSign";

	private Boolean fileSelect = false;
	private Boolean dispalyCol = true;
	private Boolean esigned = false;
	private Boolean dmsUploaded = false;

	private Boolean dmsUploadedAlready = false;
	private Boolean fileDoubleSign = false;
	private Boolean disableUploadBtn = false;
	private StreamedContent viewSignFile = null;
	private byte[] imageInBytes;

	public DmsFlag getDmsFlag() {
		if (dmsFlag == null) {
			dmsFlag = new DmsFlag();
		}
		return dmsFlag;
	}

	public void setDmsFlag(DmsFlag dmsFlag) {
		this.dmsFlag = dmsFlag;
	}

	private String signerName;

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDocUrl() {
		return docUrl;
	}

	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

	public String getSelectedFileName() {
		return selectedFileName;
	}

	public void setSelectedFileName(String selectedFileName) {
		this.selectedFileName = selectedFileName;
	}

	public String getSignedDocUrl() {
		return signedDocUrl;
	}

	public void setSignedDocUrl(String signedDocUrl) {
		this.signedDocUrl = signedDocUrl;
	}

	public String getSignerName() {
		return signerName;
	}

	public void setSignerName(String signerName) {
		this.signerName = signerName;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Integer getSelectOrder() {
		return selectOrder;
	}

	public void setSelectOrder(Integer selectOrder) {
		this.selectOrder = selectOrder;
	}

	public Integer getSubCatId() {
		return subCatId;
	}

	public void setSubCatId(Integer subCatId) {
		this.subCatId = subCatId;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getSignBtn() {
		return signBtn;
	}

	public void setSignBtn(String signBtn) {
		this.signBtn = signBtn;
	}

	public Boolean getFileSelect() {
		return fileSelect;
	}

	public void setFileSelect(Boolean fileSelect) {
		this.fileSelect = fileSelect;
	}

	public Boolean getDispalyCol() {
		return dispalyCol;
	}

	public void setDispalyCol(Boolean dispalyCol) {
		this.dispalyCol = dispalyCol;
	}

	public Boolean getEsigned() {
		return esigned;
	}

	public void setEsigned(Boolean esigned) {
		this.esigned = esigned;
	}

	public Boolean getDmsUploaded() {
		return dmsUploaded;
	}

	public void setDmsUploaded(Boolean dmsUploaded) {
		this.dmsUploaded = dmsUploaded;
	}

	public Boolean getDmsUploadedAlready() {
		return dmsUploadedAlready;
	}

	public void setDmsUploadedAlready(Boolean dmsUploadedAlready) {
		this.dmsUploadedAlready = dmsUploadedAlready;
	}

	public Boolean getFileDoubleSign() {
		return fileDoubleSign;
	}

	public void setFileDoubleSign(Boolean fileDoubleSign) {
		this.fileDoubleSign = fileDoubleSign;
	}

	public Boolean getDisableUploadBtn() {
		return disableUploadBtn;
	}

	public void setDisableUploadBtn(Boolean disableUploadBtn) {
		this.disableUploadBtn = disableUploadBtn;
	}

	public StreamedContent getViewSignFile() {
		return viewSignFile;
	}

	public void setViewSignFile(StreamedContent viewSignFile) {
		this.viewSignFile = viewSignFile;
	}

	public byte[] getImageInBytes() {
		return imageInBytes;
	}

	public void setImageInBytes(byte[] imageInBytes) {
		this.imageInBytes = imageInBytes;
	}

}
