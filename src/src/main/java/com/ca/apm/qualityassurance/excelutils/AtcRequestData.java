package com.ca.apm.qualityassurance.excelutils;

import java.util.List;

public class AtcRequestData {

	private String testcaseName;
	private String sourceER;
	private String attributeER;
	private String sourceName;
	private String attributeName;
	private List<String> sourceNames;
	private List<String> attributeNames;
	private long rangeSize;
	private long frequency;
	private String limit;
	private Boolean includeAggregateTimeSeries;
	private String Operator;
	private String nameAttr;
	private List<String> nameAttrValues;
	private List<String> verficationValues;
	private List<String> verifySource;
	private List<String> verifyAttribute;
	private String requestId;

	public String getTestcaseName() {
		return testcaseName;
	}

	public void setTestcaseName(String testcaseName) {
		this.testcaseName = testcaseName;
	}

	public String getSourceER() {
		return sourceER;
	}

	public void setSourceER(String sourceER) {
		this.sourceER = sourceER;
	}

	public String getAttributeER() {
		return attributeER;
	}

	public void setAttributeER(String attributeER) {
		this.attributeER = attributeER;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public long getRangeSize() {
		return rangeSize;
	}

	public void setRangeSize(long rangeSize) {
		this.rangeSize = rangeSize;
	}

	public long getFrequency() {
		return frequency;
	}

	public void setFrequency(long frequency) {
		this.frequency = frequency;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public Boolean getIncludeAggregateTimeSeries() {
		return includeAggregateTimeSeries;
	}

	public void setIncludeAggregateTimeSeries(Boolean includeAggregateTimeSeries) {
		this.includeAggregateTimeSeries = includeAggregateTimeSeries;
	}

	public List<String> getAttributeNames() {
		return attributeNames;
	}

	public void setAttributeNames(List<String> attributeNames) {
		this.attributeNames = attributeNames;
	}

	public List<String> getSourceNames() {
		return sourceNames;
	}

	public void setSourceNames(List<String> sourceNames) {
		this.sourceNames = sourceNames;
	}

	public String getOperator() {
		return Operator;
	}

	public void setOperator(String operator) {
		Operator = operator;
	}

	public String getNameAttr() {
		return nameAttr;
	}

	public void setNameAttr(String nameAttr) {
		this.nameAttr = nameAttr;
	}

	public List<String> getNameAttrValues() {
		return nameAttrValues;
	}

	public void setNameAttrValues(List<String> nameAttrValues) {
		this.nameAttrValues = nameAttrValues;
	}

	public List<String> getVerifySource() {
		return verifySource;
	}

	public void setVerifySource(List<String> verifySource) {
		this.verifySource = verifySource;
	}

	public List<String> getVerifyAttribute() {
		return verifyAttribute;
	}

	public void setVerifyAttribute(List<String> verifyAttribute) {
		this.verifyAttribute = verifyAttribute;
	}

	public List<String> getVerficationValues() {
		return verficationValues;
	}

	public void setVerficationValues(List<String> verficationValues) {
		this.verficationValues = verficationValues;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@Override
	public String toString() {
		return "ATCData [testcaseName=" + testcaseName + ", sourceER="
				+ sourceER + ", attributeER=" + attributeER + ", sourceName="
				+ sourceName + ", attributeName=" + attributeName
				+ ", sourceNames=" + sourceNames + ", attributeNames="
				+ attributeNames + ", rangeSize=" + rangeSize + ", frequency="
				+ frequency + ", limit=" + limit
				+ ", includeAggregateTimeSeries=" + includeAggregateTimeSeries
				+ ", Operator=" + Operator + ", nameAttr=" + nameAttr
				+ ", nameAttrValues=" + nameAttrValues + ", Verficationvalues="
				+ verficationValues + "]";
	}

}
