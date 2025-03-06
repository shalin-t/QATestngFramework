package maven_testng;

import org.testng.annotations.Test;
import pageObject.*;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import supporter.GenericMethod;
import supporter.Baseclass;

public class RunTest {

	public static WebDriver driver;
	public static Properties config;

	@BeforeMethod
	public void setupBrowser() throws IOException {
		try {
			driver = GenericMethod.OpenBrowser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		new Baseclass(driver);
	}


	@Test(priority = 0, enabled = false)
	public void logintest() throws Exception {
		try {
			PageMethods.happyPathLogin("Test1");
		} catch (Exception e) {
			System.err.println("Test script failed.");
			GenericMethod.takeScreenshot(driver, "logintest");
			throw (e);
		}
	}

	@Test(priority = 1, enabled = false)
	public void invalidlogintest() throws Exception {
		try {
			PageMethods.errorLogin("Test2");
		} catch (Exception e) {
			System.err.println("Test script failed.");
			GenericMethod.takeScreenshot(driver, "logintest");
			throw (e);
		}
	}
	
	
	@Test(priority = 2)
	public void verifyBrokenProductImages() throws Exception {
		try {
			PageMethods.verifybrokenProductImage("Test3");
		} catch (Exception e) {
			System.err.println("Test script failed.");
			GenericMethod.takeScreenshot(driver, "logintest");
			throw (e);
		}
	}

	@Test(dataProvider = "dp", enabled = false)
	public void f(Integer n, String s) {
		System.out.println("This is my test area " + n);
	}

	@DataProvider
	public Object[][] dp() {
		return new Object[][] { new Object[] { 1, "a" }, new Object[] { 2, "b" }, };
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
		System.out.println("Browser closed");
	}

}
