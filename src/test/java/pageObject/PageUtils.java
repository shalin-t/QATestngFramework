package pageObject;

import java.io.IOException;
import java.net.HttpURLConnection;

//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.HttpClientBuilder;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import supporter.Baseclass;
import supporter.GenericMethod;
import supporter.XLSReader;

public class PageUtils extends Baseclass {
	static WebElement element;
	public static XLSReader xls = new XLSReader("src/main/resources/data/BrokenLinks.xlsx");

	public PageUtils(WebDriver driver) {
		super(driver);
	}

	public static void enterUsername(String username) {
		try {
			element = driver.findElement(By.id(ObjectRepository.USERNAME));
			GenericMethod.highlightElement_link(element);
			element.clear();
			element.sendKeys(username);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void enterPassword(String password) {
		try {
			element = driver.findElement(By.id(ObjectRepository.PASSWORD));
			GenericMethod.highlightElement_link(element);
			element.clear();
			if (password != null)
				element.sendKeys(password);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void clicklogin() throws Exception {
		element = driver.findElement(By.id(ObjectRepository.LOGIN_BUTTON));
		GenericMethod.highlightElement_link(element);
		element.click();

	}

	public static void verifyLoginSuccess() {
		try {
			element = GenericMethod.waitForElement(driver.findElement(By.xpath("//div[@class='app_logo']")));
			Assert.assertTrue(element.isDisplayed(), "User dashboard is not displayed");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void verifyLoginFailure() {
		try {
			element = GenericMethod.waitForElement(driver.findElement(By.xpath(ObjectRepository.LOGINFAILUREERROR)));
			System.out.println("Error message: " + element.getText());
			Assert.assertTrue(element.isDisplayed(), "Validation failure for login error message");
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void verifyBrokenLinks(String sheetName) {
		List<WebElement> links = driver.findElements(By.xpath("//a"));
		Iterator<WebElement> it = links.iterator();
		int rowIndex = 2;
		while (it.hasNext()) {
			WebElement we = it.next();
			String url = we.getDomAttribute("href");

			if (url == null || url.isEmpty() || url.contains("apple.com") || url.startsWith("javascript:") || url.startsWith("#")) {
				System.out.println("Skipping empty or invalid URL");
				continue;
			}

			int respCode = verifyLink(url);

			if (respCode >= 400) {
				System.out.println(url + " is broken. Status Code: " + respCode);
			} else {
				System.out.println(url + " is working. Status Code: " + respCode);
			}

			// Set URL and Status Code in Excel
			xls.setCellData(sheetName, "URL", rowIndex, url);
			xls.setCellData(sheetName, "Status Code", rowIndex, String.valueOf(respCode));
			rowIndex++;
		}
	}

	public static int verifyLink(String url) {
		int respCode = -1;
		try {
			HttpURLConnection huc = (HttpURLConnection) (new URL(url).openConnection());
			huc.setRequestMethod("HEAD");
			huc.connect();
			respCode = huc.getResponseCode();
			huc.disconnect();
		} catch (Exception e) {
			System.out.println("Error checking URL: " + url);
		}
		return respCode;
	}

	public static void verifyBrokenImages() {
		try {
			int iBrokenImageCount = 0;
			List<WebElement> image_list = driver.findElements(By.xpath("//div[@class='inventory_item_img']//img"));
			System.out.println("The page under test has " + image_list.size() + " images");
			for (WebElement img : image_list) {
				if (img != null) {
					String src = img.getDomProperty("src");
					if (isImageBroken(src) || (img.getDomProperty("naturalWidth").equals("0")
							&& img.getDomProperty("naturalHeight").equals("0"))) {
						System.err.println(img.getDomProperty("outerHTML") + " is broken.");
						iBrokenImageCount++;
					}
				}
			}
			System.out.println("The page has " + iBrokenImageCount + " broken images");
		} catch (Exception e) {
			System.err.println("Test is failed due to : " + e.getMessage());
		}
	}

	private static boolean isImageBroken(String imageUrl) {
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(imageUrl).openConnection();
			connection.setRequestMethod("HEAD");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.connect();

			int responseCode = connection.getResponseCode();
			return responseCode == 404 || responseCode >= 400;
		} catch (IOException e) {
			return true;
		}
	}

}
