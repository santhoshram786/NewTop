package com.ca.apm.tests.test;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ca.apm.commons.restapi.utils.RestAssuredUtils;
import com.ca.apm.qualityassurance.excelutils.AtcRequestData;
import com.ca.apm.qualityassurance.excelutils.ExcelReadDataProvider;
import com.ca.apm.qualityassurance.excelutils.MetadataNassData;
import com.ca.apm.qualityassurance.excelutils.ReadMetadataNass;
import com.ca.apm.qualityassurance.utils.ATCRequestBodyCommon;
import com.ca.apm.qualityassurance.utils.Constants;
import com.ca.apm.qualityassurance.utils.DataUtils;
import com.ca.apm.qualityassurance.utils.PostMetadatNassData;
import com.ca.apm.rest.common.GenerateTokenAPI;
import com.ca.apm.tests.utils.Reader;

public class TopkTest {
	private static Logger log = Logger.getLogger(TopkTest.class);
	private String atc_url;
	private String atc_requestBody;
	private Map<String, MetadataNassData> actual_metric_ids;
	Response atc_response = null;
	Response response = null;
	long ep = DataUtils.epochtime();
	Map<Integer, List<String>> actualAtcValidation;
	Map<Integer, List<String>> finalAtcValidation;
	Map<String, String> keyvalueSourceAttribute;
	List<String> actualList;
	List<String> metanass;
	List<String> listids;
	Map<String, List> aggValues;
	Map<String, MetadataNassData> actualAggValues;
	Reader reader = new Reader();

	@BeforeClass
	public void setUpData() throws IOException {

		// Jenkins

		// mvn test
		// -DbaseUrl="https://apmservices-gateway-ao-apm.app.gdue4.saasdev.broadcom.com";

		String baseUrl = System.getProperty("baseUrl", reader.baseUrl);

		log.info("GCP Dev base Url:" + baseUrl);
		// Token generator
		GenerateTokenAPI atoken = GenerateTokenAPI.getInstance("automation",
				"interOP@123", reader.baseUrl);
		log.info("Token:" + atoken.token);

		ReadMetadataNass readMetadataNass = new ReadMetadataNass();
		PostMetadatNassData postMetadatNassData = new PostMetadatNassData(
				readMetadataNass.getdataListFromExcel(), reader.baseUrl,
				reader.token);
		actual_metric_ids = postMetadatNassData.getMetadtaMetricIds();

		if (!baseUrl.endsWith("/")) {
			atc_url = baseUrl + "/atc/metric/queryMetric";
		} else {
			atc_url = baseUrl + "atc/metric/queryMetric";
		}

		long timePeriod = DataUtils.epochtime() - 15;
		for (int i = 0; i < 32; i++) {
			timePeriod = postMetadatNassData.insertData(timePeriod);
		}
		postMetadatNassData.start();
	}

	@Test(dataProvider = "data-source", enabled = true)
	public void test_01(final AtcRequestData atcRequestData) {
		log.info("1");
		atc_requestBody = getRequestBody(atcRequestData);
		log.info("atc request body1:" + atc_requestBody);

		atc_response = RestAssuredUtils.validatePostResponseCode(atc_url,
				atc_requestBody, 200, reader.token);

		log.info("atc response:" + atc_response.asString());
		verifyResponseData("TOPK", actual_metric_ids, atc_response,
				atcRequestData.getVerficationValues());

	}

	private void verifyResponseData(String string,
			Map<String, MetadataNassData> actual_metric_ids2,
			Response atc_response2, List<String> verficationValues) {

	}

	@DataProvider(name = "data-source")
	public Iterator<Object[]> dataOneByOne() {
		System.out.println("in data One by One");
		return new ExcelReadDataProvider(Constants.EXCEL_SHEET_NAME,
				Constants.ATC_SHEET_NAME);
	}

	private String getRequestBody(AtcRequestData atcRequestData) {
		switch (atcRequestData.getRequestId()) {
		case "1":
			return ATCRequestBodyCommon.createAtcCommonRequestBodyNew(
					atcRequestData.getSourceER(),
					atcRequestData.getAttributeER(),
					atcRequestData.getSourceNames(),
					atcRequestData.getAttributeNames(),
					atcRequestData.getRangeSize(), ep,
					atcRequestData.getFrequency(), atcRequestData.getLimit(),
					atcRequestData.getIncludeAggregateTimeSeries()).toString();
		case "2":
			return ATCRequestBodyCommon.createAtcAttributeRequestBody(
					atcRequestData.getSourceER(),
					atcRequestData.getSourceNames(),
					atcRequestData.getAttributeER(),
					atcRequestData.getNameAttr(),
					atcRequestData.getNameAttrValues(),
					atcRequestData.getOperator(),
					atcRequestData.getRangeSize(), ep,
					atcRequestData.getFrequency(), atcRequestData.getLimit(),
					atcRequestData.getIncludeAggregateTimeSeries()).toString();
		case "3":
			return ATCRequestBodyCommon.createAtcAttributeCompRequestBody(
					atcRequestData.getSourceER(),
					atcRequestData.getSourceNames(),
					atcRequestData.getAttributeER(),
					atcRequestData.getAttributeNames(),
					atcRequestData.getNameAttrValues(),
					atcRequestData.getNameAttr(), atcRequestData.getOperator(),
					atcRequestData.getRangeSize(), ep,
					atcRequestData.getFrequency(), atcRequestData.getLimit(),
					atcRequestData.getIncludeAggregateTimeSeries()).toString();
		case "4":
			return ATCRequestBodyCommon.createAtcCommonRequestBodyBukket(
					atcRequestData.getSourceER(),
					atcRequestData.getAttributeER(),
					atcRequestData.getSourceNames(),
					atcRequestData.getAttributeNames(),
					atcRequestData.getNameAttr(),
					atcRequestData.getRangeSize(), ep,
					atcRequestData.getFrequency(), atcRequestData.getLimit(),
					atcRequestData.getIncludeAggregateTimeSeries()).toString();
		case "5":
			return ATCRequestBodyCommon.createAtcAttributeBukketRequestBody(
					atcRequestData.getSourceER(),
					atcRequestData.getSourceNames(),
					atcRequestData.getAttributeER(),
					atcRequestData.getNameAttr(),
					atcRequestData.getNameAttrValues(),
					atcRequestData.getOperator(),
					atcRequestData.getRangeSize(), ep,
					atcRequestData.getFrequency(), atcRequestData.getLimit(),
					atcRequestData.getIncludeAggregateTimeSeries()).toString();
		case "6":
			return ATCRequestBodyCommon
					.createAtcAttributeCompBukketRequestBody(
							atcRequestData.getSourceER(),
							atcRequestData.getSourceNames(),
							atcRequestData.getAttributeER(),
							atcRequestData.getAttributeNames(),
							atcRequestData.getNameAttrValues(),
							atcRequestData.getNameAttr(),
							atcRequestData.getOperator(),
							atcRequestData.getRangeSize(), ep,
							atcRequestData.getFrequency(),
							atcRequestData.getLimit(),
							atcRequestData.getIncludeAggregateTimeSeries())
					.toString();
		default:
			log.warn("Invalid Request ID " + atcRequestData.toString());
			break;
		}
		return null;
	}

