package elements.module2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.BaseFunction;

public class BaiduTiebaPageElements extends BaseFunction{

	private WebElement searchButton;
	private By searchButtonLocator;
	
	public WebElement getSearchButton() {
		searchButton = driver.findElement(By.xpath("//form[@name=\"f1\"]//span[1]//a"));
		return searchButton;
	}
	public By getSearchButtonLocator() {
		searchButtonLocator = By.xpath("//form[@name=\"f1\"]//span[1]//a");
		return searchButtonLocator;
	}
}
