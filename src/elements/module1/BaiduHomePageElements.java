package elements.module1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.BaseFunction;

public class BaiduHomePageElements extends BaseFunction{
	private WebElement tieba;
	
	public WebElement getTieba() {
		tieba = driver.findElement(By.name("tj_trtieba"));
		return tieba;
	}
}
