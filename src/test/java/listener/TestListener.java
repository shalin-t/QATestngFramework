package listener;

import org.testng.ITestListener;
import org.testng.ITestResult;

import supporter.GenericMethod;

public class TestListener implements ITestListener{
	
	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("Test started -- ITest Listerner: "+ result.getName());
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("Test is successful "+ result.getName());
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		try {
			GenericMethod.takeScreenshot(result.getTestName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
