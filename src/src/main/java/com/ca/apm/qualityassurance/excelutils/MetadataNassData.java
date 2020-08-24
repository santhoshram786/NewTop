package com.ca.apm.qualityassurance.excelutils;

import java.util.List;
import java.util.Map;

public class MetadataNassData {

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

	public String getMetricType() {
		return metricType;
	}

	public void setMetricType(String metricType) {
		this.metricType = metricType;
	}

	public Map<String, List<String>> getMetricAttributes() {
		return metricAttributes;
	}

	public void setMetricAttributes(Map<String, List<String>> metricAttributes) {
		this.metricAttributes = metricAttributes;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getMetricId() {
		return metricId;
	}

	public void setMetricId(String metricId) {
		this.metricId = metricId;
	}

	private String sourceName;
	private String attributeName;
	private String metricType;
	private Map<String, List<String>> metricAttributes;
	private String min;
	private String max;
	private String value;
	private String count;
	private String metricId;

	@Override
	public String toString() {
		return sourceName + "\t" + attributeName + "\t" + metricId;
	}

}
