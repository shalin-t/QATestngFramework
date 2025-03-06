package pageObject;

import java.io.IOException;
import java.net.HttpURLConnection;

//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.HttpClientBuilder;

import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import supporter.Baseclass;
import supporter.GenericMethod;

public class PageUtils extends Baseclass {
	static WebElement element;

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

	public static void verifyBrokenLinks() {
		// Finding all the available links on webpage
		List<WebElement> links = driver.findElements(By.tagName("a"));

		// Iterating each link and checking the response status
		for (WebElement link : links) {
//		String url = link.getAttribute("href");
			String url = link.getDomAttribute("href");
			verifyLink(url);
		}

	}

	public static void verifyLink(String url) {
		try {
			URL link = new URL(url);
			HttpURLConnection httpURLConnection = (HttpURLConnection) link.openConnection();
			httpURLConnection.setConnectTimeout(3000); // Set connection timeout to 3 seconds
			httpURLConnection.connect();

			if (httpURLConnection.getResponseCode() == 200) {
				System.out.println(url + " - " + httpURLConnection.getResponseMessage());
			} else {
				System.err.println(url + " - " + httpURLConnection.getResponseMessage() + " - " + "is a broken link");
			}
		} catch (Exception e) {
			System.out.println(url + " - " + "is a broken link");
		}
	}


	public static void verifyBrokenImages() {
        try {
            int iBrokenImageCount = 0;
            List<WebElement> image_list = driver.findElements(By.xpath("//div[@class='inventory_item_img']//img"));
            System.out.println("The page under test has " + image_list.size() + " images");
            for (WebElement img : image_list) {
                if (img != null) {
                    String src = img.getDomProperty("src");
                    if (isImageBroken(src) || (img.getDomProperty("naturalWidth").equals("0") && img.getDomProperty("naturalHeight").equals("0"))) {
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
