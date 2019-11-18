package codeTest;

import java.io.File;
import java.io.IOException;
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

public class SujuCodeTest {

	public static void main(String[] args) throws Exception {
		
		System.setProperty("webdriver.chrome.driver", "browserExe/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		driver.get("http://automationpractice.com/index.php");
		
//		1. Login into the application
		driver.findElement(By.xpath("//a[@class='login']")).click();
		driver.findElement(By.id("email")).sendKeys("indra.shobha.shakya@gmail.com");
		driver.findElement(By.id("passwd")).sendKeys("I<3nepal");
		driver.findElement(By.id("SubmitLogin")).click();
		
//		2. Navigate to 'My Addresses' and add a new address
		driver.findElement(By.xpath("//span[contains(text(),'My addresses')]")).click();
		driver.findElement(By.xpath("//span[contains(text(),'Add a new address')]")).click();
		
//		3. Fill all the information(not only the mandatory)
		driver.findElement(By.id("firstname")).sendKeys("Indra");
		driver.findElement(By.id("lastname")).sendKeys("Shakya");
		driver.findElement(By.id("company")).sendKeys("XYZ");
		driver.findElement(By.id("address1")).sendKeys("123 main street");
		driver.findElement(By.id("address2")).sendKeys("apt1");
		driver.findElement(By.id("city")).sendKeys("New York");
		
		WebElement stateElement = driver.findElement(By.id("id_state"));
		Select stateSelect = new Select(stateElement);
		stateSelect.selectByVisibleText("New York");
		
		driver.findElement(By.id("postcode")).sendKeys("10001");
		
		WebElement countryElement = driver.findElement(By.id("id_country"));
		Select countrySelect = new Select(countryElement);
		countrySelect.selectByVisibleText("United States");
		
		driver.findElement(By.id("phone")).sendKeys("4145555555");
		driver.findElement(By.id("phone_mobile")).sendKeys("4785555555");
		driver.findElement(By.id("other")).sendKeys("test");
		driver.findElement(By.id("alias")).sendKeys("Address");
		
		
//		4. Click 'Save'
		driver.findElement(By.id("submitAddress")).click();
		
		for(int shoppingCount=1;shoppingCount<=3;shoppingCount++){
			shopping(driver, shoppingCount);
		}
        				
		
//		13. Proceed to checkout and complete the payment
		driver.findElement(By.xpath("//a[@class='button btn btn-default standard-checkout button-medium']//span[contains(text(),'Proceed to checkout')]")).click();
		driver.findElement(By.xpath("//button[@name='processAddress']//span[contains(text(),'Proceed to checkout')]")).click();
		driver.findElement(By.id("cgv")).click();
		driver.findElement(By.xpath("//button[@name='processCarrier']//span[contains(text(),'Proceed to checkout')]")).click();
		driver.findElement(By.xpath("//a[@class='cheque']//span[contains(text(),'(order processing will be longer)')]")).click();
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
		driver.findElement(By.xpath("//span[contains(text(),'Indra Shakya')]")).click();
		driver.findElement(By.xpath("//span[contains(text(),'Order history and details')]")).click();
		
//		15. Capture a screenshot of the order history
		
		takeScreenshot(driver, "/Users/suru/Sujata Important Document/H2K_Automation_Shantosh/workspace_neon/selenium_automation_test_imtiaz/SujataScreenShot/order.jpg");
		
		
//		16. Sign out from the application
		driver.findElement(By.xpath("//a[@class='logout']")).click();
		
		
		Thread.sleep(5000);
		driver.close();		
	}

	private static void takeScreenshot(WebDriver driver, String fileName) throws IOException {
		//Convert web driver object to TakeScreenshot
		
	    TakesScreenshot scrShot =((TakesScreenshot)driver);
	
	    //Call getScreenshotAs method to create image file
	
	    File srcFile=scrShot.getScreenshotAs(OutputType.FILE);
	
	    //Move image file to new destination
	
	    File destFile=new File(fileName);
	
	    //Copy file at destination
	
	    FileUtils.copyFile(srcFile, destFile);
	}

	private static void shopping(WebDriver driver, int shoppingCount) {
//		5. Navigate to 'Women' --> Summer dresses
		WebElement womenElem = driver.findElement(By.xpath("//a[@class='sf-with-ul'][contains(text(),'Women')]"));
		Actions actions = new Actions(driver);
		actions.moveToElement(womenElem).build().perform();
		driver.findElement(By.xpath("//li[@class='sfHover']//a[contains(text(),'Summer Dresses')]")).click();
		
		
//		6. The Items would be in 'Grid view'. Change it to 'List View'.
		WebElement gridElement = driver.findElement(By.xpath("//li[@id='grid']"));
		String isGridSelected = gridElement.getAttribute("class");
		
		if (isGridSelected.equals("selected"))
		{
			driver.findElement(By.xpath("//li[@id='list']")).click();;
		}
		
		
//		7. Click on the first item to view.
		List<WebElement> imageElements = driver.findElements(By.xpath("//a[@class='product-name']"));
		WebElement firstImageElement = imageElements.get(0);
		firstImageElement.click();
		
		
		
//		8. Increase the quantity to 5
		driver.findElement(By.id("quantity_wanted")).sendKeys("5");
		
		
//		9. Change the size to 'L'
		WebElement sizeElement = driver.findElement(By.id("group_1"));
		Select sizeSelect = new Select(sizeElement);
		sizeSelect.selectByVisibleText("L");
		
//		10.Select any colour. 
		driver.findElement(By.xpath("//a[@class='color_pick selected']")).click();
				
//		11.Add to cart
		driver.findElement(By.xpath("//button[@name='Submit']")).click();
		
//		      12.Click 'Continue shopping' and repeat the same for the other 2 items as well under the summer dresses.
		
		if(shoppingCount==3){
			driver.findElement(By.xpath("//span[contains(text(),'Proceed to checkout')]")).click();
		}
		else{
			driver.findElement(By.xpath("//span[@class='continue btn btn-default button exclusive-medium']")).click();
		}				
	}

}