	@AfterClass
	public void stopPosting() {
		PostMetadatNassData.shouldStopPosting = false;
	}

	public Response validatePostResponseCode(String url, String requestBody,
			int responseCode, String token) {

		response = given().header("Authorization", "Bearer " + token)
				.contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(requestBody).relaxedHTTPSValidation().

				when().post(url);

		return response;

	}

	public String verifyKeyfromAtcResponse(Response response) {
		JsonPath jsonPathEvaluator = response.jsonPath();

		String key = jsonPathEvaluator.get("aggregations.TOPK[0].key");

		return key;

	}

	public List<String> verifyidsfromAtcResponse(Response response) {
		List<String> jsonResponse = response.jsonPath().getList(
				"aggregations.TOPK[0].ids");
		return jsonResponse;
	}

	public Map<Integer, List<String>> verifymetricIdfromAtcResponseasMap(
			Response response) {

		JSONArray responseArray = new JSONArray();
		JSONObject object = new JSONObject(response.asString());
		JSONObject agg = object.getJSONObject("aggregations");
		responseArray = agg.getJSONArray("TOPK");
		Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();
		for (int i = 0; i < responseArray.length(); ++i) {

			JSONObject jsn = responseArray.getJSONObject(i);
			JSONObject metrics = jsn.getJSONObject("metric");
			map.put(i + 1,
					Arrays.asList(
							metrics.getString("id"),
							metrics.getString("source")
									+ metrics.getString("attribute")));

		}
		return map;
	}

	public List<String> verifylistidsfromAtcResponse(Response response) {
		List<String> jsonResponse = response.jsonPath().getList(
				"aggregations.TOPK[0].ids");
		return jsonResponse;
	}

	public Map<String, List> verifyaggvaluefromAtcResponse(Response response) {

		JSONArray responseArray = new JSONArray();
		JSONArray aggresponseArray = new JSONArray();
		JSONArray valuesresponseArray = new JSONArray();

		Map<String, List> map = new HashMap<String, List>();
		JSONObject object = new JSONObject(response.asString());
		JSONObject agg = object.getJSONObject("aggregations");
		responseArray = agg.getJSONArray("TOPK");

		for (int i = 0; i < responseArray.length(); ++i) {
			ArrayList result = new ArrayList();
			JSONObject jsn = responseArray.getJSONObject(i);
			String key = jsn.getString("key");
			aggresponseArray = jsn.getJSONArray("aggregateValues");

			valuesresponseArray = aggresponseArray.getJSONArray(0);
			result.clear();
			for (int j = 0; j < valuesresponseArray.length(); j++) {

				result.add(valuesresponseArray.get(j).toString());

			}

			map.put(key, result);

		}

		return map;
	}

	public List<String> verifyattValuesfromAtcResponse(Response response) {

		JSONArray responseArray = new JSONArray();
		JSONArray attrArray = new JSONArray();
		JSONObject object = new JSONObject(response.asString());
		JSONObject agg = object.getJSONObject("aggregations");
		responseArray = agg.getJSONArray("TOPK");
		List<String> attrValues = new ArrayList<String>();
		for (int i = 0; i < responseArray.length(); ++i) {
			// log.info("print "+i);

			JSONObject jsn = responseArray.getJSONObject(i);
			JSONObject metrics = jsn.getJSONObject("metric");
			if (metrics.has("attributes")) {
				JSONObject attributes = metrics.getJSONObject("attributes");
				if (attributes.has("location")) {
					attrArray = attributes.getJSONArray("location");
					for (int k = 0; k < attrArray.length(); k++) {
						log.info("Lcation value:" + k + ":" + attrArray.get(k));
						String a = (String) attrArray.get(k);
						attrValues.add(a);
					}
				} else {
					attrArray = attributes.getJSONArray("resources");
					for (int k = 0; k < attrArray.length(); k++) {
						log.info("Recources value:" + k + ":"
								+ attrArray.get(k));
						String a = (String) attrArray.get(k);
						attrValues.add(a);
					}

				}

			}

		}
		return attrValues;
	}

}
