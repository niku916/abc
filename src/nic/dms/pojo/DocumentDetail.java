package nic.dms.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.primefaces.component.fileupload.FileUpload;
import org.primefaces.model.UploadedFile;

public class DocumentDetail implements Comparable<DocumentDetail> {

	
	

	public UploadedFile getUploadedfile() {
		return uploadedfile;
	}

	public void setUploadedfile(UploadedFile uploadedfile) {
		this.uploadedfile = uploadedfile;
	}

	private UploadedFile uploadedfile;
	@JsonProperty("catId")
	private String catId;
	@JsonProperty("catName")
	private String catName;
	@JsonProperty("mandatory")
	private String mandatory;
	@JsonProperty("docUploaded")
	private Boolean docUploaded;
	@JsonProperty("fileName")
	private String fileName;
	@JsonProperty("uniqueRefNo")
	private String uniqueRefNo;
	@JsonProperty("docVerified")
	private Boolean docVerified;
	@JsonProperty("docApproved")
	private Boolean docApproved;
	@JsonProperty("docRecieved")
	private Boolean docRecieved;
	@JsonProperty("tempDocApproved")
	private Boolean tempDocApproved;
	@JsonProperty("subcategoryMasterData")
	private SubcategoryMasterData subcategoryMasterData;
	@JsonProperty("subcategoryMasterDataList")
	private List<SubcategoryMasterDataList> subcategoryMasterDataList;
	@JsonProperty("objectId")
	private String objectId;
	@JsonProperty("file")
	private Object file;
	@JsonProperty("apiFile")
	private Object apiFile;
	@JsonProperty("message")
	private String message;
	@JsonProperty("docUploadedDate")
	private String docUploadedDate;
	@JsonProperty("docUrl")
	private String docUrl;
	private DmsUpload dmsUpload = new DmsUpload();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	private Integer selectOrder = 0;

	@Override
	public int compareTo(DocumentDetail uploadedList) {
		int order = uploadedList.getSelectOrder();

		// desc order
		return order - this.selectOrder;
	}

	@JsonProperty("catId")
	public String getCatId() {
		return catId;
	}

	@JsonProperty("catId")
	public void setCatId(String catId) {
		this.catId = catId;
	}

	@JsonProperty("catName")
	public String getCatName() {
		return catName;
	}

	@JsonProperty("catName")
	public void setCatName(String catName) {
		this.catName = catName;
	}

	@JsonProperty("mandatory")
	public String getMandatory() {
		return mandatory;
	}

	@JsonProperty("mandatory")
	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}

	@JsonProperty("docUploaded")
	public Boolean getDocUploaded() {
		return docUploaded;
	}

	@JsonProperty("docUploaded")
	public void setDocUploaded(Boolean docUploaded) {
		this.docUploaded = docUploaded;
	}

	@JsonProperty("fileName")
	public String getFileName() {
		return fileName;
	}

	@JsonProperty("fileName")
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@JsonProperty("uniqueRefNo")
	public String getUniqueRefNo() {
		return uniqueRefNo;
	}

	@JsonProperty("uniqueRefNo")
	public void setUniqueRefNo(String uniqueRefNo) {
		this.uniqueRefNo = uniqueRefNo;
	}

	@JsonProperty("docVerified")
	public Boolean getDocVerified() {
		return docVerified;
	}

	@JsonProperty("docVerified")
	public void setDocVerified(Boolean docVerified) {
		this.docVerified = docVerified;
	}

	@JsonProperty("docApproved")
	public Boolean getDocApproved() {
		return docApproved;
	}

	@JsonProperty("docApproved")
	public void setDocApproved(Boolean docApproved) {
		this.docApproved = docApproved;
	}

	@JsonProperty("docRecieved")
	public Boolean getDocRecieved() {
		return docRecieved;
	}

	@JsonProperty("docRecieved")
	public void setDocRecieved(Boolean docRecieved) {
		this.docRecieved = docRecieved;
	}

	@JsonProperty("tempDocApproved")
	public Boolean getTempDocApproved() {
		return tempDocApproved;
	}

	@JsonProperty("tempDocApproved")
	public void setTempDocApproved(Boolean tempDocApproved) {
		this.tempDocApproved = tempDocApproved;
	}

	@JsonProperty("subcategoryMasterData")
	public SubcategoryMasterData getSubcategoryMasterData() {
		return subcategoryMasterData;
	}

	@JsonProperty("subcategoryMasterData")
	public void setSubcategoryMasterData(SubcategoryMasterData subcategoryMasterData) {
		this.subcategoryMasterData = subcategoryMasterData;
	}

	@JsonProperty("subcategoryMasterDataList")
	public List<SubcategoryMasterDataList> getSubcategoryMasterDataList() {
		return subcategoryMasterDataList;
	}

	@JsonProperty("subcategoryMasterDataList")
	public void setSubcategoryMasterDataList(List<SubcategoryMasterDataList> subcategoryMasterDataList) {
		this.subcategoryMasterDataList = subcategoryMasterDataList;
	}

	@JsonProperty("objectId")
	public String getObjectId() {
		return objectId;
	}

	@JsonProperty("objectId")
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	@JsonProperty("file")
	public Object getFile() {
		return file;
	}

	@JsonProperty("file")
	public void setFile(Object file) {
		this.file = file;
	}

	@JsonProperty("apiFile")
	public Object getApiFile() {
		return apiFile;
	}

	@JsonProperty("apiFile")
	public void setApiFile(Object apiFile) {
		this.apiFile = apiFile;
	}

	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage(String message) {
		this.message = message;
	}

	@JsonProperty("docUploadedDate")
	public String getDocUploadedDate() {
		return docUploadedDate;
	}

	@JsonProperty("docUploadedDate")
	public void setDocUploadedDate(String docUploadedDate) {
		this.docUploadedDate = docUploadedDate;
	}

	@JsonProperty("docUrl")
	public String getDocUrl() {
		return docUrl;
	}

	@JsonProperty("docUrl")
	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public DmsUpload getDmsUpload() {
		return dmsUpload;
	}

	public void setDmsUpload(DmsUpload dmsUpload) {
		this.dmsUpload = dmsUpload;
	}

	public Integer getSelectOrder() {
		return selectOrder;
	}

	public void setSelectOrder(Integer selectOrder) {
		this.selectOrder = selectOrder;
	}
}