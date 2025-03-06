package supporter;

import org.openqa.selenium.WebDriver;

public class Baseclass 
{
	public static WebDriver driver;
	public static boolean bResult;
	
	public Baseclass(WebDriver driver) 
	{
		Baseclass.driver = driver;
		Baseclass.bResult = true;
	}
}