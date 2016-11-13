/**
 * 
 */
package objectstesting;

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
public class PageHeaderTest {
	WebDriver driver;
	LandingPage landingpageobj;
	
	By pizzamenu = By.cssSelector("a[id='md-nav-pizza']");
	By sidesmenu = By.cssSelector("a[id='md-nav-sides']");
	By drinksmenu = By.cssSelector("a[id='md-nav-drinks']");
	By dealssmenu = By.cssSelector("a[id='md-nav-deals']");


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
	public void pageHeaderTest() throws InterruptedException {
		landingpageobj = new LandingPage(driver);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		Thread.sleep(5000);
		/**
		 * 1. Check for Pizza menu
		 */
		driver.findElement(pizzamenu).click();
		Thread.sleep(5000);
		Assert.assertTrue((landingpageobj.getPageHeader().contains("pizza")));
		/**
		 * 2. Check for Sides menu
		 */
		driver.findElement(sidesmenu).click();
		Thread.sleep(5000);
		Assert.assertTrue((landingpageobj.getPageHeader().contains("sides")));
		
		/**
		 * 3. Check for Drinks menu
		 */
		driver.findElement(drinksmenu).click();
		Thread.sleep(5000);
		Assert.assertTrue((landingpageobj.getPageHeader().contains("drinks")));
		
		/**
		 * 4. Check for Deals menu
		 */
		driver.findElement(dealssmenu).click();
		Thread.sleep(5000);
		Assert.assertTrue((landingpageobj.getPageHeader().contains("deals")));
	}
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		landingpageobj.close(driver);
	}


}
