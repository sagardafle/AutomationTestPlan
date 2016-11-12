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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import junit.framework.Assert;

/**
 * @author Sagar
 *
 */
public class registrationDataDriven {
	WebDriver driver;
	LandingPage landingpageobj;
	private boolean clickRadioButtons = false;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.pizzahut.com/");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void registerUsersTest() throws InterruptedException, IOException {
		landingpageobj = new LandingPage(driver);
		landingpageobj.clickRegistrationurl();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		Sheet registrationSheet = landingpageobj.loadExcelSheet("registrationdata");
		Iterator<Row> iterator = registrationSheet.iterator();
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				int columnIndex = cell.getColumnIndex();
				switch (columnIndex) {
				case 0:
					clickRadioButtons = false;
					driver.findElement(landingpageobj.signupfirstNameinput).sendKeys(Keys.chord(Keys.CONTROL, "a"),
							cell.getStringCellValue());
					break;
				case 1:
					driver.findElement(landingpageobj.signuplastNameinput).sendKeys(Keys.chord(Keys.CONTROL, "a"),
							cell.getStringCellValue());
					break;
				case 2:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					driver.findElement(landingpageobj.signupphoneNumberinput).sendKeys(Keys.chord(Keys.CONTROL, "a"),
							cell.getStringCellValue());
					break;
				case 3:
					driver.findElement(landingpageobj.signupeMailAddressinput).sendKeys(Keys.chord(Keys.CONTROL, "a"),
							cell.getStringCellValue());
					break;
				case 4:
					driver.findElement(landingpageobj.signupconfirmEmailinput).sendKeys(Keys.chord(Keys.CONTROL, "a"),
							cell.getStringCellValue());
					break;
				case 5:
					driver.findElement(landingpageobj.signuppasswordinput).sendKeys(Keys.chord(Keys.CONTROL, "a"),
							cell.getStringCellValue());
					break;
				case 6:
					driver.findElement(landingpageobj.signupconfirmPasswordinput)
							.sendKeys(Keys.chord(Keys.CONTROL, "a"), cell.getStringCellValue());
					clickRadioButtons = true;
					break;
				}
				if (clickRadioButtons) {
					// driver.findElement(landingpageobj.signupextinput).sendKeys("");

					if (!driver.findElement(landingpageobj.signupnoThanksinput).isSelected()) {
						driver.findElement(landingpageobj.signupnoThanksinput).click();
					}

					if (!driver.findElement(landingpageobj.signuptermsinput).isSelected()) {
						driver.findElement(landingpageobj.signuptermsinput).click();
					}

					if (!driver.findElement(landingpageobj.signupcertifyinput).isSelected()) {
						driver.findElement(landingpageobj.signupcertifyinput).click();
					}
					driver.findElement(landingpageobj.createUserbtn).click();
				}
			}
			if (driver.findElements(landingpageobj.accepterror).size() > 0) {
				driver.findElement(landingpageobj.accepterror).click();
			}

			System.out.print(" - ");
			try {
				Assert.assertEquals("https://www.pizzahut.com/#/dashboard", driver.getCurrentUrl());
				System.out.println("Registration successful");
			} catch (AssertionError e) {
				String errormsg = landingpageobj.getErrorMessage();
				System.out.println("Registration Error: " + errormsg);
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
		driver.close();
	}

}
