package com.ca.apm.tests.utils;

import java.util.ResourceBundle;

public class Reader {
	
	public ResourceBundle environmentConstants = Utf8ResourceBundle.getBundle("Data");
	
	public String baseUrl = environmentConstants.getString("baseUrl");
	public String token = environmentConstants.getString("token");

}
