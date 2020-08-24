package com.ca.apm.qualityassurance.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ATCRequestBodyCommon {

	public static JSONObject createAtcAttributeRequestBody(String sourceER,
			List<String> sourceNames, String attributeER, String AttributeName,
			List<String> attributeValues, String operator, long duration,
			long endTime, long frequency, String limit,
			Boolean includeAggregateTimeSeries) {
		Collection<JSONObject> queries = new LinkedList();
		Collection<JSONObject> queries1 = new LinkedList();
		JSONArray array1 = new JSONArray();
		// #####sourceNameSpecifier###########
		JSONObject sourceNameSpecifier = new JSONObject();
		if (sourceER.contains("EXACT")) {
			sourceNameSpecifier.put("op", sourceER);

			for (int i = 0; i < sourceNames.size(); i++) {
				array1.put(sourceNames.get(i));
			}

			sourceNameSpecifier.put("names", array1);

		} else if (sourceER.contains("REGEX")) {
			sourceNameSpecifier.put("op", sourceER);
			sourceNameSpecifier.put("pattern", sourceNames.get(0));

		}
		// #####attributeNameSpecifier###########

		JSONArray array = new JSONArray();
		for (int i = 0; i < attributeValues.size(); i++) {
			array.put(attributeValues.get(i));
		}
		JSONObject expressions = new JSONObject();

		expressions.put("values", array);

		expressions.put("name", AttributeName);

		expressions.put("operator", operator);

		queries1.add(expressions);
		JSONObject attributeNameSpecifier = new JSONObject();
		attributeNameSpecifier.put("expressions", new JSONArray(queries1));
		attributeNameSpecifier.put("op", attributeER);

		// ########################
		JSONObject query = new JSONObject();
		query.put("op", "SPEC");
		query.put("sourceNameSpecifier", sourceNameSpecifier);
		query.put("attributeNameSpecifier", attributeNameSpecifier);

		// ############################
		JSONObject queryRange = new JSONObject();
		queryRange.put("frequency", frequency);
		queryRange.put("rangeSize", duration);
		queryRange.put("endTime", JSONObject.NULL);

		// ###########################
		JSONObject input = new JSONObject();
		input.put("op", "TOPK");
		input.put("limit", limit);
		input.put("includeAggregateTimeSeries", includeAggregateTimeSeries);
		JSONObject input1 = new JSONObject();
		input1.put("input", input);
		queries.add(input1);

		// ##############################

		JSONObject body = new JSONObject();
		body.put("querySpecifier", query);
		body.put("queryRange", queryRange);
		body.put("aggregations", new JSONArray(queries));

		return body;
	}

	public static JSONObject createAtcCommonRequestBodyNew(String sourceER,
			String attributeER, List<String> sourceNames,
			List<String> attributeNames, long duration, long endTime,
			long frequency, String limit, Boolean includeAggregateTimeSeries) {
		Collection<JSONObject> queries = new LinkedList();
		// #####sourceNameSpecifier###########

		JSONObject sourceNameSpecifier = new JSONObject();

		JSONArray array = new JSONArray();

		if (sourceER.contains("EXACT")) {
			sourceNameSpecifier.put("op", sourceER);

			for (int i = 0; i < sourceNames.size(); i++) {
				array.put(sourceNames.get(i));
			}

			sourceNameSpecifier.put("names", array);

		} else if (sourceER.contains("REGEX")) {
			sourceNameSpecifier.put("op", sourceER);
			sourceNameSpecifier.put("pattern", sourceNames.get(0));

		}

		// #####attributeNameSpecifier###########
		JSONObject attributeNameSpecifier = new JSONObject();
		// List<String> listnames1 = new ArrayList<String>();
		JSONArray array1 = new JSONArray();

		if (attributeER.contains("EXACT")) {
			attributeNameSpecifier.put("op", attributeER);
			// listnames1.add(attributeName);

			for (int i = 0; i < attributeNames.size(); i++) {
				array1.put(attributeNames.get(i));
			}

			attributeNameSpecifier.put("names", array1);

		} else if (attributeER.contains("REGEX")) {
			attributeNameSpecifier.put("op", attributeER);
			attributeNameSpecifier.put("pattern", attributeNames.get(0));

		}

		// ########################
		JSONObject query = new JSONObject();
		query.put("op", "SPEC");
		query.put("sourceNameSpecifier", sourceNameSpecifier);
		query.put("attributeNameSpecifier", attributeNameSpecifier);

		// ############################
		JSONObject queryRange = new JSONObject();
		queryRange.put("frequency", frequency);
		queryRange.put("rangeSize", duration);
		queryRange.put("endTime", JSONObject.NULL);

		// ###########################
		JSONObject input = new JSONObject();
		input.put("op", "TOPK");
		input.put("limit", limit);
		input.put("includeAggregateTimeSeries", includeAggregateTimeSeries);
		JSONObject input1 = new JSONObject();
		input1.put("input", input);
		queries.add(input1);

		// ##############################

		JSONObject body = new JSONObject();
		body.put("querySpecifier", query);
		body.put("queryRange", queryRange);
		body.put("aggregations", new JSONArray(queries));

		return body;
	}

	public static JSONObject createAtcAttributeCompRequestBody(String sourceER,
			List<String> sourceNames, String attributeER,
			List<String> AttributeNames, List<String> attributeValues,
			String attrName, String operator, long duration, long endTime,
			long frequency, String limit, Boolean includeAggregateTimeSeries) {
		Collection<JSONObject> queries = new LinkedList();
		JSONArray array1 = new JSONArray();
		// #####sourceNameSpecifier###########
		JSONObject sourceNameSpecifier = new JSONObject();
		if (sourceER.contains("EXACT")) {
			sourceNameSpecifier.put("op", sourceER);

			for (int i = 0; i < sourceNames.size(); i++) {
				array1.put(sourceNames.get(i));
			}

			sourceNameSpecifier.put("names", array1);

		} else if (sourceER.contains("REGEX")) {
			sourceNameSpecifier.put("op", sourceER);
			sourceNameSpecifier.put("pattern", sourceNames.get(0));

		}
		// #####attributeNameSpecifier###########

		Collection<JSONObject> queries1 = new LinkedList();
		Collection<JSONObject> queries2 = new LinkedList();
		JSONObject attributeNameSpecifier = new JSONObject();

		JSONArray array = new JSONArray();
		for (int i = 0; i < attributeValues.size(); i++) {
			array.put(attributeValues.get(i));
		}
		JSONObject expressions = new JSONObject();
		expressions.put("values", array);

		expressions.put("name", attrName);

		expressions.put("operator", operator);
		expressions.put("comparator", "LEXICAL");
		queries1.add(expressions);
		JSONObject specifiers2 = new JSONObject();

		specifiers2.put("expressions", new JSONArray(queries1));
		specifiers2.put("op", "ATTRIBUTE");
		JSONObject specifiers1 = new JSONObject();
		// ##############
		// JSONArray array1 = new JSONArray();

		if (attributeER.contains("EXACT")) {
			specifiers1.put("op", attributeER);
			// listnames1.add(attributeName);

			for (int i = 0; i < AttributeNames.size(); i++) {
				array1.put(AttributeNames.get(i));
			}

			specifiers1.put("names", array1);

		} else if (attributeER.contains("REGEX")) {
			specifiers1.put("op", attributeER);
			specifiers1.put("pattern", AttributeNames.get(0));

		}

		// ##############

		queries2.add(specifiers1);
		queries2.add(specifiers2);

		JSONObject specifiers = new JSONObject();
		specifiers.put("specifiers", new JSONArray(queries2));
		specifiers.put("op", "AND");

		// ########################
		JSONObject query = new JSONObject();
		query.put("op", "SPEC");
		query.put("sourceNameSpecifier", sourceNameSpecifier);
		query.put("attributeNameSpecifier", specifiers);

		// ############################
		JSONObject queryRange = new JSONObject();
		queryRange.put("frequency", frequency);
		queryRange.put("rangeSize", duration);
		queryRange.put("endTime", JSONObject.NULL);

		// ###########################
		JSONObject input = new JSONObject();
		input.put("op", "TOPK");
		input.put("limit", limit);
		input.put("includeAggregateTimeSeries", includeAggregateTimeSeries);
		JSONObject input1 = new JSONObject();
		input1.put("input", input);
		queries.add(input1);

		// ##############################

		JSONObject body = new JSONObject();
		body.put("querySpecifier", query);
		body.put("queryRange", queryRange);
		body.put("aggregations", new JSONArray(queries));

		return body;
	}

	public static JSONObject createAtcCommonRequestBodyBukket(String sourceER,
			String attributeER, List<String> sourceNames,
			List<String> attributeNames, String attrName, long duration,
			long endTime, long frequency, String limit,
			Boolean includeAggregateTimeSeries) {
		Collection<JSONObject> queries = new LinkedList();
		// #####sourceNameSpecifier###########

		JSONObject sourceNameSpecifier = new JSONObject();

		JSONArray array = new JSONArray();

		if (sourceER.contains("EXACT")) {
			sourceNameSpecifier.put("op", sourceER);

			for (int i = 0; i < sourceNames.size(); i++) {
				array.put(sourceNames.get(i));
			}

			sourceNameSpecifier.put("names", array);

		} else if (sourceER.contains("REGEX")) {
			sourceNameSpecifier.put("op", sourceER);
			sourceNameSpecifier.put("pattern", sourceNames.get(0));

		}

		// #####attributeNameSpecifier###########
		JSONObject attributeNameSpecifier = new JSONObject();

		JSONArray array1 = new JSONArray();

		if (attributeER.contains("EXACT")) {
			attributeNameSpecifier.put("op", attributeER);

			for (int i = 0; i < attributeNames.size(); i++) {
				array1.put(attributeNames.get(i));
			}

			attributeNameSpecifier.put("names", array1);

		} else if (attributeER.contains("REGEX")) {
			attributeNameSpecifier.put("op", attributeER);
			attributeNameSpecifier.put("pattern", attributeNames.get(0));

		}

		// ########################
		JSONObject query = new JSONObject();
		query.put("op", "SPEC");
		query.put("sourceNameSpecifier", sourceNameSpecifier);
		query.put("attributeNameSpecifier", attributeNameSpecifier);

		// ############################
		JSONObject queryRange = new JSONObject();
		queryRange.put("frequency", frequency);
		queryRange.put("rangeSize", duration);
		queryRange.put("endTime", JSONObject.NULL);

		// ###########################
		JSONObject input = new JSONObject();
		input.put("op", "TOPK");
		input.put("limit", limit);
		JSONObject innerinput = new JSONObject();
		innerinput.put("op", "BUCKET_ATTRIBUTE");
		innerinput.put("value", attrName);
		input.put("input", innerinput);
		input.put("includeAggregateTimeSeries", includeAggregateTimeSeries);
		JSONObject input1 = new JSONObject();
		input1.put("input", input);
		queries.add(input1);

		// ##############################

		JSONObject body = new JSONObject();
		body.put("querySpecifier", query);
		body.put("queryRange", queryRange);
		body.put("aggregations", new JSONArray(queries));

		return body;
	}

	public static JSONObject createAtcAttributeBukketRequestBody(
			String sourceER, List<String> sourceNames, String attributeER,
			String AttributeName, List<String> attributeValues,
			String operator, long duration, long endTime, long frequency,
			String limit, Boolean includeAggregateTimeSeries) {
		Collection<JSONObject> queries = new LinkedList();
		Collection<JSONObject> queries1 = new LinkedList();
		List<String> listnames = new ArrayList<String>();
		JSONArray array1 = new JSONArray();
		// #####sourceNameSpecifier###########
		JSONObject sourceNameSpecifier = new JSONObject();
		if (sourceER.contains("EXACT")) {
			sourceNameSpecifier.put("op", sourceER);

			for (int i = 0; i < sourceNames.size(); i++) {
				array1.put(sourceNames.get(i));
			}

			sourceNameSpecifier.put("names", array1);

		} else if (sourceER.contains("REGEX")) {
			sourceNameSpecifier.put("op", sourceER);
			sourceNameSpecifier.put("pattern", sourceNames.get(0));

		}
		// #####attributeNameSpecifier###########

		JSONArray array = new JSONArray();
		for (int i = 0; i < attributeValues.size(); i++) {
			array.put(attributeValues.get(i));
		}
		JSONObject expressions = new JSONObject();

		expressions.put("values", array);

		expressions.put("name", AttributeName);

		expressions.put("operator", operator);

		queries1.add(expressions);
		JSONObject attributeNameSpecifier = new JSONObject();
		attributeNameSpecifier.put("expressions", new JSONArray(queries1));
		attributeNameSpecifier.put("op", attributeER);

		// ########################
		JSONObject query = new JSONObject();
		query.put("op", "SPEC");
		query.put("sourceNameSpecifier", sourceNameSpecifier);
		query.put("attributeNameSpecifier", attributeNameSpecifier);

		// ############################
		JSONObject queryRange = new JSONObject();
		queryRange.put("frequency", frequency);
		queryRange.put("rangeSize", duration);
		queryRange.put("endTime", JSONObject.NULL);

		// ###########################
		JSONObject input = new JSONObject();
		input.put("op", "TOPK");
		input.put("limit", limit);
		JSONObject innerinput = new JSONObject();
		innerinput.put("op", "BUCKET_ATTRIBUTE");
		innerinput.put("value", AttributeName);
		input.put("input", innerinput);
		input.put("includeAggregateTimeSeries", includeAggregateTimeSeries);
		JSONObject input1 = new JSONObject();
		input1.put("input", input);
		queries.add(input1);

		// ##############################

		JSONObject body = new JSONObject();
		body.put("querySpecifier", query);
		body.put("queryRange", queryRange);
		body.put("aggregations", new JSONArray(queries));

		return body;
	}

	public static JSONObject createAtcAttributeCompBukketRequestBody(
			String sourceER, List<String> sourceNames, String attributeER,
			List<String> AttributeNames, List<String> attributeValues,
			String attrName, String operator, long duration, long endTime,
			long frequency, String limit, Boolean includeAggregateTimeSeries) {
		Collection<JSONObject> queries = new LinkedList();
		List<String> listnames = new ArrayList<String>();
		JSONArray array1 = new JSONArray();
		// #####sourceNameSpecifier###########
		JSONObject sourceNameSpecifier = new JSONObject();
		if (sourceER.contains("EXACT")) {
			sourceNameSpecifier.put("op", sourceER);

			for (int i = 0; i < sourceNames.size(); i++) {
				array1.put(sourceNames.get(i));
			}

			sourceNameSpecifier.put("names", array1);

		} else if (sourceER.contains("REGEX")) {
			sourceNameSpecifier.put("op", sourceER);
			sourceNameSpecifier.put("pattern", sourceNames.get(0));

		}
		// #####attributeNameSpecifier###########

		Collection<JSONObject> queries1 = new LinkedList();
		Collection<JSONObject> queries2 = new LinkedList();
		JSONObject attributeNameSpecifier = new JSONObject();

		JSONArray array = new JSONArray();
		for (int i = 0; i < attributeValues.size(); i++) {
			array.put(attributeValues.get(i));
		}
		JSONObject expressions = new JSONObject();
		expressions.put("values", array);

		expressions.put("name", attrName);

		expressions.put("operator", operator);
		expressions.put("comparator", "LEXICAL");
		queries1.add(expressions);
		JSONObject specifiers2 = new JSONObject();

		specifiers2.put("expressions", new JSONArray(queries1));
		specifiers2.put("op", "ATTRIBUTE");
		JSONObject specifiers1 = new JSONObject();
		specifiers1.put("op", attributeER);
		specifiers1.put("pattern", AttributeNames.get(0));

		queries2.add(specifiers1);
		queries2.add(specifiers2);

		JSONObject specifiers = new JSONObject();
		specifiers.put("specifiers", new JSONArray(queries2));
		specifiers.put("op", "AND");

		// ########################
		JSONObject query = new JSONObject();
		query.put("op", "SPEC");
		query.put("sourceNameSpecifier", sourceNameSpecifier);
		query.put("attributeNameSpecifier", specifiers);

		// ############################
		JSONObject queryRange = new JSONObject();
		queryRange.put("frequency", frequency);
		queryRange.put("rangeSize", duration);
		queryRange.put("endTime", JSONObject.NULL);

		// ###########################
		JSONObject input = new JSONObject();
		input.put("op", "TOPK");
		input.put("limit", limit);
		JSONObject innerinput = new JSONObject();
		innerinput.put("op", "BUCKET_ATTRIBUTE");
		innerinput.put("value", attrName);
		input.put("input", innerinput);
		input.put("includeAggregateTimeSeries", includeAggregateTimeSeries);
		JSONObject input1 = new JSONObject();
		input1.put("input", input);
		queries.add(input1);
		// ##############################

		JSONObject body = new JSONObject();
		body.put("querySpecifier", query);
		body.put("queryRange", queryRange);
		body.put("aggregations", new JSONArray(queries));

		return body;
	}

}
