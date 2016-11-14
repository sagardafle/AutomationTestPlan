/**
 * 
 */
package objectstesting;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import datadriventesting.LandingPage;
import junit.framework.Assert;

/**
 * @author Sagar
 *
 */
public class ImagesTest {
	WebDriver driver;
	LandingPage landingpageobj;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// super.setUp();
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.pizzahut.com/");
	}

	@Test
	public void imagesTest() throws InterruptedException {
		landingpageobj = new LandingPage(driver);
		landingpageobj.clickloginurl();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(landingpageobj.getLoginemailinput()).sendKeys(Keys.chord(Keys.CONTROL, "a"),
				"vikas.dafle@gmail.com");
		driver.findElement(landingpageobj.getLoginpasswordinput()).sendKeys(Keys.chord(Keys.CONTROL, "a"),
				"VikasDafle1");
		driver.findElement(landingpageobj.getLoginbtn()).click();
		Thread.sleep(5000);
		List<WebElement> imagesList = landingpageobj.getImagesList();
		Iterator<WebElement> it = imagesList.iterator();
		System.out.println("========== Images list =========== ");
		while (it.hasNext()) {
			String imagesrc = it.next().getAttribute("src");
			System.out.print(imagesrc+ " ==> ");
			Boolean ImagePresent = (Boolean) ((JavascriptExecutor) driver).executeScript(
					"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
					it.next());
			if (!ImagePresent) {
				System.out.println("Image not displayed.");
			} else {
				System.out.println("Image displayed.");
			}
		}

		Assert.assertTrue(imagesList.size() > 0);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		landingpageobj.close(driver);
	}

}
