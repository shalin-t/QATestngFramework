package supporter;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GenericMethod {
	public static WebDriver driver = null;
	public WebDriverWait wait;
	boolean acceptNextAlert = true;
	static Properties config;

	/**
	 * Takes screenshot
	 * 
	 * @param driver
	 * @param ss
	 * @throws Exception
	 */
	public static void takeScreenshot(WebDriver driver, String ss) throws Exception {
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String systime = GetTimeStampValue().replace(":", "-");
			FileUtils.copyFile(scrFile, new File("./ScreenShot_Folder/" + ss + " " + systime + ".jpg"));
		} catch (Exception e) {
			System.err.println("Exception occured while capturing ScreenShot : " + e.getMessage());
			throw new Exception();
		}
	}

	/**
	 * Takes screenshot
	 * 
	 * @param filename
	 * @throws Exception
	 */
	public static void takeScreenshot(String ss) throws Exception {
		try {
			File scrFile = ((TakesScreenshot) Baseclass.driver).getScreenshotAs(OutputType.FILE);
			String systime = GetTimeStampValue().replace(":", "-");
			FileUtils.copyFile(scrFile, new File("./ScreenShot_Folder/" + ss + " " + systime + ".jpg"));
		} catch (Exception e) {
			System.err.println("Exception occured while capturing ScreenShot : " + e.getMessage());
			throw new Exception();
		}
	}

	/**
	 * Gets the date and time
	 * 
	 * @return timestamp
	 */
	public static String GetTimeStampValue() {
		return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
	}

	/**
	 * Opens the web browser and navigates to URL.
	 * 
	 * @return WebDriver
	 * @throws Exception
	 */
	public static WebDriver OpenBrowser() throws Exception {

		String sBrowserName;
		String url;
		System.out.println("<========== Execution of TestNg Framework ===========>");

		try {
			FileInputStream fs = new FileInputStream("config\\config.properties");
			config = new Properties();
			config.load(fs);
			sBrowserName = config.getProperty("BrowserType");
			url = config.getProperty("TestURL");
//			sBrowserName = "Chrome";
//			url = "https://www.saucedemo.com/";
			System.out.println(url + "==> is the url used for testing.");
			System.out.println("Testing on : " + sBrowserName + " Browser.");
			if (sBrowserName == null || sBrowserName.isEmpty()) {
				throw new Exception("BrowserType is not specified in config.properties");
			} else if (sBrowserName.toLowerCase().equals("firefox")) {
				driver = new FirefoxDriver();
			} else if (sBrowserName.toLowerCase().equals("chrome")) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--disable-notifications");
				options.setAcceptInsecureCerts(true); // Accept insecure certificates
//				WebDriverManager.chromedriver().setup();
				System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
				driver = new ChromeDriver(options);
			} else {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless");
				options.addArguments("--disable-gpu");
				options.addArguments("--no-sandbox");
				options.addArguments("--remote-allow-origins=*");
				driver = new ChromeDriver(options);
			}
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			driver.get(url);
			System.out.println("Web application launched successfully.");
		} catch (Exception e) {
			System.err.println("Class Genericmethod | Method OpenBrowser | Exception desc : " + e.getMessage());
		}
		return driver;
	}

	/**
	 * Waits for web element until condition.
	 * 
	 * @param element
	 * @return
	 */
	public static WebElement waitForElement(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
			return wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			System.err.println("Element not found: " + e.getMessage());
			return null;
		}
	}

	/**
	 * This method switches control to the alert.
	 * 
	 * @return true / false
	 */
	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	/**
	 * This method closes alert and return the alert text.
	 * 
	 * @return Alert accept / Alert dismiss
	 */
	public String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}

	/**
	 * Highlights web element on web page.
	 * 
	 * @param element
	 * @throws Exception
	 */
	public static void highlightElement_link(WebElement element) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(false);", element);
		js.executeScript("arguments[0].setAttribute('style', arguments[1], arguments[2]);", element,
				"color: black; border: 3px solid yellow; background-color: yellow");
		Thread.sleep(200);
		js.executeScript("arguments[0].setAttribute('style', arguments[1], arguments[2]);", element, "");
	}

}