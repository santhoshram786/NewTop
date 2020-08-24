package com.ca.apm.qualityassurance.utils;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ca.apm.commons.restapi.utils.RestAssuredUtils;
import com.ca.apm.qualityassurance.excelutils.MetadataNassData;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PostMetadatNassData extends Thread {

	private static Logger log = Logger.getLogger(PostMetadatNassData.class);
	private List<MetadataNassData> metadatList;
	private String baseUrl;
	private String metadataUrl;
	private String nassUrl;
	private String token;
	String metadata_requestBody = "";
	String nass_requestBody = "";
	String metricid = "";
	Response response = null;
	Response metadata_response = null;
	Response nass_response = null;
	List<String> m_ids;
	public static boolean shouldStopPosting = true;

	public PostMetadatNassData(List<MetadataNassData> metadatList,
			String baseUrl, String token) {
		log.info("In constructor" + metadatList.get(5));
		this.metadatList = metadatList;
		this.baseUrl = baseUrl;
		this.token = token;
	}

	@Override
	public void run() {

		long timePeriod = DataUtils.epochtime() - 15;

		while (shouldStopPosting) {
			timePeriod = insertData(timePeriod);
		}

	}

	public long insertData(long timePeriod) {
		Map<String, MetadataNassData> metricIds = getMetadtaMetricIds();

		timePeriod = insertNassData(metricIds, timePeriod + 15);
		DataUtils.pause(15);
		return timePeriod;
	}

	private long insertNassData(Map<String, MetadataNassData> metricIds,
			long timePeriod) {
		if (!baseUrl.endsWith("/")) {
			nassUrl = baseUrl + "/nass/metricValue/store";
		} else {
			nassUrl = baseUrl + "nass/metricValue/store";
		}
		log.info("nass url:" + nassUrl);
		JsonObject jsonObject = new JsonObject();
		JsonArray metricsArray = new JsonArray();
		jsonObject.add("values", metricsArray);
		for (Entry<String, MetadataNassData> emtricId : metricIds.entrySet())
			for (int i = 0; i < metricIds.size(); i++) {
				JsonArray valueArray = new JsonArray();
				valueArray.add(emtricId.getValue().getMetricId());
				valueArray.add(timePeriod);
				addToValueArray(valueArray, emtricId.getValue().getMin());
				addToValueArray(valueArray, emtricId.getValue().getMax());
				addToValueArray(valueArray, emtricId.getValue().getValue());
				addToValueArray(valueArray, emtricId.getValue().getCount());
				metricsArray.add(valueArray);

			}
		log.info("Nass Request:" + jsonObject.toString());
		nass_requestBody = jsonObject.toString();
		nass_response = validatePostResponseCode(nassUrl, nass_requestBody,
				200, token);
		log.info("nass response:" + nass_response.asString());
		return timePeriod;

	}

	private void addToValueArray(JsonArray valueArray, String min) {
		if (min == null)
			valueArray.add(min);
		else if (StringUtils.isNumeric(min)) {
			valueArray.add(Long.parseLong(min));
		} else if (NumberUtils.isParsable(min)) {
			valueArray.add(Double.parseDouble(min));
		} else {
			valueArray.add(min);
		}
	}

	public Map<String, MetadataNassData> getMetadtaMetricIds() {
		if (!baseUrl.endsWith("/")) {
			metadataUrl = baseUrl + "/metadata/registerMetric/";
		} else {
			metadataUrl = baseUrl + "metadata/registerMetric/";
		}
		log.info(metadataUrl);
		JsonObject jsonObject = new JsonObject();
		JsonArray metricsArray = new JsonArray();
		Map<String, MetadataNassData> fullListOfData = new HashMap<String, MetadataNassData>();
		JSONArray responseArray = new JSONArray();
		jsonObject.add("metrics", metricsArray);
		for (MetadataNassData metadataNassData : metadatList) {
			// log.info(metadataNassData);
			metricsArray.add(getMetricObject(metadataNassData));
		}
		log.info("Metadata Request:" + jsonObject.toString());
		metadata_requestBody = jsonObject.toString();
		metadata_response = RestAssuredUtils.validatePostResponseCode(
				metadataUrl, metadata_requestBody, 200, token);
		log.info("metadata response:" + metadata_response.asString());

		JSONObject object = new JSONObject(metadata_response.asString());
		responseArray = object.getJSONArray("metrics");

		for (int i = 0; i < responseArray.length(); ++i) {

			JSONObject jsn = responseArray.getJSONObject(i);
			MetadataNassData metadataNassData = getMetadatNassDataForSourceAndAttributeName(
					jsn.getString("sourceName"), jsn.getString("attributeName"));
			metadataNassData.setMetricId(jsn.getString("id"));
			fullListOfData.put(
					jsn.getString("sourceName")
							+ Constants.SOURCE_ATTRIBUTE_SEPERATOR
							+ jsn.getString("attributeName"), metadataNassData);
		}

		return fullListOfData;
	}

	private MetadataNassData getMetadatNassDataForSourceAndAttributeName(
			String soruce, String attribute) {
		for (MetadataNassData metadataNassData : metadatList) {
			if (metadataNassData.getSourceName().equals(soruce)
					&& metadataNassData.getAttributeName().endsWith(attribute)) {
				return metadataNassData;
			}
		}
		return null;
	}

	private JsonObject getMetricObject(MetadataNassData metadataNassData) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("type", metadataNassData.getMetricType());
		jsonObject.addProperty("sourceName", metadataNassData.getSourceName());
		jsonObject.addProperty("attributeName",
				metadataNassData.getAttributeName());
		if (metadataNassData.getMetricAttributes().size() > 0) {
			jsonObject.add("attributes",
					getAttributes(metadataNassData.getMetricAttributes()));

		}

		return jsonObject;
	}

	private JsonElement getAttributes(Map<String, List<String>> metricAttributes) {
		JsonObject jsonElement = new JsonObject();
		for (Entry<String, List<String>> attributeEntry : metricAttributes
				.entrySet()) {
			JsonArray valuesArray = new JsonArray();
			for (String value : attributeEntry.getValue()) {
				valuesArray.add(value);
			}
			jsonElement.add(attributeEntry.getKey(), valuesArray);
		}
		return jsonElement;
	}

	public Response validatePostResponseCode(String url, String requestBody,
			int responseCode, String token) {

		response = given().header("Authorization", "Bearer " + token)
				.contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(requestBody).relaxedHTTPSValidation().

				when().post(url);

		return response;
	}

}
