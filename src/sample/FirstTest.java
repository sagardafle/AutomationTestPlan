package sample;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirstTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		// Create a new instance of the Firefox driver
				
				WebDriver driver;
				System.setProperty("webdriver.gecko.driver", "C:\\Users\\Sagar\\Downloads\\geckodriver-v0.11.1-win64\\geckodriver.exe");
				driver =new FirefoxDriver();
				
		        //Launch the Online Store Website
				driver.get("http://www.geeksforgeeks.org/");
		 
		        // Print a Log In message to the screen
		        System.out.println("Successfully opened the website www.Store.Demoqa.com");
		 
				//Wait for 5 Sec
				Thread.sleep(5);
				
		        // Close the driver
		        driver.quit();

	}

}
