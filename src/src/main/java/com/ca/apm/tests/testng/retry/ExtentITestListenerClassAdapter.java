/**
 * 
 */
package com.ca.apm.tests.testng.retry;

import java.util.Arrays;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.ca.apm.commons.extentreport.config.ExtentService;
import com.ca.apm.commons.extentreport.config.ExtentTestManager;


/**
 * @author patas07/ap670882
 * (Ashwini Kumar Pattanayak)
 */
public class ExtentITestListenerClassAdapter implements ITestListener {
	
	protected final Logger logger = LoggerFactory.getLogger(ExtentITestListenerClassAdapter.class);
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
	ExtentTest child = null;
	
	@Override
	public synchronized void onStart(ITestContext context) {

		ExtentService.getInstance().setAnalysisStrategy(AnalysisStrategy.SUITE);
	}

	@Override
	public synchronized void onTestStart(ITestResult result) {

		child = ExtentTestManager.createMethod(result, true);
		test.set(child);

	}

	@Override
	public synchronized void onTestSuccess(ITestResult result) {

		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "TEST CASE:- " + methodName.toUpperCase() + " PASSED" + "</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		test.get().pass(m);
	}

	@Override
	public void onTestFailure(ITestResult result) {

        Throwable excepionMessage=result.getThrowable();
		test.get().fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Main Exception Occured:Click to see"
				+ "</font>" + "</b >" + "</summary>" +excepionMessage+" \n");
		
		String excepionMessages=Arrays.toString(result.getThrowable().getStackTrace());  
        test.get().fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Combined Exception Occured:Click to see"
        		+ "</font>" + "</b >" + "</summary>" +excepionMessage+ "\n"+excepionMessages.replaceAll(",", "<br>")+"</details>"+" \n");
		
		String failureLogg="TEST CASE FAILED";
		Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
		test.get().log(Status.FAIL, m);

	}

	@Override
	public synchronized void onTestSkipped(ITestResult result) {
		
		ExtentService.getInstance().removeTest(child);
		ExtentService.getInstance().flush();
	}

	@Override
	public synchronized void onFinish(ITestContext context) {

		Set<ITestResult> skippedTestcases = context.getSkippedTests().getAllResults();

		for (ITestResult temp : skippedTestcases) {

			logger.info("Removing skipped test cases...");
			skippedTestcases.remove(temp);
		}

		ITestNGMethod[] allTestCases = context.getAllTestMethods();
		logger.info("Total test cases executed on Finish: " + allTestCases.length);

		for (int i = 0; i < allTestCases.length; i++) {

			logger.info("OnFinish Test Method Name is: "+allTestCases[i].getMethodName());
			logger.info("OnFinish Length of getCurrentInvocationCount: " + allTestCases[i].getCurrentInvocationCount());

			if (allTestCases[i].getCurrentInvocationCount() == 2) {

				logger.info("Length of failed test methods: "+ context.getFailedTests().getResults(allTestCases[i]).size());
				logger.info("Length of passed test methods: "+ context.getPassedTests().getResults(allTestCases[i]).size());

				if (context.getFailedTests().getResults(allTestCases[i]).size() > 1) {

					logger.info("Removing failed tests from testng report > 1....");
					context.getFailedTests().removeResult(context.getAllTestMethods()[i]);

				} else {

					if (context.getPassedTests().getResults(allTestCases[i]).size() > 0) {

						logger.info("Removing failed tests from testng report > 0....");
						context.getFailedTests().removeResult(context.getAllTestMethods()[i]);
					}
				}

			}

		}

		ExtentService.getInstance().flush();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
		
	}
}