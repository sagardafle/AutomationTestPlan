/**
 * 
 */
package datadriventesting;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author Sagar
 *
 */

public class loginDataDriven {
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
		driver.get("https://www.pizzahut.com/"); //opens the PizzaHut landing page
	}

	@SuppressWarnings("deprecation")
	@Test
	public void loginUsersTest() throws IOException {
		String errormsg = null ;
		landingpageobj = new LandingPage(driver);
		landingpageobj.clickloginurl(); // clicks the "sign-in" button
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		Sheet loginSheet = landingpageobj.loadExcelSheet("loginData"); //opens the sheet with specified name
		Iterator<Row> iterator = loginSheet.iterator(); //iterates over the row-column pairs
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				int columnIndex = cell.getColumnIndex();
				switch (columnIndex) {  //use of conditional looping to access email-id and password
				case 0:
					driver.findElement(landingpageobj.getLoginemailinput()).sendKeys(Keys.chord(Keys.CONTROL, "a"),
							cell.getStringCellValue());
					break;
				case 1:
					driver.findElement(landingpageobj.getLoginpasswordinput()).sendKeys(Keys.chord(Keys.CONTROL, "a"),
							cell.getStringCellValue());
					break;
				}
			}
			
			//clicks the login button
			driver.findElement(landingpageobj.getLoginbtn()).click();
			
			//clicks the error button message incase the error is generated.
			if (driver.findElements(landingpageobj.accepterror).size() > 0) {
				errormsg = landingpageobj.getErrorMessage();
				driver.findElement(landingpageobj.accepterror).click();
			}
			System.out.print(" - "); //seprator

			//Checks if login was successful
			if (driver.findElements(landingpageobj.loginsuccess).size() > 0) {
				Boolean loginsuccess = driver.getPageSource().contains("Welcome,");
				Assert.assertTrue(loginsuccess);
				System.out.println("Login successsful");
				break;
			}	
			 else { //displays the respective error message.
				System.out.println("Login Error: "+errormsg);
			}
		}
		System.out.println();
		
		//closes the workbook file
		landingpageobj.closeWorkBook();
		landingpageobj.closeFile();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		landingpageobj.close(driver);
	}

}
