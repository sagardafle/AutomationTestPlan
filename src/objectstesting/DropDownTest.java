/**
 * 
 */
package objectstesting;
import datadriventesting.*;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import datadriventesting.LandingPage;
import junit.framework.Assert;

/**
 * @author Sagar
 *
 */
public class DropDownTest {
	LandingPage landingpageobj;
	WebDriver driver;
	
	By moredropdown = By.cssSelector("a[id='lg-nav-more']");
	By desertdropdown = By.cssSelector("a[class='#/menu/desserts']");
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.pizzahut.com/");
	}
	
	@Test
	public void dropdownTest() {
		landingpageobj = new LandingPage(driver);
		landingpageobj.clickloginurl();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(landingpageobj.getLoginemailinput()).sendKeys(Keys.chord(Keys.CONTROL, "a"),
				"vikas.dafle@gmail.com");
		driver.findElement(landingpageobj.getLoginpasswordinput()).sendKeys(Keys.chord(Keys.CONTROL, "a"),
				"VikasDafle1");
		driver.findElement(landingpageobj.getLoginbtn()).click();
		if(driver.findElements(moredropdown).size() > 0){
			Assert.assertTrue(driver.findElements(moredropdown).size() > 0);
			System.out.println("Dropdowns present");
		} else {
			System.out.println("Dropdowns absent");
		}
		
	}


	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		driver.close();
	}

	
}
