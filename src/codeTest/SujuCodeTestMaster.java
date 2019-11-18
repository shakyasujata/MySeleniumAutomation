package codeTest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SujuCodeTestMaster {

	public static void main(String[] args) throws Exception {
		
		System.setProperty("webdriver.chrome.driver", "browserExe/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("http://automationpractice.com/index.php");
		
		Thread.sleep(3000);
		driver.close();		
	}

}
