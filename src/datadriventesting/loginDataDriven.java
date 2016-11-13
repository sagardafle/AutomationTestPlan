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
		driver.get("https://www.pizzahut.com/");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void loginUsersTest() throws IOException {
		String errormsg = null ;
		landingpageobj = new LandingPage(driver);
		landingpageobj.clickloginurl();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		Sheet loginSheet = landingpageobj.loadExcelSheet("loginData");
		Iterator<Row> iterator = loginSheet.iterator();
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				int columnIndex = cell.getColumnIndex();
				//System.out.println("columnIndex" +columnIndex);
				switch (columnIndex) {
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
			driver.findElement(landingpageobj.getLoginbtn()).click();
			if (driver.findElements(landingpageobj.accepterror).size() > 0) {
				errormsg = landingpageobj.getErrorMessage();
				driver.findElement(landingpageobj.accepterror).click();
//				System.out.println("Clicked error OK");
			}
			System.out.print(" - ");

			if (driver.findElements(landingpageobj.loginsuccess).size() > 0) {
				Boolean loginsuccess = driver.getPageSource().contains("Welcome,");
				Assert.assertTrue(loginsuccess);
				System.out.println("Login successsful");
				break;
//				landingpageobj.clicklogouturl();
			}	
			 else {
				System.out.println("Login Error: "+errormsg);
			}
		}
		System.out.println();

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
