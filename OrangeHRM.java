package Automotation;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;


public class OrangeHRM {
	public String baseUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
	public WebDriver driver;

	@BeforeTest
	public void setup()
	{
		System.out.println("Before Test executed");
		driver=new ChromeDriver();
		//maximise windows
		driver.manage().window().maximize();

		//open url
		driver.get(baseUrl);

		//timer 
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

	}
	@Test(priority = 1 , enabled=false)
	public void dologinTestwithinvalidcredentials() throws InterruptedException // with invalid credential
	{
		//find and entered valid username which is "Admin"
		driver.findElement(By.xpath("//input[@placeholder=\"Username\"]")).sendKeys("Admin");

		// find and entered invalid password which is "abc"
		driver.findElement(By.xpath("//input[@placeholder=\"Password\"]")).sendKeys("abc"); //wrong password

		//login button click
		driver.findElement(By.xpath("//button[@type=\"submit\"]")).submit();

		String message_expected ="Invalid credentials";

		String message_actual = driver.findElement(By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")).getText();

		//Assert.assertTrue(message_actual.contains(message_expected));
		Assert.assertEquals(message_expected, message_actual);

		Thread.sleep(5000); //to wait 


	}
	@Test(priority = 2) //runs after the valid credential
	public void loginTestwithvalidcredentials() //valid credential
	{
		//find and entered username which is "Admin"
		driver.findElement(By.xpath("//input[@placeholder=\"Username\"]")).sendKeys("Admin");

		// find and entered password which is "admin123"
		driver.findElement(By.xpath("//input[@placeholder=\"Password\"]")).sendKeys("admin123");

		//login button click
		driver.findElement(By.xpath("//button[@type=\"submit\"]")).submit();

		//verify if the login is successfull 
		String pageTitle = driver.getTitle();

		/* if (pageTitle.equals("OrangeHRM")) {
			System.out.println("Login Sucessful!");
		}
		else {
			System.out.println("Login failed!"); 
		} */


		Assert.assertEquals("OrangeHRM", pageTitle);

	}
	@Test(priority =3 , enabled=false)
	public void addEmployee() throws InterruptedException 
	{

		//Click on PIM menu
		driver.findElement(By.xpath("//span[text()='PIM']")).click();

		//Navigate Add employee and click add employee option
		driver.findElement(By.xpath("//a[text()='Add Employee']")).click();

		// Enter the first name
		driver.findElement(By.xpath("//input[@placeholder='First Name']")).sendKeys("Pushpa");

		// Enter the middle name
		driver.findElement(By.xpath("//input[@placeholder='Middle Name']")).sendKeys("Kamal");

		//Enter the Last name
		driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys("Dahal");
		Thread.sleep(5000);

		//click on save button
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		//verify if the employee is successfully add in employee list
		String confirmationMessage = driver.findElement(By.xpath("//h6[normalize-space()='Personal Details']")).getText();
		if (confirmationMessage.contains("Personal Details")) {
			System.out.println("Employee added Sucessfully!");
		} else {
			System.out.println("Failed to add Employee!");
		}
		Assert.assertEquals("Personal Details", confirmationMessage);
		

	}
	@Test(priority = 4 , enabled=false)
	public void searchEmployeeByname() throws InterruptedException  //By name 
	{
		//Click on PIM menu
		driver.findElement(By.xpath("//span[text()='PIM']")).click();

		// select employee list
		driver.findElement(By.xpath("//a[text()='Employee List']")).click();

		// search employee by name
		driver.findElements(By.tagName("input")).get(1).sendKeys("Pushpa");

		//click the search button    //button[normalize-space()='Search']  //normalize-space removes the wide spaces
		driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();

             Thread.sleep(3000) ;
		List<WebElement> element= driver.findElements(By.xpath("//span[@class='oxd-text oxd-text--span']"));

		String expected_message = "Record Found";
		String message_actual = element.get(0).getText();
		System.out.println(message_actual);

		
		Assert.assertTrue(message_actual.contains(expected_message));

		/* for (int i=0; i<element.size(); i++) //To check the correct element
	   {
		 System.out.println("At index" + i + "text is :" + element.get(i).getText());
	   }*/

	}
	@Test(priority=5 , enabled=false)
	public void searchEmployeeById() 
	{
		 String empId= "0372";
		 String message_actual="";
		//Click on PIM menu
		driver.findElement(By.xpath("//span[text()='PIM']")).click();

		// select employee list
		driver.findElement(By.xpath("//a[text()='Employee List']")).click();
		 
		 //enter employee Id
		 driver.findElements(By.tagName("input")).get(2).sendKeys(empId);
		 
		//click the search button    //button[normalize-space()='Search']  //normalize-space removes the wide spaces
		driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();
				// ((//div[@role='row'])[2]/div[@role='cell'])[2]
		List<WebElement> rows = driver.findElements(By.xpath("(//div[@role='row'])"));
		if(rows.size()>1)
		{
			message_actual = driver.findElement(By.xpath("((//div[@role='row'])[2]/div[@role='cell'])[2]")).getText();
		}
		Assert.assertEquals(empId, message_actual);
	}
	
	
	@Test(priority = 6 , enabled=false)
	public void LeaveApply() throws InterruptedException
	{
		//click on leave menu
		driver.findElement(By.linkText("Leave")).click(); //link given to the sourcepath
		
		//click on apply 
		driver.findElement(By.linkText("Apply")).click(); // xpath a[normalize-space()='Apply']
		
		//click on leave type
		driver.findElement(By.xpath("//i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow']")).click();
		
		//Select CAN-FMLA //*[contains(text),'CAN')] (Option from the leave type dropdown)
		//driver.findElement(By.xpath("//*[contains(text),'CAN')]")).click();
		
		driver.findElement(By.xpath("//i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow'][1]")).click();		
		Thread.sleep(2000);
		
		//select or type leave date
		driver.findElement(By.xpath("(//div[@class='oxd-date-input'])/input")).sendKeys("2024-17-06");
		
		Thread.sleep(2000);
		
		//enter comment
		driver.findElement(By.tagName("textarea")).sendKeys("For personal Reasons");
		
		//click to apply button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		
		Thread.sleep(5000);		
	}
	@Test(priority = 7)
	public void DeleteEmployee()
	{
		//Click on PIM menu
		driver.findElement(By.xpath("//span[text()='PIM']")).click();
		
		// select employee list
		driver.findElement(By.xpath("//a[text()='Employee List']")).click();
		
		// search employee by name
		driver.findElements(By.tagName("input")).get(1).sendKeys("Amelia");
		
		//click search button
		driver.findElement(By.xpath("//button[normalize-space()='Search']")).click(); //normalize function removes the space
		
		//select delete button
		driver.findElement(By.xpath("//i[@class='oxd-icon bi-trash']")).click();
		
		//click the yes delete button
		driver.findElement(By.xpath("//button[@class='oxd-button oxd-button--medium oxd-button--label-danger orangehrm-button-margin']")).click();
		
		//check no record found "message"
		String msg =driver.findElement(By.xpath("(//span[@class='oxd-text oxd-text--span'])[1]")).getText();
		
		Assert.assertEquals(msg, "No Records Found");

	}
	
	

	public void logout() 
	{
		driver.findElement(By.xpath("//p[@class=\"oxd-userdropdown-name\"] ")).click();
		driver.findElement(By.xpath("//a[normalize-space()=\"Logout\"]")).click();
		List<WebElement> elementlist = driver.findElements(By.xpath("//a[@class='oxd-userdropdown-link']")); //found multiple elements

		/* for (int i=0; i<elementlist.size(); i++)
	   {
		   Thread.sleep(2000);
		   System.out.println(i + ":" + elementlist.get(i).getText());
	   }*/
		elementlist.get(3).click(); //logout button

	}


	@AfterTest
	public void tearDown() throws InterruptedException
	{
		logout();

		Thread.sleep(5000); //wait for 10 seconds before quit
		driver.close();
		driver.quit();
	}

}
