/**
 *
 */
package com.ca.apm.tests.testng.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * @author patas07
 *
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    protected final Logger logger = LoggerFactory.getLogger(RetryAnalyzer.class);

    private int count = 0;
    private static int maxTry = 1;

	@Override
	public boolean retry(ITestResult iTestResult) {
		if (!iTestResult.isSuccess()) {
			if (count < maxTry) { 
				count++; 
				iTestResult.setStatus(ITestResult.FAILURE);				
				return true; 
			}
		} else {
			iTestResult.setStatus(ITestResult.SUCCESS);
		}
		return false;
	}

}
