package com.selenium;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class SeleniumAutomationTest {

	public static void main(String[] args) throws Exception {
		System.setProperty("webdriver.chrome.driver", "BrowserExe/chromedriver");
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
//		Target Application URL : http://automationpractice.com/index.php
//			 
//			Pre- requisite : Sign up into the application
//			 
//			Steps to be Automated:
//			 
//			1. Login into the application
//			2. Navigate to 'My Addresses' and add a new address
//			3. Fill all the information(not only the mandatory)
//			4. Click 'Save'
//			5. Navigate to 'Women' --> Summer dresses
//			6. The Items would be in 'Grid view'. Change it to 'List View'.
//			7. Click on the first item to view.
//			8. Increase the quantity to 5
//			9. Change the size to 'L'
//			10.Select any colour. 
//			11.Add to cart
//			12.Click 'Continue shopping' and repeat the same for the other 2 items as well under the summer dresses.
//			13. Proceed to checkout and complete the payment
//			14. Move to your profile and check 'order history and details'
//			15. Capture a screenshot of the order history
//			16. Sign out from the application
		
//		1. Login into the application
		driver.get("http://automationpractice.com/index.php");
		driver.findElement(By.xpath("//a[@class='login']")).click();
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("shakya.dummy@gmail.com");
		driver.findElement(By.id("passwd")).sendKeys("P@ssw0rd");
		driver.findElement(By.id("SubmitLogin")).click();
		
//		2. Navigate to 'My Addresses' and add a new address
		driver.findElement(By.xpath("//span[contains(text(),'My addresses')]")).click();		
		driver.findElement(By.xpath("//span[contains(text(),'Add a new address')]")).click();		
		
//		3. Fill all the information(not only the mandatory)
		driver.findElement(By.id("firstname")).sendKeys("Dummy");
		driver.findElement(By.id("lastname")).sendKeys("Shakya");
		driver.findElement(By.id("company")).sendKeys("XYZ");
		driver.findElement(By.id("address1")).sendKeys("123 Main St");
		driver.findElement(By.id("address2")).sendKeys("Suite 123");
		driver.findElement(By.id("city")).sendKeys("New York");		
		
		WebElement stateElement = driver.findElement(By.id("id_state"));
		Select stateDropDown = new Select(stateElement);
		stateDropDown.selectByVisibleText("New York"); //value 32
		
		driver.findElement(By.id("postcode")).sendKeys("10001");
		
		WebElement countryElement = driver.findElement(By.id("id_country"));
		Select countryDropDown = new Select(countryElement);
		countryDropDown.selectByVisibleText("United States"); 
		
		driver.findElement(By.id("phone")).sendKeys("1234567890");
		driver.findElement(By.id("phone_mobile")).sendKeys("1234567890");
		driver.findElement(By.xpath("//textarea[@id='other']")).sendKeys("TEST1234");
		driver.findElement(By.id("alias")).sendKeys("HOME");
		
//		4. Click 'Save'
		driver.findElement(By.id("submitAddress")).click();
		
		//repeat the shopping for 3 times
		for(int shoppingCount=1;shoppingCount<=3;shoppingCount++){
			shopping(driver, shoppingCount);
		}

//		13. Proceed to checkout and complete the payment
		driver.findElement(By.xpath("//a[@class='button btn btn-default standard-checkout button-medium']//span[contains(text(),'Proceed to checkout')] ")).click();
		driver.findElement(By.xpath("//button[@name='processAddress']//span[contains(text(),'Proceed to checkout')]")).click();
		driver.findElement(By.xpath("//input[@name='cgv']")).click();
		driver.findElement(By.xpath("//button[@name='processCarrier']//span[contains(text(),'Proceed to checkout')]")).click();
		
		driver.findElement(By.xpath("//a[@class='cheque']")).click();
		driver.findElement(By.xpath("//span[contains(text(),'I confirm my order')]")).click();
		
		//confirm that order is complete
		if(driver.findElement(By.xpath("//p[@class='alert alert-success']")).getText().contains("Your order on My Store is complete")){
			System.out.println("ORDER IS COMPLETED SUCCESSSFULLY");
		}
		else{
			driver.close();
			throw new Exception("ORDER IS NOT COMPLETED - PLEASE REVIEW THE WHOLE PROCESS");
		}
		
		
//		14. Move to your profile and check 'order history and details'
		driver.findElement(By.xpath("//a[@class='account']")).click();
		driver.findElement(By.xpath("//span[contains(text(),'Order history and details')]")).click();
		
//		15. Capture a screenshot of the order history
		takeSnapShot(driver, "screenshots/order.jpg");
		
//		16. Sign out from the application
		driver.findElement(By.xpath("//a[@class='logout']")).click();
		
		Thread.sleep(5000);
		driver.close();

	}
	
	public static void shopping(WebDriver driver, int shoppingCount){
//			5. Navigate to 'Women' --> Summer dresses
		WebElement womenElem = driver.findElement(By.xpath("//a[@class='sf-with-ul'][contains(text(),'Women')]"));
		Actions actions = new Actions(driver);
		actions.moveToElement(womenElem).build().perform();
		driver.findElement(By.xpath("//li[@class='sfHover']//a[contains(text(),'Summer Dresses')]")).click();

//			6. The Items would be in 'Grid view'. Change it to 'List View'.
		String isSelectedattribute = driver.findElement(By.xpath("//a[contains(text(),'Grid')]")).getAttribute("class");
		if(isSelectedattribute.equals("selected")){
			driver.findElement(By.xpath("//a[contains(text(),'List')]")).click();
		}

//			7. Click on the first item to view.
		List<WebElement> imageElements = driver.findElements(By.xpath("//a[@class='product-name']"));
		WebElement firstImageElement = imageElements.get(0);
		firstImageElement.click();
		
//			8. Increase the quantity to 5
		WebElement plusElement = driver.findElement(By.xpath("//i[@class='icon-plus']"));
		for(int times=1;times<5;times++){
			plusElement.click();
		}
		
		//OR Simply we can do right??
		//driver.findElement(By.id("quantity_wanted")).sendKeys("5");
		
//			9. Change the size to 'L'
		WebElement sizeElement = driver.findElement(By.id("group_1"));
		Select sizeDropDown = new Select(sizeElement);
		sizeDropDown.selectByVisibleText("L");
		
//			10.Select any colour. 
		driver.findElement(By.xpath("//a[@class='color_pick']")).click();
		
//			11.Add to cart
		driver.findElement(By.xpath("//span[contains(text(),'Add to cart')]")).click();
		
//			12.Click 'Continue shopping' and repeat the same for the other 2 items as well under the summer dresses.
		if(shoppingCount == 3){
			driver.findElement(By.xpath("//span[contains(text(),'Proceed to checkout')]")).click();
		}
		else{
			driver.findElement(By.xpath("//span[@class='continue btn btn-default button exclusive-medium']")).click();
		}		
	}
	
	public static void takeSnapShot(WebDriver driver,String fileWithPath) throws Exception{

	    //Convert web driver object to TakeScreenshot	
	    TakesScreenshot scrShot =((TakesScreenshot)driver);
	
	    //Call getScreenshotAs method to create image file	
	    File srcFile=scrShot.getScreenshotAs(OutputType.FILE);
	
	    //Move image file to new destination	
	    File destFile=new File(fileWithPath);
	
	    //Copy file at destination	
	    FileUtils.copyFile(srcFile, destFile);

    }

}
