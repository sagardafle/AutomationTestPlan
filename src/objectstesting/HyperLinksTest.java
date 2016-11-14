/**
 * 
 */
package objectstesting;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLException;

import java.net.URL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
public class HyperLinksTest {
	WebDriver driver;
	LandingPage landingpageobj;

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
	public void hyperLinksTest() throws InterruptedException, IOException {
		landingpageobj = new LandingPage(driver);
		landingpageobj.clickloginurl();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(landingpageobj.getLoginemailinput()).sendKeys(Keys.chord(Keys.CONTROL, "a"),
				"vikas.dafle@gmail.com");
		driver.findElement(landingpageobj.getLoginpasswordinput()).sendKeys(Keys.chord(Keys.CONTROL, "a"),
				"VikasDafle1");
		driver.findElement(landingpageobj.getLoginbtn()).click();
		Thread.sleep(5000);
		List<WebElement> hyperlinksList = landingpageobj.getHyperlinkList();
		Iterator<WebElement> it = hyperlinksList.iterator();
		System.out.println("========== HyperLinks list =========== ");
		List<String> hyperlinks = new ArrayList<String>();
		while (it.hasNext()) {
			String url = it.next().getAttribute("href");
			// System.out.println(url);
			if (url != null) {
				hyperlinks.add(url);
			}
			// verifyBrokenURL(url);
		}
		hyperlinks.removeAll(Collections.singleton(null));
		hyperlinks.removeIf(Objects::isNull);
		System.out.println(hyperlinks);
		Iterator linksiterator = hyperlinks.iterator();
		while (linksiterator.hasNext()) {
			// System.out.println(linksiterator.next());
			String url = linksiterator.next().toString();
			if (url.length() > 0) {
				//System.out.println(url);
				verifyBrokenURL(url);
			}

		}
	}

	private void verifyBrokenURL(String url) throws IOException {
		String response = "";
		if (validateURL(url)) {
			if (getHTTPResponseCode(url) < 400) {
				System.out.println("Valid URL ==> " + url);
				assertTrue(getHTTPResponseCode(url) < 400);
			} else {
				System.out.println("Broken URL ==>" +url+" Error code: "+ getHTTPResponseCode(url));
				assertTrue(getHTTPResponseCode(url) >= 400);
			}
		}
	}

	// checks if the URL has http keyword
	private Boolean validateURL(String href) {
		return href.indexOf("http") >= 0;
	}

	//Gets the HTTP Response Code for each URL
	private int getHTTPResponseCode(String url) throws IOException {
		int responseCode = 0;
		try {
			HttpURLConnection httpConnection = makeConnection(url);
			httpConnection.connect();
			responseCode = httpConnection.getResponseCode();
		} catch (ProtocolException | SSLException exception) {
			System.out.println("could not open " + url);
		}
		return responseCode;
	}

	// create the httpConnection object
	private HttpURLConnection makeConnection(String href) throws IOException {
		URL url = new URL(href);
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		huc.setRequestMethod("GET");
		return huc;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		landingpageobj.close(driver);
	}

}
