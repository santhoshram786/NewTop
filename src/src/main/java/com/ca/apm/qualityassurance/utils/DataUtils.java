package com.ca.apm.qualityassurance.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class DataUtils {
	private static Logger log = Logger.getLogger(DataUtils.class);

	public static String getRandomNumber() {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss_SS");
		return ft.format(new Date());
	}

	public static BigDecimal truncateDecimal(final double x,
			final int numberofDecimals) {
		if (x > 0) {
			return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals,
					RoundingMode.CEILING);
		} else {
			return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals,
					RoundingMode.CEILING);
		}
	}

	public static long epochtime() {

		long epochTime = ((System.currentTimeMillis() / 1000) / 15) * 15;

		return epochTime;
	}

	public static void pause(final int seconds) {
		Date start = new Date();
		log.info("Started Sleeping at " + start.toString());
		Date end = new Date();
		long part = end.getTime() + 10000;
		int i = 0;
		log.info("Sleeping for "
				+ DurationFormatUtils.formatDurationWords(seconds * 1000, true,
						false));
		while (end.getTime() - start.getTime() < seconds * 1000) {
			if (part - end.getTime() <= 0) {
				part = end.getTime() + 10000;
				i++;
				System.out.print(" . ");
			}
			if (i == 6) {
				log.info("60 seconds completed Going to sleep again, Do some thing else .. !!!");
				i = 0;
			}
			end = new Date();
		}
		log.info("Awakee sleeping is done ... !!!!");
		log.info("Started Sleeping at " + end.toString());
	}

	public static String getTenatnIdFromEncodedToken(final String encodedToken)
			throws JSONException {
		log.info(encodedToken);
		String str = encodedToken.split("\\s+")[1];
		JSONObject json = new JSONObject(new String(Base64.getDecoder().decode(
				str.getBytes())));
		return json.getString("tkn");

	}

}