package specifications;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;

import core.Application;

// TODO: Auto-generated Javadoc
/**
 * The Class Webdata.
 */
public class Webdata {

	/**
	 * Execute.
	 *
	 * @param url the url
	 * @param id1 the id 1
	 * @param id2 the id 2
	 */
	public void execute(String url,String id1, String id2 ) {
		WebDriver driver;

		Wait<WebDriver> wait;
		driver = new ChromeDriver();
		driver.get(url);
		if (id1!=null)
		{
			Application.webdata=driver.findElement(By.name(id1)).getAttribute("value");
		}
		if (id2!=null)
		{
			Application.webdata1=driver.findElement(By.name(id2)).getAttribute("value");
		}
		driver.close();
		driver.quit();
	}
}


