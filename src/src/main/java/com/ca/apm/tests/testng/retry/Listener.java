package com.ca.apm.tests.testng.retry;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

/**
 * @author patas07
 *
 */

public class Listener implements ITestListener {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	WebDriver driver;

	@Override
	public void onFinish(ITestContext context) {

		logger.info("All Test runs are finished: "+context.getName());
		
		Set<ITestResult> failedTests = context.getFailedTests().getAllResults();
		
		for (ITestResult temp : failedTests) {
			
			ITestNGMethod method = temp.getMethod();
			
			if (context.getFailedTests().getResults(method).size() > 1) {
				
				failedTests.remove(temp);
				
			} else {
				
				if (context.getPassedTests().getResults(method).size() > 0) {
					
					failedTests.remove(temp);
					
				}
			}
		}
		
	}

	@Override
	public void onStart(ITestContext context) {

		logger.info("About to start the suite (onStart): "+context.getName());
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

		
	}

	@Override
	public void onTestFailure(ITestResult result) {

		logger.info("The test case/method FAILED... (onTestFailure): "+result.getName());
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {

		logger.info("Test method is skipped (onTestSkipped): "+result.getName());
		
	}

	@Override
	public void onTestStart(ITestResult result) {

		logger.info("About to start the test method (onTestStart): "+result.getName());
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		logger.info("The test method PASSED successfully ... (onTestSuccess): "+result.getName());

		
	}
	
	

}
